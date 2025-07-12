package com.kevinqiu.lotterysystem.service.activityStatus.impl;

import com.kevinqiu.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ServiceException;
import com.kevinqiu.lotterysystem.service.ActivityService;
import com.kevinqiu.lotterysystem.service.activityStatus.ActivityStatusManager;
import com.kevinqiu.lotterysystem.service.activityStatus.operator.AbstractActivityOperator;
import com.kevinqiu.lotterysystem.service.dto.ConvertActivityStatusDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Component
public class ActivityStatusManagerImpl implements ActivityStatusManager {

    @Autowired
    private ActivityService activityService;

    // 所以这构成了一个依赖关系，扭转活动需要在人员和奖品都扭转完毕后再做扭转
    // 为了便于代码的可维护性和可扩展性，采用责任链的设计模式明确之间的依赖关系
    // 为了使每个元素的扭转方式解耦，采用策略设计模式
    // 这里使用到了 Spring 的自动装配机制，
    // Spring 会把 IoC 仓库中的 AbstractActivityOperator的子类自动装配到 map 中
    @Autowired
    private final Map<String, AbstractActivityOperator> operatorMap = new HashMap<>();


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlerEvent(ConvertActivityStatusDTO convertActivityStatusDTO) {

        // 判断操作的 map 是否为空
        if (CollectionUtils.isEmpty(operatorMap)) {
            log.warn("Map<String, AbstractActivityOperator> operatorMap 为空！");
            return;
        }

        // 重新创建一个 map，以防止改动 operatorMap
        Map<String, AbstractActivityOperator> currOperatorMap = new HashMap<>(operatorMap);

        // 定义一个更新标签，以判断是否对元素进行了更新，是否需要更新缓存
        Boolean isUpdate = false;

        // 首先扭转人员和奖品
        isUpdate = processConvertActivityStatus(currOperatorMap, convertActivityStatusDTO, 1);


        // 最后扭转活动
        isUpdate = processConvertActivityStatus(currOperatorMap, convertActivityStatusDTO, 2) || isUpdate;

        // 更新缓存
        if (isUpdate) {
            activityService.cacheActivity(convertActivityStatusDTO.getActivityId());
        }

    }

    @Override
    public void rollBackHandlerEvent(ConvertActivityStatusDTO convertActivityStatusDTO) {

        if (null == convertActivityStatusDTO) {
            log.warn("所要回滚的 ConvertActivityStatusDTO 为空!");
            return;
        }

        // 回滚全部状态：活动、人员、奖品
        // 使用策略模式

        Map<String, AbstractActivityOperator> curMap = new HashMap<>(operatorMap);
        for (Map.Entry<String, AbstractActivityOperator> stringAbstractActivityOperatorEntry : curMap.entrySet()) {
            AbstractActivityOperator activityOperator = stringAbstractActivityOperatorEntry.getValue();
            activityOperator.convert(convertActivityStatusDTO);
        }

        // 更新缓存
        activityService.cacheActivity(convertActivityStatusDTO.getActivityId());

    }

    private Boolean processConvertActivityStatus(Map<String, AbstractActivityOperator> currOperatorMap,
                                                 ConvertActivityStatusDTO convertActivityStatusDTO,
                                                 int sequence) {

        boolean update = false;

        // 构造一个迭代器，用于遍历 Map 中的所有 entry
        Iterator<Map.Entry<String, AbstractActivityOperator>> mapIterator = currOperatorMap.entrySet().iterator();

        // 循环遍历 Map 的 entry 集合
        while (mapIterator.hasNext()) {

            // 获取策略对象，根据 sequence 和 needConvert 判定
            AbstractActivityOperator operator = mapIterator.next().getValue();
            if (operator.sequence() != sequence || !operator.needConvert(convertActivityStatusDTO)) {
                // 当前的操作器所在链路不符合 sequence 或者当前操作器不需要被扭转状态，跳过
                continue;
            }

            // 如果需要转换，执行转换
            if (!operator.convert(convertActivityStatusDTO)) {
                // 如果转换失败，抛异常
                log.error("{} 状态转换失败：", operator.getClass().getName());
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_STATUS_CONVERT_ERROR);
            }

            // 从 map 中删除此 operator
            mapIterator.remove();
            update = true;
        }

        return update;
    }
}

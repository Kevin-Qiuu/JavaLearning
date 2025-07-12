package com.kevinqiu.lotterysystem.service.activityStatus;

import com.kevinqiu.lotterysystem.service.dto.ConvertActivityStatusDTO;

public interface ActivityStatusManager {

    /**
     * 转换活动状态
     * @param convertActivityStatusDTO 转换的目标状态类
     */
    void handlerEvent(ConvertActivityStatusDTO convertActivityStatusDTO);

    /**
     * 回滚活动状态
     *
     * @param convertActivityStatusDTO
     */
    void rollBackHandlerEvent(ConvertActivityStatusDTO convertActivityStatusDTO);

}

package com.kevinqiu.lotterysystem.service.activityStatus.operator;

import com.kevinqiu.lotterysystem.service.dto.ConvertActivityStatusDTO;

public abstract class AbstractActivityOperator {

    /**
     * 获取操作器的执行顺序
     * @return 执行顺序的编号
     */
    public abstract Integer sequence();

    /**
     * 判断要操作的对象是否需要转换
     *
     * @param convertActivityStatusDTO
     * @return 判断结果
     */
    public abstract Boolean needConvert(ConvertActivityStatusDTO convertActivityStatusDTO);

    /**
     * 执行状态扭转的全部操作逻辑
     *
     * @param convertActivityStatusDTO
     * @return
     */
    public abstract Boolean convert(ConvertActivityStatusDTO convertActivityStatusDTO);

}

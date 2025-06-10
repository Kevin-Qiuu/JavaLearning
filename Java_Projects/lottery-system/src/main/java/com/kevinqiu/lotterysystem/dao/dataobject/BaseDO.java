package com.kevinqiu.lotterysystem.dao.dataobject;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class BaseDO implements Serializable {
    /**
     * 主键 ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private Data gmtCreate;

    /**
     * 修改时间
     */
    private Data gmtModified;

}

package com.kevinqiu.lotterysystem.dao.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserBaseInfoDO extends BaseDO{
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户身份
     */
    private String identity;
}

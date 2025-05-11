package com.kevinqiu.springBookDemo.model;

public enum BookStatus {
    DELETED(0, "无效"),
    NORMAL(1, "可借阅"),
    FORBIDDEN(2, "被借出");

    private Integer code;
    private String desc;

    BookStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static BookStatus getDescByCode(Integer code){
        return switch (code) {
            case 0 -> DELETED;
            case 1 -> NORMAL;
            case 2 -> FORBIDDEN;
            default -> null;
        };
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

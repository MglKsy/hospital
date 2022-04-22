package com.thylovezj.hospital.exception;

public enum ThylovezjHospitalExceptionEnum {
    REQUEST_PARAM_ERROR(10012, "参数错误"),
    SYSTEM_ERROR(20000, "系统异常"),
    NEED_PASSWORD(10013, "密码不能为空"),
    USERNAME_EXISTS(10014,"用户名重名"),
    INSERT_FAILED(10015,"插入失败"),
    ROLE_ERROR(10016,"用户权限错误"),
    NEED_USER_NAME(10001, "用户名不能为空");

    Integer code;
    String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ThylovezjHospitalExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

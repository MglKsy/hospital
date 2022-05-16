package com.thylovezj.hospital.exception;

public enum ThylovezjHospitalExceptionEnum {
    REQUEST_PARAM_ERROR(10012, "参数错误"),
    NOT_LOGIN(10010,"未登录"),
    SAVE_ERROR(10020,"保存失败"),
    LOGIN_STATUS_ERROR(10011,"登录有效期已过"),
    SYSTEM_ERROR(20000, "系统异常"),
    NEED_PASSWORD(10013, "密码不能为空"),
    USERNAME_EXISTS(10014,"用户名重名"),
    INSERT_FAILED(10015,"插入失败"),
    ROLE_ERROR(10016,"用户权限错误"),
    PROBLEM_NOT_ENOUGH(10017,"数据库问题不足"),
    BONUS_ADD_ERROR(10018,"积分添加过于频繁，请稍后添加"),
    NEED_USER_NAME(10001, "用户名不能为空"),
    SIGN_ERROR(100022,"签到失败"),
    UPLOAD_SONG_FAILED(100023,"添加音乐失败"),
    REPEAT_ADD_SONG(100024,"音乐已经添加"),
    ALREADY_DELETE_SONG(100025,"歌曲已经删除");

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

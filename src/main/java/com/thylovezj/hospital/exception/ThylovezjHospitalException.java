package com.thylovezj.hospital.exception;

public class ThylovezjHospitalException extends RuntimeException{
    private final Integer code;
    private final String message;

    public ThylovezjHospitalException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ThylovezjHospitalException(ThylovezjHospitalExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

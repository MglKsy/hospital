package com.thylovezj.hospital.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 这是用来接收登录请求的类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxLogin {

    private String code;

    private String rawData;

}

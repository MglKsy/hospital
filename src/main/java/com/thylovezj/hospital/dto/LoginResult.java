package com.thylovezj.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 登录时候返回给前端的java对象
 */
public class LoginResult {

    private String openid;

    private String token;
}

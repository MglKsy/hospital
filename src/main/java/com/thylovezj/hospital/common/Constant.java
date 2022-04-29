package com.thylovezj.hospital.common;

import org.springframework.stereotype.Component;

@Component
public class Constant {
    public static final String salt = "98scc2154545";
    public static String FILE_UPLOAD_DIR;

    //客观题type
    public static final Integer subType = 0;
    //主观题type
    public static final Integer objType = 1;
    //医生问题每页记录数
    public static final Integer pageRows = 10;
}

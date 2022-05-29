package com.thylovezj.hospital.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {
    public static final String salt = "98scc2154545";
    public static String FILE_UPLOAD_DIR;

    //客观题type
    public static final Integer subType = 1;
    //主观题type
    public static final Integer objType = 2;
    //图片题type
    public static final Integer picType = 3;
    //医生问题每页记录数
    public static final Integer pageRows = 10;
    //一次增加的积分数
    public static Integer addBonus = 2;

    /**
     * 上传头像URI
     */
    public static final String AVATOR_URI = "xdu_hospital/user/avator";

    /**
     * 上传文件URI
     */
    public static final String FILE_URI = "xdu_hospital/netdisk/picture";

    public static final long MAX_UPLOAD_SIZE = 10485760L;
}

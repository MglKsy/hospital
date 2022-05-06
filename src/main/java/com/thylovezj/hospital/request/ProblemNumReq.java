package com.thylovezj.hospital.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProblemNumReq implements Serializable {
    /**
     * 客观题个数
     */
    private Integer subNumber;

    /**
     * 主观题个数
     */
    private Integer objNumber;

    /**
     * 图片题个数
     */
    private Integer picNumber;

}

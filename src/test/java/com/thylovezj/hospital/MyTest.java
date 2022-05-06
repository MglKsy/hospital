package com.thylovezj.hospital;

import com.thylovezj.hospital.MyInterface.WhatTodo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.PseudoColumnUsage;


public class MyTest {


    public static void test(WhatTodo whatTodo){
        for (int i = 0; i < 3; i++) {
            if (i==1){
                whatTodo.doIt();
            }
            System.out.println(i);
        }
    }

    public static void main(String[] args) {

    }


}

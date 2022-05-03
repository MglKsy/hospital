package com.thylovezj.hospital.util;

public class UserHolder {
    private static final ThreadLocal<String> tl = new ThreadLocal<>();

    public static void setId(String openid){
        tl.set(openid);
    }
    public static String getId(){
        return tl.get();
    }

    public static void removeId(){
        tl.remove();
    }
}

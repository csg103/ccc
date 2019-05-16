package com.stylefeng.guns.modular.api;


import com.stylefeng.guns.core.util.MD5Util;
import sun.security.provider.MD5;

public class Test {
    public static void main(String agrs[]){
        System.out.println(SHA1.encode("AppDomain-com.tencent.xin-"+"Documents/b95ad2b830273b5924b162943139ce32/DB/MM.sqlite"));
//        System.out.println("kurtfree".hashCode());
        System.out.println(MD5Util.encrypt("qq14490805"));

    }
}

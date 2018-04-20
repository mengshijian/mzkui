package com.ctf.logtest.netty;

public class Common {

    private static int pkg_uid = 0;

    //获取并自增 命令 序列号
    public static int getPkg_uid() {
        pkg_uid++;
        return pkg_uid;
    }
}

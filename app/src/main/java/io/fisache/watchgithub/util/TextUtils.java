package io.fisache.watchgithub.util;

public class TextUtils {
    public static String getForkStarString(int fork, int star) {
        String ret = "";
        if(fork >= 1000) {
            ret += fork/1000 + "." + fork/100 + "k";
        } else {
            ret += fork;
        }
        ret += " forked, ";
        if(star >= 1000) {
            ret += star/1000 + "." + star/100 + "k";
        } else {
            ret += star;
        }
        ret += " starred";
        return ret;
    }
}

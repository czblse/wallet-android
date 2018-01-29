package io.spaco.wallet.utils;

import java.util.List;

/**
 * Created by zjy on 2018/1/29.
 */

public class StringUtils {
    public static String converListToString(List<String> source){
        StringBuilder sb = new StringBuilder();
        for (String s : source) {
            sb.append(s);
        }
        return sb.toString();
    }
}

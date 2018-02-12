package io.spaco.wallet.utils;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by zjy on 2018/1/29.
 */

public class StringUtils {

    private static final String FORMAT_WALLET_ADDRESS_MIDDLE = "...";

    public static String converListToString(List<String> source){
        StringBuilder sb = new StringBuilder();
        for (String s : source) {
            sb.append(s);
        }
        return sb.toString();
    }


    /**
     * 格式化钱包地址
     * @param address
     * @return
     */
    public static String formatWalletAddress(String address){
        if(TextUtils.isEmpty(address) || address.length() < 12)
            return address;
        int length = address.length();
        return address.substring(0,6) + FORMAT_WALLET_ADDRESS_MIDDLE + address.substring(length-6,length);
    }
}

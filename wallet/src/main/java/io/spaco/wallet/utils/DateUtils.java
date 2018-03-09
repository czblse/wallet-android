package io.spaco.wallet.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luch on 2018/3/9.
 * 时间处理
 */

public class DateUtils {

    /**
     * 获取当前时间分钟
     *
     * @return
     */
    public static Integer getNowMinute() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm");
            Date date = new Date(System.currentTimeMillis());
            return Integer.parseInt(simpleDateFormat.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}

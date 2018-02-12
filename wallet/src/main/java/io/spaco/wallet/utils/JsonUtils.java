package io.spaco.wallet.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by luch on 2018/2/10.
 */

public class JsonUtils {

    private Gson gson;

    private GsonBuilder builder;

    private  String jsonStr;

    public static JsonUtils jsonUtils;

    public static JsonUtils getInstance() {

        if (jsonUtils == null) {
            jsonUtils = new JsonUtils();
        }
        return jsonUtils;
    }
}

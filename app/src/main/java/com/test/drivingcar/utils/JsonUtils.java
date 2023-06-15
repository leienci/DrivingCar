package com.test.drivingcar.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Json工具类
 *
 * @author duoma
 * @date 2019/03/18
 */
public class JsonUtils {

    private static Gson mGson = null;

    private JsonUtils() {

    }

    private static Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return getGson().toJson(object);
    }

    /**
     * 转成bean
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T fromJson(String json, Class<T> cls) {
        return getGson().fromJson(json, cls);
    }

    /**
     * 转成list
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> List<T> fromJsonList(String json, Class<T> cls) {
        List<T> list = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonArray jsonarray = parser.parse(json).getAsJsonArray();
        for (JsonElement element : jsonarray
        ) {
            list.add(getGson().fromJson(element, cls));
        }
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param json
     * @return
     */
    public static <T> List<Map<String, T>> fromJsonMap(String json) {
        return getGson().fromJson(json, new TypeToken<List<Map<String, T>>>() {
        }.getType());
    }

    /**
     * 转成map的
     *
     * @param json
     * @return
     */
    public static <T> Map<String, T> fromJsonMaps(String json) {
        return getGson().fromJson(json, new TypeToken<Map<String, T>>() {
        }.getType());
    }

    /**
     * 将object解析成指定泛型并返回
     *
     * @param object json数据的object
     * @param <T>    指定泛型
     */
    public static <T> T getObjectFromObject(Object object, Class<T> t) {
        Gson gson = new Gson();
        String data = "";
        try {
            data = gson.toJson(object);
            if (TextUtils.isEmpty(data)) {
                data = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Gson().fromJson(data, t);
    }
}

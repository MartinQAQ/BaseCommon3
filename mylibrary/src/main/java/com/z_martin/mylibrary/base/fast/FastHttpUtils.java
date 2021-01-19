package com.z_martin.mylibrary.base.fast;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vise.log.ViseLog;
import com.z_martin.mylibrary.utils.CJSON;
import com.z_martin.mylibrary.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @description: 快速http请求参数
 * @project name: BaseCommon2.0
 * @author: xiaoming723@126.com
 * @createTime: 2020/6/14 19:08
 * @version: 1.0
 */
public class FastHttpUtils {
    //region 拼接参数
    /**
     * 公用请求参数格式
     * @param object 请求参数
     * @return
     */
    public static RequestBody params(Object... object) {
        JSONObject map = new JSONObject();
        int objectSum = object.length;
        if (objectSum % 2 != 0) {
            ViseLog.e("参数错误，请检查参数");
            return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), CJSON.toJSONString(map));
        }
        for (int i = 0; i < objectSum; i+=2) {
            //            if(StringUtils.isEmpty(String.valueOf(object[i+1]))) {
            //                continue;
            //            }
            map.put((String) object[i], object[i+1]);
        }
        return FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), CJSON.toJSONString(map));
    }

    /**
     * 公用请求参数格式
     * @param object 请求参数
     * @return
     */
    public static Map<String, Object> paramsFoeGet(Object... object) {
        Map<String, Object> map = new HashMap<>();
        int objectSum = object.length;
        if (objectSum % 2 != 0) {
            ViseLog.e("参数错误，请检查参数");
            return map;
        }
        for (int i = 0; i < objectSum; i+=2) {
            //            if(StringUtils.isEmpty(String.valueOf(object[i+1]))) {
            //                continue;
            //            }
            map.put((String) object[i], object[i+1]);
        }
        return map;
    }

    /**
     * 公用请求参数格式
     * @param object 请求参数
     * @return
     */
    public static RequestBody params2(Object... object) {
        JSONObject map = new JSONObject();
        int objectSum = object.length;
        if (objectSum % 2 != 0) {
            ViseLog.e("参数错误，请检查参数");
            return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), CJSON.toJSONString(map));
        }
        for (int i = 0; i < objectSum; i+=2) {
            //            if(StringUtils.isEmpty(String.valueOf(object[i+1]))) {
            //                continue;
            //            }
            map.put((String) object[i], object[i+1]);
        }
        return FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), CJSON.toJSONString(map));
    }

    /**
     * 公用请求参数格式 参数不能为空
     * @param object 请求参数
     * @return
     */
    public static RequestBody paramsNoNull(Object... object) {
        JSONObject map = new JSONObject();
        int objectSum = object.length;
        if (objectSum % 2 != 0) {
            ViseLog.e("参数错误，请检查参数");
            return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), CJSON.toJSONString(map));
        }
        for (int i = 0; i < objectSum; i+=2) {
            if(StringUtils.isEmpty(String.valueOf(object[i+1]))) {
                continue;
            }
            map.put((String) object[i], object[i+1]);
        }
        return FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), CJSON.toJSONString(map));
    }

    /**
     * 公用请求参数格式
     * @param jsonObject 请求参数
     * @return
     */
    public static RequestBody params(JSONObject jsonObject) {
        return FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), CJSON.toJSONString(jsonObject));
    }

    /**
     * 公用请求参数格式
     * @param object 请求参数
     * @return
     */
    public static RequestBody paramsNoToken(Object... object) {
        JSONObject map = new JSONObject();
        int objectSum = object.length;
        if (objectSum % 2 != 0) {
            ViseLog.e("参数错误，请检查参数");
            //            return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), gson.toJson(map));
            return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), CJSON.toJSONString(map));
        }
        for (int i = 0; i < objectSum; i+=2) {
            //            if(StringUtils.isEmpty(String.valueOf(object[i+1]))) {
            //                continue;
            //            }
            map.put((String) object[i], object[i+1]);
        }
        return FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), CJSON.toJSONString(map));
        //        return FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), gson.toJson(map));
    }

    /**
     *
     * @param jsonObject
     * @return
     */
    public static RequestBody paramsJson(JsonObject jsonObject) {
        return FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(jsonObject));
    }
    //endregion
}

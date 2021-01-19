package com.z_martin.mylibrary.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ describe: JSONUtils--继承于FASTJson
 * @ author: Martin
 * @ createTime: 2018/9/18 10:57
 * @ version: 1.0
 */
public class CJSON extends JSONObject {
    /** 请求结果集 */
    public static final String RESULTS = "result";
    /** 状态码 */
    private static final String STATUS = "return_code";
    /** 状态描述 */
    private static final String MESSAGE = "return_message";


    /**
     * 获取状态码
     * @param json json串
     * @return 获取状态码
     */
    public static int getStatus(String json) {
        try {
            return JSON.parseObject(json).getInteger(STATUS);
        } catch (Exception e) {
            e.printStackTrace();
            return -2;
        }
    }
    /**
     * 获取状态码
     * @param json json串
     * @return 获取状态码
     */
    public static int getStatus2(String json) {
        try {
            return JSON.parseObject(json).getInteger(STATUS);
        } catch (Exception e) {
            e.printStackTrace();
            return -2;
        }
    }

    /**
     * 获取状态描述
     * @param json json串
     * @return 状态描述
     */
    public static String getMessage(String json) {
        try {
            try {
//                String[] message = JSON.parseObject(json).getString(MESSAGE).split("\\|");
//                if (message.length > 1) {
//                    String language = MultiLanguageUtil.getInstance().getLanguageType();
//                    ViseLog.e(language);
//                    switch (language) {
//                        case "zh":
//                            return message[0];
//                        case "en":
//                            return message[1];
//                        default:
//                            return message[1];
//                    }
//                } else {
                    return JSON.parseObject(json).getString(MESSAGE);
//                }
            } catch (Exception e) {
                e.printStackTrace();
                return JSON.parseObject(json).getString(MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "未知错误";
        }
    }
    /**
     * 获取状态描述
     * @param json json串
     * @return 状态描述
     */
    public static String getMessage(JSONObject json) {
        try {
            try {
//                String[] message = json.getString(MESSAGE).split("\\|");
//                if (message.length > 1) {
//                    String language = MultiLanguageUtil.getInstance().getLanguageType();
//                    switch (language) {
//                        case "zh":
//                            return message[0];
//                        case "en":
//                            return message[1];
//                        default:
//                            return message[1];
//                    }
//                } else {
                    return json.getString(MESSAGE);
//                }
            } catch (Exception e) {
                e.printStackTrace();
                return json.getString(MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "未知错误";
        }
    }

    /**
     * 获取请求结果集--String
     * CJSON.getStringResults(json);
     * @param json json串
     * @return 请求结果集--String
     */
    public static String getResultsString(JSONObject json) {
        try {
            return json.getString(RESULTS);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 获取请求结果集--String
     * CJSON.getStringResults(json);
     * @param json json串
     * @param key1 key1
     * @return 请求结果集--String
     */
    public static String getResultsString(JSONObject json, String key1) {
        try {
            return json.getJSONObject(RESULTS).getString(key1);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取请求结果集--List
     * CJSON.parseArray(CJSON.getListResults(json), XXXX.class);
     * @param json json串
     * @return 请求结果集--List
     */
    public static String getResultsList(String json) {
        try {
            return JSON.parseObject(json).getJSONArray(RESULTS).toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return "[]";
        }
    }

    /**
     * 获取请求结果集--XXX.class
     * CJSON.parseObject(CJSON.getResults(json), XXX.class);
     * @param json json串
     * @return 请求结果集--List
     */
    public static String getResults(String json) {
        try {
            return JSON.parseObject(json).getJSONObject(RESULTS).toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

    /**
     * 获取请求结果集--XXX.class
     * @param json json串
     * @return results
     */
    public static String getResults(JSONObject json) {
        try {
            return json.getString(RESULTS);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取请求结果集--XXX.class
     * @param json json串
     * @return results
     */
    public static int getResultsToInt(JSONObject json) {
        try {
            return json.getIntValue(RESULTS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     *  json转换成实体类
     * @param json:JSONObject  
     * @param clas 实体类
     * @param <T> 实体类类型
     * @return
     */
    public static <T> T getResults(JSONObject json, Class<T> clas) {
        try {
            return CJSON.parseObject(json.getJSONObject(RESULTS).toJSONString(), clas);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 获取参数Result
     * @param json    数据源
     * @param keyName key
     * @return
     */
    public static String getResult(String json, String keyName) {
        try {
            return JSON.parseObject(json).getJSONObject(RESULTS).getString(keyName);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *  json转换成实体类集合
     * @param json
     * @param clas
     * @param <T>
     * @return
     */
    public static <T>List<T> getResultsArray(JSONObject json, Class<T> clas) {
        try {
            return json.getJSONArray(RESULTS).toJavaList(clas);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  json转换成实体类集合
     * @param json
     * @param clas
     * @param <T>
     * @return
     */
    public static <T>List<T> getResultsArray(JSONObject json, String key, Class<T> clas) {
        try {
            return json.getJSONObject(RESULTS).getJSONArray(key).toJavaList(clas);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  json转换成实体类集合
     * @param json
     * @param key
     * @param key2
     * @param clas
     * @param <T>
     * @return
     */
    public static <T>List<T> getResultsArray(JSONObject json, String key, String key2, Class<T> clas) {
        try {
            return json.getJSONObject(RESULTS).getJSONObject(key).getJSONArray(key2).toJavaList(clas);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

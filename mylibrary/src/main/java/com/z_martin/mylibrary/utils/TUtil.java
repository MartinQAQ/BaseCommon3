package com.z_martin.mylibrary.utils;

import java.lang.reflect.ParameterizedType;

/**
 * 类转换初始化
 */
public class TUtil {

    /**
     * 
     * getClass().getGenericSuperclass()返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type
     * 然后将其转换ParameterizedTyp
     * getActualTypeArguments()返回表示此类型实际类型参数的 Type 对象的数组
     * @param o 对象
     * @param index 下标
     * @param <T> 实际类型
     * @return
     */
    public static <T> T getT(Object o, int index) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[index])
                    .newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (ClassCastException e) {
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

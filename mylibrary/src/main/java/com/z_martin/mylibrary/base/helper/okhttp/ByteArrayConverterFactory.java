package com.z_martin.mylibrary.base.helper.okhttp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2019/6/26 0:54
 * @ version: 1.0
 */
public class ByteArrayConverterFactory extends Converter.Factory {

    private static final MediaType MEDIA_TYPE = MediaType.get("application/octet-stream");

    public static ByteArrayConverterFactory create() {
        return new ByteArrayConverterFactory();
    }

    private ByteArrayConverterFactory() {
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(
            Type type, Annotation[] annotations, Retrofit retrofit) {
        if (!(type instanceof Class)) {
            return null;
        }
        return (type == byte[].class) ? ResponseBody::bytes : null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(
            Type type, Annotation[] parameterAnnotations,
            Annotation[] methodAnnotations, Retrofit retrofit) {
        if (!(type instanceof Class)) {
            return null;
        }
        return (type == byte[].class) ? (Converter<byte[], RequestBody>)
                value -> RequestBody.create(MEDIA_TYPE, value) : null;
    }
}

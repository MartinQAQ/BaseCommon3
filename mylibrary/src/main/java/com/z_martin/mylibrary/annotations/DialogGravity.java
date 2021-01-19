package com.z_martin.mylibrary.annotations;

import android.view.Gravity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2019/3/28 21:48
 * @ version: 1.0
 */
@Target(ElementType.TYPE) // 该注解作用在类上
@Retention(RetentionPolicy.RUNTIME) // jvm运行时通过反射机制获取该注解的值
public @interface DialogGravity {
    int value() default Gravity.TOP;
}

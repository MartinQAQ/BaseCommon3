package com.z_martin.mylibrary.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.FloatRange;

/**
 * ElementType.METHOD // 该注解作用在方法上
 * 
 * @ describe: 自动设置Dialog高度
 * @ author: Martin
 * @ createTime: 2019/3/25 15:30
 * @ version: 1.0
 */
@Target(ElementType.TYPE) // 该注解作用在类上
@Retention(RetentionPolicy.RUNTIME) // jvm运行时通过反射机制获取该注解的值
public @interface DialogHeight {
    
    @FloatRange(from=0.0, to=1)
    double value() default 1;
}

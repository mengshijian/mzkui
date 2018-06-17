package com.msj.mzkui.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义字段名
 * @author mengsj
 * @since 2017/2/23
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldName {
    /**
     * 字段名
     */
    String value() default "";
    /**
     * 是否忽略
     */
    boolean Ignore() default false;
}
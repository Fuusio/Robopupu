package com.robopupu.api.plugin;

import com.robopupu.api.dependency.DependencyScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * {@link Plug} is used to annotate instance fields for which the values are injects by
 * {@link Plugger} implementations.
 */
@Target(ElementType.FIELD)
public @interface Plug {
    Class<?> value() default DependencyScope.class;
}

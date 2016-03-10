package com.robopupu.api.dependency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * {@link Scope} is used to annotate constructors, methods, and classes  that provide dependencies
 * for injection. {@link Scope} annotation has one optional annotation parameter of type {@link Class}
 * that declares the {@link DependencyScope} for which a {@link DependencyProvider} is code generated
 * by the Fuusio Annotation Processor.
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.TYPE})
public @interface Scope {
    Class<?> value() default Object.class;
}

package org.fuusio.api.dependency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * {@link Provides} is used to annotate methods or constructors that provide dependencies for
 * injection. {@link Provides} annotation can have one annotation parameter of type {@link Class}
 * that declares the type of the provided dependency.
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface Provides {
    Class<?> value() default Object.class;
}

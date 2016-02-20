package org.fuusio.api.dependency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * {@link Scope} is used to annotate constructors, methods,  and classes that provide dependencies
 * for injection. {@link Scope} annotation can have one annotation parameter of type {@link Class}
 * that declares the type of the {@link DependencyScope}. // TODO
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.TYPE})
public @interface Scope {
    Class<?> value() default Object.class;
}

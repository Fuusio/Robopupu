package com.robopupu.api.dependency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * {@link AppScope} annotation is used for declare dependencies having application scope.
 */
@Target({ElementType.TYPE})
public @interface AppScope {
}

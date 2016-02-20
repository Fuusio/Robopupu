package org.fuusio.api.feature;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * {@link AppFeature} is used to annotate a concrete implementation of {@link Feature}.
 */
@Target(ElementType.TYPE)
public @interface AppFeature {
}

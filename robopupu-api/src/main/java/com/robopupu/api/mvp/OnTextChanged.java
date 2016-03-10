package com.robopupu.api.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * {@link OnTextChanged} is used to annotate {@link Presenter} interface methods for
 * {@link android.text.TextWatcher} events binding.
 */
@Target(ElementType.METHOD)
public @interface OnTextChanged {
}

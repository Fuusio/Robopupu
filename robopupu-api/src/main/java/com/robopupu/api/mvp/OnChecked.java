package com.robopupu.api.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * {@link OnChecked} is used to annotate {@link Presenter} interface methods for
 * {@link android.widget.CompoundButton.OnCheckedChangeListener} events binding.
 */
@Target(ElementType.METHOD)
public @interface OnChecked {
}

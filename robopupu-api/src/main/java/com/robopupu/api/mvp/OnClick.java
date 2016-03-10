package com.robopupu.api.mvp;

import com.robopupu.api.dependency.DependencyProvider;
import com.robopupu.api.dependency.DependencyScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * {@link OnClick} is used to annotate {@link Presenter} interface methods for
 * {@link android.view.View.OnClickListener} events binding.
 */
@Target(ElementType.METHOD)
public @interface OnClick {
}

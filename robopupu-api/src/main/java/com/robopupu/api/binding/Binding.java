/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://robopupu.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robopupu.api.binding;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.TextView;

import com.robopupu.api.mvp.ViewCompatDialogFragment;
import com.robopupu.api.mvp.ViewCompatFragment;

/**
 * {@link Binding} provides a convenience class for writing bindings for {@link View}s extending
 * {@link TextView} in {@link ViewCompatFragment#onCreateBindings()} method.
 */
public class Binding extends TextViewBinding {

    public Binding() {
        super(null);
    }

    public Binding(final TextView textView) {
        super(textView);
    }

    @SuppressWarnings("unchecked")
    public Binding(final ViewCompatFragment fragment, @IdRes final int viewId) {
        super((TextView) fragment.getView(viewId));
    }

    @SuppressWarnings("unchecked")
    public Binding(final ViewCompatDialogFragment fragment, @IdRes final int viewId) {
        super((TextView) fragment.getView(viewId));
    }
}

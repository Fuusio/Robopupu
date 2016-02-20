/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://fuusio.org
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
package org.fuusio.api.binding;

import android.view.View;
import android.widget.TextView;

import org.fuusio.api.mvp.ViewFragment;

/**
 * {@link Binding} provides a convenience class for writing bindings for {@link View}s extending
 * {@link TextView} in {@link ViewFragment#createBindings()} method.
 */
public class Binding extends TextViewBinding {

    @SuppressWarnings("unchecked")
    public Binding(final ViewFragment fragment, final int viewId) {
        super((TextView) fragment.getView(viewId));
    }
}

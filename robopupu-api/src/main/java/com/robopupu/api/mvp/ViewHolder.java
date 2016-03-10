/*
 * Copyright (C) 2014 - 2015 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.mvp;

import android.view.View;

/**
 * {@link ViewHolder} defines an interface for objects that implement {@link View}s for list items
 * in a {@link android.support.v7.widget.RecyclerView}.
 */
public interface ViewHolder {

    <T extends View> T getView(int layoutResId);

    View getInflatedView();

    int getType();

    void setItemPosition(int position);

    void setListener(ViewHolderListener listener);
}

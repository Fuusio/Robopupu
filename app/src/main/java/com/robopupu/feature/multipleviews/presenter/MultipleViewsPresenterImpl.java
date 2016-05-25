/*
 * Copyright (C) 2016 Marko Salmela, http://robopupu.com
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
package com.robopupu.feature.multipleviews.presenter;

import com.robopupu.R;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.feature.AbstractFeaturePresenter;
import com.robopupu.api.mvp.View;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.feature.multipleviews.MultipleViewsFeature;
import com.robopupu.feature.multipleviews.view.MultipleViewsView;

@Plugin
@Provides(MultipleViewsPresenter.class)
public class MultipleViewsPresenterImpl extends AbstractFeaturePresenter<MultipleViewsView>
        implements MultipleViewsPresenter {

    @Plug
    MultipleViewsView mView;
    @Plug
    MultipleViewsFeature mFeature;

    @Override
    public MultipleViewsView getViewPlug() {
        return mView;
    }

    @Override
    public void onViewStart(final View view) {
        super.onViewStart(view);

        mFeature.showView(R.id.fragment_container_top, TopPresenter.class,false, null);
        mFeature.showView(R.id.fragment_container_bottom, BottomPresenter.class, false, null);
    }
}

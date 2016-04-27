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
package com.robopupu.feature.about;

import com.robopupu.R;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.dependency.Scope;
import com.robopupu.api.feature.AbstractFeature;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.api.util.Params;
import com.robopupu.app.RobopupuAppScope;
import com.robopupu.component.PlatformManager;
import com.robopupu.feature.about.presenter.AboutPresenter;
import com.robopupu.feature.about.presenter.LicensesInfoPresenter;

@Plugin
public class AboutFeatureImpl extends AbstractFeature implements AboutFeature {

    @Plug PlatformManager mPlatformManager;

    @Scope(RobopupuAppScope.class)
    @Provides(AboutFeature.class)
    public AboutFeatureImpl() {
        super(AboutFeatureScope.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showView(AboutPresenter.class, false);
    }

    @Override
    public void onShowLicenseInfo() {
        final Params params = new Params(LicensesInfoPresenter.KEY_PARAM_LICENSE_URL, mPlatformManager.getString(R.string.robopupu_license_file));
        showView(LicensesInfoPresenter.class, false, params);
    }

    @Override
    public void onShowOssLicensesInfo() {
        final Params params = new Params(LicensesInfoPresenter.KEY_PARAM_LICENSE_URL, mPlatformManager.getString(R.string.oss_licenses_file));
        showView(LicensesInfoPresenter.class, false, params);
    }

    @Override
    public void onOpenSourcesWebPage() {
        final String url = mPlatformManager.getString(R.string.ft_about_text_sources);
        mPlatformManager.openWebPage(url);
    }
}

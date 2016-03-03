/*
 * Copyright (C) 2016 Marko Salmela, http://fuusio.org
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

import android.content.Intent;
import android.net.Uri;

import com.robopupu.R;
import com.robopupu.app.RobopupuAppScope;
import com.robopupu.component.AppManager;
import com.robopupu.feature.about.presenter.AboutPresenter;
import com.robopupu.feature.about.presenter.AboutPresenterListener;
import com.robopupu.feature.about.presenter.LicensesInfoPresenter;

import org.fuusio.api.dependency.Provides;
import org.fuusio.api.dependency.Scope;
import org.fuusio.api.feature.AbstractFeature;
import org.fuusio.api.mvp.Presenter;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;
import org.fuusio.api.util.Params;

@Plugin
public class AboutFeatureImpl extends AbstractFeature implements AboutFeature, AboutPresenterListener {

    @Plug AppManager mAppManager;

    @Scope(RobopupuAppScope.class)
    @Provides(AboutFeature.class)
    public AboutFeatureImpl() {
        super(AboutFeatureScope.class);
    }

    @Override
    protected void onStart() {
        showView(AboutPresenter.class, false);
    }

    @Override
    public void onShowLicenseInfo() {
        final Params params = new Params(LicensesInfoPresenter.KEY_PARAM_LICENSE_URL, mAppManager.getString(R.string.robopupu_license_file));
        showView(LicensesInfoPresenter.class, true, params);
    }

    @Override
    public void onShowOssLicensesInfo() {
        final Params params = new Params(LicensesInfoPresenter.KEY_PARAM_LICENSE_URL, mAppManager.getString(R.string.oss_licenses_file));
        showView(LicensesInfoPresenter.class, true, params);
    }

    @Override
    public void onOpenSourcesWebPage() {
        final String url = mAppManager.getString(R.string.ft_about_text_sources);
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        mAppManager.startActivity(intent);
    }

    @Override
    public void onPresenterFinished(final Presenter presenter) {

        if (presenter instanceof LicensesInfoPresenter) {
            goBack();
        }
    }
}

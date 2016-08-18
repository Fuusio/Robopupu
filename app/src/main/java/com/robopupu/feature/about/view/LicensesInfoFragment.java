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
package com.robopupu.feature.about.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.robopupu.R;
import com.robopupu.app.view.DelegatedWebViewClient;
import com.robopupu.feature.about.presenter.LicensesInfoPresenter;

import com.robopupu.api.binding.ClickBinding;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.feature.FeatureCompatDialogFragment;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;

@Plugin
public class LicensesInfoFragment extends FeatureCompatDialogFragment<LicensesInfoPresenter>
        implements LicensesInfoView {

    private WebView webView;
    private DelegatedWebViewClient webViewClient;
    private RelativeLayout webViewRelativeLayout;

    @Plug LicensesInfoPresenter presenter;

    @Provides(LicensesInfoView.class)
    public LicensesInfoFragment() {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.View_Fragment_Dialog);
    }

    @Override
    public LicensesInfoPresenter getPresenter() {
        return presenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle  inState) {
        return inflater.inflate(R.layout.fragment_licenses_info, container, false);
    }

    @Override
    protected void onCreateBindings() {
        super.onCreateBindings();

        webView = new WebView(getActivity());

        webViewRelativeLayout = getView(R.id.relative_layout_web_view);
        webViewClient = new DelegatedWebViewClient(presenter);
        webView.setWebViewClient(webViewClient);

        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        webViewRelativeLayout.addView(webView, params);

        new ClickBinding(this, R.id.button_ok) {
            @Override
            protected void clicked() {
                // REMOVE presenter.onOkButtonClick();
            }
        };
    }

    @Override
    public void loadUrl(final String url) {
        webView.post(new Runnable() {

            @Override
            public void run() {
                webView.loadUrl(url);
            }
        });
    }
}

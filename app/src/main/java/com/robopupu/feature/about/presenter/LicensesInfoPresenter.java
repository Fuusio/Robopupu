package com.robopupu.feature.about.presenter;

import com.robopupu.app.view.DelegatedWebViewClient;

import org.fuusio.api.feature.FeaturePresenter;
import org.fuusio.api.plugin.PlugInterface;

@PlugInterface
public interface LicensesInfoPresenter extends FeaturePresenter, DelegatedWebViewClient.Delegate {

    String KEY_PARAM_LICENSE_URL = "Key.Param.License.URL";

    void onOkButtonClicked();
}

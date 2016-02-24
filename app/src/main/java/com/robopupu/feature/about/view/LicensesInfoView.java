package com.robopupu.feature.about.view;

import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.PlugInterface;

@PlugInterface
public interface LicensesInfoView extends View {

    void loadUrl(String url);
}

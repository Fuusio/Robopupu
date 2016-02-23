package com.robopupu.feature.about.view;

import org.fuusio.api.mvp.View;
import org.fuusio.api.plugin.PlugInterface;

@PlugInterface
public interface AboutView extends View {

    void setLicenseText(String text);
    void setVersionText(String text);
}

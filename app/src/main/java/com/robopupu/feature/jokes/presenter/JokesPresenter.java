package com.robopupu.feature.jokes.presenter;

import com.robopupu.api.feature.FeaturePresenter;
import com.robopupu.api.mvp.OnClick;
import com.robopupu.api.plugin.PlugInterface;

@PlugInterface
public interface JokesPresenter extends FeaturePresenter {

    @OnClick void onRequestJokeClick();
}

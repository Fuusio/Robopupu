package com.robopupu.feature.multipleviews.view;

import android.os.Bundle;

import com.robopupu.R;
import com.robopupu.api.feature.FeatureContainer;
import com.robopupu.api.mvp.PluginCompatActivity;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.feature.multipleviews.MultipleViewsFeature;
import com.robopupu.feature.multipleviews.MultipleViewsFeatureScope;
import com.robopupu.feature.multipleviews.presenter.MultipleViewsPresenter;

import java.util.List;

@Plugin
public class MultipleViewsActivity extends PluginCompatActivity<MultipleViewsPresenter>
    implements MultipleViewsView {

    @Plug
    MultipleViewsFeature mFeature;
    @Plug(MultipleViewsFeatureScope.class)
    MultipleViewsPresenter mPresenter;

    @Override
    public MultipleViewsPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(final Bundle inState) {
        super.onCreate(inState);
        setContentView(R.layout.activity_multiple_views);
    }

    @Override
    protected void createFeatureContainers(final List<FeatureContainer> containers) {
        containers.add(createFeatureContainer(R.id.fragment_container_top));
        containers.add(createFeatureContainer(R.id.fragment_container_bottom));
    }
}

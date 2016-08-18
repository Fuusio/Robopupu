package com.robopupu.feature.viewdelegate.view;

import com.robopupu.api.dependency.Provides;
import com.robopupu.api.feature.FeatureViewCompatFragmentDelegate;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.feature.viewdelegate.presenter.TestPresenter;

/**
 * {@link TestViewDelegate} is a sample implementation of {@link FeatureViewCompatFragmentDelegate}.
 */
@Plugin
public class TestViewDelegate extends FeatureViewCompatFragmentDelegate<TestPresenter, TestFragment>
    implements TestView {

    @Plug TestPresenter presenter;

    @Provides(TestView.class)
    public TestViewDelegate() {
        super(new TestFragment());
    }

    @Override
    public TestPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreateBindings() {
        super.onCreateBindings();
        getFragment().onCreateBindings(binder);
    }

    @Override
    public void setSayHelloText(final String text) {
        getFragment().setSayHelloText(text);
    }
}

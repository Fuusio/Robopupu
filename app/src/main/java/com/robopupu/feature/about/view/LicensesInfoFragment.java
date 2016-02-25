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

import org.fuusio.api.binding.Binding;
import org.fuusio.api.dependency.Provides;
import org.fuusio.api.feature.FeatureDialogFragment;
import org.fuusio.api.plugin.Plug;
import org.fuusio.api.plugin.Plugin;

@Plugin
public class LicensesInfoFragment extends FeatureDialogFragment<LicensesInfoPresenter>
        implements LicensesInfoView {

    private WebView mWebView;
    private DelegatedWebViewClient mWebViewClient;
    private RelativeLayout mWebViewRelativeLayout;

    @Plug LicensesInfoPresenter mPresenter;

    @Provides(LicensesInfoView.class)
    public LicensesInfoFragment() {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.View_Fragment_Dialog);
    }

    @Override
    protected LicensesInfoPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle  inState) {
        return inflater.inflate(R.layout.fragment_licenses_info, container, false);
    }

    @Override
    protected void createBindings() {
        super.createBindings();

        mWebView = new WebView(getActivity());

        mWebViewRelativeLayout = getView(R.id.relative_layout_web_view);
        mWebViewClient = new DelegatedWebViewClient(mPresenter);
        mWebView.setWebViewClient(mWebViewClient);

        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        mWebViewRelativeLayout.addView(mWebView, params);

        bind(R.id.button_ok, new Binding() {
            @Override
            protected void clicked() {
                mPresenter.onOkButtonClicked();
            }
        });
    }

    @Override
    public void loadUrl(final String url) {
        mWebView.post(new Runnable() {

            @Override
            public void run() {
                mWebView.loadUrl(url);
            }
        });
    }
}

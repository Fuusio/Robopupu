package com.robopupu.feature.viewdelegate.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robopupu.R;
import com.robopupu.api.binding.ViewBinder;
import com.robopupu.api.dependency.D;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    private TextView helloTextView;
    private TestViewDelegate viewDelegate;

    public TestFragment() {
    }

    protected void setSayHelloText(final String text) {
        helloTextView.setText(text);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle inState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(final View view, final @Nullable Bundle savedInstanceState) {
        final TestView testView = D.get(TestView.class);
        viewDelegate = (TestViewDelegate) testView;
        super.onViewCreated(view, savedInstanceState);
    }

    protected void onCreateBindings(final ViewBinder binder) {
        helloTextView = binder.getView(R.id.text_view_hello);
    }

    @Override
    public void onActivityCreated(final @Nullable Bundle inState) {
        super.onActivityCreated(inState);
        viewDelegate.onActivityCreated(inState);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewDelegate.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewDelegate.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewDelegate.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewDelegate.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewDelegate.onDestroy();
    }
}

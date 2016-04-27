/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://robopupu.com
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
package com.robopupu.api.binding;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.robopupu.api.mvp.AbstractPresenter;
import com.robopupu.api.mvp.OnClick;
import com.robopupu.api.mvp.OnTextChanged;
import com.robopupu.api.mvp.ViewEventsDelegate;
import com.robopupu.api.mvp.PresenterDelegate;
import com.robopupu.api.mvp.Presenter;
import com.robopupu.api.mvp.PresentedView;
import com.robopupu.api.util.UIToolkit;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * {@link ViewBinder} is a component that can be used to establish bindings between {@link View}s
 * and {@link Presenter}s. It also automagically deploys a concrete implementation of
 * {@link PresenterDelegate} for a {@link Presenter} interface annotated with {@link OnClick} and
 * {@link OnTextChanged} annotations.
 */
public class ViewBinder {

    private final static String SUFFIX_EVENTS_DELEGATE = "_EventsDelegate";

    private final HashMap<Integer, ViewBinding<?>> mBindingsCache;
    private final PresentedView mView;

    private Activity mActivity;
    private ViewGroup mContentView;
    private boolean mInitialised;
    private PresenterDelegate mPresenterDelegate;

    public ViewBinder(final PresentedView view) {
        mView = view;
        mBindingsCache = new HashMap<>();
        mInitialised = false;
    }

    protected PresentedView getPresentedView() {
        return mView;
    }

    public void setActivity(@NonNull Activity activity) {
        mActivity = activity;

        final ViewGroup content = (ViewGroup) mActivity.findViewById(android.R.id.content);

        if (content != null) {
            final ViewGroup contentView = (ViewGroup) (content).getChildAt(0);

            if (contentView != null) {
                mContentView = contentView;
            } else {
                mContentView = content;
            }
        }
    }

    public void setContentView(@NonNull ViewGroup contentView) {
        mContentView = contentView;
    }

    public void initialise() {
        if (!mInitialised) {
            mInitialised = true;
            bindEventListenersForViews(mContentView);
        }
    }

    private void bindEventListenersForViews(final ViewGroup viewGroup) {
        if (mPresenterDelegate == null) {
            mPresenterDelegate = getEventsDelegate();
        }

        if (mPresenterDelegate != null) {
            final ArrayList<View> taggedViews = new ArrayList<>();
            UIToolkit.collectTaggedViewsOfType(View.class, viewGroup, taggedViews);

            for (final View view : taggedViews) {
                final Object tag = view.getTag();

                if (tag instanceof String && ((String) tag).length() > 0) {
                    final ViewEventsDelegate delegate = new ViewEventsDelegate(view, mPresenterDelegate);
                    view.setOnClickListener(delegate);

                    if (view instanceof CompoundButton) {
                        final CompoundButton compoundButton = (CompoundButton)view;
                        compoundButton.setOnCheckedChangeListener(delegate);
                    } else if (view instanceof TextView) {
                        final TextView textView = (TextView)view;
                        textView.addTextChangedListener(delegate);
                    }
                }
            }
        }
    }

    /**
     * Gets an instance of {@link PresenterDelegate}.
     * @return An {@link PresenterDelegate}. May return {@code null}.
     */
    @SuppressWarnings("unchecked")
    private PresenterDelegate getEventsDelegate() {
        final Presenter presenter = mView.getPresenter();
        final Class<? extends Presenter> presenterInterfaceClass = AbstractPresenter.getInterfaceClass(presenter);

        assert(presenterInterfaceClass != null);

        final String delegateClassName = presenterInterfaceClass.getName() + SUFFIX_EVENTS_DELEGATE;
        PresenterDelegate delegate = null;

        try {
            final Class[] paramTypes = {presenterInterfaceClass};
            final Object[] paramValues = {presenter};
            final Class<? extends PresenterDelegate> delegateClass = (Class<? extends PresenterDelegate>)Class.forName(delegateClassName);
            final Constructor<? extends PresenterDelegate> constructor = delegateClass.getConstructor(paramTypes);
            delegate = constructor.newInstance(paramValues);
        } catch (ClassNotFoundException ignore) {
            // Ignore
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return delegate;
    }

    /**
     * Looks up and returns a {@link View} with the given layout id.
     *
     * @param viewId A view id used in a layout XML resource.
     * @return The found {@link View}.
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes final int viewId) {
        if (mActivity != null) {
            return (T) mActivity.findViewById(viewId);
        } else {
            return (T) mContentView.findViewById(viewId);
        }
    }

    /**
     * Gets a {@link ViewBinding} for the specified {@link View}.
     *
     * @param viewId A view id used in a layout XML resource.
     * @return The found {@link View}.
     */
    @SuppressWarnings("unchecked")
    public <T extends ViewBinding> T getBinding(@IdRes final int viewId) {
        T binding = (T) mBindingsCache.get(viewId);

        if (binding == null) {
            final View view = getView(viewId);

            if (view instanceof TextView) {
                binding = (T) new TextViewBinding((TextView) view);
            } else {
                throw new IllegalStateException("View of type: " + view.getClass().getName() + " is not supported.");
            }
            mBindingsCache.put(viewId, binding);
        }
        return binding;
    }

    /**
     * Disposes this {@link ViewBinder} by clearing the Binding Cache.
     */
    public void dispose() {
        mBindingsCache.clear();
    }

    /**
     * Creates and binds a {@link ViewBinding} to a {@link View} specified by the given view id.
     *
     * @param viewId A view id used in a layout XML resource.
     * @param <T>    The parametrised type of the ViewDelagate.
     * @return The created {@link ViewBinding}.
     */
    @SuppressWarnings("unchecked")
    public <T extends ViewBinding<?>> T bind(@IdRes final int viewId) {
        final View view = getView(viewId);
        ViewBinding<?> binding;

        if (view instanceof AdapterView) {
            throw new IllegalStateException("For AdapterView derived classes use AdapterViewBinding.");
        } else {
            binding = new Binding((TextView) view);
        }

        mBindingsCache.put(viewId, binding);
        return (T) binding;
    }

    /**
     * Binds the given {@link ViewBinding} to the specified {@link View}.
     *
     * @param viewId  A view id in a layout XML specifying the target {@link View}.
     * @param binding An {@link ViewBinding}.
     * @return The found and bound {@link View}.
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T bind(@IdRes final int viewId, final ViewBinding<T> binding) {
        final T view = getView(viewId);

        if (binding.canBind(view)) {
            binding.setView(view);
            mBindingsCache.put(viewId, binding);
        } else {
            throw new IllegalStateException("No View with id: " + Integer.toString(viewId) + " compatible with the given ViewBinding was found");
        }
        return view;
    }

    /**
     * Binds the given {@link AdapterViewBinding} to the specified {@link AdapterView}.
     *
     * @param viewId  A view id in a layout XML specifying the target {@link AdapterView}.
     * @param binding An {@link AdapterViewBinding}.
     * @param adapter An {@link AdapterViewBinding.Adapter} that is assigned to {@link AdapterViewBinding}.
     * @return The found and bound {@link AdapterView}.
     */
    @SuppressWarnings("unchecked")
    public <T extends AdapterView> T bind(@IdRes final int viewId, final AdapterViewBinding<?> binding, final AdapterViewBinding.Adapter<?> adapter) {
        final T view = getView(viewId);

        if (binding.canBind(view)) {
            binding.setAdapter(adapter);
            binding.setView(view);
            mBindingsCache.put(viewId, binding);
        } else {
            throw new IllegalStateException("No View with id: " + Integer.toString(viewId) + " compatible with the given ViewBinding was found");
        }
        return view;
    }
}

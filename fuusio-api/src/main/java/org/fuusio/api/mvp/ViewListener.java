package org.fuusio.api.mvp;

import org.fuusio.api.util.Params;

/**
 * {@link org.fuusio.api.mvp.ViewListener} a listener interface for receiving lifecycle events
 * from a {@link View}. This interface is mainly intended to be used by an attached {@link Presenter}.
 */
public interface ViewListener {
    /**
     * Invoked by a {@link View} implementation when it is created-
     *
     * @param view    A {@link View}
     * @param inState {@lin Params} containing the initial state.
     */
    void onViewCreated(View view, Params inState);

    /**
     * Invoked by a {@link View} implementation when it is resumed,
     * e.g. on {@link ViewFragment#onResume()}.
     *
     * @param view A {@link View}
     */
    void onViewResume(View view);

    /**
     * Invoked by a {@link View} implementation when it is paused,
     * e.g. on {@link ViewFragment#onPause()}.
     *
     * @param view A {@link View}
     */
    void onViewPause(View view);

    /**
     * Invoked by a {@link View} implementation when it is started,
     * e.g. on {@link ViewFragment#onStart()}.
     *
     * @param view A {@link View}
     */
    void onViewStart(View view);

    /**
     * Invoked by a {@link View} implementation when it is stopped,
     * e.g. on {@link ViewFragment#onStop()}.
     *
     * @param view A {@link View}
     */
    void onViewStop(View view);

    /**
     * Invoked by a {@link View} implementation when it is destroyed,
     * e.g. on {@link ViewFragment#onDestroy()}.
     *
     * @param view A {@link View}
     */
    void onViewDestroy(View view);
}

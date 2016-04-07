package com.robopupu.api.mvp;

/**
 * {@link PresenterListener} defines listener interface for receiving events from a {@link Presenter}.
 */
public interface PresenterListener {

    /**
     * Invoked by a {@link Presenter} when it is finished by invoking {@link Presenter#finish()}.
     *
     * @param presenter The finished {@link Presenter}.
     */
    void onPresenterFinished(Presenter presenter);
}

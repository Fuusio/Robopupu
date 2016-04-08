package com.robopupu.feature.jokes.component;

import com.robopupu.api.component.Interactor;
import com.robopupu.api.graph.Graph;
import com.robopupu.api.plugin.PlugInterface;
import com.robopupu.feature.jokes.model.JokeResponse;

@PlugInterface
public interface JokesInteractor extends Interactor {

    void requestJoke(Graph.Callback<JokeResponse> callback);
}

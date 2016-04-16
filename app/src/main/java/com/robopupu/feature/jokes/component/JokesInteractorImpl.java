package com.robopupu.feature.jokes.component;

import android.content.Context;

import com.robopupu.api.component.GraphInteractor;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.graph.Graph;
import com.robopupu.api.graph.NodeObserver;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.component.AppManager;
import com.robopupu.feature.jokes.model.JokeResponse;
import com.robopupu.volley.GsonRequest;
import com.robopupu.volley.RequestSpec;

@Provides(JokesInteractor.class)
@Plugin
public class JokesInteractorImpl extends GraphInteractor implements JokesInteractor {

    private RequestSpec<JokeResponse> mGetJoke;

    @Plug AppManager mAppManager;

    @Override
    public void onPlugged(final PluginBus bus) {
        super.onPlugged(bus);
        final Context context = mAppManager.getAppContext();
        mGetJoke = new RequestSpec<JokeResponse>(context, "http://api.icndb.com/jokes/random").
                request(new GsonRequest<>(JokeResponse.class));
    }

    @Override
    public void requestJoke(final NodeObserver<JokeResponse> callback) {
        Graph.begin(mGetJoke).observer(callback).start();
    }
}

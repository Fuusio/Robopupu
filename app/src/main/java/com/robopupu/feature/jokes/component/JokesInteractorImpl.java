package com.robopupu.feature.jokes.component;

import android.content.Context;

import com.robopupu.api.component.GraphInteractor;
import com.robopupu.api.dependency.Provides;
import com.robopupu.api.graph.Graph;
import com.robopupu.api.plugin.Plug;
import com.robopupu.api.plugin.Plugin;
import com.robopupu.api.plugin.PluginBus;
import com.robopupu.api.util.Params;
import com.robopupu.component.AppManager;
import com.robopupu.feature.jokes.model.JokeResponse;
import com.robopupu.volley.GsonRequest;
import com.robopupu.volley.RequestBuilder;

@Provides(JokesInteractor.class)
@Plugin
public class JokesInteractorImpl extends GraphInteractor implements JokesInteractor {

    private RequestBuilder<JokeResponse> mGetJoke;

    @Plug AppManager mAppManager;

    @Override
    public void onPlugged(final PluginBus bus) {
        super.onPlugged(bus);
        final Context context = mAppManager.getAppContext();
        mGetJoke = new RequestBuilder<JokeResponse>(context, "http://api.icndb.com/jokes/random").
                request(new GsonRequest<>(JokeResponse.class));
    }

    @Override
    public void requestJoke(final Graph.Callback<JokeResponse> callback) {
        Graph.begin(mGetJoke).callback(callback).emit();
    }
}

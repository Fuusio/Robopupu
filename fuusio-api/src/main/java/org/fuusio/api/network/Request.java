package org.fuusio.api.network;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.fuusio.api.network.volley.VolleyRequest;
import org.fuusio.api.network.volley.VolleyRequestError;
import org.fuusio.api.util.KeyValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

public abstract class Request<T_Response> {

    protected static final String PATH_SEPARATOR = "/";

    protected final HttpHeaders mHeaders;
    protected final HttpMethod mMethod;
    protected final HttpParams mParams;
    protected final HttpParams mPathParams;
    protected final String mRelativeUrl;
    protected final Response.ErrorListener mErrorListener;
    protected final RequestListener<T_Response> mRequestListener;
    protected final Response.Listener<T_Response> mResponseListener;

    protected Object mBody;
    protected VolleyRequest<T_Response> mVolleyRequest;

    protected Request(final String relativeUrl, final RequestListener<T_Response> requestListener) {
        this(HttpMethod.GET, relativeUrl, requestListener);
    }

    protected Request(final HttpMethod method, final String relativeUrl, final RequestListener<T_Response> requestListener) {
        mMethod = method;
        mRelativeUrl = relativeUrl;
        mRequestListener = requestListener;

        mHeaders = new HttpHeaders();
        mErrorListener = createErrorListener(requestListener);
        mPathParams = new HttpParams(getParamsEncoding());
        mParams = new HttpParams(getParamsEncoding());
        mResponseListener = createResponseListener(requestListener);
    }

    protected abstract String getBaseUrl();

    protected Charset getParamsEncoding() {
        return HttpParams.DEFAULT_ENCODING;
    }

    public String getRelativeUrl() {
        return mRelativeUrl;
    }

    public VolleyRequest getPeerRequest() {
        return mVolleyRequest;
    }

    public HttpMethod getMethod() {
        return mMethod;
    }

    /**
     * Construct the request URL from base URL, relative URL, path parameters, and query parameters.
     * This method is invoked by {@link Request#createVolleyRequest()}
     *
     * @param baseUrl The base URL as a {@link String}.
     * @param relativeUrl The relative URL as a {@link String}.
     * @return The constructed URL as a {@link String}.
     */
    protected String constructUrl(final String baseUrl, final String relativeUrl) {
        final StringBuilder builder = new StringBuilder(baseUrl);

        if (!baseUrl.endsWith(PATH_SEPARATOR)) {
            if (!relativeUrl.startsWith(PATH_SEPARATOR)) {
                builder.append(PATH_SEPARATOR);
            }
            builder.append(relativeUrl);
        } else {
            if (relativeUrl.startsWith(PATH_SEPARATOR)) {
                builder.append(relativeUrl.substring(1));
            } else {
                builder.append(relativeUrl);
            }
        }

        if (hasQueryParams()) {
            builder.append('?');
            getParams().encodeParameters(builder);
        }

        String url = builder.toString();

        if (mPathParams != null) {
            final String encoding = getParamsEncoding().name();
            try {
                final List<KeyValue<String, String>> keyValues = mPathParams.getKeyValues();

                for (final KeyValue<String, String> keyValue : keyValues) {
                    final String key = "{" + keyValue.getKey() + "}";
                    final String value = URLEncoder.encode(keyValue.getValue(), encoding);
                    url = url.replace(key, value);
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Failed to encode path parameter.", e);
            }
        }
        return url;
    }

    /**
     * Prepares this {@link Request} for execution.
     */
    protected final void prepareForExecution() {
        mVolleyRequest = createVolleyRequest();

        mVolleyRequest.setTag(getClass().getSimpleName());
        mVolleyRequest.setHeaders(mHeaders);

        if (isPost()) {
            mVolleyRequest.setBody(mBody);
        }

        initializeVolleyRequest(mVolleyRequest);
    }

    /**
     * Invoked to initialise the given {@link VolleyRequest}. Overriding this method can be used
     * to customise initialisation of a {@link VolleyRequest}.
     * @param volleyRequest A {@link VolleyRequest}.
     */
    protected void initializeVolleyRequest(final VolleyRequest<T_Response> volleyRequest) {
        // By default do nothing
    }

    /**
     * Creates an instance of a concrete implementation of {@link VolleyRequest}.
     *
     * @return An instance of a concrete implementation of {@link VolleyRequest}.
     */
    protected final VolleyRequest<T_Response> createVolleyRequest() {
        final String url = constructUrl(getBaseUrl(), getRelativeUrl());
        return createVolleyRequest(url, mResponseListener, mErrorListener);
    }

    /**
     * Creates an instance of a concrete implementation of {@link VolleyRequest}. This method needs
     * to overridden in a class that provides a concrete implemenation of {@link Request}.
     *
     * @param url The URL as a {@link String}.
     * @param responseListener A {@link Response.Listener}.
     * @param errorListener
     * @return An instance of a concrete implementation of {@link VolleyRequest}.
     */
    protected abstract VolleyRequest<T_Response> createVolleyRequest(String url, Response.Listener<T_Response> responseListener, Response.ErrorListener errorListener);

    protected Response.Listener<T_Response> createResponseListener(final RequestListener<T_Response> requestListener) {
        return new Response.Listener<T_Response>() {
            @Override
            public void onResponse(final T_Response response) {
                Log.d("VolleyRestRequest", "onResponse");
                requestListener.onResponse(response);
            }
        };
    }

    protected Response.ErrorListener createErrorListener(final RequestListener<T_Response> requestListener) {
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Log.d("VolleyRestRequest", "onError");
                requestListener.onError(new VolleyRequestError(error));
            }
        };
    }

    public boolean hasQueryParams() {
        return mParams != null && mParams.hasValues();
    }

    /**
     * Set the body of the request. The given object is assumed to be a POJO that can be converted
     * by GSON to JSON.
     *
     * @param body A POJO as an {@link Object}.
     */
    public void setBody(final Object body) {
        mBody = body;
    }

    /**
     * Add the specified query parameter with the given value.
     *
     * @param key   The name of the parameter to be added as a {@link String}.
     * @param value The value of the parameter.
     */
    public Request addParam(final String key, final String value) {
        mParams.add(key, value);
        return this;
    }

    public Request addParam(final String key, final boolean value) {
        mParams.add(key, Boolean.toString(value));
        return this;
    }

    public Request addParam(final String key, final float value) {
        mParams.add(key, Float.toString(value));
        return this;
    }

    public Request addParam(final String key, final int value) {
        mParams.add(key, Integer.toString(value));
        return this;
    }

    public Request addParam(final String key, final long value) {
        mParams.add(key, Long.toString(value));
        return this;
    }

    /**
     * Add the specified path parameter with the given value.
     *
     * @param key   The name of the parameter to be added as a {@link String}.
     * @param value The value of the parameter.
     */
    public Request addPathParam(final String key, final String value) {
        mPathParams.add(key, value);
        return this;
    }

    public Request addPathParam(final String key, final boolean value) {
        mPathParams.add(key, Boolean.toString(value));
        return this;
    }

    public Request addPathParam(final String key, final float value) {
        mPathParams.add(key, Float.toString(value));
        return this;
    }

    public Request addPathParam(final String key, final int value) {
        mPathParams.add(key, Integer.toString(value));
        return this;
    }

    public Request addPathParam(final String key, final long value) {
        mPathParams.add(key, Long.toString(value));
        return this;
    }


    public Request addHeader(final String field, final String value) {
        mHeaders.add(field, value);
        return this;
    }

    public final HttpHeaders getHeaders() {
        return mHeaders;
    }

    public final HttpParams getParams() {
        return mParams;
    }

    public void setParams(final HttpParams params) {
        mParams.clear();
        mParams.addAll(params);
    }

    public final HttpParams getPathParams() {
        return mPathParams;
    }

    public void setPathParams(final HttpParams params) {
        mPathParams.clear();
        mPathParams.addAll(params);
    }

    public final RequestListener<T_Response> getRequestListener() {
        return mRequestListener;
    }

    public boolean isDelete() {
        return (mMethod.isDelete());
    }

    public boolean isGet() {
        return (mMethod.isGet());
    }

    public boolean isPost() {
        return (mMethod.isPost());
    }

    public boolean isPut() {
        return (mMethod.isPut());
    }

    /**
     * Executes this {@link Request} using the given {@link RequestManager}.
     * @param requestManager A {@link RequestManager}.
     */
    public void execute(final RequestManager requestManager) {
        prepareForExecution();
        requestManager.execute(this);
    }
}
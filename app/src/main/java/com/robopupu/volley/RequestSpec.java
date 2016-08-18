package com.robopupu.volley;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.robopupu.api.network.HeaderRequestField;
import com.robopupu.api.network.HttpHeaders;
import com.robopupu.api.network.HttpParams;
import com.robopupu.api.network.RequestCallback;
import com.robopupu.api.network.RequestDelegate;
import com.robopupu.api.util.KeyValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * {@link RequestSpec} is a utility that is used to build a complete URL from given base URL,
 * relative URL, query params, and path params.
 */
public class RequestSpec<T_Response> implements RequestDelegate<T_Response> {

    protected static final String PATH_SEPARATOR = "/";

    private final HttpHeaders headers;
    private final HttpParams pathParams;
    private final HttpParams params;

    private String baseUrl;
    private boolean build;
    private String buildUrl;
    private Context context;
    private Charset encoding;
    private String relativeUrl;
    private BaseRequest<T_Response> request;
    private RetryPolicy retryPolicy;
    private RequestQueue requestQueue;
    private String tag;
    private int timeout; // In milliseconds
    private String url;
    
    public RequestSpec(final Context context) {
        this(context, null);
    }

    public RequestSpec(final Context context, final String url) {
        this.context = context;
        this.url = url;
        build = false;
        headers = new HttpHeaders();
        encoding = HttpParams.DEFAULT_ENCODING;
        pathParams = new HttpParams();
        params = new HttpParams();
    }

    public RequestSpec(final Context context, final String baseUrl, final String relativeUrl) {
        this(context, null);
        this.baseUrl = baseUrl;
        this.relativeUrl = relativeUrl;
    }

    /**
     * Gets the given {@link Context}.
     * @return A {@link Context}.
     */
    public Context getContext() {
        return context;
    }

    private RequestSpec<T_Response> reset() {
        build = false;
        buildUrl = null;
        return this;
    }

    protected RequestSpec<T_Response> encoding(final Charset encoding) {
        this.encoding = encoding;
        return reset();
    }

    public RequestSpec<T_Response> baseUrl(final String url) {
        baseUrl = url;
        return reset();
    }

    public RequestSpec<T_Response> header(final HeaderRequestField field, final String value) {
        headers.add(field, value);
        return this;
    }

    public RequestSpec<T_Response> header(final String field, final String value) {
        headers.add(field, value);
        return this;
    }

    public RequestSpec<T_Response> headers(final HttpHeaders headers) {
        this.headers.addAll(headers);
        return this;
    }

    public RequestSpec<T_Response> pathParams(final HttpParams params) {
        pathParams.addAll(params);
        return this;
    }

    public RequestSpec<T_Response> params(final HttpParams params) {
        this.params.addAll(params);
        return reset();
    }

    public RequestSpec<T_Response> relativeUrl(final String url) {
        relativeUrl = url;
        return reset();
    }

    public RequestSpec<T_Response> retryPolicy(final RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
        return this;
    }

    /**
     * Gets the {@link BaseRequest}. A {@link BaseRequest} can be added to {@link RequestQueue} only
     * after it has been prepared by invoking {@link RequestSpec#build()}.
     *
     * @return A {@link BaseRequest},
     */
    public BaseRequest<T_Response> getRequest() {
        return request;
    }

    /**
     * Sets the {@link BaseRequest} to be build.
     * @param request A {@link BaseRequest}.
     *
     * @return This {@link RequestSpec}.
     */
    public RequestSpec<T_Response> request(final BaseRequest<T_Response> request) {
        this.request = request;
        return this;
    }

    /**
     * Gets the timeout for waiting the request response as seconds.
     * @return An {@link int} value as seconds.
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Sets the timeout for waiting the request responseas seconds.
     * @param timeout An {@link int} value as seconds.
     * @return This {@link RequestSpec}.
     */
    public RequestSpec<T_Response> timeout(final int timeout) {
        this.timeout = timeout;
        return this;
    }

    public RequestSpec<T_Response> url(final String url) {
        this.url = url;
        return this;
    }

    /**
     * Add the specified query parameter with the given value.
     *
     * @param key   The name of the parameter to be added as a {@link String}.
     * @param value The value of the parameter.
     * @return This {@link RequestSpec}.
     */
    public RequestSpec<T_Response> param(final String key, final String value) {
        params.add(key, value);
        return reset();
    }

    public RequestSpec<T_Response> param(final String key, final boolean value) {
        params.add(key, Boolean.toString(value));
        return reset();
    }

    public RequestSpec<T_Response> param(final String key, final float value) {
        params.add(key, Float.toString(value));
        return reset();
    }

    public RequestSpec<T_Response> param(final String key, final int value) {
        params.add(key, Integer.toString(value));
        return reset();
    }

    public RequestSpec<T_Response> param(final String key, final long value) {
        params.add(key, Long.toString(value));
        return reset();
    }

    /**
     * Add the specified path parameter with the given value.
     *
     * @param key   The name of the parameter to be added as a {@link String}.
     * @param value The value of the parameter.
     * @return This {@link RequestSpec}.
     */
    public RequestSpec<T_Response> pathParam(final String key, final String value) {
        pathParams.add(key, value);
        return reset();
    }

    public RequestSpec<T_Response> pathParam(final String key, final boolean value) {
        pathParams.add(key, Boolean.toString(value));
        return reset();
    }

    public RequestSpec<T_Response> pathParam(final String key, final float value) {
        pathParams.add(key, Float.toString(value));
        return reset();
    }

    public RequestSpec<T_Response> pathParam(final String key, final int value) {
        pathParams.add(key, Integer.toString(value));
        return reset();
    }

    public RequestSpec<T_Response> pathParam(final String key, final long value) {
        pathParams.add(key, Long.toString(value));
        return reset();
    }

    /**
     * Initialises the given {@link BaseRequest}.
     * @param request A {@link BaseRequest}.
     */
    protected BaseRequest<T_Response> initialise(final BaseRequest<T_Response> request) {
        if (tag != null) {
            tag = Long.toString(System.currentTimeMillis());
        }
        request.setTag(tag);

        if (!headers.isEmpty()) {
            request.setHeaders(headers);
        }
        request.setUrl(getUrl());

        if (retryPolicy == null) {
            retryPolicy = new DefaultRetryPolicy();
        }
        this.request.setRetryPolicy(retryPolicy);
        return this.request;
    }

    /**
     * Builds the URL from the given base and relative URLs, path params, and query params.
     * @return A URL as a {@link String}
     */
    public String getUrl() {
        if (buildUrl == null) {
            final StringBuilder urlBuilder = new StringBuilder();

            if (url != null) {
                urlBuilder.append(url);
            } else {
                urlBuilder.append(baseUrl);

                if (!baseUrl.endsWith(PATH_SEPARATOR)) {
                    if (!relativeUrl.startsWith(PATH_SEPARATOR)) {
                        urlBuilder.append(PATH_SEPARATOR);
                    }
                    urlBuilder.append(relativeUrl);
                } else {
                    if (relativeUrl.startsWith(PATH_SEPARATOR)) {
                        urlBuilder.append(relativeUrl.substring(1));
                    } else {
                        urlBuilder.append(relativeUrl);
                    }
                }
            }

            if (params.hasValues()) {
                urlBuilder.append('?');
                params.encodeParameters(urlBuilder);
            }

            buildUrl = urlBuilder.toString();

            if (pathParams.hasValues()) {
                try {
                    final List<KeyValue<String, String>> keyValues = pathParams.getKeyValues();

                    for (final KeyValue<String, String> keyValue : keyValues) {
                        final String key = "{" + keyValue.getKey() + "}";
                        final String value = URLEncoder.encode(keyValue.getValue(), encoding.name());
                        buildUrl = buildUrl.replace(key, value);
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("Failed to encode path parameter.", e);
                }
            }
        }
        return buildUrl;
    }

    /**
     * Builds the set {@link BaseRequest} and prepares if for the execution using the given request
     * properties.
     *
     * @return A {@link BaseRequest} which is ready to be added into {@link RequestQueue}.
     */
    public BaseRequest<T_Response> build() {
        request.setBuild(true);
        return initialise(request);
    }

    @Override
    public void executeRequest(final RequestCallback<T_Response> callback) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getContext());
        }
        if (!request.isBuild()) {
            build();
        }
        request.setCallback(callback);
        requestQueue.add(request);
    }
}

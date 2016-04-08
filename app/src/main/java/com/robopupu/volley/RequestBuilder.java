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
 * {@link RequestBuilder} is a utility that is used to build a complete URL from given base URL,
 * relative URL, query params, and path params.
 */
public class RequestBuilder<T_Response> implements RequestDelegate<T_Response> {

    protected static final String PATH_SEPARATOR = "/";

    private final HttpHeaders mHeaders;
    private final HttpParams mPathParams;
    private final HttpParams mParams;

    private String mBaseUrl;
    private boolean mBuild;
    private String mBuildUrl;
    private Context mContext;
    private Charset mEncoding;
    private String mRelativeUrl;
    private BaseRequest<T_Response> mRequest;
    private RetryPolicy mRetryPolicy;
    private RequestQueue mRequestQueue;
    private String mTag;
    private int mTimeout; // In milliseconds
    private String mUrl;
    
    public RequestBuilder(final Context context) {
        this(context, null);
    }

    public RequestBuilder(final Context context, final String url) {
        mContext = context;
        mUrl = url;
        mBuild = false;
        mHeaders = new HttpHeaders();
        mEncoding = HttpParams.DEFAULT_ENCODING;
        mPathParams = new HttpParams();
        mParams = new HttpParams();
    }

    public RequestBuilder(final Context context, final String baseUrl, final String relativeUrl) {
        this(context, null);
        mBaseUrl = baseUrl;
        mRelativeUrl = relativeUrl;
    }

    /**
     * Gets the given {@link Context}.
     * @return A {@link Context}.
     */
    public Context getContext() {
        return mContext;
    }

    private RequestBuilder<T_Response> reset() {
        mBuild = false;
        mBuildUrl = null;
        return this;
    }

    protected RequestBuilder<T_Response> encoding(final Charset encoding) {
        mEncoding = encoding;
        return reset();
    }

    public RequestBuilder<T_Response> baseUrl(final String url) {
        mBaseUrl = url;
        return reset();
    }

    public RequestBuilder<T_Response> header(final HeaderRequestField field, final String value) {
        mHeaders.add(field, value);
        return this;
    }

    public RequestBuilder<T_Response> header(final String field, final String value) {
        mHeaders.add(field, value);
        return this;
    }

    public RequestBuilder<T_Response> headers(final HttpHeaders headers) {
        mHeaders.addAll(headers);
        return this;
    }

    public RequestBuilder<T_Response> pathParams(final HttpParams params) {
        mPathParams.addAll(params);
        return this;
    }

    public RequestBuilder<T_Response> params(final HttpParams params) {
        mParams.addAll(params);
        return reset();
    }

    public RequestBuilder<T_Response> relativeUrl(final String url) {
        mRelativeUrl = url;
        return reset();
    }

    public RequestBuilder<T_Response> retryPolicy(final RetryPolicy retryPolicy) {
        mRetryPolicy = retryPolicy;
        return this;
    }

    /**
     * Gets the {@link BaseRequest}. A {@link BaseRequest} can be added to {@link RequestQueue} only
     * after it has been prepared by invoking {@link RequestBuilder#build()}.
     *
     * @return A {@link BaseRequest},
     */
    public BaseRequest<T_Response> getRequest() {
        return mRequest;
    }

    /**
     * Sets the {@link BaseRequest} to be build.
     * @param request A {@link BaseRequest}.
     *
     * @return This {@link RequestBuilder}.
     */
    public RequestBuilder<T_Response> request(final BaseRequest<T_Response> request) {
        mRequest = request;
        return this;
    }

    /**
     * Gets the timeout for waiting the request response as seconds.
     * @return An {@link int} value as seconds.
     */
    public int getTimeout() {
        return mTimeout;
    }

    /**
     * Sets the timeout for waiting the request responseas seconds.
     * @param timeout An {@link int} value as seconds.
     * @return This {@link RequestBuilder}.
     */
    public RequestBuilder<T_Response> timeout(final int timeout) {
        mTimeout = timeout;
        return this;
    }

    public RequestBuilder<T_Response> url(final String url) {
        mUrl = url;
        return this;
    }

    /**
     * Add the specified query parameter with the given value.
     *
     * @param key   The name of the parameter to be added as a {@link String}.
     * @param value The value of the parameter.
     * @return This {@link RequestBuilder}.
     */
    public RequestBuilder<T_Response> param(final String key, final String value) {
        mParams.add(key, value);
        return reset();
    }

    public RequestBuilder<T_Response> param(final String key, final boolean value) {
        mParams.add(key, Boolean.toString(value));
        return reset();
    }

    public RequestBuilder<T_Response> param(final String key, final float value) {
        mParams.add(key, Float.toString(value));
        return reset();
    }

    public RequestBuilder<T_Response> param(final String key, final int value) {
        mParams.add(key, Integer.toString(value));
        return reset();
    }

    public RequestBuilder<T_Response> param(final String key, final long value) {
        mParams.add(key, Long.toString(value));
        return reset();
    }

    /**
     * Add the specified path parameter with the given value.
     *
     * @param key   The name of the parameter to be added as a {@link String}.
     * @param value The value of the parameter.
     * @return This {@link RequestBuilder}.
     */
    public RequestBuilder<T_Response> pathParam(final String key, final String value) {
        mPathParams.add(key, value);
        return reset();
    }

    public RequestBuilder<T_Response> pathParam(final String key, final boolean value) {
        mPathParams.add(key, Boolean.toString(value));
        return reset();
    }

    public RequestBuilder<T_Response> pathParam(final String key, final float value) {
        mPathParams.add(key, Float.toString(value));
        return reset();
    }

    public RequestBuilder<T_Response> pathParam(final String key, final int value) {
        mPathParams.add(key, Integer.toString(value));
        return reset();
    }

    public RequestBuilder<T_Response> pathParam(final String key, final long value) {
        mPathParams.add(key, Long.toString(value));
        return reset();
    }

    /**
     * Initialises the given {@link BaseRequest}.
     * @param request A {@link BaseRequest}.
     */
    protected BaseRequest<T_Response> initialise(final BaseRequest<T_Response> request) {
        if (mTag != null) {
            mTag = Long.toString(System.currentTimeMillis());
        }
        request.setTag(mTag);

        if (!mHeaders.isEmpty()) {
            request.setHeaders(mHeaders);
        }
        request.setUrl(getUrl());

        if (mRetryPolicy == null) {
            mRetryPolicy = new DefaultRetryPolicy();
        }
        mRequest.setRetryPolicy(mRetryPolicy);
        return mRequest;
    }

    /**
     * Builds the URL from the given base and relative URLs, path params, and query params.
     * @return A URL as a {@link String}
     */
    public String getUrl() {
        if (mBuildUrl == null) {
            final StringBuilder urlBuilder = new StringBuilder();

            if (mUrl != null) {
                urlBuilder.append(mUrl);
            } else {
                urlBuilder.append(mBaseUrl);

                if (!mBaseUrl.endsWith(PATH_SEPARATOR)) {
                    if (!mRelativeUrl.startsWith(PATH_SEPARATOR)) {
                        urlBuilder.append(PATH_SEPARATOR);
                    }
                    urlBuilder.append(mRelativeUrl);
                } else {
                    if (mRelativeUrl.startsWith(PATH_SEPARATOR)) {
                        urlBuilder.append(mRelativeUrl.substring(1));
                    } else {
                        urlBuilder.append(mRelativeUrl);
                    }
                }
            }

            if (mParams.hasValues()) {
                urlBuilder.append('?');
                mParams.encodeParameters(urlBuilder);
            }

            mBuildUrl = urlBuilder.toString();

            if (mPathParams.hasValues()) {
                try {
                    final List<KeyValue<String, String>> keyValues = mPathParams.getKeyValues();

                    for (final KeyValue<String, String> keyValue : keyValues) {
                        final String key = "{" + keyValue.getKey() + "}";
                        final String value = URLEncoder.encode(keyValue.getValue(), mEncoding.name());
                        mBuildUrl = mBuildUrl.replace(key, value);
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("Failed to encode path parameter.", e);
                }
            }
        }
        return mBuildUrl;
    }

    /**
     * Builds the set {@link BaseRequest} and prepares if for the execution using the given request
     * properties.
     *
     * @return A {@link BaseRequest} which is ready to be added into {@link RequestQueue}.
     */
    public BaseRequest<T_Response> build() {
        mRequest.setBuild(true);
        return initialise(mRequest);
    }

    @Override
    public void executeRequest(final RequestCallback<T_Response> callback) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getContext());
        }
        if (!mRequest.isBuild()) {
            build();
        }
        mRequest.setCallback(callback);
        mRequestQueue.add(mRequest);
    }
}

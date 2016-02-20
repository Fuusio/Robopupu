/*
 * Copyright (C) 2014 - 2015 Marko Salmela.
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
package org.fuusio.api.network.volley;

import android.util.Base64;

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import org.fuusio.api.util.StringToolkit;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * {@link UrlStack} extends {@link HurlStack} for implementing certificate pinning and for
 * replacing the deprecated HTTP client with {@link OkHttpClient} from Square.
 */
public class UrlStack extends HurlStack {

    private static final String PREFIX_SHA1 = "sha1/";

    private final OkUrlFactory mUrlFactory;

    private OkHttpClient mHttpClient;

    public UrlStack() {
        mHttpClient = createHttpClient();
        mUrlFactory = new OkUrlFactory(mHttpClient);
        setupCertificatePinning();
    }

    protected static OkHttpClient createHttpClient() {
        return new OkHttpClient();
    }

    @Override
    protected HttpURLConnection createConnection(final URL url) throws IOException {
        return mUrlFactory.open(url);
    }

    protected String getHostName() {
        return null;
    }

    protected String[] getPublicKeys() {
        return null;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(final java.security.cert.X509Certificate[] chain, final String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(final java.security.cert.X509Certificate[] chain, final String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            final OkHttpClient httpClient = createHttpClient();

            httpClient.setSslSocketFactory(sslSocketFactory);
            httpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return httpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void setupCertificatePinning() {

        final String hostName = getHostName();
        final String[] publicKeys = getPublicKeys();

        if (!StringToolkit.isEmpty(hostName) && publicKeys != null && publicKeys.length > 0) {
            final CertificatePinner.Builder builder = new CertificatePinner.Builder();

            for (int i = 0; i < publicKeys.length; i++) {
                final byte[] bytes = hexStringToBytes(publicKeys[i]);
                builder.add(hostName, PREFIX_SHA1 + Base64.encodeToString(bytes, Base64.DEFAULT));
            }

            final CertificatePinner certificatePinner = builder.build();
            mUrlFactory.client().setCertificatePinner(certificatePinner);
        }
    }

    private byte[] hexStringToBytes(final String hexString) {
        final byte[] bytes = new byte[hexString.length() / 2];
        int index = 0;

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((Character.digit(hexString.charAt(index++), 16) << 4) + Character.digit(hexString.charAt(index++), 16));
        }
        return bytes;
    }
}

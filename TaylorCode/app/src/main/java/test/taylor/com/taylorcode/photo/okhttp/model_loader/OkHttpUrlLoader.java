package test.taylor.com.taylorcode.photo.okhttp.model_loader;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/** A simple model loader for fetching media over http/https using OkHttp. */
public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    private final Call.Factory client;

    // Public API.
    @SuppressWarnings("WeakerAccess")
    public OkHttpUrlLoader(@NonNull Call.Factory client) {
        this.client = client;
    }

    @Override
    public boolean handles(@NonNull GlideUrl url) {
        return true;
    }

    @Override
    public LoadData<InputStream> buildLoadData(
            @NonNull GlideUrl model, int width, int height, @NonNull Options options) {
        return new LoadData<>(model, new OkHttpStreamFetcher(client, model));
    }

    /** The default factory for {@link OkHttpUrlLoader}s. */
    // Public API.
    @SuppressWarnings("WeakerAccess")
    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private static volatile Call.Factory internalClient;
        private final Call.Factory client;

        private static Call.Factory getInternalClient() {
            if (internalClient == null) {
                synchronized (Factory.class) {
                    if (internalClient == null) {
                        internalClient = (Call.Factory) new OkHttpClient.Builder().eventListener(new EventListener() {
                            @Override
                            public void cacheConditionalHit(@NonNull Call call, @NonNull Response cachedResponse) {
                                super.cacheConditionalHit(call, cachedResponse);
                            }

                            @Override
                            public void cacheHit(@NonNull Call call, @NonNull Response response) {
                                super.cacheHit(call, response);
                            }

                            @Override
                            public void cacheMiss(@NonNull Call call) {
                                super.cacheMiss(call);
                            }

                            @Override
                            public void callEnd(@NonNull Call call) {
                                super.callEnd(call);
                            }

                            @Override
                            public void callFailed(@NonNull Call call, @NonNull IOException ioe) {
                                super.callFailed(call, ioe);
                            }

                            @Override
                            public void callStart(@NonNull Call call) {
                                Log.v("ttaylor","[okhttp-glide]onCallStart()");
                            }

                            @Override
                            public void canceled(@NonNull Call call) {
                                super.canceled(call);
                            }

                            @Override
                            public void connectEnd(@NonNull Call call, @NonNull InetSocketAddress inetSocketAddress, @NonNull Proxy proxy, @Nullable Protocol protocol) {
                                super.connectEnd(call, inetSocketAddress, proxy, protocol);
                            }

                            @Override
                            public void connectFailed(@NonNull Call call, @NonNull InetSocketAddress inetSocketAddress, @NonNull Proxy proxy, @Nullable Protocol protocol, @NonNull IOException ioe) {
                                super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
                            }

                            @Override
                            public void connectStart(@NonNull Call call, @NonNull InetSocketAddress inetSocketAddress, @NonNull Proxy proxy) {
                                super.connectStart(call, inetSocketAddress, proxy);
                            }

                            @Override
                            public void connectionAcquired(@NonNull Call call, @NonNull Connection connection) {
                                super.connectionAcquired(call, connection);
                            }

                            @Override
                            public void connectionReleased(@NonNull Call call, @NonNull Connection connection) {
                                super.connectionReleased(call, connection);
                            }

                            @Override
                            public void dnsEnd(@NonNull Call call, @NonNull String domainName, @NonNull List<InetAddress> inetAddressList) {
                                super.dnsEnd(call, domainName, inetAddressList);
                            }

                            @Override
                            public void dnsStart(@NonNull Call call, @NonNull String domainName) {
                                super.dnsStart(call, domainName);
                            }

                            @Override
                            public void proxySelectEnd(@NonNull Call call, @NonNull HttpUrl url, @NonNull List<Proxy> proxies) {
                                super.proxySelectEnd(call, url, proxies);
                            }

                            @Override
                            public void proxySelectStart(@NonNull Call call, @NonNull HttpUrl url) {
                                super.proxySelectStart(call, url);
                            }

                            @Override
                            public void requestBodyEnd(@NonNull Call call, long byteCount) {
                                super.requestBodyEnd(call, byteCount);
                            }

                            @Override
                            public void requestBodyStart(@NonNull Call call) {
                                super.requestBodyStart(call);
                            }

                            @Override
                            public void requestFailed(@NonNull Call call, @NonNull IOException ioe) {
                                super.requestFailed(call, ioe);
                            }

                            @Override
                            public void requestHeadersEnd(@NonNull Call call, @NonNull Request request) {
                                super.requestHeadersEnd(call, request);
                            }

                            @Override
                            public void requestHeadersStart(@NonNull Call call) {
                                super.requestHeadersStart(call);
                            }

                            @Override
                            public void responseBodyEnd(@NonNull Call call, long byteCount) {
                                super.responseBodyEnd(call, byteCount);
                            }

                            @Override
                            public void responseBodyStart(@NonNull Call call) {
                                super.responseBodyStart(call);
                            }

                            @Override
                            public void responseFailed(@NonNull Call call, @NonNull IOException ioe) {
                                super.responseFailed(call, ioe);
                            }

                            @Override
                            public void responseHeadersEnd(@NonNull Call call, @NonNull Response response) {
                                super.responseHeadersEnd(call, response);
                            }

                            @Override
                            public void responseHeadersStart(@NonNull Call call) {
                                super.responseHeadersStart(call);
                            }

                            @Override
                            public void satisfactionFailure(@NonNull Call call, @NonNull Response response) {
                                super.satisfactionFailure(call, response);
                            }

                            @Override
                            public void secureConnectEnd(@NonNull Call call, @Nullable Handshake handshake) {
                                super.secureConnectEnd(call, handshake);
                            }

                            @Override
                            public void secureConnectStart(@NonNull Call call) {
                                super.secureConnectStart(call);
                            }
                        }).build();
                    }
                }
            }
            return internalClient;
        }

        /** Constructor for a new Factory that runs requests using a static singleton client. */
        public Factory() {
            this(getInternalClient());
        }

        /**
         * Constructor for a new Factory that runs requests using given client.
         *
         * @param client this is typically an instance of {@code OkHttpClient}.
         */
        public Factory(@NonNull Call.Factory client) {
            this.client = client;
        }

        @NonNull
        @Override
        public ModelLoader<GlideUrl, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new OkHttpUrlLoader(client);
        }

        @Override
        public void teardown() {
            // Do nothing, this instance doesn't own the client.
        }
    }
}
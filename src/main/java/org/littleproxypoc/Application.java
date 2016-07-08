package org.littleproxypoc;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSource;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

public class Application {

    public static void main(String[] args) {
        DefaultHttpProxyServer
                .bootstrap()
                .withConnectTimeout(3)
                .withAllowLocalOnly(false)
                .withPort(8080)
                .withFiltersSource(getFiltersSource())
                .start();
    }

    public static HttpFiltersSource getFiltersSource() {
        return new HttpFiltersSourceAdapter() {
            @Override
            public HttpFilters filterRequest(HttpRequest originalRequest) {

                return new HttpFiltersAdapter(originalRequest) {
                    @Override
                    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
                        if (httpObject instanceof HttpRequest) {

                            // todo how do I forward requests to localhost:18080 ???

                            HttpRequest httpRequest = (HttpRequest) httpObject;
                            httpRequest.setMethod(HttpMethod.CONNECT);
//                            httpRequest.headers().remove("Host");
//                            httpRequest.headers().add("Host", "localhost:18080");
                        }
                        return null;
                    }
                };
            }
        };
    }
}

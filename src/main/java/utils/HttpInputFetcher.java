package utils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;

public class HttpInputFetcher {

    private final HttpClient httpClient;
    private final String cookie;

    public HttpInputFetcher(String cookie) {
        this.httpClient = HttpClient.newBuilder()
                .sslContext(trustAllSslContext())
                .build();
        this.cookie = cookie;
    }

    private static SSLContext trustAllSslContext() {
        TrustManager[] trustAll = {new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {}
            public void checkServerTrusted(X509Certificate[] certs, String authType) {}
        }};
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, trustAll, null);
            return ctx;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new InputFetchException("Failed to initialize SSL context", e);
        }
    }

    public List<String> fetchLines(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Cookie", cookie)
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body().lines().toList();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InputFetchException("Request interrupted for: " + url, e);
        } catch (IOException e) {
            throw new InputFetchException("Failed to fetch input from: " + url, e);
        }
    }

}

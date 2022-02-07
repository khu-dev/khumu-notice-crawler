package khumu.spring.batch.configuration;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;

import javax.net.ssl.*;
import javax.security.cert.CertificateException;

public class HttpClientConfig {

    public static HttpComponentsClientHttpRequestFactory trustRequestFactory() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        /*
        * restTemplate SSL ignore
        * @return HttpComponentsClientHttpRequestFactory
        * @throws KeyStoreException
        * @throws NoSuchAlgorithmException
        * @throws KeyManagementException
        */

        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient); return requestFactory;
    }

    public static void setSSL() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        // TODO Auto-generated method stub
                        return null;
                    }
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType){
                        // TODO Auto-generated method stub

                    }
                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType){
                        // TODO Auto-generated method stub
                    }
                }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
}

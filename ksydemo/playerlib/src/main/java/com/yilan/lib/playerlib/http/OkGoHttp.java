package com.yilan.lib.playerlib.http;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class OkGoHttp {

    public static final int CODE_SUCCESS = 200;
    public static final int CODE_TOKEN_VALID = 401;

    public static OkGoHttp singleton;

    public static OkGoHttp getInstance(Application app) {
        if (singleton == null) {
            synchronized (OkGoHttp.class) {
                if (singleton == null) {
                    singleton = new OkGoHttp(app);
                }
            }
        }
        return singleton;
    }


    public OkGoHttp(Application app) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

//        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//        builder.hostnameVerifier(new SafeHostnameVerifier());

        OkGo.getInstance().init(app)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(getGlobalHttpHeaders())                      //全局公共头
                .addCommonParams(getGlobalHttpParams());
    }


    /**
     * 全局header配置
     * @return
     */
    private HttpHeaders getGlobalHttpHeaders() {
        HttpHeaders _headers = new HttpHeaders();
        _headers.put("Content-Type", "application/json");
        return _headers;
    }

    /**
     * 全局params配置
     * @return
     */
    private HttpParams getGlobalHttpParams() {
        HttpParams _params = new HttpParams();
//        _params.put("language", "cn")
        return _params;
    }


    private class SafeTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                for (X509Certificate certificate : chain) {
                    certificate.checkValidity(); //检查证书是否过期，签名是否通过等
                }
            } catch (Exception e) {
                throw new CertificateException(e);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}
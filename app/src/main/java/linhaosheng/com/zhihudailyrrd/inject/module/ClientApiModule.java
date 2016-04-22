package linhaosheng.com.zhihudailyrrd.inject.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import linhaosheng.com.zhihudailyrrd.protocol.ClientApi;
import linhaosheng.com.zhihudailyrrd.protocol.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by coreVK on 2016/4/2.
 */

@Module
public class ClientApiModule {

    private static final String API_VERSION = "4";
    private static final String BASE_URL = "http://news-at.zhihu.com/api/4/";

    @Singleton
    @Provides
    public ClientApi provideClientApi(OkHttpClient client, retrofit.Converter.Factory factory){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(ClientApi.class);
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor providerLog(){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Singleton
    @Provides
    public retrofit.Converter.Factory provideConverter(Gson gson){
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    public Gson provideGson(){
        return new GsonBuilder().serializeNulls().create();
    }

    @Provides
    @Singleton
    public OkHttpClient provideClient(HttpLoggingInterceptor interceptor){
        OkHttpClient client=new OkHttpClient();
        client.interceptors().add(providerLog());
        return client;
    }
}

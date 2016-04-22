package linhaosheng.com.zhihudailyrrd.inject.component;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import linhaosheng.com.zhihudailyrrd.inject.module.ClientApiModule;
import linhaosheng.com.zhihudailyrrd.protocol.ClientApi;
import linhaosheng.com.zhihudailyrrd.service.impl.DailyManager;

/**
 * Created by coreVK on 2016/4/2.
 */
@Singleton
@Component(modules = ClientApiModule.class)
public interface ClientApiComponent {

    void inject(DailyManager dailyManager);
    public Gson getGson();
    public ClientApi getApi();

}

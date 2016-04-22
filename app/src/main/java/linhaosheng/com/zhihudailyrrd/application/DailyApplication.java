package linhaosheng.com.zhihudailyrrd.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import linhaosheng.com.zhihudailyrrd.inject.component.AppLayerComponent;
import linhaosheng.com.zhihudailyrrd.inject.component.ClientApiComponent;
import linhaosheng.com.zhihudailyrrd.inject.component.DaggerAppLayerComponent;
import linhaosheng.com.zhihudailyrrd.inject.component.DaggerClientApiComponent;
import linhaosheng.com.zhihudailyrrd.inject.module.AppLayerModule;
import linhaosheng.com.zhihudailyrrd.inject.module.ClientApiModule;
import linhaosheng.com.zhihudailyrrd.service.impl.DailyManager;
import linhaosheng.com.zhihudailyrrd.ui.fragment.BaseFragment;
import linhaosheng.com.zhihudailyrrd.util.SpUtil;

/**
 * Created by coreVK on 2016/4/2.
 */
public class DailyApplication extends Application {

    /**
     * Dragger2  + Retrofi  + RxJava
     *
     * 开发模式  ：  MVP
     */
    ClientApiComponent clientApiComponent = null;
    AppLayerComponent appLayerComponent = null;
    SharedPreferences preferences;
    @Override
    public void onCreate() {
        super.onCreate();

        clientApiComponent= DaggerClientApiComponent.builder()
                .clientApiModule(new ClientApiModule())
                .build();

        appLayerComponent= DaggerAppLayerComponent.builder()
                .appLayerModule(new AppLayerModule())
                .build();

        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        SpUtil.init(preferences);

       setApplication();
    }

    public ClientApiComponent getClientApiComponent(){
        return clientApiComponent;
    }

    public AppLayerComponent getAppLayerComponent(){
        return appLayerComponent;
    }

    private void setApplication(){
        DailyManager.getApplication(this);
        BaseFragment.getApplication(this);
    }
}

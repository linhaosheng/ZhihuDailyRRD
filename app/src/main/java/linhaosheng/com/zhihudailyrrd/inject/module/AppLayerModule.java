package linhaosheng.com.zhihudailyrrd.inject.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import linhaosheng.com.zhihudailyrrd.service.DataLayer;
import linhaosheng.com.zhihudailyrrd.service.impl.DailyManager;

/**
 * Created by coreVK on 2016/4/2.
 */
@Module
public class AppLayerModule {

    @Singleton
    @Provides
    public DataLayer provideDataLayer(){
        return new DataLayer(new DailyManager());
    }
}

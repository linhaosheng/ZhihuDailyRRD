package linhaosheng.com.zhihudailyrrd.inject.component;

import javax.inject.Singleton;

import dagger.Component;
import linhaosheng.com.zhihudailyrrd.inject.module.AppLayerModule;
import linhaosheng.com.zhihudailyrrd.ui.activity.BaseActivity;
import linhaosheng.com.zhihudailyrrd.ui.dialog.BaseDialogFragment;
import linhaosheng.com.zhihudailyrrd.ui.fragment.BaseFragment;

/**
 * Created by coreVK on 2016/4/2.
 */
@Singleton
@Component(dependencies = AppLayerModule.class)
public interface AppLayerComponent {

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

    void inject(BaseDialogFragment baseDialogFragment);
}

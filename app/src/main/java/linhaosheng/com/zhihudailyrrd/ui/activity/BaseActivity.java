package linhaosheng.com.zhihudailyrrd.ui.activity;

import android.os.Bundle;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import linhaosheng.com.zhihudailyrrd.application.DailyApplication;
import linhaosheng.com.zhihudailyrrd.service.DataLayer;

/**
 * Created by coreVK on 2016/4/2.
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    @Inject
    DataLayer mDataLayer;

    private DailyApplication mDailyApplication;

    public void setmDailyApplication() {
        mDailyApplication = (DailyApplication) getApplication();
        mDailyApplication.getAppLayerComponent().inject(this);
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle bundle);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        afterCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

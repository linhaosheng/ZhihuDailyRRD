package linhaosheng.com.zhihudailyrrd.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import linhaosheng.com.zhihudailyrrd.application.DailyApplication;
import linhaosheng.com.zhihudailyrrd.service.DataLayer;


/**
 * Created by coreVK on 2016/4/2.
 */
public abstract class BaseFragment extends RxFragment {
    public static final String TAG = BaseFragment.class.getSimpleName();
    protected View mRootView;
    static DailyApplication mDailyApplication=null;

    @Inject
    DataLayer mDataLayer;

    public static void getApplication(DailyApplication dailyApplication){
        mDailyApplication=dailyApplication;
    }

    public BaseFragment() {
        mDailyApplication.getAppLayerComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        afterCreate();
    }

    /**
     * 托管的时候解绑
     */
    @Override
    public void onDetach() {
        super.onDetach();
        ButterKnife.unbind(this);
    }

    public DataLayer getmDataLayer() {
        return mDataLayer;
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate();
}

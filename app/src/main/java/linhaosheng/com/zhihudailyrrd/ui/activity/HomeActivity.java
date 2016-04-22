package linhaosheng.com.zhihudailyrrd.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import linhaosheng.com.zhihudailyrrd.R;
import linhaosheng.com.zhihudailyrrd.ui.fragment.NewsListFragment;
import linhaosheng.com.zhihudailyrrd.util.ExitClickUtil;

/**
 * Created by coreVK on 2016/4/2.
 */
public class HomeActivity extends BaseActivity {

    private FrameLayout frameLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        setmDailyApplication();
        frameLayout = (FrameLayout) findViewById(R.id.fl_container);
        Fragment fragment = NewsListFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_container, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (!ExitClickUtil.isClickAgain()) {
            Snackbar.make(frameLayout, getString(R.string.click_again_to_exit_app), Snackbar.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }
}

package linhaosheng.com.zhihudailyrrd.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import linhaosheng.com.zhihudailyrrd.R;
import linhaosheng.com.zhihudailyrrd.model.TodayNews;
import linhaosheng.com.zhihudailyrrd.ui.fragment.NewsDetailFragment;

/**
 * Created by coreVK on 2016/4/3.
 */
public class NewsDetailActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        TodayNews.Story story = (TodayNews.Story) getIntent().getSerializableExtra("story");
        showNewsFragment(story);
    }

    private void showNewsFragment(TodayNews.Story story) {
        Fragment fragment = NewsDetailFragment.Instance(story);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rl_news_container, fragment, NewsDetailFragment.TAG);
        ft.commit();
    }

    public static void start(Context context, TodayNews.Story story) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("story", story);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

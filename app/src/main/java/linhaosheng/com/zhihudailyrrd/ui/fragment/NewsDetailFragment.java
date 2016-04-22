package linhaosheng.com.zhihudailyrrd.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle.FragmentEvent;

import butterknife.Bind;
import linhaosheng.com.zhihudailyrrd.R;
import linhaosheng.com.zhihudailyrrd.model.News;
import linhaosheng.com.zhihudailyrrd.model.TodayNews;
import linhaosheng.com.zhihudailyrrd.util.HtmlUtil;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by coreVK on 2016/4/3.
 * 新闻内容
 */
public class NewsDetailFragment extends BaseFragment {

    @Bind(R.id.wv_news)
    WebView mWvNews;
    @Bind(R.id.cpb_loading)
    ContentLoadingProgressBar mCpbLoading;
    @Bind(R.id.iv_header)
    ImageView mImageView;
    @Bind(R.id.tv_source)
    TextView mTvSource;
    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.nested_view)
    NestedScrollView mNestedScrollView;
    @Bind(R.id.tv_load_empty)
    TextView mTvLoadEmpty;
    @Bind(R.id.tv_load_error)
    TextView mTvLoadError;

    private TodayNews.Story mStory;
    private News mNews;


    public static Fragment Instance(TodayNews.Story story) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("story", story);
        Fragment fragment = new NewsDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_detail;
    }

    @Override
    protected void afterCreate() {
        mStory = (TodayNews.Story) getArguments().getSerializable("story");
        init();
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        //服务器端的数据
        Observable<News> network = getmDataLayer().getmDailyService().getNews(mStory.getmId());

        //缓存的数据
        Observable<News> cache = getmDataLayer().getmDailyService().getLocalNews(String.valueOf(mStory.getmId()));

        //输出数据前，先缓存到本地
        network = network.doOnNext(new Action1<News>() {
            @Override
            public void call(News news) {
                getmDataLayer().getmDailyService().cacheNews(news);
            }
        });

        //默认从本地读取数据
        Observable<News> source = Observable.concat(cache, network)
                .first(new Func1<News, Boolean>() {
                    @Override
                    public Boolean call(News news) {
                        //如果本地数据存在
                        return news != null;
                    }
                });

        //使用RXlifeCycle用来严格控制由于发布一个订阅后，由于没有及时取消，导致Activity/Fragment无法及时销毁而导致的内存泄漏

        source.compose(this.<News>bindUntilEvent(FragmentEvent.PAUSE))
                .doOnNext(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        if (news != null) {
                            getmDataLayer().getmDailyService().cacheNews(news);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        //解除订阅关系
                        hideLoading();
                        if (mNews == null) {
                            mTvLoadEmpty.setVisibility(View.GONE);
                            mTvLoadError.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        hideLoading();
                        mNews = news;
                        if (mNews == null) {
                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        } else {
                            Picasso.with(getActivity())
                                    .load(news.getmImage())
                                    .into(mImageView);
                            mTvSource.setText(news.getmImageSource());

                            String htmlData = HtmlUtil.createHtmlData(news);
                            mWvNews.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
                            mTvLoadEmpty.setVisibility(View.GONE);
                        }
                        mTvLoadError.setVisibility(View.GONE);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //隐藏progress
                        hideLoading();
                    }
                });
    }

    /**
     * 初始化
     */
    private void init() {
        /**
         * 显示actionBar
         */
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setHasOptionsMenu(true);
        mNestedScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mNestedScrollView.setElevation(0);

        mWvNews.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWvNews.setElevation(0);
        //自动加载图片
        mWvNews.getSettings().setLoadsImagesAutomatically(true);
        //设置缓存模式
        mWvNews.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        //开启DOM storage API 功能
        mWvNews.getSettings().setDomStorageEnabled(true);
        //为可折叠toolbar设置标题
        mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));
    }

    /**
     * 显示Loading
     */
    private void showLoading() {
        mCpbLoading.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏Loading
     */
    private void hideLoading() {
        mCpbLoading.setVisibility(View.GONE);
    }
}

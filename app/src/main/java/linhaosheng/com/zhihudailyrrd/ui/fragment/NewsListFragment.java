package linhaosheng.com.zhihudailyrrd.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.trello.rxlifecycle.FragmentEvent;

import org.joda.time.DateTime;

import java.util.ArrayList;

import butterknife.Bind;
import linhaosheng.com.zhihudailyrrd.R;
import linhaosheng.com.zhihudailyrrd.model.TodayNews;
import linhaosheng.com.zhihudailyrrd.ui.activity.NewsDetailActivity;
import linhaosheng.com.zhihudailyrrd.ui.adapter.NewsListAdapter;
import linhaosheng.com.zhihudailyrrd.view.DividerItemDecoration;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by coreVK on 2016/4/2.
 */
public class NewsListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    public static final int SPAN_COUNT = 1;  //列数

    NewsListAdapter mNewsListAdapter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rcv_news_list)
    RecyclerView mRcvNewsList;
    @Bind(R.id.srl_news_list)
    SwipeRefreshLayout mSrlNewsList;
    @Bind(R.id.tv_load_empty)
    TextView mTvLoadEmpty;
    @Bind(R.id.tv_load_error)
    TextView mTvLoadError;
    @Bind(R.id.coor)
    CoordinatorLayout coordinatorLayout;

    public static Fragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void afterCreate() {
        init();
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {
      String nowDateStr= DateTime.now().minusDays(1).toString("yyyyMMdd");
        //缓存一天的数据
        Observable<TodayNews>cache=getmDataLayer().getmDailyService().getLocalTodayNews(nowDateStr);

        //服务器端的数据
        Observable<TodayNews>network=getmDataLayer().getmDailyService().getTodayNews();

        //输出前缓存一下
        network=network.doOnNext(new Action1<TodayNews>() {
            @Override
            public void call(TodayNews todayNews) {
                getmDataLayer().getmDailyService().cacheTodayNews(todayNews);
            }
        });

        //先获取缓存里的数据
        Observable<TodayNews>source=Observable.concat(cache,network)
                .first(new Func1<TodayNews, Boolean>() {
                    @Override
                    public Boolean call(TodayNews todayNews) {
                        return todayNews!=null;
                    }
                });
        /**
         * 把网络请求绑定在Fragment的生命周期上
         */
        source.compose(this.<TodayNews>bindUntilEvent(FragmentEvent.PAUSE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {  //需要在主线程上运行的，observeOn(AndroidSchedulers.mainThread())之后必须在
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        //解除订阅关系
                        hideProgress();
                        if (mNewsListAdapter.getmStories().isEmpty()){
                            mTvLoadEmpty.setVisibility(View.GONE);
                            mTvLoadError.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TodayNews>() {   //取数据
                    @Override
                    public void call(TodayNews todayNews) {
                        hideProgress();
                        if (todayNews.getmStories() == null) {
                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        } else {
                            mNewsListAdapter.setStories(todayNews.getmStories(), todayNews.getmTopStories());
                            mNewsListAdapter.notifyDataSetChanged();
                            mTvLoadEmpty.setVisibility(View.GONE);
                        }
                        mTvLoadEmpty.setVisibility(View.GONE);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        //当有错误时停止动画显示
                        hideProgress();
                        Snackbar.make(coordinatorLayout,getString(R.string.load_fail),Snackbar.LENGTH_SHORT).show();
                        mTvLoadEmpty.setVisibility(View.GONE);
                        mTvLoadError.setVisibility(View.VISIBLE);
                    }
                });
    }

    /**
     * 显示加载框
     */
    public void showProgress(){
        mSrlNewsList.post(new Runnable() {
            @Override
            public void run() {
                //刷新动画
                mSrlNewsList.setRefreshing(true);
            }
        });
    }
    /**
     * 关闭加载框
     */

    public void hideProgress(){
        mSrlNewsList.post(new Runnable() {
            @Override
            public void run() {
                mSrlNewsList.setRefreshing(false);
            }
        });
    }
    /**
     * 初始化主键
     */
    private void init() {
        mToolbar.setTitle(getString(R.string.today_news));
        //添加toolbar
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        //为下拉刷新组件绑定监听器
        mSrlNewsList.setOnRefreshListener(this);

        //为下来刷新组件设置CircleProgress主色调
        mSrlNewsList.setColorSchemeColors(getResources().getColor(R.color.color_primary));

        //实例化布局管理器
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        mRcvNewsList.setLayoutManager(manager);

        //初始化并绑定适配器
        mNewsListAdapter = new NewsListAdapter(getActivity(), new ArrayList<TodayNews.Story>(), new ArrayList<TodayNews.Story>(), this);
        mRcvNewsList.setAdapter(mNewsListAdapter);
        //设置ItemView的divider
        mRcvNewsList.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onClick(View v) {
     final int position=mRcvNewsList.getChildAdapterPosition(v);
        if (RecyclerView.NO_POSITION!=position) {
            TodayNews.Story story=mNewsListAdapter.getItemData(position);
            NewsDetailActivity.start(getActivity(),story);
        }
    }

    @Override
    public void onRefresh() {
    loadData();
    }
}

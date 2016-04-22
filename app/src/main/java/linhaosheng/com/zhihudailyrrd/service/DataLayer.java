package linhaosheng.com.zhihudailyrrd.service;

import linhaosheng.com.zhihudailyrrd.model.News;
import linhaosheng.com.zhihudailyrrd.model.StartImage;
import linhaosheng.com.zhihudailyrrd.model.TodayNews;
import rx.Observable;

/**
 * Created by coreVK on 2016/4/2.
 */
public class DataLayer {

    public DailyService mDailyService;

    public DataLayer(DailyService dailyService){
        this.mDailyService=dailyService;
    }

    public DailyService getmDailyService(){
        return mDailyService;
    }

    public interface DailyService {

        /**
         * 获取每日最新日报的新闻列表
         */

        Observable<TodayNews> getTodayNews();

        /**
         * 获取新闻
         */
        Observable<News> getNews(long newsId);

        /**
         * 获取启动图片
         */
        Observable<StartImage> getStartImage();

        /**
         * 获取本地新闻
         */
        Observable<News> getLocalNews(final String id);

        /**
         * 获取本地今日热文
         */
        Observable<TodayNews> getLocalTodayNews(final String date);

        /**
         * 缓存新闻
         */
        void cacheNews(final News news);

        /**
         * 获取缓存在本地的新闻
         */

        void cacheTodayNews(final TodayNews todayNews);
    }
}

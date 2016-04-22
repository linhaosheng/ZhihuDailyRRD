package linhaosheng.com.zhihudailyrrd.service.impl;

import linhaosheng.com.zhihudailyrrd.application.DailyApplication;
import linhaosheng.com.zhihudailyrrd.model.News;
import linhaosheng.com.zhihudailyrrd.model.StartImage;
import linhaosheng.com.zhihudailyrrd.model.TodayNews;
import linhaosheng.com.zhihudailyrrd.service.DataLayer;
import linhaosheng.com.zhihudailyrrd.util.SpUtil;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by linhao on 2016/4/2.
 */
public  class DailyManager implements DataLayer.DailyService {

     static DailyApplication dailyApplication=null;

    public static void getApplication(DailyApplication dailyApplication1){
        dailyApplication=dailyApplication1;
    }
    public DailyManager() {
        dailyApplication.getClientApiComponent().inject(this);
    }


    @Override
    public Observable<TodayNews> getTodayNews() {
        return dailyApplication.getClientApiComponent().getApi().getTodayNews();
    }

    @Override
    public Observable<News> getNews(long newsId) {
        return dailyApplication.getClientApiComponent().getApi().getNews(newsId);
    }

    @Override
    public Observable<StartImage> getStartImage() {
        return dailyApplication.getClientApiComponent().getApi().getStartImage();
    }

    @Override
    public Observable<News> getLocalNews(final String id) {
        return Observable.create(new Observable.OnSubscribe<News>() {
            @Override
            public void call(Subscriber<? super News> subscriber) {
                try {
                    subscriber.onStart();
                    String json = SpUtil.find(id);
                    News news = dailyApplication.getClientApiComponent().getGson().fromJson(json, News.class);
                    subscriber.onNext(news);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<TodayNews> getLocalTodayNews(final String date) {
        return Observable.create(new Observable.OnSubscribe<TodayNews>() {
            @Override
            public void call(Subscriber<? super TodayNews> subscriber) {
                try{
                    subscriber.onStart();
                    String json=SpUtil.find(date);
                    TodayNews todayNews=dailyApplication.getClientApiComponent().getGson().fromJson(json,TodayNews.class);
                    subscriber.onNext(todayNews);
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public void cacheNews(News news) {
     SpUtil.saveOrUpdate(String.valueOf(news.getmId()),dailyApplication.getClientApiComponent().getGson().toJson(news));
    }

    @Override
    public void cacheTodayNews(TodayNews todayNews) {
        SpUtil.saveOrUpdate(String.valueOf(todayNews.getmDate()),dailyApplication.getClientApiComponent().getGson().toJson(todayNews));
    }
}

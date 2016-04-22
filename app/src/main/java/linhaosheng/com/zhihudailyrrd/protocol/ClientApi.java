package linhaosheng.com.zhihudailyrrd.protocol;

import linhaosheng.com.zhihudailyrrd.model.News;
import linhaosheng.com.zhihudailyrrd.model.StartImage;
import linhaosheng.com.zhihudailyrrd.model.TodayNews;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by coreVK on 2016/4/2.
 * 所有请求的url
 */
public interface ClientApi {

    /**
     * FIELD
     */

    String FIELD_NEWS_ID = "newsId";

    /**
     * URL
     */

    //获取启动页面的图片
    String URL_GET_START_IMAGE = "start-image/1080*1776";

    //获取最新日报新闻列表
    String URL_GET_LATEST_NEWS = "news/latest";

    //获取新闻
    String URL_GET_NEWS = "news/{newsId}";

    /**
     * 获取今日日报新闻列表
     *
     * @return
     */
    @GET(URL_GET_LATEST_NEWS)
    Observable<TodayNews> getTodayNews();

    /**
     * 获取启动图片的列表
     *
     * @return
     */
    @GET(URL_GET_START_IMAGE)
    Observable<StartImage> getStartImage();

    /**
     * 获取新闻
     *
     * @param newsId
     * @return
     */
    @GET(URL_GET_NEWS)
    Observable<News> getNews(@Path(FIELD_NEWS_ID) long newsId);
}

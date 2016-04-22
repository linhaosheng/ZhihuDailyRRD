package linhaosheng.com.zhihudailyrrd.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by coreVK on 2016/4/2.
 * 今日热文
 */
public class TodayNews implements Serializable {

    //日期,唯一,重复的直接覆盖
    @SerializedName("date")      //用于对应的gson解析中的时间
    private String mDate;

    //当日新闻
    @SerializedName("stories")
    private List<Story>mStories;

    //顶部的ViewPage滚动显示的新闻
    @SerializedName("top_stories")
    private List<Story>mTopStories;

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public List<Story> getmTopStories() {
        return mTopStories;
    }

    public void setmTopStories(List<Story> mTopStories) {
        this.mTopStories = mTopStories;
    }

    public List<Story> getmStories() {
        return mStories;
    }

    public void setmStories(List<Story> mStories) {
        this.mStories = mStories;
    }

    @Override
    public String toString() {
        return "TodayNews{" +
                "mDate='" + mDate + '\'' +
                ", mStories=" + mStories +
                ", mTopStories=" + mTopStories +
                '}';
    }


    public static class Story implements Serializable{

        //id
        @SerializedName("id")
        private long mId;

        //标题
        @SerializedName("title")
        private String mTitle;

        //图像地址(官方 API 使用数组形式。目前暂未有使用多张图片的情形出现，曾见无 images 属性的情况，请在使用中注意 ）
        @SerializedName("images")
        private List<String>mImageUrls;

        //图像地址 ，只有topStories才有
        @SerializedName("image")
        private String mImageUrl;

        //类型，作用未知
        @SerializedName("type")
        private int mType;

        //供Google Analytics使用
        @SerializedName("ga_prefix")
        private String mGaPrefix;

        //消息是否包含多张图片（仅出现在包含多图的新闻中）
        @SerializedName("multipic")
        private boolean mMultiPic;

        public String getmTitle() {
            return mTitle;
        }

        public void setmTitle(String mTitle) {
            this.mTitle = mTitle;
        }

        public long getmId() {
            return mId;
        }

        public void setmId(long mId) {
            this.mId = mId;
        }

        public List<String> getmImageUrls() {
            return mImageUrls;
        }

        public void setmImageUrls(List<String> mImageUrls) {
            this.mImageUrls = mImageUrls;
        }

        public String getmImageUrl() {
            return mImageUrl;
        }

        public void setmImageUrl(String mImageUrl) {
            this.mImageUrl = mImageUrl;
        }

        public int getmType() {
            return mType;
        }

        public void setmType(int mType) {
            this.mType = mType;
        }

        public String getmGaPrefix() {
            return mGaPrefix;
        }

        public void setmGaPrefix(String mGaPrefix) {
            this.mGaPrefix = mGaPrefix;
        }

        public boolean ismMultiPic() {
            return mMultiPic;
        }

        public void setmMultiPic(boolean mMultiPic) {
            this.mMultiPic = mMultiPic;
        }

        @Override
        public String toString() {
            return "Story{" +
                    "mId=" + mId +
                    ", mTitle='" + mTitle + '\'' +
                    ", mImageUrls=" + mImageUrls +
                    ", mImageUrl='" + mImageUrl + '\'' +
                    ", mType=" + mType +
                    ", mGaPrefix='" + mGaPrefix + '\'' +
                    ", mMultiPic=" + mMultiPic +
                    '}';
        }
    }
}

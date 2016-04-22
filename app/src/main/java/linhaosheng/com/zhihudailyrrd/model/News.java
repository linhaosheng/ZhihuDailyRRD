package linhaosheng.com.zhihudailyrrd.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by coreVK on 2016/4/2.
 * 详细新闻
 */
public class News implements Serializable {

    //新闻的id
    @SerializedName("id")
    private Long mId;

    //HTML格式的新闻
    @SerializedName("body")
    private String mBody;

    //图片的内容提供方，为了避免被非法起诉使用图片，在显示图片时最好附上其版权信息。
    @SerializedName("image_source")
    private String mImageSource;

    //新闻标题
    @SerializedName("title")
    private String mTitle;

    //获得的图片同 最新消息 获得的图片分辨率不同。这里获得的是在文章浏览界面中使用的大图。
    @SerializedName("image")
    private String mImage;

    //供在线查看内容分享到SNS 用的URL
    @SerializedName("share_url")
    private String mShareUrl;

    //供手机的WebView使用的
    @SerializedName("js")
    private List<String> mjsList;

    //供手机端的 WebView(UIWebView) 使用
    @SerializedName("css")
    private List<String> mCssList;

    //供 Google Analytics 使用
    @SerializedName("ga_prefix")
    private String mGaPrefix;

    //这篇文章的推荐者
    @SerializedName("recommenders")
    private List<Recommender> mRecommenderList;

    //新闻的类型
    @SerializedName("type")
    private String mType;

    /**
     * 在较为特殊的情况下，知乎日报可能将某个主题日报的站外文章推送至知乎日报首页。
     * 此时返回的 JSON 数据缺少 body，image-source，image，js 属性。
     * 多出 theme_name，editor_name，theme_id 三个属性。type 由 0 变为 1。
     */

    //主题id
    @SerializedName("theme_id")
    private String mThemeId;

    //主题名
    @SerializedName("theme_name")
    private String mThemeName;

    //编辑者名
    @SerializedName("editor_name")
    private String mEditorName;

    //栏目
    @SerializedName("section")
   private Section mSection;

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public String getmBody() {
        return mBody;
    }

    public void setmBody(String mBody) {
        this.mBody = mBody;
    }

    public String getmImageSource() {
        return mImageSource;
    }

    public void setmImageSource(String mImageSource) {
        this.mImageSource = mImageSource;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmShareUrl() {
        return mShareUrl;
    }

    public void setmShareUrl(String mShareUrl) {
        this.mShareUrl = mShareUrl;
    }

    public List<String> getMjsList() {
        return mjsList;
    }

    public void setMjsList(List<String> mjsList) {
        this.mjsList = mjsList;
    }

    public List<String> getmCssList() {
        return mCssList;
    }

    public void setmCssList(List<String> mCssList) {
        this.mCssList = mCssList;
    }

    public String getmGaPrefix() {
        return mGaPrefix;
    }

    public void setmGaPrefix(String mGaPrefix) {
        this.mGaPrefix = mGaPrefix;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public List<Recommender> getmRecommenderList() {
        return mRecommenderList;
    }

    public void setmRecommenderList(List<Recommender> mRecommenderList) {
        this.mRecommenderList = mRecommenderList;
    }

    public String getmThemeId() {
        return mThemeId;
    }

    public void setmThemeId(String mThemeId) {
        this.mThemeId = mThemeId;
    }

    public String getmThemeName() {
        return mThemeName;
    }

    public void setmThemeName(String mThemeName) {
        this.mThemeName = mThemeName;
    }

    public String getmEditorName() {
        return mEditorName;
    }

    public void setmEditorName(String mEditorName) {
        this.mEditorName = mEditorName;
    }

    public Section getmSection() {
        return mSection;
    }

    public void setmSection(Section mSection) {
        this.mSection = mSection;
    }

    @Override
    public String toString() {
        return "News{" +
                "mId=" + mId +
                ", mBody='" + mBody + '\'' +
                ", mImageSource='" + mImageSource + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mShareUrl='" + mShareUrl + '\'' +
                ", mjsList=" + mjsList +
                ", mCssList=" + mCssList +
                ", mGaPrefix='" + mGaPrefix + '\'' +
                ", mRecommenderList=" + mRecommenderList +
                ", mType='" + mType + '\'' +
                ", mThemeId='" + mThemeId + '\'' +
                ", mThemeName='" + mThemeName + '\'' +
                ", mEditorName='" + mEditorName + '\'' +
                ", mSection=" + mSection +
                '}';
    }


    /**
     * 栏目的信息
     */
    public static class Section implements Serializable {

        //该栏目的id；
        @SerializedName("id")
        private Long mSectionId;

        //该栏目的名称
        @SerializedName("name")
        private String mName;

        //栏目的缩略图
        @SerializedName("thumbnail")
        private String mThumbnail;

        public Long getmSectionId() {
            return mSectionId;
        }

        public void setmSectionId(Long mSectionId) {
            this.mSectionId = mSectionId;
        }

        public String getmThumbnail() {
            return mThumbnail;
        }

        public void setmThumbnail(String mThumbnail) {
            this.mThumbnail = mThumbnail;
        }

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        @Override
        public String toString() {
            return "Section{" +
                    "mSectionId=" + mSectionId +
                    ", mName='" + mName + '\'' +
                    ", mThumbnail='" + mThumbnail + '\'' +
                    '}';
        }
    }

    /**
     * 推荐者的信息
     */
    public static class Recommender implements Serializable {

        //这篇文章的推荐者的头像
        @SerializedName("avatar")
        private String mAvatarUrl;

        public String getmAvatarUrl() {
            return mAvatarUrl;
        }

        public void setmAvatarUrl(String mAvatarUrl) {
            this.mAvatarUrl = mAvatarUrl;
        }
    }

}

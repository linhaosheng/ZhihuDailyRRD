package linhaosheng.com.zhihudailyrrd.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by coreVK on 2016/4/2.
 */
public class StartImage implements Serializable {

    //图像的URL
    @SerializedName("img")
    private String url;

    //供显示使用的版权信息
    @SerializedName("text")
    private String mText;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    @Override
    public String toString() {
        return "StartImage{" +
                "url='" + url + '\'' +
                ", mText='" + mText + '\'' +
                '}';
    }
}

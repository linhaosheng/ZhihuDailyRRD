package linhaosheng.com.zhihudailyrrd.util;

import android.content.SharedPreferences;

/**
 * Created by coreVK on 2016/4/2.
 * 存储本地新闻和今日任务
 */
public class SpUtil {

    private static SharedPreferences mPreferences;

    public static void init(SharedPreferences preferences) {
        mPreferences = preferences;
    }

    /**
     * 根据key保存json数据
     *
     * @param key
     * @param json
     */
    public static void saveOrUpdate(String key, String json) {
        mPreferences.edit().putString(key, json).apply();
    }

    /**
     * 根据key来获取json数据
     *
     * @param key
     * @return
     */
    public static String find(String key) {
        return mPreferences.getString(key, null);
    }

    /**
     * 根据key来删除json数据
     *
     * @param key
     */
    public static void delete(String key) {
        mPreferences.edit().remove(key).apply();
    }

    /**
     * 删除所有json数据
     */
    public static void clearAll() {
        mPreferences.edit().clear().apply();
    }
}

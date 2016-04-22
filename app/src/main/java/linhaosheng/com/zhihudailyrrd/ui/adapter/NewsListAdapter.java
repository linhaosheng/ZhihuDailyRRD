package linhaosheng.com.zhihudailyrrd.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import linhaosheng.com.zhihudailyrrd.R;
import linhaosheng.com.zhihudailyrrd.model.TodayNews;
import linhaosheng.com.zhihudailyrrd.view.TextSliderView;

/**
 * Created by coreVK on 2016/4/2.
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TodayNews.Story> mStories;
    private Context mContext;
    private View.OnClickListener mListener;
    private List<TodayNews.Story> mHeaderStories;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_IMEM = 1;

    public NewsListAdapter(Context context, List<TodayNews.Story> stories, List<TodayNews.Story> headerStories, View.OnClickListener listener) {
        mContext = context;
        mStories = stories;
        mListener = listener;
        mHeaderStories = headerStories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TYPE_IMEM) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item_news, parent, false);
            return new ItemVH(itemView);
        } else if (viewType == TYPE_HEADER) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item_header, parent, false);
            return new HeaderVH(itemView);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_HEADER;
        }else {
            return TYPE_IMEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemVH) {
            ItemVH itemVH = (ItemVH) holder;
            final TodayNews.Story story = mStories.get(position - (mHeaderStories == null || mHeaderStories.isEmpty() ? 0 : 1));
            //设置新闻标题
            itemVH.mTvTitle.setText(story.getmTitle());
            //加载图片
            Picasso.with(mContext)
                    .load(story.getmImageUrls().get(0))
                    .placeholder(R.drawable.ic_placeholder)
                    .into(itemVH.mIvNewsThumbnail);
            itemVH.itemView.setOnClickListener(mListener);
        } else if (holder instanceof HeaderVH) {
            HeaderVH headerVH = (HeaderVH) holder;
            headerVH.mSlHeader.removeAllSliders();
            for (int i = 0; i < mHeaderStories.size(); i++) {
                final TodayNews.Story story = mHeaderStories.get(i);
                TextSliderView textSliderView = new TextSliderView(mContext);
                textSliderView
                        .description(story.getmTitle())
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                        .image(story.getmImageUrl());
                headerVH.mSlHeader.addSlider(textSliderView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mStories.size() + (mHeaderStories == null || mHeaderStories.isEmpty() ? 0 : 1);
    }

    public List<TodayNews.Story> getmStories() {
        return mStories;
    }

    public TodayNews.Story getItemData(int position) {
        position = getItemCount() == mHeaderStories.size() ? position : position - 1;
        return getmStories().get(position);
    }

    public TodayNews.Story getHeaderData(int position) {
        return getHeaderStories().get(position);
    }

    public List<TodayNews.Story> getHeaderStories() {
        return mHeaderStories;
    }

    public void setStories(List<TodayNews.Story> stories, List<TodayNews.Story> topStories) {
        mStories = stories;
        mHeaderStories = topStories;
    }

    public static class ItemVH extends RecyclerView.ViewHolder {
        public CircleImageView mIvNewsThumbnail;
        public TextView mTvTitle;

        public ItemVH(View itemView) {
            super(itemView);
            mIvNewsThumbnail = (CircleImageView) itemView.findViewById(R.id.iv_story_thumbnail);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }


    public static class HeaderVH extends RecyclerView.ViewHolder {
        public SliderLayout mSlHeader;
        public PagerIndicator mPagerIndicator;

        public HeaderVH(View headerView) {
            super(headerView);
            mSlHeader = (SliderLayout) headerView.findViewById(R.id.sl_header);
            mPagerIndicator = (PagerIndicator) headerView.findViewById(R.id.pi_header);
            mSlHeader.setCustomIndicator(mPagerIndicator);
        }
    }

}

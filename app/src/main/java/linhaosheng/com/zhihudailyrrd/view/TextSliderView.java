package linhaosheng.com.zhihudailyrrd.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import linhaosheng.com.zhihudailyrrd.R;

/**
 * Created by coreVK on 2016/4/3.
 */
public class TextSliderView  extends BaseSliderView{

    public TextSliderView(Context context){
        super(context);
    }

    @Override
    public View getView() {
        View view= LayoutInflater.from(getContext()).inflate(R.layout.slider_item,null);
        ImageView target=(ImageView)view.findViewById(R.id.iv_slider);
        TextView title=(TextView)view.findViewById(R.id.tv_title);
        title.setText(getDescription());
        bindEventAndShow(view,target);
        return view;
    }
}

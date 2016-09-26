package newsday.zxsc.com.newsday.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.bean.NewsDayInfoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean;
import newsday.zxsc.com.newsday.callback.OnRecyclerViewListener;

/**
 * Created by lenovo on 2016/9/18.
 */
public class NewsContentAdapter extends BaseLoadingAdapter<ContentlistBean>{
    private OnRecyclerViewListener listener;
    private List<ContentlistBean> myDatas;
    private DisplayImageOptions options;

    public OnRecyclerViewListener getListener() {
        return listener;
    }

    public void setListener(OnRecyclerViewListener listener) {
        this.listener = listener;
    }

    public NewsContentAdapter(List<ContentlistBean> myDatas, Context context, RecyclerView recyclerView){
        super(myDatas, context, recyclerView);
        this.myDatas=myDatas;
        options=new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.icon_aboutme)
                .showImageOnFail(R.drawable.icon_aboutme)
                .showImageOnLoading(R.drawable.img_news_loding)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolderViewHolder(ViewGroup parent) {
        View itemView=super.inflater.inflate(R.layout.item_recyler_holder,parent,false);
        HolderViewHolder holderViewHolder=new HolderViewHolder(itemView);
        return holderViewHolder;
    }

    @Override
    public void onBindHolderViewHolder(RecyclerView.ViewHolder viewHolder,final int position) {
        HolderViewHolder holderViewHolder=(HolderViewHolder)viewHolder;
        holderViewHolder.holder_tv.setText(myDatas.get(position).getTitle());
        List<ContentlistBean.ImageEntity> entity =myDatas.get(position).getImageurls();
        if (entity.size()!= 0) {
            ImageLoader.getInstance().displayImage(entity.get(0).getUrl()
                    , holderViewHolder.holder_img, options);
        }
        if (listener !=null){
            ((HolderViewHolder)viewHolder).relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    listener.onItemClickListener(v,position);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateNormalViewHolder(ViewGroup parent){
        NormalViewHolder normalViewHolder = new NormalViewHolder(inflater.inflate(R.layout.item_recyler_new_info,parent,false));
        return normalViewHolder;
    }

    @Override
    public void onBindNormalViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        NormalViewHolder normalViewHolder = (NormalViewHolder) viewHolder;
        ContentlistBean contentlistBean =myDatas.get(position);
        normalViewHolder.item_news_content.setText(contentlistBean.getDesc());
        normalViewHolder.item_news_time.setText(contentlistBean.getPubDate());
        normalViewHolder.item_news_title.setText(contentlistBean.getTitle());
        List<ContentlistBean.ImageEntity> entity =myDatas.get(position).getImageurls();
        if (entity.size() != 0)
            ImageLoader.getInstance().displayImage(entity.get(0).getUrl()
                    , normalViewHolder.item_news_icon, options);
        if (listener !=null){
            ((NormalViewHolder)viewHolder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(v,position);
                }
            });
        }
    }
    private class HolderViewHolder extends RecyclerView.ViewHolder{
        public TextView holder_tv;
        public ImageView holder_img;
        private RelativeLayout relativeLayout;
        public HolderViewHolder(View itemView) {
            super(itemView);
            holder_tv=(TextView)itemView.findViewById(R.id.item_head_text);
            holder_img=(ImageView)itemView.findViewById(R.id.item_head_img);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.rl_holder);
        }
    }
    private class NormalViewHolder extends RecyclerView.ViewHolder{
        public TextView item_news_title,item_news_time,item_news_content;
        public ImageView item_news_icon;
        public LinearLayout linearLayout;
        public NormalViewHolder(View itemView) {
            super(itemView);
            item_news_title=(TextView)itemView.findViewById(R.id.item_recycle_title);
            item_news_time=(TextView)itemView.findViewById(R.id.item_recycle_time);
            item_news_content=(TextView)itemView.findViewById(R.id.item_recycle_content);
            item_news_icon=(ImageView)itemView.findViewById(R.id.item_recycle_img);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.ll_normal);
        }
    }
}

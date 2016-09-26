package newsday.zxsc.com.newsday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.bean.VolleyNewsBean;
import newsday.zxsc.com.newsday.dohttp.VolleyDoHttp;


/**
 * Created by lenovo on 2016/9/9.
 */
public class NewsAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private List<VolleyNewsBean.NewslistBean> datas;
    private Context context;
    private VolleyDoHttp doHttp;
    public NewsAdapter(Context context,List<VolleyNewsBean.NewslistBean> datas){
        this.datas=datas;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return datas.size();
    }
    @Override
    public VolleyNewsBean.NewslistBean getItem(int position){
        return datas.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_news,null);
            viewHolder=new ViewHolder();
            viewHolder.title=(TextView)convertView.findViewById(R.id.tv_title);
            viewHolder.ctime=(TextView)convertView.findViewById(R.id.tv_time);
            viewHolder.description=(TextView)convertView.findViewById(R.id.tv_from);
            viewHolder.picUrl=(ImageView)convertView.findViewById(R.id.iv_news_icon);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        VolleyNewsBean.NewslistBean newsInfo=getItem(position);
        viewHolder.title.setText(newsInfo.getTitle());
        viewHolder.ctime.setText(newsInfo.getCtime());
        viewHolder.description.setText(newsInfo.getDescription());
        viewHolder.picUrl.setTag(newsInfo.getDescription());
        viewHolder.picUrl.setImageResource(R.drawable.icon_aboutme);
//        doHttp.imageLoaderByLruCache(newsInfo.getPicUrl(),viewHolder.picUrl);
        VolleyDoHttp.getInstance().imageRequeByGET(newsInfo.getPicUrl(),viewHolder.picUrl);
        return convertView;
    }
    class ViewHolder{
        TextView title,ctime,description;
        ImageView picUrl;
    }
}

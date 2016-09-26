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

/**
 * Created by lenovo on 2016/9/22.
 */
public class CollectAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;
    private LayoutInflater inflater;
    private Onclick onclick;

    public interface Onclick {
        void onimgClick(View view, int position);
    }
    public Onclick getOnclick() {
        return onclick;
    }
    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }
    public CollectAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater = inflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHodle hodle;
        if (convertView == null) {
            hodle = new ViewHodle();
            convertView = inflater.inflate(R.layout.search_listview_text, null);
            hodle.textView = (TextView) convertView.findViewById(R.id.tv_collect_text);
            hodle.imageView = (ImageView) convertView.findViewById(R.id.iv_collect_delete);
            convertView.setTag(hodle);
        }
        hodle = (ViewHodle) convertView.getTag();
        hodle.textView.setText(list.get(position));
        hodle.imageView.setImageResource(R.drawable.edit_close);
        hodle.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclick!= null) {
                    onclick.onimgClick(v,position);
                }
            }
        });
        return convertView;
    }
    class ViewHodle {
        TextView textView;
        ImageView imageView;
    }
}

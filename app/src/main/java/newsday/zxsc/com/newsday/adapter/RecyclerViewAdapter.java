package newsday.zxsc.com.newsday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.callback.OnRecyclerViewListener;

/**
 * Created by lenovo on 2016/9/7.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater inflater;
    private List<String>dates;
    private Context context;
    private OnRecyclerViewListener listener;
    private List<Integer> sums;

    public OnRecyclerViewListener getListener() {
        return listener;
    }

    public void setListener(OnRecyclerViewListener listener) {
        this.listener = listener;
    }

    public RecyclerViewAdapter(Context context, List<String> dates){
        super();
        this.context=context;
        this.dates=dates;
        inflater = LayoutInflater.from(context);
        sums=new ArrayList<>();
        for(int i=0;i<dates.size();i++){
            sums.add((int)(50+Math.random()*220));
        }
    }
    @Override
    public int getItemCount(){
        return dates.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=inflater.inflate(R.layout.item_recycler_view,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams=((MyViewHolder)holder).tv_text.getLayoutParams();
        layoutParams.height=sums.get(position);
//        layoutParams.width=sums.get(position);
        ((MyViewHolder)holder).tv_text.setLayoutParams(layoutParams);
        ((MyViewHolder)holder).tv_text.setText(dates.get(position));
        if(listener !=null){
            ((MyViewHolder)holder).tv_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(view,holder.getLayoutPosition());
                }
            });
        }
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    public TextView tv_text;
    public MyViewHolder(View itemView) {
        super(itemView);
        tv_text=(TextView)itemView.findViewById(R.id.item_tv_text);
    }
}

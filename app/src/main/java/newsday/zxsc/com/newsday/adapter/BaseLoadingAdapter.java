package newsday.zxsc.com.newsday.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.callback.OnLoadingListener;

/**
 * Created by lenovo on 2016/9/18.
 */
public abstract class BaseLoadingAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<T> myDatas;
    private Context context;
    protected LayoutInflater inflater;
    private RecyclerView recyclerView;
    private LoadingViewHolder loadingViewHolder;
    private boolean isLoading=false;
    private boolean firstLoading=true;
    private static final int HOLDER_TYPE_NORMAL=0;
    private static final int HOLDER_TYPE_LOADING=1;
    private static final int HOLDER_TYPE_HOLDER=2;
    private OnLoadingListener onLoadingListener;
    public List<T> getMyDatas() {
        return myDatas;
    }
    public void setMyDatas(List<T> myDatas) {
        this.myDatas = myDatas;
    }
    public void setOnLoadingListener(OnLoadingListener onLoadingListener) {
        setOnScrollListener();
        this.onLoadingListener = onLoadingListener;
    }
    public BaseLoadingAdapter(List<T> myDatas, Context context, RecyclerView recyclerView){
        super();
        this.context=context;
        this.inflater=LayoutInflater.from(context);
        this.recyclerView=recyclerView;
        this.myDatas=myDatas;
//        notifyLoading();
    }
    private void notifyLoading(){
        if(myDatas.size()!=0&&(myDatas.get(myDatas.size()-1)!=null)){
            myDatas.add(null);
            notifyItemInserted(myDatas.size()-1);
        }
    }
    public void setLoadingComplete(){//todo 加载成功
        if(myDatas.size()>0&&(myDatas.get(myDatas.size()-1)==null)){
            isLoading=false;
            myDatas.remove(myDatas.size()-1);
            notifyItemRemoved(myDatas.size()-1);
        }
    }
    public void setLoadingError(){//todo 加载失败
        if(loadingViewHolder!=null){
            isLoading=false;
            loadingViewHolder.progressBar.setVisibility(View.GONE);
            loadingViewHolder.textView.setText("加载失败，请点击屏幕重新加载数据！");
            loadingViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(onLoadingListener!=null){//todo 加载失败重新加载的操作
                        isLoading=true;
                        loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                        loadingViewHolder.textView.setText("正在加载中...请稍等！");
                        onLoadingListener.onLoading();
                    }
                }
            });
        }
    }
    public void setLoadingNormore(){//todo 没有更多的数据
        isLoading=false;
        if(loadingViewHolder!=null){
            loadingViewHolder.progressBar.setVisibility(View.GONE);
            loadingViewHolder.textView.setText("没有更多的数据！");
        }
    }
    private void setOnScrollListener(){
        if(this.recyclerView==null){
            return;
        }
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            //todo 滑动状态的更改
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState){
                super.onScrollStateChanged(recyclerView, newState);
            }
            //todo 滑动过程当中
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);
                if(!ViewCompat.canScrollVertically(recyclerView,1)){
                    if(!isLoading&&!firstLoading){
                        notifyLoading();
                        isLoading=true;
                        if(loadingViewHolder!=null){
                            loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                            loadingViewHolder.textView.setText("正在加载当中！");
                        }
                        if(onLoadingListener!=null)
                            onLoadingListener.onLoading();
                    }
                }
                if (firstLoading)
                    firstLoading = false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return myDatas.size();
    }
    @Override
    public int getItemViewType(int position) {
        T t=myDatas.get(position);
        if(t==null){
            return HOLDER_TYPE_LOADING;
        }else if(position==0){
            return HOLDER_TYPE_HOLDER;
        }else{
            return HOLDER_TYPE_NORMAL;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        RecyclerView.ViewHolder viewHolder=null;
        switch(viewType){
            case HOLDER_TYPE_HOLDER:
                viewHolder=onCreateHolderViewHolder(parent);
                break;
            case HOLDER_TYPE_LOADING:
                viewHolder=onCreateLoadingViewHolder(parent);
                loadingViewHolder = (LoadingViewHolder) viewHolder;
                break;
            case HOLDER_TYPE_NORMAL:
                viewHolder=onCreateNormalViewHolder(parent);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType=getItemViewType(position);
        switch(viewType){
            case HOLDER_TYPE_HOLDER:
                onBindHolderViewHolder(holder,position);
                break;
            case HOLDER_TYPE_LOADING:
                onBindLoadingViewHolder(holder);
                break;
            case HOLDER_TYPE_NORMAL:
                onBindNormalViewHolder(holder,position);
                break;
        }
    }
    private class LoadingViewHolder extends RecyclerView.ViewHolder{
        public ProgressBar progressBar;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar=(ProgressBar)itemView.findViewById(R.id.item_recycle_loading_pg);
            textView=(TextView)itemView.findViewById(R.id.item_recycle_loading_tv);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.item_recycle_loading_layout);
        }
    }
    public RecyclerView.ViewHolder onCreateLoadingViewHolder(ViewGroup parent){
        View itemView=inflater.inflate(R.layout.item_recycler_foot,parent,false);
        LoadingViewHolder loadingViewHolder=new LoadingViewHolder(itemView);
        return loadingViewHolder;
    }
    public void onBindLoadingViewHolder(RecyclerView.ViewHolder holder){

    }
    public abstract RecyclerView.ViewHolder onCreateHolderViewHolder(ViewGroup parent);
    public abstract void onBindHolderViewHolder(RecyclerView.ViewHolder viewHolder,int position);

    public abstract RecyclerView.ViewHolder onCreateNormalViewHolder(ViewGroup parent);
    public abstract void onBindNormalViewHolder(RecyclerView.ViewHolder viewHolder,int position);
    public void addAll(List<T> addDatas){
        if(this.myDatas!=null)
        this.myDatas.addAll(addDatas);
        notifyDataSetChanged();
    }
    public void clearAll(){
        if(this.myDatas!=null){
            this.myDatas.clear();
            notifyDataSetChanged();
        }
    }
}

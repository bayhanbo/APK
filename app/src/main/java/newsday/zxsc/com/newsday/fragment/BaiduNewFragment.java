package newsday.zxsc.com.newsday.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.activity.ShowWebViewActivity;
import newsday.zxsc.com.newsday.adapter.NewsContentAdapter;
import newsday.zxsc.com.newsday.application.MyApplication;
import newsday.zxsc.com.newsday.bean.NewsDayInfoBean;
import newsday.zxsc.com.newsday.callback.OnLoadingListener;
import newsday.zxsc.com.newsday.callback.OnRecyclerViewListener;
import newsday.zxsc.com.newsday.url.GetURL;
import newsday.zxsc.com.newsday.bean.NewsDayInfoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean;
/**
 * Created by lenovo on 2016/9/18.
 */
public class BaiduNewFragment extends Fragment{
    private View contentView;
    private RecyclerView recyclerView;
    private String newName;
    private RequestQueue queue;
    private Gson gson;
    private List<ContentlistBean> contentlistBens;
    private NewsContentAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final int ADD_DATA_FLAG=0;//上拉加载
    private final int UP_DATA_FLAG=1;//下拉刷新
    private int all_page;
    private int newPage=1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView=inflater.inflate(R.layout.activity_new_baidu_fragment,container,false);
        newName=getArguments().getString("newName");
        queue= MyApplication.getInstance().getQueue();
        gson=MyApplication.getInstance().getGson();
        return contentView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        recyclerView=(RecyclerView)view.findViewById(R.id.rv_baiDu_fragment);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.srl_baiDu_refreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.lightcoral);//颜色可以多个选择
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                getBaiduNewsInfo(UP_DATA_FLAG,1);
            }
        });
        contentlistBens=new ArrayList<>();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new NewsContentAdapter(contentlistBens, getActivity(), recyclerView);
        adapter.setListener(new OnRecyclerViewListener(){
            @Override
            public void onItemClickListener(View view, int position) {
                Intent intent=new Intent(getActivity(), ShowWebViewActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("URL",contentlistBens.get(position).getLink());
                intent.putExtras(bundle);
                startActivity(intent);
            }
            @Override
            public void onItemClickActionListener(View view, int position) {
            }
        });
        adapter.setOnLoadingListener(new OnLoadingListener(){
            @Override
            public void onLoading() {
                getBaiduNewsInfo(ADD_DATA_FLAG,newPage);
            }
        });
        recyclerView.setAdapter(adapter);
        getBaiduNewsInfo(ADD_DATA_FLAG,newPage);
        super.onViewCreated(view, savedInstanceState);
    }
    private void getBaiduNewsInfo(final int getDataType, int pager){
        if(all_page>0&&newPage>all_page){
            swipeRefreshLayout.setRefreshing(false);
            adapter.setLoadingNormore();
        }
        StringRequest stringRequest=new StringRequest(GetURL.findNewsName(newName,newPage),new Response.Listener<String>(){
            @Override
            public void onResponse(String s){
                try {
                    NewsDayInfoBean infoBean=gson.fromJson(s,NewsDayInfoBean.class);
                    contentlistBens=infoBean.getShowapi_res_body().getPagebean().getContentlist();
                    all_page=infoBean.getShowapi_res_body().getPagebean().getAllPages();
                    onGetDataSuccess(getDataType);
                }catch(Exception e){
                    getDefult(getDataType);
                }
//                Log.i("Volley_response",infoBean.toString());
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError){
                getDefult(getDataType);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map=new HashMap<>();
                map.put("apikey",GetURL.APIKEY);
                return map;
            }
        };
        queue.add(stringRequest);
    }
    private void getDefult(int getDataType){
        switch(getDataType){
            case ADD_DATA_FLAG:
                adapter.setLoadingError();
                break;
            case UP_DATA_FLAG:
                swipeRefreshLayout.setRefreshing(false);
                break;
        }
    }
    private void onGetDataSuccess(int getDataType){
        if (contentlistBens == null || contentlistBens.size() == 0)
            return;
        Iterator<ContentlistBean> iterator = contentlistBens.iterator();
        ContentlistBean temp = null;
        // 遍历数据源 并且把所有的没有图片地址的信息删除掉
        while (iterator.hasNext()) {
            temp = iterator.next();
            if (temp==null||temp.getImageurls().size()==0||temp.getImageurls().get(0).getUrl()== null)
                iterator.remove();
        }
        /*for (ContentlistBean bean : contentlistBeans) {
            if (bean.getImageurls().size() == 0)
                Log.i("ImageUrlTest", newName + "不含图片");
            else {
                Log.i("ImageUrlTest", newName + "含有");
            }
        }*/
        switch(getDataType){
            case ADD_DATA_FLAG:
                adapter.setLoadingComplete();
                if(contentlistBens!=null)
                    adapter.addAll(contentlistBens);
                    newPage++;
                break;
            case UP_DATA_FLAG:
                if(contentlistBens!=null){
                    adapter.clearAll();
                    adapter.addAll(contentlistBens);
                }
                swipeRefreshLayout.setRefreshing(false);
                newPage=2;
                break;
        }
    }
}

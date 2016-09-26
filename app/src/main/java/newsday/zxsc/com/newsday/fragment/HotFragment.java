package newsday.zxsc.com.newsday.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

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
import newsday.zxsc.com.newsday.adapter.RecyclerViewAdapter;
import newsday.zxsc.com.newsday.application.MyApplication;
import newsday.zxsc.com.newsday.bean.NewsDayInfoBean;
import newsday.zxsc.com.newsday.callback.OnLoadingListener;
import newsday.zxsc.com.newsday.callback.OnRecyclerViewListener;
import newsday.zxsc.com.newsday.url.GetURL;

public class HotFragment extends Fragment{
//    private RecyclerViewAdapter adapter;
//    private RecyclerView recyclerView;
//    private List<String> list;
//    private LinearLayoutManager linearLayoutManagerV;
//    private LinearLayoutManager linearLayoutManagerH;
//    private GridLayoutManager gridLayoutManagerV;
//    private StaggeredGridLayoutManager gridLayoutManagerH;

    private View contentView;
//    private RecyclerView recyclerView;
//    private String newName;
//    private RequestQueue queue;
//    private Gson gson;
//    private List<NewsDayInfoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlistBens;
//    private NewsContentAdapter adapter;
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private final int ADD_DATA_FLAG=0;//上拉加载
//    private final int UP_DATA_FLAG=1;//下拉刷新
//    private int all_page;
//    private int newPage=1;

    private WebView webView;
    private ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView=inflater.inflate(R.layout.activity_hot_fragment,container,false);
//        newName="奇闻";
//        queue= MyApplication.getInstance().getQueue();
//        gson=MyApplication.getInstance().getGson();
        return contentView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
//        recyclerView=(RecyclerView)view.findViewById(R.id.rv_hot_recyclerView);
//        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.srl_hot_refreshLayout);
//        swipeRefreshLayout.setColorSchemeResources(R.color.cornflowerblue);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
//            @Override
//            public void onRefresh(){
//                getQiwenHotInfo(UP_DATA_FLAG,1);
//            }
//        });
//        contentlistBens=new ArrayList<>();
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this.getActivity());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        adapter = new NewsContentAdapter(contentlistBens,getActivity(),recyclerView);
//        adapter.setListener(new OnRecyclerViewListener(){
//            @Override
//            public void onItemClickListener(View view, int position) {
//                Intent intent=new Intent(getActivity(), ShowWebViewActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putString("URL",contentlistBens.get(position).getLink());
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//            @Override
//            public void onItemClickActionListener(View view, int position) {
//            }
//        });
//        adapter.setOnLoadingListener(new OnLoadingListener(){
//            @Override
//            public void onLoading(){
//                getQiwenHotInfo(ADD_DATA_FLAG,newPage);
//            }
//        });
//        recyclerView.setAdapter(adapter);
//        getQiwenHotInfo(ADD_DATA_FLAG,newPage);
        super.onViewCreated(view,savedInstanceState);
        webView=(WebView)view.findViewById(R.id.wv_hot_web);
        imageView=(ImageView)view.findViewById(R.id.iv_hot_return);
        webView.loadUrl("http://news.cngold.org/");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(webView.canGoBack())
                    webView.goBack();
            }
        });
////        setHasOptionsMenu(true);
//        initHipping();
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL));
//        list=new ArrayList<>();
//        for(int i=0;i<100;i++){
//            list.add("冯"+i);
//        }
//        adapter=new RecyclerViewAdapter(getActivity(),list);
//        adapter.setListener(new OnRecyclerViewListener(){
//            @Override
//            public void onItemClickListener(View view, int position) {
//                list.remove(position);
//                adapter.notifyItemRemoved(position);
//            }
//            @Override
//            public void onItemClickActionListener(View view, int position) {
//
//            }
//        });
//        recyclerView.setAdapter(adapter);
    }
//    private void getQiwenHotInfo(final int getDataType, int pager){
//        if(all_page>0&&newPage>all_page){
//            swipeRefreshLayout.setRefreshing(false);
//            adapter.setLoadingNormore();
//        }
//        StringRequest stringRequest=new StringRequest(GetURL.findNewsHot(newName,newPage),new Response.Listener<String>(){
//            @Override
//            public void onResponse(String s){
//                try {
//                    NewsDayInfoBean infoBean=gson.fromJson(s,NewsDayInfoBean.class);
//                    contentlistBens=infoBean.getShowapi_res_body().getPagebean().getContentlist();
//                    all_page=infoBean.getShowapi_res_body().getPagebean().getAllPages();
//                    onGetDataSuccess(getDataType);
//                }catch(Exception e){
//                    getDefult(getDataType);
//                }
////                Log.i("Volley_response",infoBean.toString());
//            }
//        }, new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError volleyError){
//                getDefult(getDataType);
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> map=new HashMap<>();
//                map.put("apikey",GetURL.APIKEY);
//                return map;
//            }
//        };
//        queue.add(stringRequest);
//    }
//    private void getDefult(int getDataType){
//        switch(getDataType){
//            case ADD_DATA_FLAG:
//                adapter.setLoadingError();
//                break;
//            case UP_DATA_FLAG:
//                swipeRefreshLayout.setRefreshing(false);
//                break;
//        }
//    }
//    private void onGetDataSuccess(int getDataType){
//        if (contentlistBens == null || contentlistBens.size() == 0)
//            return;
//        Iterator<NewsDayInfoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> iterator = contentlistBens.iterator();
//        NewsDayInfoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean temp = null;
//        // 遍历数据源 并且把所有的没有图片地址的信息删除掉
//        while (iterator.hasNext()) {
//            temp = iterator.next();
//            if (temp==null||temp.getImageurls().size()==0||temp.getImageurls().get(0).getUrl()== null)
//                iterator.remove();
//        }
//        /*for (ContentlistBean bean : contentlistBeans) {
//            if (bean.getImageurls().size() == 0)
//                Log.i("ImageUrlTest", newName + "不含图片");
//            else {
//                Log.i("ImageUrlTest", newName + "含有");
//            }
//        }*/
//        switch(getDataType){
//            case ADD_DATA_FLAG:
//                adapter.setLoadingComplete();
//                if(contentlistBens!=null)
//                    adapter.addAll(contentlistBens);
//                newPage++;
//                break;
//            case UP_DATA_FLAG:
//                if(contentlistBens!=null){
//                    adapter.clearAll();
//                    adapter.addAll(contentlistBens);
//                }
//                swipeRefreshLayout.setRefreshing(false);
//                newPage=2;
//                break;
//        }
//    }
//    private void initHipping(){
//        //todo 列表模式 横竖
//        linearLayoutManagerV=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
//        linearLayoutManagerH=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
//        //todo 网格模式
//        gridLayoutManagerV=new GridLayoutManager(getActivity(),4,LinearLayoutManager.VERTICAL,false);
//        gridLayoutManagerH=new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL);
//    }
//    //todo 无效的
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.toolbar_hot,menu);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch(item.getItemId()){
//            case R.id.menu_add:
//                list.add(1,"itemOne");
//                adapter.notifyItemInserted(1);
//                break;
//            case R.id.menu_remove:
//                list.remove(1);
//                adapter.notifyItemRemoved(1);
//                break;
//            case R.id.menu_item_vertical:
//                recyclerView.setLayoutManager(linearLayoutManagerV);
//                break;
//            case R.id.menu_item_horizontal:
//                recyclerView.setLayoutManager(linearLayoutManagerH);
//                break;
//            case R.id.menu_item_griddingV:
//                recyclerView.setLayoutManager(gridLayoutManagerV);
//                break;
//            case R.id.menu_item_griddingH:
//                recyclerView.setLayoutManager(gridLayoutManagerH);
//                break;
//        }
//        return true;
//    }
}

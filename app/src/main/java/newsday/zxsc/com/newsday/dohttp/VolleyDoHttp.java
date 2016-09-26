package newsday.zxsc.com.newsday.dohttp;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.activity.SettingActivity;
import newsday.zxsc.com.newsday.adapter.NewsAdapter;
import newsday.zxsc.com.newsday.application.MyApplication;
import newsday.zxsc.com.newsday.bean.VolleyNewsBean;
import newsday.zxsc.com.newsday.url.GetURL;

/**
 * Created by lenovo on 2016/9/13.
 */
public class VolleyDoHttp{
    private static VolleyDoHttp http;
    private VolleyHttpBack back;
    public VolleyHttpBack getBack() {
        return back;
    }
    public void setBack(VolleyHttpBack back) {
        this.back = back;
    }
    private VolleyDoHttp(){

    }
    public  static  VolleyDoHttp getInstance(){
        if(http==null){
            http=new VolleyDoHttp();
        }
        return http;
    }
    public interface VolleyHttpBack{
        void onResponse(String s);
        void onErrorResponse(VolleyError volleyError);
    }
    public void stringRequeByGET(String url/*, final ListView listView, final SettingActivity settingActivity*/){
        RequestQueue queue= MyApplication.getQueue();
        final Gson gson=MyApplication.getGson();
        //todo 1.请求方式 2.请求地址 3.请求成功回调 4.请求失败回调
        StringRequest stringRequest=new StringRequest(Request.Method.GET,url,new Response.Listener<String>(){
            @Override
            public void onResponse(String s){//todo 请求成功回调 UIThread
                VolleyNewsBean bean=gson.fromJson(s,VolleyNewsBean.class);
                bean.getNewslist();
                if(back!=null){
                    back.onResponse(s);
                }
//                NewsAdapter newsAdapter=new NewsAdapter(settingActivity,bean.getNewslist());
//                listView.setAdapter(newsAdapter);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError){//todo 请求失败回调
                if(back!=null){
                    back.onErrorResponse(volleyError);
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                    //todo 获取到请求头
                    Map<String, String> apikey=new HashMap<>();
                    apikey.put("apikey", GetURL.APIKEY);
                    return apikey;
            }
        };
        stringRequest.setTag(url);
        queue.add(stringRequest);//todo 添加到queue当中
    }
    private class myImageCache implements ImageLoader.ImageCache{
        private LruCache<String,Bitmap> lruCache;
        public myImageCache(){
            int maxSize=10*1024*1024;
            this.lruCache=new LruCache<String,Bitmap>(maxSize){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount();
                }
            };
        }
        @Override
        public Bitmap getBitmap(String s) {
            return lruCache.get(s);
        }
        @Override
        public void putBitmap(String s, Bitmap bitmap) {
            if(s!=null&&bitmap!=null){
                lruCache.put(s,bitmap);
            }
        }
    }
    public  void imageLoaderByLruCache(String picUrl,ImageView imageView){
        ImageLoader imageLoader=new ImageLoader(MyApplication.getQueue(),new myImageCache());
        ImageLoader.ImageListener imageListener=ImageLoader.getImageListener(imageView,R.drawable.icon_aboutme,R.mipmap.ic_launcher);
        imageLoader.get(picUrl,imageListener);
    }
    public void imageRequeByGET(String url,final ImageView imageView){
        RequestQueue queue= MyApplication.getQueue();
        ImageRequest imageRequest=new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }, 100, 100, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imageView.setImageResource(R.drawable.icon_aboutme);
            }
        });
        imageRequest.setTag(url);
        queue.add(imageRequest);
    }
//    public void jsonObjectRequeByGET(String url){
//        RequestQueue queue= MyApplication.getQueue();
//        final Gson gson=MyApplication.getGson();
//        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//            }
//        });
//        queue.add(jsonObjectRequest);
//    }

}

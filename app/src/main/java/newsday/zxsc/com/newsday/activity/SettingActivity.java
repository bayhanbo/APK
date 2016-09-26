package newsday.zxsc.com.newsday.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InterfaceAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.adapter.NewsAdapter;
import newsday.zxsc.com.newsday.application.MyApplication;
import newsday.zxsc.com.newsday.bean.VolleyNewsBean;
import newsday.zxsc.com.newsday.dohttp.VolleyDoHttp;
import newsday.zxsc.com.newsday.loader.ImgerLoader;
import newsday.zxsc.com.newsday.url.GetURL;

/**
 * Created by lenovo on 2016/9/8.
 */
public class SettingActivity extends AppCompatActivity{
    private ListView list;
    private ImgerLoader imgerLoader=new ImgerLoader();
//    private TextView tv;
//    private WebView webView;
//    private ImageView imageView;
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_fragment);
        list=(ListView)findViewById(R.id.lv_test);
        VolleyDoHttp volleyDoHttp=VolleyDoHttp.getInstance();
        final Gson gson= MyApplication.getGson();
        volleyDoHttp.setBack(new VolleyDoHttp.VolleyHttpBack(){
            @Override
            public void onResponse(String s){
                VolleyNewsBean volleyNewsBean=gson.fromJson(s,VolleyNewsBean.class);
                list.setAdapter(new NewsAdapter(SettingActivity.this,volleyNewsBean.getNewslist()));
            }
            @Override
            public void onErrorResponse(VolleyError volleyError){

            }
        });
        volleyDoHttp.stringRequeByGET(GetURL.BAIDUGETURL+"?num=30&page=1");
//        new NewsAsync().execute("http://apis.baidu.com/txapi/tiyu/tiyu?key=7953ce4c523638a25a8f7fa917c0179c&num=20&page=1");
//        tv=(TextView)findViewById(R.id.tv_test);
//        Parameters para=new Parameters();
//        para.put("city","chengdu");
//        ApiStoreSDK.execute("http://apis.baidu.com/txapi/social/social?num=20&page=1",ApiStoreSDK.GET,para,new ApiCallBack(){
//            @Override
//            public void onSuccess(int i, String s){
//                Log.i("sdkdemo", "onSuccess");
//                tv.setText(s);
//            }
//            @Override
//            public void onComplete(){
//                Log.i("sdkdemo", "onComplete");
////                super.onComplete();
//            }
//            @Override
//            public void onError(int i, String s, Exception e){
//                Log.i("sdkdemo","onError,status:"+i);
//                Log.i("sdkdemo","errMsg:"+(e == null ? "": e.getMessage()));
////                tv.setText(getStack);
//            }
//        });
//        webView=(WebView)findViewById(R.id.webView_test);
//        imageView=(ImageView)findViewById(R.id.imageView_test);
//        new loadReadThread().start();
//        jsonTest();
//        webView.loadUrl("https://www.baidu.com");
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url){
//                view.loadUrl(url);
//                return true;
//            }
//        });
    }
    private String getJSONStr(String urlStr){
        String json="";
        StringBuilder stringBuilder=new StringBuilder();
        try {
            URL url=new URL(urlStr);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("apikey","7953ce4c523638a25a8f7fa917c0179c");
            InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String readLine="";
            while((readLine=bufferedReader.readLine())!=null){
                stringBuilder.append(readLine);
            }
            inputStreamReader.close();
            bufferedReader.close();
        } catch (Exception e){
        }
        json=stringBuilder.toString();
        return json;
    }
    private List<VolleyNewsBean.NewslistBean> getNewsListFromJSON(String json){
        List<VolleyNewsBean.NewslistBean> newsInfos=new ArrayList<>();
        Gson gson=new Gson();
        try {
            JSONObject jsonObject=new JSONObject(json);
            if(jsonObject.getInt("code")==200){
                JSONArray jsonArray=jsonObject.getJSONArray("newslist");
                JSONObject indexObject;
                VolleyNewsBean.NewslistBean indexInfo;
                for(int index=0;index<jsonArray.length();index++){
                    indexObject=jsonArray.getJSONObject(index);
//                    indexInfo=new NewsInfo(indexObject.getString("title"),indexObject.getString("ctime"),indexObject.getString("picUrl"),indexObject.getString("description"));
                    indexInfo=gson.fromJson(indexObject.toString(),VolleyNewsBean.NewslistBean.class);
                    newsInfos.add(indexInfo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsInfos;
    }
//    public class NewsAdapter extends BaseAdapter {
//        private List<VolleyNewsBean.NewslistBean> datas1;
//        public NewsAdapter(List<VolleyNewsBean.NewslistBean> datas1){
//            this.datas1=datas1;
//        }
//        @Override
//        public int getCount() {
//            return datas1.size();
//        }
//        @Override
//        public VolleyNewsBean.NewslistBean getItem(int position){
//            return datas1.get(position);
//        }
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent){
//            View view=getLayoutInflater().inflate(R.layout.item_news,null,false);
//            VolleyNewsBean.NewslistBean newsInfo=getItem(position);
//            ((TextView)view.findViewById(R.id.tv_title)).setText(newsInfo.getTitle());
//            ((TextView)view.findViewById(R.id.tv_time)).setText(newsInfo.getCtime());
//            ((TextView)view.findViewById(R.id.tv_from)).setText(newsInfo.getDescription());
//            VolleyDoHttp.getInstance().imageRequeByGET(newsInfo.getPicUrl(),(ImageView)view.findViewById(R.id.iv_news_icon));
////            new ImgerLoader().showHttpBitmapFromURLByThread(newsInfo.picUrl,(ImageView)view.findViewById(R.id.iv_news_icon));
////            imgerLoader.showHttpBitmapFromURLByAsyncTask(newsInfo.picUrl,(ImageView)view.findViewById(R.id.iv_news_icon));
//            return view;
//        }
//        private Bitmap getHttpBitmap(String urlStr){
//            Bitmap bitmap=null;
//            try {
//                URL url=new URL(urlStr);
//                bitmap=BitmapFactory.decodeStream(url.openStream());
//            } catch (Exception e){
//            }
//            return bitmap;
//        }
//    }
//    private class NewsAsync extends AsyncTask<String,Integer,List<NewsInfo>>{
//        @Override
//        protected void onPostExecute(List<NewsInfo> newsInfos) {
//            super.onPostExecute(newsInfos);
//            NewsAdapter adapter=new NewsAdapter(newsInfos);
//            list.setAdapter(adapter);
//        }
//        @Override
//        protected List<NewsInfo> doInBackground(String... params) {
//            return getNewsListFromJSON(getJSONStr(params[0]));
//        }
//    }
    public class NewsInfo{
        public String time,title,picUrl,description;
        public NewsInfo( String title,String time,String picUrl,String description){
            this.time = time;
            this.title = title;
            this.picUrl = picUrl;
            this.description=description;
        }
    }
    private void jsonTest(){
        String json="{\"code\":200,\"msg\":\"success\",\"newslist\":\n" +
                " [{\"ctime\":\"2016-09-08 10:56\",\"title\":\"四川患者抗生素过敏死亡 病历中现伪造签名\",\"description\":\"网易社会\",\"picUrl\":\"http:\\/\\/s.cimg.163.com\\/catchpic\\/8\\/8D\\/8D8876FA4AF9B1534CE2273BD49296EE.jpg.119x83.jpg\",\"url\":\"http:\\/\\/news.163.com\\/16\\/0908\\/10\\/C0EG06CM00011229.html#f=slist\"}\n" +
                " ,{\"ctime\":\"2016-09-08 11:49\",\"title\":\"9年前信用卡透支9元如今要还9千元，卡主：没收过催款\",\"description\":\"网易社会\",\"picUrl\":\"http:\\/\\/s.cimg.163.com\\/catchpic\\/6\\/63\\/630E598EF960C5A0FDC0AF46FBD1B815.jpg.119x83.jpg\",\"url\":\"http:\\/\\/news.163.com\\/16\\/0908\\/11\\/C0EJ1HJ900014SEH.html#f=slist\"}," +
                "{\"ctime\":\"2016-09-08 11:51\",\"title\":\"香港纺织大王孙女赴警局领回被绑案逾600万赎金\",\"description\":\"网易社会\",\"picUrl\":\"http:\\/\\/s.cimg.163.com\\/cnews\\/2016\\/9\\/8\\/201609080957551ebe3.jpg.119x83.jpg\",\"url\":\"http:\\/\\/news.163.com\\/16\\/0908\\/11\\/C0EJ56LR00014JB6.html#f=slist\"}," +
                "{\"ctime\":\"2016-09-08 12:02\",\"title\":\"云南一男子保护区内电鱼 被村民围堵报警自首\",\"description\":\"网易社会\",\"picUrl\":\"http:\\/\\/s.cimg.163.com\\/catchpic\\/2\\/20\\/2060D8DF848CABD4619354242300C100.jpg.119x83.jpg\",\"url\":\"http:\\/\\/news.163.com\\/16\\/0908\\/12\\/C0EJNMJ800014JB6.html#f=slist\"}," +
                "{\"ctime\":\"2016-09-08 12:09\",\"title\":\"湖南男子冒充人事主管骗多名女孩开房，水中投冰毒致一\",\"description\":\"网易社会\",\"picUrl\":\"http:\\/\\/s.cimg.163.com\\/catchpic\\/0\\/07\\/073241F3B2C568E2789136744C078A38.jpg.119x83.jpg\",\"url\":\"http:\\/\\/news.163.com\\/16\\/0908\\/12\\/C0EK5USJ00014SEH.html#f=slist\"}," +
                "{\"ctime\":\"2016-09-08 12:17\",\"title\":\"湖南郴州一岁男童被拐 警方全城搜索10小时后被解救\",\"description\":\"网易社会\",\"picUrl\":\"http:\\/\\/s.cimg.163.com\\/catchpic\\/7\\/73\\/7318046A08AD38FE3F084FD6442F0F1C.jpg.119x83.jpg\",\"url\":\"http:\\/\\/news.163.com\\/16\\/0908\\/12\\/C0EKK55900014JB6.html#f=slist\"}," +
                "{\"ctime\":\"2016-09-08 12:20\",\"title\":\"湖北男子摘马蜂窝欲卖钱 遭蜂群袭击坠崖身亡\",\"description\":\"网易社会\",\"picUrl\":\"http:\\/\\/s.cimg.163.com\\/catchpic\\/4\\/48\\/481E1368CF827DC9F2A7221DC4D48D56.jpg.119x83.jpg\",\"url\":\"http:\\/\\/news.163.com\\/16\\/0908\\/12\\/C0EKOK9G00014JB6.html#f=slist\"}," +
                "{\"ctime\":\"2016-09-08 13:08\",\"title\":\"探访辽宁运钞车劫案案发地：行踪被小区监控记录\",\"description\":\"网易社会\",\"picUrl\":\"http:\\/\\/s.cimg.163.com\\/catchpic\\/8\\/8B\\/8BAA84DC7FFED672980A149176983F97.jpg.119x83.jpg\",\"url\":\"http:\\/\\/news.163.com\\/16\\/0908\\/13\\/C0ENHSJ600011229.html#f=slist\"}," +
                "{\"ctime\":\"2016-09-08 09:26\",\"title\":\"北京男子因纠纷驾车冲撞大排档 致多人受伤被公诉\",\"description\":\"网易社会\",\"picUrl\":\"http:\\/\\/s.cimg.163.com\\/catchpic\\/8\\/8D\\/8D8876FA4AF9B1534CE2273BD49296EE.jpg.119x83.jpg\",\"url\":\"http:\\/\\/news.163.com\\/16\\/0908\\/09\\/C0EAQVN100014JB6.html#f=slist\"}," +
                "{\"ctime\":\"2016-09-08 09:28\",\"title\":\"湖北银行职员倒卖储户信息，每条最高50元含银行账号及\",\"description\":\"网易社会\",\"picUrl\":\"http:\\/\\/s.cimg.163.com\\/catchpic\\/6\\/63\\/630E598EF960C5A0FDC0AF46FBD1B815.jpg.119x83.jpg\",\"url\":\"http:\\/\\/news.163.com\\/16\\/0908\\/09\\/C0EAU7M700014SEH.html#f=slist\"}]}\n";
        try {
            JSONObject jsonAllData=new JSONObject(json);
            int code=jsonAllData.getInt("code");
            String msg=jsonAllData.getString("msg");
            JSONArray jsonList=jsonAllData.getJSONArray("newslist");
            Log.i("jsonCode",code+"");
            Log.i("jsonMsg",msg);
            //todo jsonIndex=All
            for(int i=0;i<jsonList.length();i++){
                JSONObject jsonData=jsonList.getJSONObject(i);
                String ctime=jsonData.getString("ctime");
                String title=jsonData.getString("title");
                String description=jsonData.getString("description");
                String picUrl=jsonData.getString("picUrl");
                String url=jsonData.getString("url");
                Log.i("jsonCtime",ctime);
                Log.i("jsonTitle",title);
                Log.i("jsonDescription",description);
                Log.i("jsonPicUrl",picUrl);
                Log.i("jsonUrl",url);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

//    class loadReadThread extends Thread{
//        @Override
//        public void run(){
//            super.run();
//            URL url=null;
//            HttpURLConnection connection;
//            try {
//                url=new URL("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2970322397,3177696435&fm=116&gp=0.jpg");
//                connection=(HttpURLConnection)url.openConnection();
//                connection.setReadTimeout(5000);
//                connection.setRequestMethod("GET");
//                connection.setDoInput(true);
//
//                InputStream inputStream=connection.getInputStream();
//                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//                    File parent=Environment.getExternalStorageDirectory();
//                    final File imager=new File(parent,"imager");
//                    BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
//                    byte[]buffer=new byte[2048];
//                    int len=0;
//                    FileOutputStream fileOutputStream=new FileOutputStream(imager);
//                    BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
//                    while((len=bufferedInputStream.read(buffer))!=-1){
//                        bufferedOutputStream.write(buffer,0,len);
//                    }
//                    bufferedOutputStream.flush();
//                    bufferedInputStream.close();
//                    bufferedOutputStream.close();
//                    fileOutputStream.close();
//                    inputStream.close();
//                    final Bitmap bitmap= BitmapFactory.decodeFile(imager.getPath());
//                    handler.post(new Runnable(){
//                        @Override
//                        public void run(){
//                            imageView.setImageBitmap(bitmap);
//                        }
//                    });
//                }
//                connection.disconnect();//todo 关闭网络连接
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }catch (IOException e){
//            }
//        }
//    }
}

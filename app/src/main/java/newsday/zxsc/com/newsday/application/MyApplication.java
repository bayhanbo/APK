package newsday.zxsc.com.newsday.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * Created by lenovo on 2016/9/8.
 */
public class MyApplication extends Application{
    private static RequestQueue queue;
    private static Gson gson;
    private static MyApplication myApplication;
    public static void setQueue(RequestQueue queue) {
        MyApplication.queue = queue;
    }
    public static void setGson(Gson gson) {
        MyApplication.gson = gson;
    }
    public static RequestQueue getQueue(){
        return queue;
    }
    public static Gson getGson(){
        return gson;
    }
    public static MyApplication getInstance(){
        return myApplication;
    }
    @Override
    public void onCreate(){
        ApiStoreSDK.init(this,"7953ce4c523638a25a8f7fa917c0179c");
        myApplication=this;
        super.onCreate();
        //todo 创建出请求队列
        queue=Volley.newRequestQueue(getApplicationContext());
        gson=new Gson();
//        ImageLoaderConfiguration configuration=ImageLoaderConfiguration.createDefault(this);
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480,800)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY-1)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSize(2*1024*1024).memoryCacheSizePercentage(12)
//                .diskCacheSize(50*1024*1024).diskCacheFileCount(50)
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
//                .imageDownloader(new BaseImageDownloader(this))
                .imageDecoder(new BaseImageDecoder(true))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(configuration);
    }
    //todo 应用停止
    @Override
    public void onTerminate() {
        super.onTerminate();
        queue.cancelAll(this);//todo 停止所有的请求
    }
}

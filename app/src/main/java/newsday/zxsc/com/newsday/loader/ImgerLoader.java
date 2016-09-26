package newsday.zxsc.com.newsday.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lenovo on 2016/9/11.
 */
public class ImgerLoader {
    private ImageView imageView;
    private String urlStr;
    private LruCache<String,Bitmap> lruCache;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            imageView.setImageBitmap((Bitmap)msg.obj);
        }
    };
    public ImgerLoader(){
        int maxSize=(int)Runtime.getRuntime().maxMemory();
        lruCache=new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }
    public void addBitmapToCache(String urlStr,Bitmap bitmap){
        if((getBitmapFromCache(urlStr))!=null)
            return;
        lruCache.put(urlStr,bitmap);
    }
    public Bitmap getBitmapFromCache(String urlStr){
        return lruCache.get(urlStr);
    }
    public void showHttpBitmapFromURLByThread(final String urlStr,ImageView imageView){
        this.urlStr=urlStr;this.imageView=imageView;
        new Thread(){
            @Override
            public void run() {
                super.run();
                Bitmap bitmap=getBitmapFromCache(urlStr);
                if(bitmap==null){
                    bitmap=getHttpBitmapFromURL(urlStr);
                    addBitmapToCache(urlStr,bitmap);
                }
                Message message=Message.obtain();
                message.obj=bitmap;
                handler.sendMessage(message);
            }
        }.start();
    }
    public Bitmap getHttpBitmapFromURL(final String urlStr) {
        InputStream inputStream = null;
        Bitmap bitmap = getBitmapFromCache(urlStr);
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            inputStream = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(inputStream);
            addBitmapToCache(urlStr,bitmap);
            inputStream.close();
            connection.disconnect();
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
        } finally {
        }
        return bitmap;
    }
    public void showHttpBitmapFromURLByAsyncTask(String urlStr,ImageView imageView){
        new HttpBitmapAsyncTask(imageView).execute(urlStr);
    }
    private class HttpBitmapAsyncTask extends AsyncTask<String,Integer,Bitmap>{
        private ImageView imageView;
        public HttpBitmapAsyncTask(ImageView imageView){
            this.imageView=imageView;
        }
        @Override
        protected Bitmap doInBackground(String... params){
            String bitmapUrl=params[0];
            Bitmap bitmap=getBitmapFromCache(bitmapUrl);
            if(bitmap==null){
                bitmap=getHttpBitmapFromURL(urlStr);
                addBitmapToCache(bitmapUrl,bitmap);
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}

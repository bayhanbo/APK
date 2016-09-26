package newsday.zxsc.com.newsday.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;

import java.util.List;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.db.MyExpress;

public class ShowWebViewActivity extends AppCompatActivity {
    private WebView webView;
    private Toolbar toolbar;
    private ImageView imageView;
    private MyExpress myExpress;
    private ProgressBar progressBar;
    private boolean isCheck=false;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
                    if(msg.arg1>0)
                        progressBar.setProgress(msg.arg1);
                    if(msg.arg1==100)
                    progressBar.setVisibility(View.GONE);
        }
    };
//    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_web_view);
        webView=(WebView)findViewById(R.id.show_webView);
        imageView=(ImageView) findViewById(R.id.iv_collect);
        toolbar=(Toolbar)findViewById(R.id.toolbar_web);
        progressBar=(ProgressBar)findViewById(R.id.pb_progress);
//        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.cl);
        toolbar.setTitle("NewsDay");
        toolbar.setTitleTextColor(Color.parseColor("#ff00ff"));
        toolbar.setNavigationIcon(R.drawable.btn_return);
        setSupportActionBar(toolbar);
        myExpress=new MyExpress(ShowWebViewActivity.this);
        final String url=getIntent().getExtras().getString("URL");
        String temp=myExpress.searchYesOrNoMsg(url);
        Log.i("----",temp.toString());
        if(temp.length()!=0){
            isCheck=true;
            imageView.setImageResource(R.drawable.star_selected);
        }
        webView.loadUrl(url);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view,int newProgress){
                Log.i("----",newProgress+"");
                super.onProgressChanged(view,newProgress);
                Message message=new Message();
                message.arg1=newProgress;
                handler.sendMessage(message);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(isCheck==false){
                    myExpress.addMsg(url);
                    imageView.setImageResource(R.drawable.star_selected);
                    Toast.makeText(ShowWebViewActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                    isCheck=true;
                }else if(isCheck==true){
                    myExpress.deleteOne(url);
                    imageView.setImageResource(R.drawable.star_defult);
                    Toast.makeText(ShowWebViewActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                    isCheck=false;
                }
//                Snackbar.make(coordinatorLayout,"收藏成功",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}

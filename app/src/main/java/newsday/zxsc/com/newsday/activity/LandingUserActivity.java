package newsday.zxsc.com.newsday.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import newsday.zxsc.com.newsday.R;

public class LandingUserActivity extends AppCompatActivity {
    private Button button;
    private ImageView imageView;
    private Toolbar toolbar;
    private boolean needUpdate;//todo 代表是否需要刷新
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_user);
        button=(Button)findViewById(R.id.btn_landing_dl);
        imageView=(ImageView)findViewById(R.id.iv_landing_qq);
        toolbar=(Toolbar)findViewById(R.id.tool_bar_landing);
        toolbar.setTitle("登陆界面");
        toolbar.setTitleTextColor(Color.parseColor("#ff00ff"));
        toolbar.setNavigationIcon(R.drawable.btn_return);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent();
                intent.putExtra("needUpdate",needUpdate);
                setResult(101,intent);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText(LandingUserActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(LandingUserActivity.this,"第三方登陆暂未完善...请等待更新",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

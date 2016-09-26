package newsday.zxsc.com.newsday.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Set;
import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.base.BaseActivity;
import newsday.zxsc.com.newsday.callback.FlowLayoutCallback;
import newsday.zxsc.com.newsday.util.SharedPreUtil;
import newsday.zxsc.com.newsday.view.FlowLayout;

public class AddTitleActivity extends BaseActivity{
    private Toolbar toolbar;
    private FlowLayout flowLayout;
    private ListView listView;
    private String[]tabs={"国内","国际","军事","财经","互联网","房产","汽车","体育","娱乐","游戏","教育","女人","科技","社会"};
//    private String[]tabs={"国内","台湾","港澳","国际","军事","财经","理财","宏观经济","互联网","房产","汽车","体育","国际足球","国内足球"
//    ,"CBA","综合体育","娱乐","电影","电视","游戏","教育","女人","美容护肤","情感两性","健康养生","科技","数码","电脑","科普","社会"};
    private SharedPreUtil sharedPreUtil;
    private Set<String>sharedTabs;
    private boolean needUpdate;//todo 代表是否需要刷新
    @Override
    public void setContentLayout(){
        setContentView(R.layout.activity_add_title);
    }
    @Override
    public void initView(){
        listView=(ListView)findViewById(R.id.lv_add_title);
        toolbar=(Toolbar)findViewById(R.id.toolbar_add);
        flowLayout=(FlowLayout)findViewById(R.id.flow_layout);
        toolbar.setTitle("NewsDay");
        toolbar.setTitleTextColor(Color.parseColor("#ff00ff"));
        toolbar.setNavigationIcon(R.drawable.btn_return);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.i("onDestory001",sharedPreUtil.getFromSharedInfo().size()+"");
                Intent intent=new Intent();
                intent.putExtra("needUpdate",needUpdate);
                setResult(101,intent);
                finish();
            }
        });
    }
    @Override
    public void afterViewLogic(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.add_listview_text,tabs);
        listView.setAdapter(arrayAdapter);
        sharedPreUtil=new SharedPreUtil(this);
        sharedTabs=sharedPreUtil.getFromSharedInfo();
        flowLayout.getSetData(sharedTabs);
        flowLayout.setCallback(new FlowLayoutCallback() {
            @Override
            public void afterOnChildClick() {
                Toast.makeText(AddTitleActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                needUpdate=true;
                sharedPreUtil.saveToSharedInfo(sharedTabs);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                if(sharedTabs.add(tabs[position])){
                    Toast.makeText(AddTitleActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                    flowLayout.getSetData(sharedTabs);
                    needUpdate=true;
                    sharedPreUtil.saveToSharedInfo(sharedTabs);
                }
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
//        sharedPreUtil.saveToSharedInfo(sharedTabs);
        Log.i("onDestory",sharedPreUtil.getFromSharedInfo().size()+"");
    }
    @Override
    public void onClick(View view){
    }
}

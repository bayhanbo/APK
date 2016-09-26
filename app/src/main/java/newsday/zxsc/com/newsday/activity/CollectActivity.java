package newsday.zxsc.com.newsday.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.adapter.CollectAdapter;
import newsday.zxsc.com.newsday.db.MyExpress;

public class CollectActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
//    private ArrayAdapter arrayAdapter;
    private CollectAdapter adapter;
    private MyExpress myExpress;
    private List<String> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        myExpress=new MyExpress(this);
        toolbar=(Toolbar)findViewById(R.id.toolbar_collect);
        listView=(ListView)findViewById(R.id.lv_collect_www);
        toolbar.setTitle("我的收藏");
        toolbar.setTitleTextColor(Color.parseColor("#ff00ff"));
        toolbar.setNavigationIcon(R.drawable.btn_return);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
//        arrayAdapter=new ArrayAdapter(this,R.layout.search_listview_text,setDatas());
        adapter=new CollectAdapter(this,setDatas());
        adapter.setOnclick(new CollectAdapter.Onclick() {
            @Override
            public void onimgClick(View view, int position) {
                myExpress.deleteOne(datas.get(position));
                datas.remove(position);
                listView.setAdapter(adapter);
                Toast.makeText(CollectActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
            }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(CollectActivity.this,ShowWebViewActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("URL",datas.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    //todo 模拟数据
    List<String> setDatas(){
        datas=new ArrayList<>();
        datas=myExpress.searchMsgAll();
        return datas;
    }
    @Override
    protected void onResume() {
        adapter=new CollectAdapter(this,setDatas());
        listView.setAdapter(adapter);
        super.onResume();
    }
}

package newsday.zxsc.com.newsday.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.adapter.RecyclerViewAdapter;
import newsday.zxsc.com.newsday.callback.OnRecyclerViewListener;

public class RegisterAcyivity extends AppCompatActivity{
//    private RecyclerViewAdapter adapter;
//    private RecyclerView recyclerView;
//    private List<String> list;
////    private Toolbar toolbar;
//    private LinearLayoutManager linearLayoutManagerV;
//    private LinearLayoutManager linearLayoutManagerH;
//    private GridLayoutManager gridLayoutManagerV;
//    private StaggeredGridLayoutManager gridLayoutManagerH;
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hot_fragment);
//        recyclerView=(RecyclerView)findViewById(R.id.rv_hot_recyclerView);
////        toolbar=(Toolbar)findViewById(R.id.toolbar_hot);
////        toolbar.setTitle("NewsDay");
////        setSupportActionBar(toolbar);
//        //todo 分割线调用方法
////        RecyclerView.ItemDecoration
//        initHipping();
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
//        list=new ArrayList<>();
//        for(int i=0;i<100;i++){
//            list.add("冯"+i);
//        }
//        adapter=new RecyclerViewAdapter(this,list);
//        adapter.setListener(new OnRecyclerViewListener() {
//            @Override
//            public void onItemClickListener(View view, int position) {
//                Toast.makeText(RegisterAcyivity.this,"删除成功",Toast.LENGTH_SHORT).show();
//                list.remove(position);
//                adapter.notifyItemRemoved(position);
//            }
//            @Override
//            public void onItemClickActionListener(View view, int position) {
//                //todo 待写
//            }
//        });
//        recyclerView.setAdapter(adapter);
//    }
//
//    private void initHipping(){
//        //todo 列表模式 横竖
//        linearLayoutManagerV=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//        linearLayoutManagerH=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//        //todo 网格模式
//        gridLayoutManagerV=new GridLayoutManager(this,4,LinearLayoutManager.VERTICAL,false);
//        gridLayoutManagerH=new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.toolbar_hot,menu);
//        return true;
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

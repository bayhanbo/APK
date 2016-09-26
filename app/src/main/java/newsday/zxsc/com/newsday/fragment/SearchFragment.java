package newsday.zxsc.com.newsday.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.activity.CollectActivity;
import newsday.zxsc.com.newsday.activity.SettingActivity;
import newsday.zxsc.com.newsday.activity.ShowWebViewActivity;
import newsday.zxsc.com.newsday.adapter.CollectAdapter;
import newsday.zxsc.com.newsday.adapter.RecyclerViewAdapter;
import newsday.zxsc.com.newsday.db.MyExpress;

public class SearchFragment extends Fragment{
    private EditText editText;
    private ListView listView;
    private ImageView imageView;
//    private ArrayAdapter arrayAdapter,adapter;
    private CollectAdapter collectAdapter,adapter;
//    private RecyclerViewAdapter adapter;
//    private RecyclerView recyclerView;
    private MyExpress myExpress;
    private String str;
    private List<String> list,datas;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 0:
                    String text=(String)msg.obj;
                    datas=new ArrayList<>();
                    datas.add(text);
                    datas=myExpress.searchMsg(text);
                    str=text;
                    collectAdapter=new CollectAdapter(getActivity(),datas);
                    listView.setAdapter(collectAdapter);
                    collectAdapter.setOnclick(new CollectAdapter.Onclick(){
                        @Override
                        public void onimgClick(View view, int position) {
                            datas.remove(position);
                            listView.setAdapter(collectAdapter);
                        }
                    });
                    break;
                case 1:
                    list=new ArrayList<>();
                    adapter=new CollectAdapter(getActivity(),list);
                    listView.setAdapter(adapter);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_search_fragment,container,false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        editText=(EditText)view.findViewById(R.id.et_search);
        imageView=(ImageView)view.findViewById(R.id.iv_search_web);
        listView=(ListView)view.findViewById(R.id.lv_search_one);
        myExpress=new MyExpress(getActivity());
        editText.addTextChangedListener(new myText());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ShowWebViewActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("URL",str);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText(collectAdapter.getItem(position)+"");
            }
        });
    }
    class myText implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count){
            Message message=new Message();
            if(s.length()>0){
                String msg=s.toString();
                message.obj=msg;
                message.what=0;
            }else if(s.length()==0){
                message.what=1;
            }
            handler.sendMessage(message);
        }
        @Override
        public void afterTextChanged(Editable s){
        }
    }
//    //todo 模拟数据
//    void setDatas(){
//        datas=new ArrayList<>();
//        datas.add("1");
//        datas.add("12");
//        datas.add("123");
//        datas.add("1234");
//        datas.add("12345");
//        for(int i=0;i<datas.size();i++){
//                myExpress.addMsg(datas.get(i));
//        }
//    }
//    @Override
//    public void onDestroy(){
//        handler.removeMessages(0);
//        super.onDestroy();
//    }
}

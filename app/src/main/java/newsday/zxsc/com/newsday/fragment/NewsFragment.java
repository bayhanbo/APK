package newsday.zxsc.com.newsday.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.activity.AddTitleActivity;
import newsday.zxsc.com.newsday.adapter.NewTabPagerAdapter;
import newsday.zxsc.com.newsday.util.SharedPreUtil;

/**
 * Created by lenovo on 2016/9/1.
 */
public class NewsFragment extends Fragment {
    private TabLayout tableLayout;
    private ViewPager viewPager;
    private NewTabPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private List<String> tabTitleList;
    private SharedPreUtil sharedPreUtil;
    private ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedPreUtil = new SharedPreUtil(getActivity());
        return inflater.inflate(R.layout.activity_news_fragment, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager_home);
        tableLayout = (TabLayout) view.findViewById(R.id.tab_FindFragment_title);
        imageView = (ImageView) view.findViewById(R.id.iv_add_title);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(),AddTitleActivity.class);
                startActivityForResult(intent,100);
            }
        });
        initViewpagerFragment();//todo 有点问题待解决
        viewPager.setOffscreenPageLimit(1);
//        tableLayout.setupWithViewPager(viewPager);

    }
    private void initViewpagerFragment(){
        fragmentList = new ArrayList<>();
        if (tabTitleList != null)
            tabTitleList.clear();
            fragmentList.clear();
        tabTitleList = sharedPreUtil.getFromSharedInfoList();
        for (int i = 0; i < tabTitleList.size(); i++){
            BaiduNewFragment temp=new BaiduNewFragment();
            Bundle bundle=new Bundle();
            bundle.putString("newName",tabTitleList.get(i));
            temp.setArguments(bundle);
            fragmentList.add(temp);
        }
        this.adapter = new NewTabPagerAdapter(getActivity().getSupportFragmentManager(),fragmentList, tabTitleList);
        Log.i("textNeedUpdate", tabTitleList.size() + "" + adapter.getCount());
        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(2);
        tableLayout.setupWithViewPager(viewPager);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 100 && resultCode == 101 && data.getBooleanExtra("needUpdate",false)){
            initViewpagerFragment();
        }
    }
}

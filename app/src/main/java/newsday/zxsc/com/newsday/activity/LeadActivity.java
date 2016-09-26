package newsday.zxsc.com.newsday.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.base.BaseActivity;
import newsday.zxsc.com.newsday.util.SharedPreUtil;

public class LeadActivity extends BaseActivity{
private ViewPager viewPager_lead;
    @Override
    public void setContentLayout(){
        setContentView(R.layout.activity_lead);
        initView();listenerView();initData();
    }
    public void initData(){
        final List<View> views=new ArrayList<>();
        ImageView imageView=new ImageView(this);
        ImageView imageView1=new ImageView(this);
        ImageView imageView2=new ImageView(this);
        imageView.setBackground(getResources().getDrawable(R.drawable.lead_1));
        imageView1.setBackground(getResources().getDrawable(R.drawable.lead_2));
        imageView2.setBackground(getResources().getDrawable(R.drawable.lead_3));
        views.add(imageView2);
        views.add(imageView1);
        views.add(imageView);
        PagerAdapter pagerAdapter=new PagerAdapter(){
            @Override
            public int getCount(){
                return views.size();
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position){
                container.addView(views.get(position),0);
                return views.get(position);
            }
            @Override
            public boolean isViewFromObject(View view, Object object){
                return view==object;
            }
            @Override
            public void destroyItem(ViewGroup container, int position, Object object){
                container.removeView(views.get(position));
            }
        };
        viewPager_lead.setAdapter(pagerAdapter);
    }
    @Override
    public void initView(){
        viewPager_lead=(ViewPager)findViewById(R.id.viewPager_lead);
    }

    @Override
    public void afterViewLogic(){

    }

    public void listenerView(){
        findViewById(R.id.bt_lead_toLogo).setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.bt_lead_toLogo:
                //todo 业务跳转 保存SharedPre
                new SharedPreUtil(this).saveLeadDataToShread(getAppVersionName());
                startActivity(AnimationActivity.class);
                finish();
                break;
        }
    }
}

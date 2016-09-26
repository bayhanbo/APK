package newsday.zxsc.com.newsday.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.base.BaseActivity;
import newsday.zxsc.com.newsday.fragment.HotFragment;
import newsday.zxsc.com.newsday.fragment.NewsFragment;
import newsday.zxsc.com.newsday.fragment.SearchFragment;

public class LogoActivity extends BaseActivity{
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rb_news, rb_hot, rb_search;
    private Fragment[]fragments = new Fragment[3];
    private Toolbar toolbar;
    private DrawerLayout main_drawer;
    private NavigationView main_naView;
    private View headerView;
    private TextView textView_dl;
    @Override
    public void setContentLayout(){
        setContentView(R.layout.activity_logo);
        ShareSDK.initSDK(this,"sharesdk的appkey");
        initView();
        listenerView();
        fragmentManager=getSupportFragmentManager();
        initData();
    }
    public void initData(){
        rb_news = (RadioButton) findViewById(R.id.radioButton_news);
        rb_hot = (RadioButton) findViewById(R.id.radioButton_hot);
        rb_search = (RadioButton) findViewById(R.id.radioButton_search);
        rb_news.setOnClickListener(this);
        rb_hot.setOnClickListener(this);
        rb_search.setOnClickListener(this);
        initToolBar();
        headerView=main_naView.getHeaderView(0);
        textView_dl=(TextView)headerView.findViewById(R.id.me_text);
        textView_dl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(LogoActivity.this,LandingUserActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putString("URL","");
//                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        choiceFragment(0);
    }
    private void initToolBar(){
        toolbar=(Toolbar)findViewById(R.id.main_toolbar);
        toolbar.setTitle("NewsDay");
        toolbar.setTitleTextColor(Color.parseColor("#ff00ff"));
        //todo 关联到侧滑
        setSupportActionBar(toolbar);
        main_drawer = (DrawerLayout)findViewById(R.id.main_drawer);
        main_naView = (NavigationView)findViewById(R.id.main_naView);
        //todo 呈现Toolbar左上角的按钮
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //todo 对Drawer监听 这个监听别人已经实现了 我们直接先使用 需要注意的是我们需要关联到我们的ToolBar
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,main_drawer,toolbar,R.string.app_name,R.string.app_name);
        //todo 同步状态
        drawerToggle.syncState();
        //todo 对我们的DrawerLayout设置上监听 关于滑动的动画 ActionBarDrawerToggle在做实现  我们不需要去管理
        main_drawer.addDrawerListener(drawerToggle);
        //todo 隐藏的View 呈现出来以后 我们是可能点击的 在这边想有相应的点击处理操作 那么直接想到监听者
        main_naView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    //todo 选择情况下执行的相关操作
                    case R.id.drawer_collen://todo 收藏还没有完善
                        Intent intent=new Intent(LogoActivity.this,CollectActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("URL","");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case R.id.drawer_me:
                        Toast.makeText(LogoActivity.this,"此版本目前只供测试使用,如需全部功能请购买正版！谢谢...", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.drawer_settings:
                        Toast.makeText(LogoActivity.this,"此功能暂未完善...请等待更新",Toast.LENGTH_SHORT).show();
                        break;
                }
                //todo 注意返回值
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        showShare();
        return true;
    }
    //todo 隐藏掉所有的Fragment
    private void hideAllFragment(){
        fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (fragments[i]!= null)
                fragmentTransaction.hide(fragments[i]);
        }
        fragmentTransaction.commit();
    }
    //todo 切换Fragment
    private void choiceFragment(int index){
        //todo 隐藏掉所有的Fragment
        hideAllFragment();
        //todo 开启事务
        fragmentTransaction = fragmentManager.beginTransaction();
        if (fragments[index] == null) {
            switch (index) {
                case 0:
                    fragments[index] = new NewsFragment();
                    fragmentTransaction.add(R.id.fl_fragment, fragments[index]);
                    break;
                case 1:
                    fragments[index] = new HotFragment();
                    fragmentTransaction.add(R.id.fl_fragment, fragments[index]);
                    break;
                case 2:
                    fragments[index] = new SearchFragment();
                    fragmentTransaction.add(R.id.fl_fragment, fragments[index]);
                    break;
            }
        } else {
            fragmentTransaction.show(fragments[index]);
        }
        //todo 提交事务
        fragmentTransaction.commit();
    }
    private void showShare(){
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //todo 关闭sso授权
        oks.disableSSOWhenAuthorize();
        //todo title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        //todo titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        //todo text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //todo imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //todo oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        //todo url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        //todo comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        //todo site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        //todo siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        //todo 启动分享GUI
        oks.show(this);
    }
    @Override
    public void initView(){
    }
    @Override
    public void afterViewLogic(){
    }
    public void listenerView(){
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.radioButton_news:
                choiceFragment(0);
                break;
            case R.id.radioButton_hot:
                choiceFragment(1);
                break;
            case R.id.radioButton_search:
                choiceFragment(2);
                break;
        }
    }
}

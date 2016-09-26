package newsday.zxsc.com.newsday.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.base.BaseActivity;
import newsday.zxsc.com.newsday.fragment.HotFragment;
import newsday.zxsc.com.newsday.fragment.NewsFragment;
import newsday.zxsc.com.newsday.fragment.SearchFragment;

public class MainActivity extends BaseActivity{
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rb_news, rb_hot, rb_search;
    private Fragment[] fragments = new Fragment[3];

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_main);
        //todo 初始化
        fragmentManager = getSupportFragmentManager();
    }
    @Override
    public void initView() {
        rb_news = (RadioButton) findViewById(R.id.main_radio_new);
        rb_hot = (RadioButton) findViewById(R.id.main_radio_hot);
        rb_search = (RadioButton) findViewById(R.id.main_radio_search);
        rb_news.setOnClickListener(this);
        rb_hot.setOnClickListener(this);
        rb_search.setOnClickListener(this);
        choiceFragment(0);
    }
    //todo 隐藏掉所有的Fragment
    private void hideAllFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (fragments[i] != null)
                fragmentTransaction.hide(fragments[i]);
        }
        fragmentTransaction.commit();
    }

    //todo 切换Fragment
    private void choiceFragment(int index) {
        //todo 隐藏掉所有的Fragment
        hideAllFragment();
        //todo 开启事务
        fragmentTransaction = fragmentManager.beginTransaction();
        if (fragments[index] == null) {
            switch (index) {
                case 0:
                    fragments[index] = new NewsFragment();
                    fragmentTransaction.add(R.id.main_framelayout, fragments[index]);
                    break;
                case 1:
                    fragments[index] = new HotFragment();
                    fragmentTransaction.add(R.id.main_framelayout, fragments[index]);
                    break;
                case 2:
                    fragments[index] = new SearchFragment();
                    fragmentTransaction.add(R.id.main_framelayout, fragments[index]);
                    break;
            }
        } else {
            fragmentTransaction.show(fragments[index]);
        }
        //todo 提交事务
        fragmentTransaction.commit();
    }
    @Override
    public void afterViewLogic() {

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_radio_new:
                choiceFragment(0);
                break;
            case R.id.main_radio_hot:
                choiceFragment(1);
                break;
            case R.id.main_radio_search:
                choiceFragment(2);
                break;
        }
    }
}

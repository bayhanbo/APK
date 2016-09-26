package newsday.zxsc.com.newsday.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by lenovo on 2016/9/2.
 */
public class NewTabPagerAdapter extends FragmentStatePagerAdapter{
    private List<Fragment> fragmentList;
    private List<String> tabTitleList;
    public NewTabPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> tabTitleList){
        super(fm);
        this.fragmentList=fragmentList;
        this.tabTitleList=tabTitleList;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitleList.get(position);
    }
}

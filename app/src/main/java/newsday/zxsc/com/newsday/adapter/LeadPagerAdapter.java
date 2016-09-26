package newsday.zxsc.com.newsday.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lenovo on 2016/8/31.
 */
public class LeadPagerAdapter extends PagerAdapter{
    private List<View>pagers;
    public LeadPagerAdapter(List<View> pagers){
        this.pagers = pagers;
    }
    /**
     * 返回一共有多少个选项卡
     * 和ListView的adapter很相似
     * @return
     */
    @Override
    public int getCount(){
        return pagers.size();
    }
    /**
     * 是否来自一个对象
     * @param view   视图
     * @param object 对象
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object){
        return view==object;
    }
    /**
     * 删除选项卡
     * @param container 父容器
     * @param position  索引
     * @param object    对象
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView(pagers.get(position));
    }
    /**
     * 使用这个方法来达到对每一个选项卡的实例化
     * @param container 父容器
     * @param position  索引
     * @return 一个对象
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        //todo 注意在这里只能去填写0
        container.addView(pagers.get(position),0);
        return pagers.get(position);
    }
}

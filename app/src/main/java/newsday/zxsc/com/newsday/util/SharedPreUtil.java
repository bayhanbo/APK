package newsday.zxsc.com.newsday.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by lenovo on 2016/8/31.
 */
public class SharedPreUtil{
    private Context context;
    private String lead_SharedPre="lead_data";
    private String teb_SharedPre="teb_data";
    public SharedPreUtil(Context context){
        this.context=context;
    }
    /**
     * 保存引导信息
     */
    public void saveLeadDataToShread(String versionName){
        SharedPreferences sharedPreferences=context.getSharedPreferences(lead_SharedPre,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("versionName",versionName);
        editor.commit();
    }
    public String getLeadDataFromShared(){
        return context.getSharedPreferences(lead_SharedPre,0).getString("versionName","");
    }
    public List<String> getFromSharedInfoList(){
        Set<String> list = getFromSharedInfo();
        List<String> tablist = new ArrayList<>();
        for (String tab:list){
            tablist.add(tab);
        }
        return tablist;
    }
    public Set<String> getFromSharedInfo(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(teb_SharedPre,0);
        Set<String> list = sharedPreferences.getStringSet("menu",null);
        if (list == null)
        list = new TreeSet<>();
        list.add("互联网");
//        list.add("视频");
//        list.add("体育");
//        list.add("四川");
//        list.add("军事");
//        list.add("星座");
        return list;
    }
    public void saveToSharedInfo(Set<String> list){
        SharedPreferences sharedPreferences = context.getSharedPreferences(teb_SharedPre,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("menu",list);
        editor.commit();
    }
}

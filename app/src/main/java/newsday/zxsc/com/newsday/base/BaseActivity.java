package newsday.zxsc.com.newsday.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lenovo on 2016/8/31.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo 加载布局
        setContentLayout();
        //todo 初始化View
        initView();
        //todo 后逻辑
        afterViewLogic();
    }
    /**初始化控件 控件的监听*/
    public abstract void setContentLayout();
    public abstract void initView();
    public abstract void afterViewLogic();
    /**界面跳转问题*/
    protected void startActivity(Class<?> targetClass){
        Intent intent = new Intent(this,targetClass);
        startActivity(intent);
    }
    /**带有Bundle数据的跳转*/
    protected void startActivity(Class<?> targetClass, Bundle bundle){
        Intent intent = new Intent(this, targetClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    /**
     * app名称
     */
    public String getAppVersionName(){
        String varName = "";
        try {
            varName = getPackageManager().getPackageInfo(getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return varName;
    }
    /**
     * 版本号获取
     */
    public String getAppVersionCode(){
        int varCode = 0;
        try {
            varCode = getPackageManager().getPackageInfo(getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return varCode+"";
    }
    @Override
    public void finish() {
        super.finish();
    }
}

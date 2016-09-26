package newsday.zxsc.com.newsday.activity;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.base.BaseActivity;
import newsday.zxsc.com.newsday.util.SharedPreUtil;

public class AnimationActivity extends BaseActivity implements Animation.AnimationListener{
private ImageView imageView;
private SharedPreUtil sharedPreUtil;
    @Override
    public void setContentLayout(){
        // TODO 判断是第一次运行APP
        sharedPreUtil = new SharedPreUtil(this);
        String versionName = sharedPreUtil.getLeadDataFromShared();
        if(versionName.equals("") || !versionName.equals(getAppVersionName())){
            // TODO 跳转LeadActivity
            startActivity(LeadActivity.class);
            Log.i("initView","finish");
            finish();
        }
        setContentView(R.layout.activity_animation);
    }
    @Override
    public void initView(){
        imageView=(ImageView)findViewById(R.id.iv_animation);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.animation_anim);
        imageView.startAnimation(animation);
        animation.setAnimationListener(this);
    }
    @Override
    public void afterViewLogic() {
    }
    @Override
    public void onAnimationStart(Animation animation) {
    }
    @Override
    public void onAnimationEnd(Animation animation){
        startActivity(LogoActivity.class);
        finish();
    }
    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    @Override
    public void onClick(View view) {
    }
}

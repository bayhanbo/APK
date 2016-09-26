package newsday.zxsc.com.newsday.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import newsday.zxsc.com.newsday.R;
import newsday.zxsc.com.newsday.callback.FlowLayoutCallback;

/**
 * Created by lenovo on 2016/9/5.
 */
public class FlowLayout extends ViewGroup implements View.OnClickListener{
    private Set<String> set;
    private FlowLayoutCallback callback;
    public FlowLayoutCallback getCallback() {
        return callback;
    }
    public void setCallback(FlowLayoutCallback callback) {
        this.callback = callback;
    }
    //todo 存储所有子View
    private List<List<View>> mAllChildViews = new ArrayList<>();
    //todo 每一行的高度
    private List<Integer> mLineHeight = new ArrayList<>();
    public FlowLayout(Context context) {
        this(context, null);
    // TODO Auto-generated constructor stub
    }
    public FlowLayout(Context context,AttributeSet attrs) {
        this(context, attrs, 0);
    // TODO Auto-generated constructor stub
    }
    public FlowLayout(Context context,AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    // TODO Auto-generated constructor stub
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // TODO Auto-generated method stub
    //todo 父控件传进来的宽度和高度以及对应的测量模式
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
    //todo 如果当前ViewGroup的宽高为wrap_content的情况
        int width = 0;//todo 自己测量的 宽度
        int height = 0;//todo 自己测量的高度
    //todo 记录每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;
    //todo 获取子view的个数
        int childCount = getChildCount();
        for(int i = 0;i < childCount; i ++){
            View child = getChildAt(i);
    //todo 测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
    //todo 得到LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
    //todo 子View占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
    //todo 子View占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
    //todo 换行时候
            if(lineWidth + childWidth > sizeWidth){
    //todo 对比得到最大的宽度
                width = Math.max(width, lineWidth);
    //todo 重置lineWidth
                lineWidth = childWidth;
    //todo 记录行高
                height += lineHeight;
                lineHeight = childHeight;
            }else{//todo 不换行情况
    //todo 叠加行宽
                lineWidth += childWidth;
    //todo 得到最大行高
                lineHeight = Math.max(lineHeight, childHeight);
            }
    //todo 处理最后一个子View的情况
            if(i == childCount -1){
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
    //todo wrap_content
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    // TODO Auto-generated method stub
        mAllChildViews.clear();
        mLineHeight.clear();
        //todo 获取当前ViewGroup的宽度
        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        //todo 记录当前行的view
        List<View> lineViews = new ArrayList<>();
        int childCount = getChildCount();
        for(int i = 0;i < childCount; i ++){
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
    //todo 如果需要换行
            if(childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width){
    //todo 记录LineHeight
                mLineHeight.add(lineHeight);
    //todo 记录当前行的Views
                mAllChildViews.add(lineViews);
    //todo 重置行的宽高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
    //todo 重置view的集合
                lineViews = new ArrayList();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        }
        //todo 处理最后一行
        mLineHeight.add(lineHeight);
        mAllChildViews.add(lineViews);
        //todo 设置子View的位置
        int left = 0;
        int top = 0;
        //todo 获取行数
        int lineCount = mAllChildViews.size();
        for(int i = 0; i < lineCount; i ++){
            //todo 当前行的views和高度
            lineViews = mAllChildViews.get(i);
            lineHeight = mLineHeight.get(i);
            for(int j = 0; j < lineViews.size(); j ++){
                View child = lineViews.get(j);
                //todo 判断是否显示
                if(child.getVisibility() == View.GONE){
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int cLeft = left + lp.leftMargin;
                int cTop = top + lp.topMargin;
                int cRight = cLeft + child.getMeasuredWidth();
                int cBottom = cTop + child.getMeasuredHeight();
                //todo 进行子View进行布局
                child.layout(cLeft, cTop, cRight, cBottom);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }
    /**
     * 与当前ViewGroup对应的LayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        // TODO Auto-generated method stub
        return new MarginLayoutParams(getContext(), attrs);
    }
    public void getSetData(Set<String> set){
        this.set=set;
        removeAllViews();
        MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for(String key:set){
            TextView view = new TextView(getContext());
            view.setText(key);
            view.setTextColor(Color.WHITE);
            view.setBackgroundResource(R.drawable.bt_bg);
            view.setTag(key);
            view.setOnClickListener(this);
            addView(view, lp);
        }
    }
    @Override
    public void onClick(View v) {
        set.remove(((TextView)v).getText());
        removeView(v);
        if(callback!=null)
            callback.afterOnChildClick();
    }
}
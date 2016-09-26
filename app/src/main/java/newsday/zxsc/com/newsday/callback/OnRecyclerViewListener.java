package newsday.zxsc.com.newsday.callback;

import android.view.View;

/**
 * Created by lenovo on 2016/9/7.
 */
public interface OnRecyclerViewListener {
    void onItemClickListener(View view, int position);
    void onItemClickActionListener(View view, int position);
}

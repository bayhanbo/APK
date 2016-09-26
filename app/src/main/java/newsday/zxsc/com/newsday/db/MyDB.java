package newsday.zxsc.com.newsday.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo on 2016/9/20.
 */
public class MyDB extends SQLiteOpenHelper{
    public MyDB(Context context){
        super(context,"flj.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table CBA(msg web_www)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

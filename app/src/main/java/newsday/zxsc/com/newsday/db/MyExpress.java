package newsday.zxsc.com.newsday.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/9/20.
 */
public class MyExpress{
    SQLiteDatabase sqLiteDatabase;
    Context context;
    public MyExpress(Context context){
        this.context=context;
        MyDB myDB=new MyDB(context);
        sqLiteDatabase=myDB.getReadableDatabase();
    }
    public void addMsg(String str){//增加
        ContentValues contentValues=new ContentValues();
        if((searchMsg(str).size())==0) {
            contentValues.put("msg", str);
            sqLiteDatabase.insert("CBA",null,contentValues);
        }
    }
    public String searchYesOrNoMsg(String name){//查看有没有
        Cursor cursor=sqLiteDatabase.rawQuery("select * from CBA where msg = ?",new String[]{name});
        String msgs="";
        if(cursor.moveToFirst())
        msgs=cursor.getString(cursor.getColumnIndex("msg"));
        return msgs;
    }
    public List<String> searchMsg(String name){//查询
        Cursor cursor=sqLiteDatabase.rawQuery("select * from CBA where msg Like ?",new String[]{'%'+name+'%'});
        List<String> datas=new ArrayList<>();
        while(cursor.moveToNext()){
            String msgs=cursor.getString(cursor.getColumnIndex("msg"));
            datas.add(msgs);
        }
        return datas;
    }
    public List<String> searchMsgAll(){//查询全部
        Cursor cursor=sqLiteDatabase.rawQuery("select * from CBA",null);
        List<String> datas=new ArrayList<>();
        while(cursor.moveToNext()){
            String msgs=cursor.getString(cursor.getColumnIndex("msg"));
            datas.add(msgs);
        }
        return datas;
    }
    public void deleteOne(String str){//删除一个
        sqLiteDatabase.delete("CBA","msg=?",new String[]{str});
    }
    public void deleteAll(){//删除所有
        sqLiteDatabase.delete("CBA",null,null);
    }
}

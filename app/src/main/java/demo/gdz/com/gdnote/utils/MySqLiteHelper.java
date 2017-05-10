package demo.gdz.com.gdnote.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import demo.gdz.com.gdnote.data.NoteListTable;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class MySqLiteHelper extends SQLiteOpenHelper {
   private static  MySqLiteHelper instance;
    private MySqLiteHelper(Context context,String name){
        super(context,name,null,1);
    }
   /* public  MySqLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }*/
    public static MySqLiteHelper getInstance(Context context,String name){
        if(instance ==null){
              synchronized (MySqLiteHelper.class){//double synchronized lock
                  if(instance ==null){
                      instance = new MySqLiteHelper(context,name);
                  }
              }
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
     //    db.execSQL("drop table if exists notelist");
         db.execSQL(NoteListTable.CREATE_NOTELIST);
        Log.i(TAG, "onCreate: db.toString():"+db.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void closeAll(){
        if (instance!=null){
            instance.close();
        }
        close();
    }
}

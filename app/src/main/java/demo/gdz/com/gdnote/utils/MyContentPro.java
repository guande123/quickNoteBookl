package demo.gdz.com.gdnote.utils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import demo.gdz.com.gdnote.data.NoteListTable;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class MyContentPro extends ContentProvider {
    public static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final String AUTHRITY ="demo.gdz.com.gdnote" ;

    private static final String NOTELIST_NAME ="notelist" ;

    public  static final Uri NOTELIST_URI = Uri.parse("content://"+AUTHRITY+"/"+NOTELIST_NAME);

    private MySqLiteHelper sHelper;
    private SQLiteDatabase db;
    static {
        sUriMatcher.addURI(AUTHRITY,NOTELIST_NAME,1);
    }

    public MyContentPro(Context context,String name){
        sHelper = MySqLiteHelper.getInstance(context,name);
        Log.i(TAG,"MyContentPro:sHelper"+sHelper.toString());
    }
    @Override
    public boolean onCreate() {
        Log.i(TAG,"onCreate:sHelper"+sHelper.toString());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        db =  sHelper.getReadableDatabase();
         switch (sUriMatcher.match(uri)){
             case 1:
                 return  db.query(NoteListTable.TABLE,projection,selection,selectionArgs,null,null,sortOrder);
         }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        db =  sHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)){
            case 1:
                db.insert(NoteListTable.TABLE,null,values);
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        db =  sHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)){
            case 1:
                 return  db.delete(NoteListTable.TABLE,selection,selectionArgs);
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        db =  sHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)){
            case 1:
                return  db.update(NoteListTable.TABLE,values ,selection,selectionArgs);
        }
        return 0;
    }
    public void closeToAll(){
        if (sHelper!=null){
            sHelper.close();
        }
    }
}

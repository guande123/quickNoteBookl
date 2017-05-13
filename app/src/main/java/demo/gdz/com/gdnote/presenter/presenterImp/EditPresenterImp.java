package demo.gdz.com.gdnote.presenter.presenterImp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import demo.gdz.com.gdnote.data.NoteList;
import demo.gdz.com.gdnote.data.NoteListTable;
import demo.gdz.com.gdnote.iface.EditView;
import demo.gdz.com.gdnote.presenter.EditPresenter;
import demo.gdz.com.gdnote.utils.MyContentPro;
import demo.gdz.com.gdnote.utils.StringFormatUtils;

import static android.content.ContentValues.TAG;
import static demo.gdz.com.gdnote.utils.StringFormatUtils.compliePath;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class EditPresenterImp implements EditPresenter {
    private EditView mEditView;
    private Context mContext;
    private MyContentPro mMyContentPro;
    private ArrayList<String> mImgPath = new ArrayList<>();
    private NoteList mNoteList;
   // private ArrayList<Integer> mPosition = new ArrayList<>();
    public EditPresenterImp(Context context,EditView editView){
        mMyContentPro = new MyContentPro(context,"notelist,db");
        mEditView = editView;
        mContext =context;
    }

    @Override
    public void addNoteList(String content){
        String time = StringFormatUtils.formatCurTime();
        Log.i(TAG,time);
        ContentValues values = new ContentValues();
        values.put(NoteListTable.TIME,time);
        values.put(NoteListTable.CONTENT,content);
        values.put(NoteListTable.TIME,time+"12324");
        if (mImgPath!=null&&mImgPath.size()>0){
            String[] paths = Arrays.copyOf(mImgPath.toArray(),mImgPath.size(),String[].class);
            String  path = compliePath(paths);
            Log.i(TAG, "saveContent: path = "+path);
            values.put(NoteListTable.PATH,path);
        }
        Log.i(TAG, "addNoteList: "+content);

        mMyContentPro.insert(MyContentPro.NOTELIST_URI,values);
        mEditView.saveSuccess();
    }

    @Override
    public void addNoteList(String content, NoteList notelist) {
        String time = StringFormatUtils.formatCurTime();
        Log.i(TAG,time);
        ContentValues values = new ContentValues();
        values.put(NoteListTable.ID,notelist.getId());
        values.put(NoteListTable.TIME,time);
        values.put(NoteListTable.CONTENT,content);
        values.put(NoteListTable.TIME,time+"12324");
        String [] imgPath =notelist.getImgPath();
        if(imgPath!=null&&imgPath.length>0){
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i =0;i<imgPath.length;i++){
                arrayList.add(imgPath[i]);
            }
            if (mImgPath!=null&&mImgPath.size()>0){
            for (int i =0;i<mImgPath.size();i++){
                arrayList.add(mImgPath.get(i));
            }
            mImgPath = arrayList;
            String[] paths = Arrays.copyOf(mImgPath.toArray(),mImgPath.size(),String[].class);
            String  path = compliePath(paths);
            Log.i(TAG, "saveContent: path = "+path);
            values.put(NoteListTable.PATH,path);
        }
        }
        String [] selectArgs = new String[]{String.valueOf(notelist.getId())};
        String select =NoteListTable.ID +"=?";
       /* mMyContentPro.update(MyContentPro.NOTELIST_URI,values, select,selectArgs);*/
        mMyContentPro.delete(MyContentPro.NOTELIST_URI,select,selectArgs);
        mMyContentPro.insert(MyContentPro.NOTELIST_URI,values);
        mEditView.saveSuccess();
    }

    @Override
    public void onResultForPath(Intent data,int index) {
        Uri imgUri = data.getData();
        String[] filePathColumn = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor =  mContext.getContentResolver().query(imgUri,filePathColumn,null,null,null);
        String path =  null;
        while(cursor.moveToNext()){
            path =   cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
        }
        Log.i(TAG, "onActivityResult: imgUri = "+ imgUri.toString());
        Log.i(TAG, "onActivityResult: path = "+path);
        cursor.close();
        mImgPath.add(path);
       // mPosition.add(index);
        mEditView.insertBitmap(path,index);

    }

    @Override
    public void closeMyContentPro() {
         if (mMyContentPro!=null)
             mMyContentPro.closeToAll();
    }
}

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

import demo.gdz.com.gdnote.data.NoteListTable;
import demo.gdz.com.gdnote.iface.EditView;
import demo.gdz.com.gdnote.presenter.EditPresenter;
import demo.gdz.com.gdnote.utils.MyContentPro;
import demo.gdz.com.gdnote.utils.StringFormatUtils;
import demo.gdz.com.gdnote.widget.MyEditView;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class EditPresenterImp implements EditPresenter {
    private EditView mEditView;
    private Context mContext;
    private MyContentPro mMyContentPro;
    private ArrayList<String> mImgPath = new ArrayList<>();
    private ArrayList<Integer> mPosition = new ArrayList<>();
    public EditPresenterImp(Context context,EditView editView){
        mMyContentPro = new MyContentPro(context,"notelist,db");
        mEditView = editView;
    }

    @Override
    public  void saveContent(MyEditView editView) {
        String content = editView.getText().toString().trim();
        String time = StringFormatUtils.formatCurTime();
        Log.i(TAG,time);
        ContentValues values = new ContentValues();
        values.put(NoteListTable.TIME,time);
        values.put(NoteListTable.CONTENT,content);
        if (mImgPath!=null&&mImgPath.size()>0){
            String[] paths = Arrays.copyOf(mImgPath.toArray(),mImgPath.size(),String[].class);
            String path = StringFormatUtils.compliePath(paths);
            Log.i(TAG, "saveContent: path = "+path);
            values.put(NoteListTable.PATH,path);
        }
        if (mPosition!=null&&mPosition.size()>0){
            Integer[] positions = Arrays.copyOf(mPosition.toArray(),mPosition.size(),  Integer[].class);
            String position = StringFormatUtils.compliePosition(positions);
            Log.i(TAG, "saveContent: position = "+position);
            values.put(NoteListTable.POSITION,position);
        }
        mMyContentPro.insert(MyContentPro.NOTELIST_URI,values);
        mEditView.saveSuccess();
    }

    @Override
    public void onResultForPath(Intent data,MyEditView editView) {
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
        int index = editView.getSelectionStart();
        mImgPath.add(path);
        mPosition.add(index);
        editView.insertBitmap(path,index);
    }

    @Override
    public void closeMyContentPro() {
         if (mMyContentPro!=null)
             mMyContentPro.closeToAll();
    }
}

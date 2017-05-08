package demo.gdz.com.gdnote.presenter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import demo.gdz.com.gdnote.data.NoteList;
import demo.gdz.com.gdnote.data.NoteListTable;
import demo.gdz.com.gdnote.iface.MainView;
import demo.gdz.com.gdnote.presenter.presenterImp.MViewPersenter;
import demo.gdz.com.gdnote.utils.MyContentPro;
import demo.gdz.com.gdnote.utils.StringFormatUtils;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class MViewPresenterImp implements MViewPersenter{
    private NoteList[] mNoteLists;
    private MainView mMainView;
    private MyContentPro mMyContentPro;
    public MViewPresenterImp(Context context , MainView mainView){
        this.mMainView = mainView;
        mMyContentPro = new MyContentPro(context,"notelist.db");
    }
   //每次从数据库里加载NoteList所有的数据
    public  void  notifyNoteLists() {
        Cursor cursor = mMyContentPro.query(MyContentPro.NOTELIST_URI,null,null,null,null);//mSQLiteDatabase.query("notelist",null,null,null,null,null,null);
        int i =0;
        int size=cursor.getCount();
        Log.i(TAG, "initNoteList:size  = "+size);
        mNoteLists= new NoteList[size];
        Log.i(TAG, "noteLists:length  = "+mNoteLists.length);
        if(size!=0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                mNoteLists[i] = new NoteList();
                mNoteLists[i].setTime(cursor.getString(0));
                mNoteLists[i].setContent(cursor.getString(1));
                String  paths = cursor.getString(2);
                String[] path =   StringFormatUtils.formatPath(paths);
                mNoteLists[i].setImgPath(path);

                String positions=cursor.getString(3);
                Integer[] position = StringFormatUtils.formatPosition(positions);
                Log.i(TAG, "notifyNoteLists: paths"+paths);
                Log.i(TAG, "notifyNoteLists: positions"+positions);
                mNoteLists[i].setPosition(position);
                i++;
            }
        }
        cursor.close();
        mMainView.notifyAdapter(mNoteLists);
    }
    //ListView中搜索合适的item并刷新adapter
    @Override
    public  void searchItem(String string) {
        Log.i(TAG, "searchItem: string = "+string);
        ArrayList<NoteList> arraylist = new ArrayList<>();
        if(mNoteLists!=null){
            for (int i=0;i<mNoteLists.length;i++){
                int index =   mNoteLists[i].toString().indexOf(string);
                Log.i(TAG, "searchItem:index"+i+" = "+index);
                Log.i(TAG, "searchItem:  mNoteList[i].toString() ="+  mNoteLists[i].toString());
                if(index >=0){
                    arraylist.add(mNoteLists[i]);
                }
            }
            Log.i(TAG, "searchItem:arraylist.size() "+arraylist.size());
            Object[] objects = arraylist.toArray();
            NoteList[] noteList = Arrays.copyOf(objects,objects.length,NoteList[].class);
            if(string.equals("")){
               mMainView.notifyAdapter(mNoteLists);
            }else{
                mMainView.notifyAdapter(noteList);
            }
        }
    }
    //删除所处位置的NoteList并刷新adapter
    @Override
    public  void deleteNoteList(int position) {
        if (mNoteLists!=null) {
            String whereClaus = NoteListTable.TIME + "=?";
            Log.i(TAG, "deleteNoteList:mNoteList[position].getTime = " + mNoteLists[position].getTime());
            mMyContentPro.delete(MyContentPro.NOTELIST_URI, whereClaus, new String[]{mNoteLists[position].getTime()});
            notifyNoteLists();
        }
    }

    @Override
    public void unBindContent(){
       mMyContentPro.closeToAll();
    }
    @Override
    public NoteList[] NoteList() {
        return mNoteLists;
    }
}

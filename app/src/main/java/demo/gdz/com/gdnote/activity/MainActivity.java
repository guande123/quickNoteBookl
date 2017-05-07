package demo.gdz.com.gdnote.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import demo.gdz.com.gdnote.R;
import demo.gdz.com.gdnote.adapter.NoteListAdapter;
import demo.gdz.com.gdnote.data.NoteList;
import demo.gdz.com.gdnote.data.NoteListTable;
import demo.gdz.com.gdnote.iface.OnDeleteListener;
import demo.gdz.com.gdnote.utils.MyContentPro;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnDeleteListener {
    private static final String TAG ="MainActivity" ;
    private ListView mListView;
    private Button mSearchBtn;
    private Button  mEditBtn;
    private EditText mEdtSearch;
    private NoteListAdapter mAdapter;
    private NoteList[] mNoteList;
    private MyContentPro mContentPro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();




    }

    @Override
    protected void onResume() {
        super.onResume();
        mNoteList = initNoteList();
        mAdapter.notifyData(mNoteList);
    }

    private NoteList[] initNoteList() {
        Cursor cursor = mContentPro.query(MyContentPro.NOTELIST_URI,null,null,null,null);//mSQLiteDatabase.query("notelist",null,null,null,null,null,null);
        int i =0;
        int size=cursor.getCount();
        Log.i(TAG, "initNoteList:size  = "+size);
        NoteList[] noteLists = new NoteList[size];
        Log.i(TAG, "noteLists:length  = "+noteLists.length);
        if(size!=0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                noteLists[i] = new NoteList();
                noteLists[i].setTime(cursor.getString(0));
                noteLists[i].setContent(cursor.getString(1));
                /*String  paths = cursor.getString(2);
                noteLists[i].setImgPath(StringFormatUtils.formatPath(paths));
                String positions = cursor.getString(3);
                noteLists[i].setPosition(StringFormatUtils.formatPosition(positions));*/
                i++;
            }
        }
        cursor.close();
        return  noteLists;
    }

    @Override
    protected void onDestroy() {
     if(mContentPro!=null){
         mContentPro.closeToAll();
     }
        super.onDestroy();
    }

    private void findViews() {
        mContentPro = new MyContentPro(MainActivity.this,"notelist.db");
        mListView = (ListView) findViewById(R.id.listview);
        mSearchBtn = (Button) findViewById(R.id.btn_manager);
        mSearchBtn.setOnClickListener(this);
        mEditBtn = (Button) findViewById(R.id.btn_edit);
        mEditBtn.setOnClickListener(this);
        mEdtSearch = (EditText) findViewById(R.id.edt_search);
        mNoteList = initNoteList();
        //init adapter
        mAdapter = new NoteListAdapter(this,mNoteList);
        mAdapter.setOnDeleteListener(this);
        mListView.setAdapter(mAdapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                intent.putExtra(NoteListTable.CONTENT,mNoteList[position].getContent());
                intent.putExtra("NoteList",mNoteList[position]);
                startActivity(intent);
            }
        });
        mEdtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                searchItem();
                return false;
            }
        });
    }

    @Override
    public void deleteNoteList(int position) {
        String whereClaus=NoteListTable.TIME+"=?";
        Log.i(TAG, "deleteNoteList:mNoteList[position].getTime = "+mNoteList[position].getTime());
        mContentPro.delete(MyContentPro.NOTELIST_URI, whereClaus,new String[]{mNoteList[position].getTime()});
        mNoteList = initNoteList();
        mAdapter.notifyData(mNoteList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_edit:
                startActivity(new Intent(MainActivity.this,EditActivity.class));
            case R.id.btn_manager:
                searchItem();
        }
    }

    private void searchItem() {
        String string =  mEdtSearch.getText().toString().trim();
        Log.i(TAG, "searchItem: string = "+string);
        ArrayList<NoteList> arraylist = new ArrayList<>();
        if(mNoteList!=null){
        for (int i=0;i<mNoteList.length;i++){
             int index =   mNoteList[i].toString().indexOf(string);
            Log.i(TAG, "searchItem:index"+i+" = "+index);
            Log.i(TAG, "searchItem:  mNoteList[i].toString() ="+  mNoteList[i].toString());
            if(index >=0){
                arraylist.add(mNoteList[i]);
            }
            }
             /*mNoteList = (NoteList[]) arraylist.toArray();*/
            Log.i(TAG, "searchItem:arraylist.size() "+arraylist.size());
            Object[] objects = arraylist.toArray();
            NoteList[] noteList = Arrays.copyOf(objects,objects.length,NoteList[].class);
            if(string.equals("")){
                mAdapter.notifyData(mNoteList);
            }else{
                mAdapter.notifyData(noteList);
            }

        }

    }


}

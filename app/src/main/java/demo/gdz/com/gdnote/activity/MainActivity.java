package demo.gdz.com.gdnote.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import demo.gdz.com.gdnote.R;
import demo.gdz.com.gdnote.adapter.NoteListAdapter;
import demo.gdz.com.gdnote.broadcast.DBChangeReceiver;
import demo.gdz.com.gdnote.data.NoteList;
import demo.gdz.com.gdnote.iface.DBChangeListener;
import demo.gdz.com.gdnote.iface.MainView;
import demo.gdz.com.gdnote.iface.OnDeleteListener;
import demo.gdz.com.gdnote.presenter.MViewPersenter;
import demo.gdz.com.gdnote.presenter.presenterImp.MViewPresenterImp;

;

public class MainActivity extends AppCompatActivity implements OnDeleteListener,MainView,View.OnClickListener,DBChangeListener{
    private static final String TAG ="MainActivity" ;
    private ListView mListView;
    private Button mSearchBtn;
    private Button  mEditBtn;
    private EditText mEdtSearch;
    private NoteListAdapter mAdapter;
    private MViewPersenter mMViewPersenter;
    private DBChangeReceiver mChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initReceiver() {
        mChangeReceiver = new DBChangeReceiver(MainActivity.this);
        IntentFilter filterIntent=new IntentFilter();
        filterIntent.addAction(DBChangeReceiver.ACTION_CHANGE);
       // filterIntent.addCategory("");
        registerReceiver(mChangeReceiver,filterIntent);
    }

    @Override
    protected void onDestroy() {
        if(mMViewPersenter!=null){
            mMViewPersenter.unBindContent();
        }
        if(mChangeReceiver!=null){
            unregisterReceiver(mChangeReceiver);
        }
        super.onDestroy();
    }
    private void findViews() {
        mListView = (ListView) findViewById(R.id.listview);
        mSearchBtn = (Button) findViewById(R.id.btn_manager);
        mSearchBtn.setOnClickListener(this);
        mEditBtn = (Button) findViewById(R.id.btn_edit);
        mEditBtn.setOnClickListener(this);
        mEdtSearch = (EditText) findViewById(R.id.edt_search);
        mAdapter = new NoteListAdapter(this,null);
        mAdapter.setOnDeleteListener(this);
        mListView.setAdapter(mAdapter);
        mMViewPersenter = new MViewPresenterImp(MainActivity.this,MainActivity.this);
        mMViewPersenter.notifyNoteLists();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: position"+position);
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                NoteList[] noteList = mMViewPersenter.NoteList();
               // intent.putExtra(NoteListTable.CONTENT,noteList[position].getContent());
                intent.putExtra("NoteList",noteList[position]);
                startActivity(intent);
            }
        });
        mEdtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String string =  mEdtSearch.getText().toString().trim();
                mMViewPersenter.searchItem(string);
                return false;
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_edit:
                startActivity(new Intent(MainActivity.this,EditActivity.class));
            case R.id.btn_manager:
                String string =  mEdtSearch.getText().toString().trim();
                mMViewPersenter.searchItem(string);
        }
}
    @Override
    public void notifyAdapter(NoteList[] noteLists) {
        mAdapter.notifyData(noteLists);
    }
    @Override
    public void deleteNoteList(int position) {
        mMViewPersenter.deleteNoteList(position);
    }
    @Override
    public void dbChange() {
        mMViewPersenter.notifyNoteLists();
    }
}

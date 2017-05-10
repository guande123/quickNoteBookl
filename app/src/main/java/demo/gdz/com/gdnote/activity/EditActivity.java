package demo.gdz.com.gdnote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import demo.gdz.com.gdnote.R;
import demo.gdz.com.gdnote.broadcast.DBChangeReceiver;
import demo.gdz.com.gdnote.data.NoteList;
import demo.gdz.com.gdnote.iface.EditView;
import demo.gdz.com.gdnote.presenter.EditPresenter;
import demo.gdz.com.gdnote.presenter.presenterImp.EditPresenterImp;
import demo.gdz.com.gdnote.widget.MyEditView;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class EditActivity extends AppCompatActivity implements View.OnClickListener,EditView{
    private static final String TAG = "EditActivity";
    private static final int CODE_MEDIESTORE = 1001;
    private MyEditView mContent;
    private Button mBtnSave;
    private Button mBtnImg;
    private EditPresenter mEditPresenter;
    private  NoteList mNoteList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        findViews();
        initEditContent();
    }
    private void initEditContent() {
        Intent intent =  getIntent();
        mNoteList = (NoteList) intent.getSerializableExtra("NoteList");
        if (mNoteList!=null){
            Log.i(TAG, "initEditContent: noteLists= "+mNoteList);
            Log.i(TAG, "initEditContent: noteLists.toString = "+mNoteList.toString());
            mContent.insertData(mNoteList);
        }
    }
    private void findViews() {
        mContent = (MyEditView) findViewById(R.id.edt_content);
        mBtnSave = (Button) findViewById(R.id.btn_save_content);
        mBtnImg = (Button) findViewById(R.id.btn_select_img);
        mBtnSave.setOnClickListener(this);
        mBtnImg.setOnClickListener(this);
        mEditPresenter = new EditPresenterImp(EditActivity.this,EditActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save_content:
                String contentStr =mContent.getText().toString().trim();
                  if (mNoteList!=null)
                  {
                      Log.i(TAG, "onClick: mNoteList update");
                      mEditPresenter.addNoteList(contentStr,mNoteList);
                  }else{
                      mEditPresenter.addNoteList(contentStr);
                  }
                break;
            case R.id.btn_select_img:
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,CODE_MEDIESTORE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CODE_MEDIESTORE&&resultCode==RESULT_OK&&data!=null){
            int index = mContent.getSelectionStart();
            mEditPresenter.onResultForPath(data,index);
        }
    }

    @Override
    protected void onDestroy() {
        if(mEditPresenter!=null){
            mEditPresenter.closeMyContentPro();
        }
        super.onDestroy();
    }


    @Override
    public void saveSuccess() {
        Intent intent = new Intent();
        intent.setAction(DBChangeReceiver.ACTION_CHANGE);
        sendBroadcast(intent);
        Toast.makeText(EditActivity.this,"save successful",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void insertBitmap(String path, int index) {
        mContent.insertBitmap(path,index);
    }
}

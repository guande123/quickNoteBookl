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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        findViews();
        initEditContent();
    }
    private void initEditContent() {
        Intent intent =  getIntent();
        NoteList noteList = (NoteList) intent.getSerializableExtra("NoteList");
        if (noteList!=null){
            Log.i(TAG, "initEditContent: noteLists= "+noteList);
            Log.i(TAG, "initEditContent: noteLists.toString = "+noteList.toString());
            mContent.insertData(noteList);
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
                mEditPresenter.saveContent(mContent);
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
            mEditPresenter.onResultForPath(data,mContent);
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
        Toast.makeText(EditActivity.this,"save successful",Toast.LENGTH_SHORT).show();
        finish();
    }
}

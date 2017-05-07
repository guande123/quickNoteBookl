package demo.gdz.com.gdnote.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import demo.gdz.com.gdnote.R;
import demo.gdz.com.gdnote.data.NoteList;
import demo.gdz.com.gdnote.data.NoteListTable;
import demo.gdz.com.gdnote.utils.MyContentPro;
import demo.gdz.com.gdnote.utils.StringFormatUtils;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class EditActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "EditActivity";
    private static final int CODE_MEDIESTORE = 1001;
    private EditText mContent;
    private Button mBtnSave;
    private Button mBtnImg;
    private MyContentPro mContentPro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        findViews();
        initEditContent();
        mContentPro = new MyContentPro(EditActivity.this,"notelist.db");

      //  insertBitmap();

    }
/*    无法通过uri直接加载图片，具体原因 未知*/
    private void insertBitmap(Uri uri) {
        Editable editable =  mContent.getEditableText();
        SpannableString ss = new SpannableString("");
   /*     Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
        ImageSpan img=new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE);*/
        ImageSpan img = new ImageSpan(EditActivity.this,uri);
        ss.setSpan(img,0,ss.length(),SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        int index = mContent.getSelectionStart();
        editable.insert(index,ss);
    }

    private void insertBitmap(String path,int index) /*throws FileNotFoundException */{
        Log.i(TAG, "insertBitmap: index 1 = "+index);
        Editable editable =  mContent.getEditableText();
        SpannableString newLine = new SpannableString("\n");
        editable.insert(index,newLine);
        SpannableString ss = new SpannableString(path);
       Bitmap bitmap = BitmapFactory.decodeFile(path);
     /*   InputStream is = new FileInputStream(new File(path));
        BitmapFactory.decodeStream(is);*/
         ImageSpan img = new ImageSpan(EditActivity.this,bitmap);
        ss.setSpan(img,0,ss.length(),SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
       // index=mContent.getSelectionStart(); Log.i(TAG, "insertBitmap: index 1 = "+index);
        editable.insert(index,ss);
        // index=mContent.getSelectionStart(); Log.i(TAG, "insertBitmap: index 1 = "+index);
        editable.insert(index,newLine);
    }

    private void initEditContent() {
        Intent intent =  getIntent();
        String content = intent.getStringExtra(NoteListTable.CONTENT);
        NoteList noteList = (NoteList) intent.getSerializableExtra("NoteList");
      /*  if(content!=null){
            mContent.setText(content);
        }*/
        if (noteList!=null){
            Log.i(TAG, "initEditContent: noteLists.length = "+noteList);
            Log.i(TAG, "initEditContent: noteLists.length = "+noteList.toString());
            insertData(noteList,content);
        }
       // content.replace()
    }

    private void insertData(NoteList noteList,String content) {
        String[] paths = noteList.getImgPath();
        int [] positions = noteList.getPosition();
        for(int i=0;i<paths.length;i++){
            content.replace(paths[i],"");
            insertBitmap(paths[i],positions[i]);
        }

    }

    private void saveContent() {
        String content = mContent.getText().toString().trim();
        String time = StringFormatUtils.formatCurTime();
        Log.i(TAG,time);
        ContentValues values = new ContentValues();
        values.put(NoteListTable.TIME,time);
        values.put(NoteListTable.CONTENT,content);
        if (mImgPath!=null&&mPosition!=null){
            String[] paths = Arrays.copyOf(mImgPath.toArray(),mImgPath.size(),String[].class);
            int[] positions = Arrays.copyOf(mPosition.toArray(),mPosition.size(),  int[].class);
           String path = StringFormatUtils.compliePath(paths);
            String position = StringFormatUtils.compliePosition(positions);
            Log.i(TAG, "saveContent: path = "+path);
            Log.i(TAG, "saveContent: position = "+position);
            values.put(NoteListTable.PATH,path);
            values.put(NoteListTable.POSITION,position);
        }

        mContentPro.insert(MyContentPro.NOTELIST_URI,values);
        Toast.makeText(EditActivity.this,"save successful!!",Toast.LENGTH_SHORT).show();
        finish();
    }

    private void findViews() {
        mContent = (EditText) findViewById(R.id.edt_content);
        mBtnSave = (Button) findViewById(R.id.btn_save_content);
        mBtnImg = (Button) findViewById(R.id.btn_select_img);
        mBtnSave.setOnClickListener(this);
        mBtnImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save_content:
                saveContent();
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
           Uri imgUri = data.getData();
            String[] filePathColumn = new String[]{MediaStore.Images.Media.DATA};
            Cursor cursor =  getContentResolver().query(imgUri,filePathColumn,null,null,null);
            String path =  null;
            while(cursor.moveToNext()){
              path =   cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            }
            Log.i(TAG, "onActivityResult: imgUri = "+ imgUri.toString());
            Log.i(TAG, "onActivityResult: path = "+path);
            cursor.close();
           // insertBitmap(imgUri);
            int index = mContent.getSelectionStart();
            mImgPath.add(path);
            mPosition.add(index);
            insertBitmap(path,index);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private ArrayList<String> mImgPath = new ArrayList<>();
    private ArrayList<Integer> mPosition = new ArrayList<>();
}

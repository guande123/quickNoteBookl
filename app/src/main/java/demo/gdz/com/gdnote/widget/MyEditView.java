package demo.gdz.com.gdnote.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;

import demo.gdz.com.gdnote.data.NoteList;
import demo.gdz.com.gdnote.utils.StringFormatUtils;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class MyEditView extends AppCompatEditText {
    private Context mContext;
    public MyEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }
    public  void insertBitmap(String path,int index) /*throws FileNotFoundException */{
        Log.i(TAG, "insertBitmap: index 1 = "+index);
        Editable editable =  getEditableText();
        SpannableString newLine = new SpannableString("\n");
        editable.insert(index,newLine);
        SpannableString ss = new SpannableString(path);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ImageSpan img = new ImageSpan(mContext,bitmap);
        ss.setSpan(img,0,ss.length(),SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        editable.insert(index,ss);
        editable.insert(index,newLine);
    }
    public  void insertData(NoteList noteList) {
        String content = noteList.getContent();
        String[] paths = noteList.getImgPath();
        Integer[] positions = noteList.getPosition();
        if(paths!=null &&positions!=null) {
            for (int i = 0; i < paths.length; i++) {
                String replace = content.replace(paths[i], "\b");
                Log.i(TAG, "insertData:replace =" + replace);
                replace = StringFormatUtils.trimNString(replace);
                Log.i(TAG, "insertData:replace trim  =" + replace);
                Log.i(TAG, "insertData:replace.length() =" + replace.length());
                Editable  editable =   getEditableText();
                editable.insert(0,replace);
                insertBitmap(paths[i], positions[i]);
            }
        }else{
            Editable  editable =  getEditableText();
            editable.insert(0,content);
        }
    }
    /*    无法通过uri直接加载图片，具体原因 未知*/
    private void insertBitmap(Uri uri) {
        Editable editable =  getEditableText();
        SpannableString ss = new SpannableString("");
   /*     Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
        ImageSpan img=new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE);*/
        ImageSpan img = new ImageSpan(mContext,uri);
        ss.setSpan(img,0,ss.length(),SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        int index =getSelectionStart();
        editable.insert(index,ss);
    }
}

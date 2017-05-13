package demo.gdz.com.gdnote.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;

import demo.gdz.com.gdnote.data.NoteList;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class MyEditView extends AppCompatEditText {
    private Context mContext;
    private Editable mEditable;
    public MyEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        mEditable =getEditableText();
    }
    public  void insertBitmap(String path,int index) /*throws FileNotFoundException */{
        Log.i(TAG, "insertBitmap: index 1 = "+index);
     /*   SpannableString newLine = new SpannableString("\n");*/
   //     mEditable.insert(index,newLine);
        SpannableString ss = new SpannableString(path);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Log.i(TAG, "insertBitmap: getwidth() = "+ bitmap.getWidth());
        Log.i(TAG, "insertBitmap: getHeight() = "+ bitmap.getHeight());
        Bitmap scaleBitmap = scaleBitmap(bitmap,50,50);
        ImageSpan img = new ImageSpan(mContext,scaleBitmap);
        ss.setSpan(img,0,ss.length(),SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        mEditable.insert(index,ss);
     //   mEditable.insert(index,newLine);
    }

    public Bitmap scaleBitmap(Bitmap origin , int   w ,int  h){
        if (origin ==null){
            return  null;
        }
       int width =  origin.getWidth();
        int height = origin.getHeight();
        Log.i(TAG, "insertBitmap:origin  getwidth() = "+ origin.getWidth());
        Log.i(TAG, "insertBitmap:origin getHeight() = "+ origin.getHeight());
        float scaleWidth = (float) w/width;
        float scaleHeight = (float) h/height;
        Log.i(TAG, "insertBitmap:origin scaleWidth = "+ scaleWidth);
        Log.i(TAG, "insertBitmap:origin scaleHeight = "+ scaleHeight);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(origin,0,0,width,height,matrix,false);
   /*      if (!origin.isRecycled()) {
            origin.recycle();
         }*/
         return bitmap;
    }
    public  void insertData(NoteList noteList) {
        String content = noteList.getContent();
        String[] paths = noteList.getImgPath();
        if(paths!=null ) {
            int len = paths.length;
            int[] index = new int[paths.length];
            for (int i = 0; i < len; i++) {
               index[i] = content.indexOf(paths[i]);
                content =content.replace(paths[i],"");
                Log.i(TAG, "insertData:index =" + index[i]);
                Log.i(TAG, "insertData:index =" + paths[i]);
                Log.i(TAG, "insertData:content =" + content);
            }
            mEditable.append(content);
            for(int i = 0; i <len; i++){
                if(index[i]!=-1){
                    insertBitmap(paths[i],index[i]);
                }
            }
        }else{
              mEditable.append(content);
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

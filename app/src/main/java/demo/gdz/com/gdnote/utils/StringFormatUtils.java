package demo.gdz.com.gdnote.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class StringFormatUtils {
    public static String formatCurTime(){
        String time =null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        time  =   dateFormat.format(date);
        return time;
    }
    public static String[] formatPath(String path){
        if (path==null)
            return null;
        return  path.split(";");
    }
    public static String compliePath(String [] path){
        StringBuffer sb = new StringBuffer();
        for(int i =0;i<path.length;i++){
            sb.append(path[i]+";");
        }
        return sb.toString();
    }
    public static  String compliePosition(Integer[] positions){
        StringBuffer sb = new StringBuffer();
        for (int i =0;i<positions.length;i++){
            Log.i(TAG, "compliePosition: positions"+i+positions[i]);
            Log.i(TAG, "compliePosition: positions"+i+positions[i].intValue());
            sb.append(positions[i].intValue()+";");
        }
        return sb.toString();
    }
    public static Integer[] formatPosition(String positionStr){
        if (positionStr==null){
            return null;
        }
        String []  positionArr = positionStr.split(";");
        int len =positionArr.length;
        Integer[]  position = new Integer[len];
        for (int i =0;i<len;i++){
            Log.i(TAG, "formatPosition: position"+position[i]);
            if (!positionArr[i].equals(""))
            {
                position[i] = Integer.parseInt(positionArr[i]);
            }
        }
        return position;
    }
    public static String trimNString(String string){
        String[] strArr = string.split("\n");
        String str= "";
        if(strArr==null){
            return  str;
        }
        int len =strArr.length;
        Log.i(TAG, "trimNString: strArr"+len);
        for (int i =0;i<len;i++){
                str+=strArr[i]+"\n";
        }
        return str;
    }
}

package demo.gdz.com.gdnote.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        return  path.split(";");
    }
    public static String compliePath(String [] path){
        StringBuffer sb = new StringBuffer();
        for(int i =0;i<path.length;i++){
            sb.append(path[i]+";");
        }
        return sb.toString();
    }
    public static  String compliePosition(int[] positions){
        StringBuffer sb = new StringBuffer();
        for (int i =0;i<positions.length;i++){
            sb.append(positions[i]+";");
        }
        return sb.toString();
    }
    public static int[] formatPosition(String positionStr){
        String []  positionArr = positionStr.split(";");
        int len =positionArr.length;
        int[]  position = new int[len];
        for (int i =0;i<len;i++){
            position[i] = Integer.parseInt(positionArr[i]);
        }
        return position;
    }
}

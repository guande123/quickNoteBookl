package demo.gdz.com.gdnote.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class NoteList implements Serializable{
    private String time;
    private String content;

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    private  String[] mImgPath;
    private int[] position;/*edittext 控件图片出现的位置*/
    public String[] getImgPath() {
        return mImgPath;
    }

    public void setImgPath(String[] imgPath) {
        mImgPath = imgPath;
    }

    public NoteList(){}
    public NoteList(String time,String content){
        this.time =time;
        this.content = content;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String toString(){
        return time +content;
    }
}

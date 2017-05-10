package demo.gdz.com.gdnote.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class NoteList implements Serializable{
    private String time;
    private String content;
    private int id;
    private  String[] mImgPath;
    private Integer[] position;/*edittext 控件图片出现的位置*/
    public NoteList(){}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public void setPosition(Integer[] position) {
        this.position = position;
    }
    public Integer[] getPosition() {
        return position;
    }
    public String[] getImgPath() {
        return mImgPath;
    }
    public void setImgPath(String[] imgPath) {
        mImgPath = imgPath;
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

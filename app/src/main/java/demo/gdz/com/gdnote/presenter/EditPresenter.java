package demo.gdz.com.gdnote.presenter;

import android.content.Intent;

import demo.gdz.com.gdnote.data.NoteList;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public interface EditPresenter {
    void onResultForPath(Intent data,int  index);
    void closeMyContentPro();
    void addNoteList(String  content);
    void addNoteList(String  content, NoteList notelist);
}

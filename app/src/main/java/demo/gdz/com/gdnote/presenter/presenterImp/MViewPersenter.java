package demo.gdz.com.gdnote.presenter.presenterImp;

import demo.gdz.com.gdnote.data.NoteList;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public interface MViewPersenter {
    void notifyNoteLists();
    void searchItem(String string);
    void deleteNoteList(int position);
    void unBindContent();

    NoteList[] NoteList();
}

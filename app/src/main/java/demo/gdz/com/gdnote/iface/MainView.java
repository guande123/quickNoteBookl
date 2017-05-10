package demo.gdz.com.gdnote.iface;

import demo.gdz.com.gdnote.data.NoteList;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public interface MainView {
    /*刷新adapter*/
    void notifyAdapter(NoteList[] noteLists);
}

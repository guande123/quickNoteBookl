package demo.gdz.com.gdnote.presenter;

import android.content.Intent;

import demo.gdz.com.gdnote.widget.MyEditView;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public interface EditPresenter {
    void saveContent(MyEditView content);

    void onResultForPath(Intent data,MyEditView editView);

    void closeMyContentPro();
}

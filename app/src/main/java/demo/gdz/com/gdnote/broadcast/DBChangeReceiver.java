package demo.gdz.com.gdnote.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import demo.gdz.com.gdnote.activity.MainActivity;
import demo.gdz.com.gdnote.iface.DBChangeListener;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class DBChangeReceiver extends BroadcastReceiver {
    public  static final String ACTION_CHANGE ="SQLITE_CHANGE";
    public  static  final String TAG="DBChangeReceiver";
    private DBChangeListener mDBChangeListener;
    public DBChangeReceiver(DBChangeListener dbChangeListener){
        mDBChangeListener = dbChangeListener;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive:context = "+context);
        Log.i(TAG, "onReceive: context.getClass ="+context.getClass());
        Log.i(TAG, "onReceive:intent "+intent);
        Log.i(TAG, "onReceive: intent.getAction"+intent.getAction());
        if(intent.getAction().equals(ACTION_CHANGE)){
             mDBChangeListener.dbChange();
            Log.i(TAG, "onReceive: context.getClass() == MainActivity.class  ::"+(context.getClass() == MainActivity.class));
        }
    }
}

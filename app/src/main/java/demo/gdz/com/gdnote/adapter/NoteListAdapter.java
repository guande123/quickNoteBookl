package demo.gdz.com.gdnote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import demo.gdz.com.gdnote.R;
import demo.gdz.com.gdnote.data.NoteList;
import demo.gdz.com.gdnote.iface.OnDeleteListener;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class NoteListAdapter extends BaseAdapter {
    private NoteList[] mNoteList;
    private Context mContext;
    private OnDeleteListener mDeleteListener;
    public NoteListAdapter(Context context,NoteList[] noteList){
        mNoteList = noteList;
        mContext = context;
    }
    @Override
    public int getCount() {
        if (mNoteList==null)
            return 0;
        return mNoteList.length;
    }

    @Override
    public Object getItem(int position) {
        if(mNoteList==null)
            return null;
        return mNoteList[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        final int posi =position;
        if(convertView ==null){
            view  = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        }else{
            view = convertView;
        }
        TextView  timeTx = (TextView) view.findViewById(R.id.tx_view_time);
        TextView  contentTx = (TextView) view.findViewById(R.id.tx_view_content);
        Button btnDel = (Button) view.findViewById(R.id.btn_delete);
        timeTx.setText(mNoteList[position].getTime());
        contentTx.setText(mNoteList[position].getContent());
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteListener.deleteNoteList(posi);
            }
        });
        return view;
    }
    public void setOnDeleteListener(OnDeleteListener listener){
        mDeleteListener =listener;
    }
    public void notifyData(NoteList[] notelist) {
        mNoteList = notelist;
        notifyDataSetChanged();
    }

}

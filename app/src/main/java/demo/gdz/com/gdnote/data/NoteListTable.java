package demo.gdz.com.gdnote.data;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class NoteListTable {
    public static  final String TABLE = "notelist";
    public  static final String TIME = "time";
    public static final String CONTENT = "content";
    public static final String PATH= "imgPath";
    public static final String POSITION ="position";
    public  static final String CREATE_NOTELIST="create table if not exists notelist(" +
            NoteListTable.TIME+  " varchar(30),"+
            NoteListTable.CONTENT+" text,"+
            NoteListTable.PATH+" varchar(200)," +
            NoteListTable.POSITION+" varchar(100) )";
}

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
    public static  final String  ID = "id";
    public static final int ADD = 1;
    public static  final int DELETE = 2;
    public static  final int MODIFY = 3;
    public static final int UPDATE = 4;
    public  static final String CREATE_NOTELIST="create table if not exists notelist(" +
            ID +"  INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,"+
            TIME+  " varchar(30),"+
            CONTENT+" text,"+
            PATH+" varchar(200) )";
}

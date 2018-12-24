package lecture.mobile.final_project.ma02_20151024;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DiaryDBHelper extends SQLiteOpenHelper {
    private final static String TAG = "DiaryDBHelper";

    private final static String DB_NAME ="diary_db";
    public final static String TABLE_NAME = "diary_table";
    public final static String ID = "_id";
    public final static String TITLE = "title";
    public final static String DATE = "date";
    public final static String PATH = "path";
    public final static String MEMO = "memo";

    public DiaryDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TABLE_NAME + " (" + ID + " integer primary key autoincrement, "
                + TITLE + " text, " + DATE + " text, " + PATH + " text, " + MEMO + " text);";
        Log.d(TAG, sql);
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

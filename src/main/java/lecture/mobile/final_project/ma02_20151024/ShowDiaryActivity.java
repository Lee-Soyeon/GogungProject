package lecture.mobile.final_project.ma02_20151024;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowDiaryActivity extends AppCompatActivity {
    final static String TAG = "ShowDiaryActivity";

    DiaryDBHelper helper;
    Cursor cursor;

    TextView tvTitle;
    TextView tvDate;
    ImageView ivPhoto;
    TextView tvMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_diary);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvDate = (TextView) findViewById(R.id.tvDate);
        ivPhoto = (ImageView)findViewById(R.id.ivPhoto);
        tvMemo = (TextView)findViewById(R.id.tvMemo);

//        MainActivity 에서 전달 받은 _id 값을 사용하여 DB 레코드를 가져온 후 ImageView 와 TextView 설정
        Intent intent = getIntent();
        int id = intent.getIntExtra("index", 0);

        helper = new DiaryDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DiaryDBHelper.TABLE_NAME + " WHERE _id = " + id + ";", null);
        cursor.moveToNext();

        tvTitle.setText(cursor.getString(1));
        tvDate.setText(cursor.getString(2));
        String path = cursor.getString(3);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ivPhoto.setImageBitmap(bitmap);
        tvMemo.setText(cursor.getString(4));

        helper.close();
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnClose:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) cursor.close();
    }

}

package lecture.mobile.final_project.ma02_20151024;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddDiaryActivity extends AppCompatActivity {
    private static final int REQUEST_TAKE_PHOTO = 200;

    private String mCurrentPhotoPath;

    TextView tvTitle;
    EditText etDate;
    ImageView ivPhoto;
    EditText etMemo;

    DiaryDBHelper helper;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        helper = new DiaryDBHelper(this);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        etDate = (EditText)findViewById(R.id.etDate);
        ivPhoto = (ImageView)findViewById(R.id.ivPhoto);
        etMemo = (EditText)findViewById(R.id.etMemo);

        intent = getIntent();
        tvTitle.setText("DIARY OF  < " + intent.getStringExtra("gungName") + " >");

        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 설정
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // nowDate 변수에 값을 저장
        String formatDate = sdfNow.format(date);
        etDate.setText(formatDate);

        ivPhoto.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    외부 카메라 호출
                    dispatchTakePictureIntent();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(); // 저장할 이미지 파일 생성
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                // 카메라 앱이 사진 캡처 후 저장할 이미지 파일의 정보를 content:// URI 형태로 전달
                Uri photoURI = FileProvider.getUriForFile(this, "lecture.mobile.final_project.ma02_20151024.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        // 지정한 경로에 파일 생성
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",     /* suffix */
                storageDir          /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.e("myPath", mCurrentPhotoPath);
        return image;
    }

    private void setPic() { // 저장한 이미지가 고화질일 경우 ImageView에 표시할 수 있는 적정한 크기로 변환
        ivPhoto.invalidate();

        // Get the dimensions of the View
        int targetW = ivPhoto.getWidth();
        int targetH = ivPhoto.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        ivPhoto.setImageBitmap(bitmap);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSave:
//                DB에 촬영한 사진의 파일 경로 및 메모 저장
                if (mCurrentPhotoPath != null) { // 사진을 촬영하고 SAVE 버튼을 누른 경우에만 DB에 저장
                    SQLiteDatabase db = helper.getWritableDatabase();

                    ContentValues row = new ContentValues();
                    row.put(DiaryDBHelper.TITLE, tvTitle.getText().toString());
                    row.put(DiaryDBHelper.DATE, etDate.getText().toString());
                    row.put(DiaryDBHelper.PATH, mCurrentPhotoPath);
                    row.put(DiaryDBHelper.MEMO, etMemo.getText().toString());
                    db.insert(DiaryDBHelper.TABLE_NAME, null, row);

                    helper.close();

                    finish();
                    Toast.makeText(this, "SAVE COMPLETE", Toast.LENGTH_SHORT).show();
                } else { // 사진을 촬영하지 않고 SAVE 버튼을 눌렀을 경우 Tosat로 사진 촬영 요청메세지 출력
                    Toast.makeText(this, "사진을 촬영해주세요", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnCancel:
                if (mCurrentPhotoPath != null) { // AddMemoActivity 에서 사진 추가 후 취소를 눌러 메모를 저장안 할 경우 저장한 사진을 저장소에서 삭제
                    File file = new File(mCurrentPhotoPath);
                    file.delete();
                }
                finish();
                break;
        }
    }
}

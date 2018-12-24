package lecture.mobile.final_project.ma02_20151024;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class BuildingActivity extends Activity {

    TextView tvTitle;
    TextView tvBuilding;
    TextView tvExplanation;
    ImageView ivBuilding;

    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvBuilding = (TextView)findViewById(R.id.tvBuilding);
        tvExplanation = (TextView)findViewById(R.id.tvExplanation);
        ivBuilding = (ImageView)findViewById(R.id.ivBuilding);

        intent = getIntent();
        tvTitle.setText(intent.getStringExtra("gungName"));
        tvBuilding.setText(intent.getStringExtra("buildingName"));
        tvExplanation.setText(intent.getStringExtra("explanation"));

    }

    @Override
    protected void onResume() {
        super.onResume();

        new getImageAsyncTask().execute(intent.getStringExtra("imageUrl"));
        Log.i("url", intent.getStringExtra("imageUrl"));
    }

    class getImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap imgBitmap = null;

            try {
                URL url = new URL(urls[0]);
                URLConnection conn = url.openConnection();
                conn.connect();

                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                imgBitmap = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                Log.e("failure", "외부 이미지 불러오기 실패! : " + e.getCause());
                e.printStackTrace();
            }

            return imgBitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {
            ivBuilding.setImageBitmap(bitmap);
        }
    }

}

package lecture.mobile.final_project.ma02_20151024;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GungActivity extends AppCompatActivity {

    TextView tvTitle;
    TextView tvGungInfo;
    ImageView ivImage;

    String title;
    int index;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gung);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvGungInfo = (TextView)findViewById(R.id.tvGungInfo);
        ivImage = (ImageView)findViewById(R.id.ivImage);
        ivImage.setScaleType(ImageView.ScaleType.FIT_XY);

        Intent intent = getIntent();
        title = intent.getStringExtra("gungName");
        index = intent.getIntExtra("index", -1);
        tvTitle.setText(title);

        switch (title) {
            case "경복궁":
                ivImage.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.img_gbg));
                tvGungInfo.setText(R.string.gbg_introduction);
                break;
            case "창덕궁":
                ivImage.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.img_cdg));
                tvGungInfo.setText(R.string.cdg_introduction);
                break;
            case "창경궁":
                ivImage.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.img_cgg));
                tvGungInfo.setText(R.string.cgg_introduction);
                break;
            case "덕수궁":
                ivImage.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.img_dsg));
                tvGungInfo.setText(R.string.dsg_introduction);
                break;
            case "종묘":
                ivImage.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.img_jm));
                tvGungInfo.setText(R.string.jm_introduction);
                break;
        }
    }

    public void onClick(View v) {
        Intent intent;

        switch(v.getId()) {
            case R.id.btnBuildings:
                intent = new Intent(GungActivity.this, BuildingListActivity.class);
                intent.putExtra("index", index);
                intent.putExtra("gungName", title);
                startActivity(intent);
                break;
            case R.id.btnInfo:
                intent = new Intent(GungActivity.this, InfoActivity.class);
                intent.putExtra("gungName", title);
                startActivity(intent);
                break;
            case R.id.btnSurround:
                intent = new Intent(GungActivity.this, SurroundingsActivity.class);
                intent.putExtra("gungName", title);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.item01:
                intent = new Intent(GungActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.item02:
                intent = new Intent(GungActivity.this, DiaryActivity.class);
                intent.putExtra("gungName", title);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
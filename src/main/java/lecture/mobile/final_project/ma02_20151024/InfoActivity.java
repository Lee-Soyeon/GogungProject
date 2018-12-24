package lecture.mobile.final_project.ma02_20151024;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    TextView tvTime;
    TextView tvFee;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        tvTime = (TextView)findViewById(R.id.tvTime);
        tvFee = (TextView)findViewById(R.id.tvFee);

        Intent intent = getIntent();
        String title = intent.getStringExtra("gungName");

        switch (title) {
            case "경복궁":
                tvTime.setText(R.string.gbg_time);
                tvFee.setText(R.string.fee1);
                break;
            case "창덕궁":
                tvTime.setText(R.string.cdg_time);
                tvFee.setText(R.string.fee1);
                break;
            case "창경궁":
                tvTime.setText(R.string.cgg_time);
                tvFee.setText(R.string.fee2);
                break;
            case "덕수궁":
                tvTime.setText(R.string.dsg_time);
                tvFee.setText(R.string.fee2);
                break;
            case "종묘":
                tvTime.setText(R.string.jm_time);
                tvFee.setText(R.string.fee2);
                break;
        }
    }

}

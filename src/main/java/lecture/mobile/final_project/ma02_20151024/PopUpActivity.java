package lecture.mobile.final_project.ma02_20151024;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class PopUpActivity extends Activity {
    TextView tvIntroduction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        tvIntroduction = (TextView)findViewById(R.id.tvIntroduction);
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        finish();
    }

    // 바깥 레이어 클릭시 안닫히게
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    // 안드로이드 백버튼 막기
    public void onBackPressed() {
        return;
    }
}

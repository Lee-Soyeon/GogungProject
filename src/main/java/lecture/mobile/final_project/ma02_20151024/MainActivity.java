package lecture.mobile.final_project.ma02_20151024;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvGungName;
    ArrayAdapter<String> adapter;
    ArrayList<String> gungName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popUpIntroduction();

        try {
            PackageInfo info = getPackageManager().getPackageInfo("lecture.mobile.final_project.ma02_20151024", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        lvGungName = (ListView) findViewById(R.id.lvGungName);

        gungName = new ArrayList<String>();
        gungName.add("경복궁");
        gungName.add("창덕궁");
        gungName.add("창경궁");
        gungName.add("덕수궁");
        gungName.add("종묘");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gungName);
        lvGungName.setAdapter(adapter);

        lvGungName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) { // 리스트 항목 클릭 시 궁궐 별 화면으로 이동
                Intent intent = new Intent(MainActivity.this, GungActivity.class);
                intent.putExtra("index", pos);
                intent.putExtra("gungName", gungName.get(pos).toString());
                startActivity(intent);
            }
        });
    }

    public void popUpIntroduction() {
        Intent intent = new Intent(this, PopUpActivity.class);
        startActivity(intent);
    }

}

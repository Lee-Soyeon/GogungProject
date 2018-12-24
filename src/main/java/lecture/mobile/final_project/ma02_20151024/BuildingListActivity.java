package lecture.mobile.final_project.ma02_20151024;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BuildingListActivity extends Activity {

    TextView tvTitle;
    ListView lvBuildings;

    BuildingsAdapter adapter;
    ArrayList<Gogung> resultList;

    Intent intent;
    String title;
    int index;

    String address;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        lvBuildings = (ListView)findViewById(R.id.lvBuildings);

        intent = getIntent();
        title = intent.getStringExtra("gungName");
        index = intent.getIntExtra("index", -1);
        tvTitle.setText(title);

        resultList = new ArrayList<Gogung>();
        adapter = new BuildingsAdapter(this, R.layout.activity_buildings_custom, resultList);
        lvBuildings.setAdapter(adapter);

        lvBuildings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) { // 리스트 항목 클릭 시 세부 설명 확인 가능
                Intent intent = new Intent(BuildingListActivity.this, BuildingActivity.class);
                intent.putExtra("gungName", title);
                intent.putExtra("buildingName", resultList.get(pos).getContents());
                intent.putExtra("explanation", resultList.get(pos).getExplanation());
                intent.putExtra("imageUrl", "http://www.heritage.go.kr/" + resultList.get(pos).getImgUrl());
                startActivity(intent);
            }
        });

        address = getResources().getString(R.string.server_url);
        new NetworkAsyncTask().execute(address + (index + 1));   // server_url 에 입력한 정보를 결합한 후 AsyncTask 실행
    }

    class NetworkAsyncTask extends AsyncTask<String, Integer, String> {

        public final static String TAG = "NetworkAsyncTask";
        public final static int TIME_OUT = 10000;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            StringBuilder result = new StringBuilder();
            BufferedReader br = null;
            HttpURLConnection conn = null;

            try {
                URL url = new URL(address);
                conn = (HttpURLConnection) url.openConnection();

                if (conn != null) {
                    conn.setConnectTimeout(TIME_OUT);
                    conn.setUseCaches(false);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                        for (String line = br.readLine(); line != null; line = br.readLine()) {
                            result.append(line + '\n');
                        }
                    }
                }

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                cancel(false);
            } catch (Exception ex) {
                ex.printStackTrace();
                cancel(false);
            } finally {
                try {
                    if (br != null) br.close();
                    if (conn != null) conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
//          parser 생성 및 parsing 수행
            BuildingsXmlParser parser = new BuildingsXmlParser();
            resultList = parser.parse(result);

//            어댑터에 이전에 보여준 데이터가 있을 경우 클리어
            if (!resultList.isEmpty()) adapter.clear();

//            리스트뷰에 연결되어 있는 어댑터에 parsing 결과 ArrayList 를 추가
            adapter.addAll(resultList);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(BuildingListActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}

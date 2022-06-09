package com.example.assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class DiseaseSuduG extends AppCompatActivity {
    public static  String rate;
    public static  String def;
    public static  String uri;
    public static  String region;
    public static  String patients;
    public static  String hospital_name;
    public static  String hospital_address;
    public static  String latitude;
    public static  String longitude;
    public static  String OH;
    public static  String name;
    private TextView GPSlocation,textTitle,text1,textDetail;
    private YouTubePlayerView youTubePlayerView;
private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        button = (Button) findViewById(R.id.btn);
        GPSlocation = (TextView)findViewById(R.id.GPSlocation);
        textTitle = (TextView)findViewById(R.id.textTitle);
        text1 = (TextView)findViewById(R.id.text1);
        textDetail = (TextView)findViewById(R.id.textDetail);

        String json = "";
        try {
            InputStream is = getAssets().open("json/Data.json"); // json파일 이름
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            //json파일명을 가져와서 String 변수에 담음
            json = new String(buffer, "UTF-8");
            JSONObject jsonObjectSS = new JSONObject(json);
            JSONArray Array = jsonObjectSS.getJSONArray("Varicella");//배열의 이름
            JSONObject ObjectAG = Array.getJSONObject(1);
            name = ObjectAG.getString("name");
            rate = ObjectAG.getString("rate");
            def =ObjectAG.getString("def");
            region = ObjectAG.getString("region");
            patients = ObjectAG.getString("patients");
            hospital_name= ObjectAG.getString("hospital_name");
            hospital_address= ObjectAG.getString("hospital_address");
            latitude= ObjectAG.getString("latitude");
            longitude= ObjectAG.getString("longitude");
            OH= ObjectAG.getString("OH");
            uri= ObjectAG.getString("uri");
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append(name).append("[").append(rate).append("]");
            textTitle.setText(stringBuilder1);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("위도 :").append(latitude).append(" / 경도 :").append(longitude).append("\n");
            GPSlocation.setText(stringBuilder2);
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("지역 :").append(region).append("\n")
                    .append("환자수  :").append(patients).append("\n")
                    .append("병원명  :").append(hospital_name).append("\n")
                    .append("병원 주소 :").append(hospital_address).append("\n")
                    .append("병원 영업시간 :").append(OH).append("\n");
            text1.setText(stringBuilder3);
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("[수두정의]").append(def);
            textDetail.setText(stringBuilder4);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(loginChecked()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_detail, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.editmenu:
                Intent intented = new Intent(DiseaseSuduG.this, AddActivity.class);
                startActivity(intented);
                return true;
            case R.id.deletemenu:
                Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                SDList.sdnum--;
                SDList.mydata[1] = null;
                Intent intent0 = new Intent(this,MainActivity.class);
                startActivity(intent0);
                return true;
            case R.id.listcheck:
                Intent intents = new Intent(this,SDList.class);
                startActivity(intents);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean loginChecked(){ //로그인 확인 - 로그인 정보가 프리퍼런스에 저장되어 있을 때
        SharedPreferences prefs = getSharedPreferences("person_info", 0);

        String id = prefs.getString("id", "");
        String password = prefs.getString("password", "");
        boolean logincheck = ((id != "" && password != "") ? true : false);
        return logincheck;
    }
    public void mOnClick(View v){
        switch(v.getId()) {
            case R.id.btn:
                //이 밑에 3줄로 인해 프레그먼트를 붙는걸 바꿀수 있음.동적응용가능.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                break;
        }
        }

}
package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static int ssize;
    ArrayList<String> KeyList = new ArrayList<>(); //질병명 이름이 담긴 배열
    ArrayList<String[]> DiseaseInfo = new ArrayList<>(); //질병 정보가 담긴 배열
    JSONObject jsonObject;
    String info[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (savedInstanceState == null) { //매번 앱을 처음 실행할 때 마다 다시 로그인 하기
            SharedPreferences prefs = getSharedPreferences("person_info", 0);
            SharedPreferences.Editor editor = prefs.edit();

            editor.clear();
            editor.commit();
        }



        File files = new File("/data/data/com.example.assignment/files/test1.txt");
        if(files.exists() == false){
            try{

                //앱을 처음 실행할 때 질병 txt파일이 생성되고, 접근 카운트 0으로 초기화
                SharedPreferences prefs = getSharedPreferences("person_info_access", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("access_count", 0);
                editor.commit();

                FileOutputStream fos = openFileOutput("test1.txt", Context.MODE_PRIVATE);
                String Rtext = getString(R.string.data);
                fos.write(Rtext.getBytes());
                fos.close();
            }catch(Exception e){
                e.getStackTrace();
            }
        }




        String Rtext;
        try {
            SharedPreferences prefs = getSharedPreferences("person_info_access", 0);
            SharedPreferences.Editor editor = prefs.edit();
            int a = prefs.getInt("access_count", 0); //a는 접근 카운트

            FileInputStream fis = openFileInput("test1.txt"); //내부저장소 txt파일을 가져온다.
            int nbytes = fis.available();
            byte[] Rdata = new byte[nbytes];
            fis.read(Rdata);
            Rtext = new String(Rdata);

            if(a == 0){ //a는 최초 접근 플래그, 띄어쓰기 오류 제어 위함,
                Rtext = Rtext.replace("{ ","{ \"").replace(": ","\": \"").replace(", ","\", \"").replace("} ","\"}").replace("]\"","]").replace("}\"","}").replace("\"Varicella\": \"","\"Varicella\": ").replace("\"emergence\": \"","\"emergence\": ").replace("\"Hepatitis_A\": \"","\"Hepatitis_A\": ").replace("\"Mumps_virus\": \"","\"Mumps_virus\": ").replace("\"Hepatitis_C\": \"","\"Hepatitis_C\": ").replace("\"Tsutsugamushi\": \"","\"Tsutsugamushi\": ").replace("\"{","{").replace(" },","\" },").replace("}]\" },","}] },").replace("}] }] \"}", "}] }] }");
                editor.commit();
            }
            else{
                Rtext = Rtext.replace("{","{ \"").replace(":","\": \"").replace(",","\", \"").replace("}","\"}").replace("]\"","]").replace("}\"","}").replace("\"Varicella\":\"","\"Varicella\": ").replace("\"emergence\":\"","\"emergence\": ").replace("\"Hepatitis_A\":\"","\"Hepatitis_A\": ").replace("\"Mumps_virus\":\"","\"Mumps_virus\": ").replace("\"Hepatitis_C\":\"","\"Hepatitis_C\": ").replace("\"Tsutsugamushi\":\"","\"Tsutsugamushi\": ").replace("\"{","{").replace("},","\" },").replace("}]\"},","}] },").replace("2~3주\", \"보통 13~17일", "2~3주,보통 13~17일").replace("주말\", \"공휴일", "주말,공휴일").replace("C virus\", \"HCV","C virus,HCV").replace("발열\", \"두통\", \"피부발진","발열,두통,피부발진").replace("emergence\": \"[{","emergence\": [{").replace("\"\"","\"");
                Rtext = Rtext.replace("\"\\","\"").replace("\\\"","\"").replace("]\"","]").replace("A virus\", \"HAV","A virus,HAV").replace("\": \"[{", "\": [{");
                editor.commit();
            }
            JSONObject jsonObject = new JSONObject(Rtext); //txt파일 json으로 만들기


            fis.close();


            Iterator it = jsonObject.keys(); //질병명이 담긴 배열 추출
            while (it.hasNext()) {
                String b = it.next().toString();
                KeyList.add(b); //KeyList라는 리스트에 질병명을 담음
            }

            for (int i=0; i<KeyList.size();i++){
                JSONArray Array = jsonObject.getJSONArray(KeyList.get(i).toString());
                JSONObject Object = Array.getJSONObject(0);


                String name_D = Object.get("name").toString(); //질병명
                String rate_D = Object.get("rate").toString(); //등급
                String def_d = Object.get("def").toString(); //정의

                int region_cnt = Object.getJSONArray("emergence").length(); //질병 내의 지역별 정보 추출하는 함수
                for(int j=0; j<region_cnt;j++) {
                    JSONObject region_info = (JSONObject) Object.getJSONArray("emergence").get(j); //-> emerge의 지역별 정보

                    String reg_d = region_info.get("region").toString(); // 지역
                    String parent_d = region_info.get("patients").toString(); // 환자수
                    String hosp_d = region_info.get("hospital_name").toString(); // 병원명
                    String add_d = region_info.get("hospital_address").toString(); // 주소
                    String lat_d = region_info.get("latitude").toString(); // 위도
                    String lng_d = region_info.get("longitude").toString(); // 경도
                    String OH_d = region_info.get("OH").toString(); // 운영시간

                    info =new String[] {name_D, rate_D, def_d, reg_d, parent_d, hosp_d, add_d, lat_d, lng_d, OH_d};
                    DiseaseInfo.add(info);

                }
            }
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
            Log.d("오류","오류");
        }


    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng position = new LatLng(37.212061, 126.952696); // 기본값은 우리 학교

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // 변경 예정
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //마커
        for (int i=0;i<DiseaseInfo.size();i++){
            position = new LatLng(Double.parseDouble(DiseaseInfo.get(i)[7]), Double.parseDouble(DiseaseInfo.get(i)[8]));
            mMap.addMarker(new MarkerOptions().position(position).title(DiseaseInfo.get(i)[5])
                    .snippet(DiseaseInfo.get(i)[9]));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 7));

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        switch (loginChecked()) {
            case 0://로그인 안 된 상태
                inflater.inflate(R.menu.menu_main, menu);
                break;
            case 1://로그인 된 상태(사용자)
                inflater.inflate(R.menu.menu_login, menu);
                break;
            case 2://로그인 된 상태(관리자)
                inflater.inflate(R.menu.menu_admin, menu);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.loginMenu:    //로그인 액티비티 이동
//                if(loginChecked()==1||loginChecked()==2){
//                    SharedPreferences prefs = getSharedPreferences("person_info", 0);
//                    SharedPreferences.Editor editor = prefs.edit();
//
//                    editor.clear();
//                    editor.commit();
//
//                    Toast alreadyLogin = Toast.makeText(this.getApplicationContext(), "로그아웃 성공", Toast.LENGTH_SHORT);
//                    alreadyLogin.show();
//                }else{
//                    intent = new Intent(this, LoginActivity.class);
//                    startActivity(intent);
//                }
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.logoutMenu:
                SharedPreferences prefs = getSharedPreferences("person_info", 0);
                SharedPreferences.Editor editor = prefs.edit();

                editor.clear();
                editor.commit();

                Toast alreadyLogin = Toast.makeText(this.getApplicationContext(), "로그아웃 성공", Toast.LENGTH_SHORT);
                alreadyLogin.show();
                return true;
            case R.id.edit: //질병 정보 추가 편집 액티비티 이동
                intent = new Intent(this, AddActivity.class);
                startActivity(intent);

//                if(loginChecked()){ //로그인 정보가 프리퍼런스에 저장되어 있을 때에만 실행
//                    intent = new Intent(this, AddActivity.class);
//                    startActivity(intent);
//                }else{
//                    Toast loginToast = Toast.makeText(this.getApplicationContext(),"로그인을 해주세요.",Toast.LENGTH_SHORT);
//                    loginToast.show();
//                }
                return true;

            case R.id.searchsd: //질병 검색
                if(SDList.sdnum != 1){
                    if(SDList.sdnum ==0){
                        Toast.makeText(this, "값이 없습니다.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    Intent intents = new Intent(this,SDList.class);
                    startActivity(intents);
                    return true;
                }else{
                    if(SDList.mydata[0] != null){
                        Intent intents = new Intent(this,DiseaseSuduS.class);
                        startActivity(intents);
                        return true;
                    }else if(SDList.mydata[1] != null){
                        Intent intents = new Intent(this,DiseaseSuduG.class);
                        startActivity(intents);
                        return true;
                    }else if(SDList.mydata[2] != null){
                        Intent intents = new Intent(this,DiseaseSuduGN.class);
                        startActivity(intents);
                        return true;
                    }else{
                        Toast.makeText(this, "오류입니다.", Toast.LENGTH_SHORT).show();
                    }

                }

            case R.id.searchah: //질병 검색
                if(AHList.ahnum != 1){
                    if(AHList.ahnum ==0){
                        Toast.makeText(this, "값이 없습니다.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    Intent intents = new Intent(this,AHList.class);
                    startActivity(intents);
                    return true;
                }else{
                    if(AHList.mydata[0] != null){
                        Intent intents = new Intent(this,DiseaseAHG.class);
                        startActivity(intents);
                        return true;
                    }else{
                        Toast.makeText(this, "오류입니다.", Toast.LENGTH_SHORT).show();
                    }

                }
            case R.id.searchyh: //질병 검색
                if(YHList.yhnum != 1){
                    if(YHList.yhnum ==0){
                        Toast.makeText(this, "값이 없습니다.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    Intent intents = new Intent(this,YHList.class);
                    startActivity(intents);
                    return true;
                }else{
                    if(YHList.mydata[0] != null){
                        Intent intents = new Intent(this,DiseaseYHG.class);
                        startActivity(intents);
                        return true;
                    }else{
                        Toast.makeText(this, "오류입니다.", Toast.LENGTH_SHORT).show();
                    }

                }
            case R.id.searchch: //질병 검색
                if(CHList.chnum != 1){
                    if(CHList.chnum ==0){
                        Toast.makeText(this, "값이 없습니다.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    Intent intents = new Intent(this,CHList.class);
                    startActivity(intents);
                    return true;
                }else{
                    if(CHList.mydata[0] != null){
                        Intent intents = new Intent(this,DiseaseCHS.class);
                        startActivity(intents);
                        return true;
                    }else if(CHList.mydata[1] != null){
                        Intent intents = new Intent(this,DiseaseCHG.class);
                        startActivity(intents);
                        return true;
                    }else{
                        Toast.makeText(this, "오류입니다.", Toast.LENGTH_SHORT).show();
                    }

                }


        }

        return super.onOptionsItemSelected(item);
    }

    public int loginChecked() { //로그인 확인 - 로그인 정보가 프리퍼런스에 저장되어 있을 때
        SharedPreferences prefs = getSharedPreferences("person_info", 0);

        String id = prefs.getString("id", "");
        String password = prefs.getString("password", "");
        if (id.equals("admin") && password.equals("admin")) { // admin 계정이면
            Toast.makeText(this.getApplicationContext(), "id : " + id + "pw: " + password, Toast.LENGTH_SHORT);
            return 2;
        } else if (id != "" && password != "") { //admin이 아닌 계정으로 로그인이 되면
            return 1;
        }
        return 0; //로그인이 안 되면
    }

}
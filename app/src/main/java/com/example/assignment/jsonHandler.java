package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;


// 테스트용 액티비티
public class jsonHandler extends AppCompatActivity {

    ArrayList<String> KeyList = new ArrayList<>(); //질병명 이름이 담긴 배열
    ArrayList<String[]> DiseaseInfo = new ArrayList<>(); //질병 정보가 담긴 배열
    JSONObject jsonObject;
    String info[];
    Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TextView tv = (TextView) findViewById(R.id.testText);
//        Button mBtn = (Button) findViewById(R.id.mapButton);

        String json = "";

        try {
            InputStream is = getAssets().open("json/data_pre.json"); // json파일 이름
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            //json파일명을 가져와서 String 변수에 담음
            json = new String(buffer, "UTF-8");

            jsonObject = new JSONObject(json);
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

//            ***************
//            TEST
//            ***************
//            tv.setText("HI");
//            tv.setText(DiseaseInfo.toString()); //-> 이상한 형식으로 출력됨
//            tv.setText(DiseaseInfo.get(0).toString()); //->이상한 형식으로 출력됨
//            tv.setText(Arrays.toString(DiseaseInfo.toArray())); //이상한 형식으로 출력됨
//            tv.setText(Arrays.deepToString(DiseaseInfo.toArray()));
//            **************

//            ***************
//            TEST / find함수 Test
//            ***************



//            tv.setText(Arrays.deepToString(DiseaseInfo.toArray()));
//            **************


        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
            Log.d("오류","오류");
        }

    }


//    public void mOnClick(View v){
////        Intent intent = new Intent(this,MapsActivity.class);
//        Intent intent = new Intent(this,SearchActivity.class);
//        intent.putExtra("cnt",this.DiseaseInfo.size()); // 질병별 병원 정보 전달
//        for (int i=0;i<DiseaseInfo.size();i++){
//            intent.putExtra("info"+(i+1),DiseaseInfo.get(i)); //1차원 배열
//        }
//        startActivity(intent);
//    }

    public int getCnt(){
        return KeyList.size();
    }

    //모든 정보 배열로 받아오는 함수
    public ArrayList<String[]> getInfo() {
        return this.DiseaseInfo;
    }

    //Disease_n과 일치하는 배열 리턴
    public ArrayList<String[]> getfindDisease(String Disease_n) throws JSONException {
        int num = KeyList.indexOf(Disease_n);

        //배열로된 자료를 가져올때
        //지정한 인덱스의 맞는 배열을 가져옴
        JSONArray Array = jsonObject.getJSONArray(Disease_n);//배열의 이름
        JSONObject Object = Array.getJSONObject(0);
        return null;
    }
}

/*

<ArrayList 사용 가이드>
ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2,3));

System.out.println(list.get(0));//0번째 index 출력

for(Integer i : list) { //for문을 통한 전체출력
    System.out.println(i);
}

Iterator iter = list.iterator(); //Iterator 선언
while(iter.hasNext()){//다음값이 있는지 체크
    System.out.println(iter.next()); //값 출력
}
 */
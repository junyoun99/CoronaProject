package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class AddActivity extends AppCompatActivity implements AddFragment.DataSend,EditFragment.Edit_DataSend{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


    }

    public void addOnClick(View v){
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new com.example.assignment.AddFragment());
        ft.commit();
    }

    public void editOnClick(View v){
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new com.example.assignment.EditFragment());
        ft.commit();
    }
    //replace는



    //data1은 추가할 데이터, data2는 선택한 데이터
    public void onDataSend(String data1, String data2){
        String Rtext;

        try{
            FileInputStream fis = openFileInput("test1.txt");
            int nbytes = fis.available();
            byte[] Rdata = new byte[nbytes];
            fis.read(Rdata);
            Rtext = new String(Rdata);

            SharedPreferences prefs = getSharedPreferences("person_info_access", 0);
            SharedPreferences.Editor editor = prefs.edit();
            int a = prefs.getInt("access_count", 0);

            if(a == 0){ //a는 최초 접근 플래그, 띄어쓰기 오류 제어 위함,
                Rtext = Rtext.replace("{ ","{ \"").replace(": ","\": \"").replace(", ","\", \"").replace("} ","\"}").replace("]\"","]").replace("}\"","}").replace("\"Varicella\": \"","\"Varicella\": ").replace("\"emergence\": \"","\"emergence\": ").replace("\"Hepatitis_A\": \"","\"Hepatitis_A\": ").replace("\"Mumps_virus\": \"","\"Mumps_virus\": ").replace("\"Hepatitis_C\": \"","\"Hepatitis_C\": ").replace("\"Tsutsugamushi\": \"","\"Tsutsugamushi\": ").replace("\"{","{").replace(" },","\" },").replace("}]\" },","}] },").replace("}] }] \"}", "}] }] }");
                a++;
                editor.putInt("access_count", a);
                editor.commit();
            }
            else{
                Rtext = Rtext.replace("{","{ \"").replace(":","\": \"").replace(",","\", \"").replace("}","\"}").replace("]\"","]").replace("}\"","}").replace("\"Varicella\":\"","\"Varicella\": ").replace("\"emergence\":\"","\"emergence\": ").replace("\"Hepatitis_A\":\"","\"Hepatitis_A\": ").replace("\"Mumps_virus\":\"","\"Mumps_virus\": ").replace("\"Hepatitis_C\":\"","\"Hepatitis_C\": ").replace("\"Tsutsugamushi\":\"","\"Tsutsugamushi\": ").replace("\"{","{").replace("},","\" },").replace("}]\"},","}] },").replace("2~3주\", \"보통 13~17일", "2~3주,보통 13~17일").replace("주말\", \"공휴일", "주말,공휴일").replace("C virus\", \"HCV","C virus,HCV").replace("발열\", \"두통\", \"피부발진","발열,두통,피부발진").replace("emergence\": \"[{","emergence\": [{").replace("\"\"","\"");
                Rtext = Rtext.replace("\"\\","\"").replace("\\\"","\"").replace("]\"","]").replace("A virus\", \"HAV","A virus,HAV").replace("\": \"[{", "\": [{");
                editor.commit();
            }

            //System.out.println(data2); 테스트용
            JSONObject jsonObject = new JSONObject(Rtext); //txt파일 json으로 만들기


            fis.close();

            //질병명, 병원 입력하면 나머지 데이터는 자동으로 삽입
            //data2의 기존 데이터와 수두가 일치하면 해당하는 위치에 입력데이터 삽입
            if(data2.equals("수두")){
                JSONArray Array = jsonObject.getJSONArray("Varicella");//배열의 이름
//            JSONObject Object = Array.getJSONObject(0);
//            textView1.setText(String.format(Locale.KOREAN,"name is: "+ Object.getString(("title"))));

                    //Object는 varicella 내부
                for(int i = 0; i < Array.length(); i++){
                    JSONObject Array2 = Array.getJSONObject(i);
                    JSONArray Array3 = Array2.getJSONArray("emergence");
                    Array3.put(data1);

                }


            }
            if(data2.equals("A형간염")){
                JSONArray Array = jsonObject.getJSONArray("Hepatitis_A");//배열의 이름
//            JSONObject Object = Array.getJSONObject(0);
//            textView1.setText(String.format(Locale.KOREAN,"name is: "+ Object.getString(("title"))));

                //Object는 varicella 내부
                for(int i = 0; i < Array.length(); i++){
                    JSONObject Array2 = Array.getJSONObject(i);
                    JSONArray Array3 = Array2.getJSONArray("emergence");
                    Array3.put(data1);

                }
            }
            if(data2.equals("유행성이하선염")){
                JSONArray Array = jsonObject.getJSONArray("Mumps_virus");//배열의 이름
//            JSONObject Object = Array.getJSONObject(0);
//            textView1.setText(String.format(Locale.KOREAN,"name is: "+ Object.getString(("title"))));

                //Object는 varicella 내부
                for(int i = 0; i < Array.length(); i++){
                    JSONObject Array2 = Array.getJSONObject(i);
                    JSONArray Array3 = Array2.getJSONArray("emergence");
                    Array3.put(data1);

                }
            }

            if(data2.equals("C형간염")){
                JSONArray Array = jsonObject.getJSONArray("Hepatitis_C");//배열의 이름
//            JSONObject Object = Array.getJSONObject(0);
//            textView1.setText(String.format(Locale.KOREAN,"name is: "+ Object.getString(("title"))));

                //Object는 varicella 내부
                for(int i = 0; i < Array.length(); i++){
                    JSONObject Array2 = Array.getJSONObject(i);
                    JSONArray Array3 = Array2.getJSONArray("emergence");
                    Array3.put(data1);

                }
            }

            FileOutputStream fos = openFileOutput("test1.txt", Context.MODE_PRIVATE);
            String jsonString = jsonObject.toString();
            jsonString = jsonString.replace("\"{", "{").replace("}\"", "}");
            jsonString = jsonString.replace("\"", "");
            fos.write(jsonString.getBytes());
            fos.close();



        }
        catch (Exception e){
            e.printStackTrace();
        }



    }
//data1:질병명, data2:병원명, data3:위도, data4:경도, data5:운영시간
    public void onEditDataSend(String data1, String data2, double data3, double data4, String data5) {
        String Rtext;

        try{
            FileInputStream fis = openFileInput("test1.txt");
            int nbytes = fis.available();
            byte[] Rdata = new byte[nbytes];
            fis.read(Rdata);
            Rtext = new String(Rdata);

            SharedPreferences prefs = getSharedPreferences("person_info_access", 0);
            SharedPreferences.Editor editor = prefs.edit();
            int a = prefs.getInt("access_count", 0);

            if(a == 0){ //a는 최초 접근 플래그, 띄어쓰기 오류 제어 위함,
                Rtext = Rtext.replace("{ ","{ \"").replace(": ","\": \"").replace(", ","\", \"").replace("} ","\"}").replace("]\"","]").replace("}\"","}").replace("\"Varicella\": \"","\"Varicella\": ").replace("\"emergence\": \"","\"emergence\": ").replace("\"Hepatitis_A\": \"","\"Hepatitis_A\": ").replace("\"Mumps_virus\": \"","\"Mumps_virus\": ").replace("\"Hepatitis_C\": \"","\"Hepatitis_C\": ").replace("\"Tsutsugamushi\": \"","\"Tsutsugamushi\": ").replace("\"{","{").replace(" },","\" },").replace("}]\" },","}] },").replace("}] }] \"}", "}] }] }");
                a++;
                editor.putInt("access_count", a);
                editor.commit();

            }
            else{
                Rtext = Rtext.replace("{","{ \"").replace(":","\": \"").replace(",","\", \"").replace("}","\"}").replace("]\"","]").replace("}\"","}").replace("\"Varicella\":\"","\"Varicella\": ").replace("\"emergence\":\"","\"emergence\": ").replace("\"Hepatitis_A\":\"","\"Hepatitis_A\": ").replace("\"Mumps_virus\":\"","\"Mumps_virus\": ").replace("\"Hepatitis_C\":\"","\"Hepatitis_C\": ").replace("\"Tsutsugamushi\":\"","\"Tsutsugamushi\": ").replace("\"{","{").replace("},","\" },").replace("}]\"},","}] },").replace("2~3주\", \"보통 13~17일", "2~3주,보통 13~17일").replace("주말\", \"공휴일", "주말,공휴일").replace("C virus\", \"HCV","C virus,HCV").replace("발열\", \"두통\", \"피부발진","발열,두통,피부발진").replace("emergence\": \"[{","emergence\": [{").replace("\"\"","\"");
                Rtext = Rtext.replace("\"\\","\"").replace("\\\"","\"").replace("]\"","]").replace("A virus\", \"HAV","A virus,HAV").replace("\": \"[{", "\": [{");
                editor.commit();
            }

            //System.out.println(data1); 테스트용용

           JSONObject jsonObject = new JSONObject(Rtext); //txt파일 json으로 만들기


            fis.close();

            if(data1.equals("수두")){
                if(data2.equals("서울삼성병원")){
                    JSONArray Array = jsonObject.getJSONArray("Varicella");//배열의 이름

                        JSONObject Array2 = Array.getJSONObject(0);
                        JSONArray Array3 = Array2.getJSONArray("emergence");
                    for(int j=0;j<Array3.length();j++){
                        //Object2는 emergence 내부
                        JSONObject Object2 = Array3.getJSONObject(j);
                        if(Object2.getString("hospital_name").equals("서울삼성병원")){
                            Object2.put("latitude", data3);
                            Object2.put("longitude", data4);
                            Object2.put("OH", data5);
                            System.out.println(Object2.getString("latitude"));
                        }
                    }




                }
                if(data2.equals("경기도의료원수원병원")){
                    JSONObject Array = jsonObject.getJSONObject("Varicella");//배열의 이름
                    JSONArray Array2 = Array.getJSONArray("emergence");
                    for(int j=0;j<Array2.length();j++){
                        //Object2는 emergence 내부
                        JSONObject Object2 = Array2.getJSONObject(j);
                        if(Object2.getString("hospital_name").equals("경기도의료원수원병원")){
                            Object2.put("latitude", data3);
                            Object2.put("longitude", data4);
                            Object2.put("OH", data5);
                        }
                    }
                }
                if(data2.equals("창원제일종합병원")){
                    JSONObject Array = jsonObject.getJSONObject("Varicella");//배열의 이름
                    JSONArray Array2 = Array.getJSONArray("emergence");
                    for(int j=0;j<Array2.length();j++){
                        //Object2는 emergence 내부
                        JSONObject Object2 = Array2.getJSONObject(j);
                        if(Object2.getString("hospital_name").equals("창원제일종합병원")){
                            Object2.put("latitude", data3);
                            Object2.put("longitude", data4);
                            Object2.put("OH", data5);
                        }
                    }
                }




            }
            if(data1.equals("A형간염")){
                if(data2.equals("고려대학교 안산병원")){
                    JSONObject Array = jsonObject.getJSONObject("Varicella");//배열의 이름
                    JSONArray Array2 = Array.getJSONArray("emergence");
                    for(int j=0;j<Array2.length();j++){
                        //Object2는 emergence 내부
                        JSONObject Object2 = Array2.getJSONObject(j);
                        if(Object2.getString("hospital_name").equals("고려대학교 안산병원")){
                            Object2.put("latitude", data3);
                            Object2.put("longitude", data4);
                            Object2.put("OH", data5);
                        }
                    }
                }
            }
            if(data1.equals("유행성이하선염")){
                if(data2.equals("시흥 센트럴병원")){
                    JSONObject Array = jsonObject.getJSONObject("Varicella");//배열의 이름
                    JSONArray Array2 = Array.getJSONArray("emergence");
                    for(int j=0;j<Array2.length();j++){
                        //Object2는 emergence 내부
                        JSONObject Object2 = Array2.getJSONObject(j);
                        if(Object2.getString("hospital_name").equals("시흥 센트럴병원")){
                            Object2.put("latitude", data3);
                            Object2.put("longitude", data4);
                            Object2.put("OH", data5);
                        }
                    }
                }
            }

            if(data1.equals("C형간염")){
                if(data2.equals("서울대학교병원")){
                    JSONObject Array = jsonObject.getJSONObject("Varicella");//배열의 이름
                    JSONArray Array2 = Array.getJSONArray("emergence");
                    for(int j=0;j<Array2.length();j++){
                        //Object2는 emergence 내부
                        JSONObject Object2 = Array2.getJSONObject(j);
                        if(Object2.getString("hospital_name").equals("서울대학교병원")){
                            Object2.put("latitude", data3);
                            Object2.put("longitude", data4);
                            Object2.put("OH", data5);
                        }
                    }
                }
            }

            FileOutputStream fos = openFileOutput("test1.txt", Context.MODE_PRIVATE);
            String jsonString = jsonObject.toString();
            jsonString = jsonString.replace("\"{", "{").replace("}\"", "}");
            jsonString = jsonString.replace("\"", "");
            fos.write(jsonString.getBytes());
            fos.close();



        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
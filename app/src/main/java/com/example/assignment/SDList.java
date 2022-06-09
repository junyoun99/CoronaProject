package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class SDList extends AppCompatActivity {
    private Button button;
    private ListView mList;
    public static DiseaseAdapter mAdapter;
    public static int sdnum = 3;
    public static DiseaseMyData[] mydata ={
            new DiseaseMyData(R.raw.sd1,"수두","2급", "서울","서울삼성병원"),
            new DiseaseMyData(R.raw.sd2,"수두","2급", "경기","경기도의료원수원병원"),
            new DiseaseMyData(R.raw.sd3,"수두","2급", "경남","창원제일종합병원"),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sdlist);
        mList = (ListView) findViewById(R.id.list);
        mAdapter = new DiseaseAdapter(this,mydata);
        mList.setAdapter(mAdapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (mydata[position].title) {
                    case "수두":
                        if (mydata[position].region.equals("서울")) {
                            Intent intent = new Intent(SDList.this, DiseaseSuduS.class);
                            startActivity(intent);
                        }else if (mydata[position].region.equals("경기")) {
                            Intent intent = new Intent(SDList.this, DiseaseSuduG.class);
                            startActivity(intent);
                        }else if (mydata[position].region.equals("경남")) {
                            Intent intent = new Intent(SDList.this, DiseaseSuduGN.class);
                            startActivity(intent);
                        }
//                        else if(data[position].source.equals("과천시 보건소 관리자")){
//                            Intent intent = new Intent(getApplicationContext(), DiseaseSuduG.class);
//                            startActivity(intent);
//                        }else if(data[position].source.equals("김해시 보건소 관리자")){
//                            Intent intent = new Intent(getApplicationContext(), DiseaseSuduGN.class);
//                            startActivity(intent);
                        }
                }


                });


            }
    }
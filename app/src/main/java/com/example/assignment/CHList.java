package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


public class CHList extends AppCompatActivity {
    private Button button;
    private ListView mList;
    private DiseaseAdapter mAdapter;
    public static int chnum = 2;
    public static DiseaseMyData[] mydata ={
            new DiseaseMyData(R.raw.sd6,"C형간염","2급", "서울","서울대학교병원"),
            new DiseaseMyData(R.raw.sd7,"C형간염","2급", "경기","순천향대학교 부천병원")
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
                    case "C형간염":
                        if (mydata[position].region.equals("서울")) {
                            Intent intent = new Intent(CHList.this, DiseaseCHS.class);
                            startActivity(intent);
                        }else if (mydata[position].region.equals("경기")) {
                            Intent intent = new Intent(CHList.this, DiseaseCHG.class);
                            startActivity(intent);
                        }
                        }
                }


                });


            }
    }
package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


public class AHList extends AppCompatActivity {
    private Button button;
    private ListView mList;
    private DiseaseAdapter mAdapter;
    public static int ahnum = 1;
    public static DiseaseMyData[] mydata ={
            new DiseaseMyData(R.raw.sd4,"A형간염","2급", "경기","고려대학교 안산병원")
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
                    case "A형간염":
                        if (mydata[position].region.equals("경기")) {
                            Intent intent = new Intent(AHList.this, DiseaseAHG.class);
                            startActivity(intent);
                        }
                        }
                }


                });


            }
    }
package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


public class YHList extends AppCompatActivity {
    private Button button;
    private ListView mList;
    private DiseaseAdapter mAdapter;
    public static int yhnum = 1;
    public static DiseaseMyData[] mydata ={
            new DiseaseMyData(R.raw.sd5,"유행성이하선염","2급", "경기","시흥 센트럴병원")
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
                    case "유행성이하선염":
                        if (mydata[position].region.equals("경기")) {
                            Intent intent = new Intent(YHList.this, DiseaseYHG.class);
                            startActivity(intent);
                        }
                        }
                }


                });


            }
    }
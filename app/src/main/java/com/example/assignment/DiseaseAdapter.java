package com.example.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DiseaseAdapter extends BaseAdapter {
    private Context ctx;
    private DiseaseMyData[] mydata;



    public DiseaseAdapter(Context ctx, DiseaseMyData[] mydata){
        this.ctx = ctx;
        this.mydata = mydata;
    }

    //총 몇개인가 세는거
    @Override
    public int getCount() {
        return mydata.length;
    }
    // 데이터 보내는거
    @Override
    public Object getItem(int i) {
        return i;
    }
    // 어디에 있는지
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view ==null){
            LayoutInflater inflater = LayoutInflater.from(ctx);
            view = inflater.inflate(R.layout.item_list,viewGroup, false);
        }
        ImageView image = (ImageView) view.findViewById(R.id.Iv);
        image.setImageResource(mydata[i].icon);
        TextView title = (TextView) view.findViewById(R.id.item_title);
        title.setText(mydata[i].title);
        TextView rate = (TextView) view.findViewById(R.id.item_rate);
        rate.setText(mydata[i].rate);
        TextView region = (TextView) view.findViewById(R.id.item_region);
        region.setText(mydata[i].region);
        TextView hospitalname = (TextView) view.findViewById(R.id.item_hospitalname);
        hospitalname.setText(mydata[i].hospitalname);

        return view;
    }
}

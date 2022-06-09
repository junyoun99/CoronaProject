package com.example.assignment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.assignment.AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.assignment.AddFragment newInstance(String param1, String param2) {
        com.example.assignment.AddFragment fragment = new com.example.assignment.AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    //spinner사용
    private Spinner spinner;
    private String[] items = {"수두", "A형간염", "유행성이하선염", "C형간염", "쯔쯔가무시병"};

    private String spinnerSelected = ""; //스피너에서 선택된 질병 종류

    private String region = ""; //지역
    private int patients = 0; //발생인원
    private String hospital_name = ""; //병원명
    private String hospital_address = ""; //병원위치
    private double latitude = 0;//위도
    private double longitude = 0;//경도
    private String oH = "";//영업시간



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add, container, false);



        EditText editArea = (EditText) v.findViewById(R.id.area);

        EditText editPersonnel = (EditText) v.findViewById(R.id.personnel);

        EditText editHospitalname = (EditText) v.findViewById(R.id.hospitalname);

        EditText editHospitallocation = (EditText) v.findViewById(R.id.hospitallocation);

        EditText editlatitude = (EditText) v.findViewById(R.id.latitude);

        EditText editlongitude = (EditText) v.findViewById(R.id.longitude);

        EditText editopentime = (EditText) v.findViewById(R.id.opentime);




        //스피너 초기화
        spinner = v.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        Button mybutton = v.findViewById(R.id.Fragment_add_Button); //프레그먼트 내에서 버튼을 사용할 땐 onClick()메서드를 오버라이드 해줘야 함.

        mybutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //선택된 항목 인덱스 얻기
                int pos1 = spinner.getSelectedItemPosition();
                //선택된 항목 데이터 얻기
                spinnerSelected = items[pos1];



                region = editArea.getText().toString();
                patients = Integer.parseInt(editPersonnel.getText().toString());
                hospital_name = editHospitalname.getText().toString();
                hospital_address = editHospitallocation.getText().toString();
                latitude = Double.parseDouble(editlatitude.getText().toString());
                longitude = Double.parseDouble(editlongitude.getText().toString());
                oH = editopentime.getText().toString();

                String rs = String.format("{\"region\":%s,\"patients\":%d,\"hospital_name\":%s,\"hospital_address\":%s,\"latitude\":%f,\"longitude\":%f,\"OH\":%s}", region, patients, hospital_name, hospital_address, latitude, longitude, oH);

                Activity activity = getActivity();
                ((DataSend)activity).onDataSend(rs, spinnerSelected);    //인터페이스로 값을 액티비티로 넘김.






                //getActivity()는 액티비티에서 보여준다는 뜻.
                Toast.makeText(getActivity(), "질병이 추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        return v;

    }

    public interface DataSend{
        void onDataSend(String data1, String data2);
    }

    private DataSend mDataSend;



    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof DataSend){
            mDataSend = (DataSend) getActivity();
        }
    }



}
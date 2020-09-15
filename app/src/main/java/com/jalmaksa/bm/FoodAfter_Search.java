package com.jalmaksa.bm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;

public class FoodAfter_Search extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    Button googlebtn;
    SearchView search;
    ImageView home;
    ImageButton myp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_after__search);

        //액션바사라지기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        search = (SearchView) findViewById(R.id.search);
        Intent intent = getIntent();
        String str = intent.getStringExtra("str");
        search.setQuery(str, true);

        home=(ImageView)findViewById(R.id.home);
        myp=(ImageButton)findViewById(R.id.myp);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        myp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MyPage.class);
                startActivity(intent);
            }
        });

        //검색 가능하게 또 한번
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String str = search.getQuery().toString();
                Intent intent = new Intent(getApplicationContext(), FoodAfter_Search.class);
                intent.putExtra("str",str);
                startActivity(intent);

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //구글맵가기
        googlebtn = (Button)findViewById(R.id.googlebtn);
        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("click","m");
                 Intent intent1 = new Intent(getApplicationContext(), MapsActivity.class);
                 startActivity(intent1);
            }
        });

        //스피너
        Spinner gu_spin=(Spinner) findViewById(R.id.spin_gu);
        String[] gu={"강남구", "강동구", "강북구", "강서구",
                "구로구", "금천구", "관악구", "광진구", "노원구", "도봉구", "동작구",
                "동대문구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구",
                "양천구", "영등포구", "용산구", "은평구", "중랑구", "중구", "종로구"};


        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gu);
        gu_spin.setAdapter(adapter);

//        gu_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),arrayList.get(i)+"가 선택되었습니다.",
//                        Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });


    }
}
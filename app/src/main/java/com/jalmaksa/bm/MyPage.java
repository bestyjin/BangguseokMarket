package com.jalmaksa.bm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyPage extends AppCompatActivity{
    DBHelper mydb;
    Button shoppingBtn, noticeBtn, questionBtn, labBtn;

    ImageView home;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        getSupportActionBar().hide();
            mydb = new DBHelper(this);
        shoppingBtn = (Button)findViewById(R.id.shoppingBtn);
                noticeBtn = (Button)findViewById(R.id.noticeBtn);
                questionBtn = (Button)findViewById(R.id.questionBtn);
                labBtn = (Button)findViewById(R.id.labBtn);
                textView = (TextView)findViewById(R.id.textView6);

        String nickname;
        try {
            nickname = mydb.getNickname();

            Log.d("myLog", "result" + nickname);
            textView.setText(nickname);
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {          Toast.makeText(getApplicationContext(),"마이페이지",Toast.LENGTH_SHORT).show();
        }
                home = (ImageView)findViewById(R.id.home);

                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

        noticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Notice.class);
                startActivity(intent);
            }
        });

        questionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Question.class);
                startActivity(intent);
            }
        });

        labBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Version.class);
                startActivity(intent);
            }
        });

        shoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShoppingList.class);
                startActivity(intent);
            }
        });
    }
}
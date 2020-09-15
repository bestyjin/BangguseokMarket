package com.jalmaksa.bm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class FoodRecomend extends AppCompatActivity {
    int[] img = {R.drawable.doenjangjjigae, R.drawable.mandu, R.drawable.oligogi,R.drawable.sabgyeobsal, R.drawable.suyug,
            R.drawable.noodle, R.drawable.salmon, R.drawable.spaghetti, R.drawable.udon, R.drawable.chickensoup, R.drawable.curry,
            R.drawable.makchang, R.drawable.meat_stew, R.drawable.salad, R.drawable.yupdduk};

    String[] str = {"흰쌀밥과 된장찌개는 훌륭한 조합이죠!",
            "뜨뜻한 만두전골 어떠신가요?", "매콤한 양념에 깨를 솔솔~","맛있게 먹으면 0칼로리!", "김치와 돼지고기의 만남!",
            "시원 칼칼한 국물과 호로록 면이 생각나는 날 ", "비타민이 풍부한 연어를 든든한 덮밥으로 즐겨보아요", "누구든 세상 쉽게 만들수 있는 토마토 스파게티",
            "비오는 쌀쌀한 날, 뜨끈한 어묵 우동 한그릇", "여름철 보양의 최고봉! 삼계탕 어떠신가요? ", "맛있는 영양듬뿍 카레라이스 한끼",
            "특히 비오는 날이면 더 맛있는 막창 ", "추울 때 칼칼하게 한 그릇 뚝딱", "간단하고 light한 음식~", "우울 할 때 매운거 어때요?"};

    Button btn1;
    ImageView best1,best2,best3,best4,best5;
    TextView text1,text2,text3,text4, text5;
    ImageView home;
    ImageButton myp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recomend);
        getSupportActionBar().hide();

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

        int num1 = 0, num2 = 0, num3=0, num4=0, num5=0;  //랜덤숫자
        int numlist[] = new int[15]; //랜덤배열
        int numstr[] = new int[15];
        for(int i = 0 ; i<numlist.length; i++) {
            numlist[i]=9999; //초기화
            numstr[i] = 9999; //초기화
        }

        btn1 = (Button) findViewById(R.id.btn1);
        best1 = (ImageView)findViewById(R.id.img1);
        best2 = (ImageView)findViewById(R.id.img2);
        best3 = (ImageView)findViewById(R.id.img3);
        best4 = (ImageView)findViewById(R.id.img4);
        best5 = (ImageView)findViewById(R.id.img5);
        text1 = (TextView)findViewById(R.id.text1);
        text2 = (TextView)findViewById(R.id.text2);
        text3 = (TextView)findViewById(R.id.text3);
        text4 = (TextView)findViewById(R.id.text4);
        text5 = (TextView)findViewById(R.id.text5);

        String five = "    Best5";

        btn1.setText(btn1.getText() + five);
        Random ram = new Random(); //랜덤 발생
        int num, count;
        int i=0;
        while(i!=5){
            if(i==5)
                break;
            num = ram.nextInt(img.length);  //랜덤 숫자 발생 0~10 5번
            count = 0;
            for(int j=0; j<=i; j++){
                if(num!= numlist[j]){
                    count=count+1;
                }
                else
                    count = 0;
            }
            if(count-1 == i){
                numlist[i] = num; //이미지넘버
                numstr[i] = num; //멘트배열
                i++;
            }
        }

        best1.setImageResource(img[numlist[0]]);
        text1.setText(str[numstr[0]]);
        best2.setImageResource(img[numlist[1]]);
        text2.setText(str[numstr[1]]);
        best3.setImageResource(img[numlist[2]]);
        text3.setText(str[numstr[2]]);
        best4.setImageResource(img[numlist[3]]);
        text4.setText(str[numstr[3]]);
        best5.setImageResource(img[numlist[4]]);
        text5.setText(str[numstr[4]]);


    }
}

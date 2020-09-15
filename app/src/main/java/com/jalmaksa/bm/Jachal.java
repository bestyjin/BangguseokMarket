package com.jalmaksa.bm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Jachal extends AppCompatActivity {
    ImageView home;
    ImageButton myp;
    TextView fruit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jachal);
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

        Gallery gallery = (Gallery) findViewById(R.id.gallery1);

        MyGalleryAdapter galAdapter = new MyGalleryAdapter(this);
        gallery.setAdapter(galAdapter);

        fruit=(TextView)findViewById(R.id.fruit);
        fruit.setText("\n                                               ▼딸기▼\n비타민C가 다량 함유되어 있어 향산화 작용이 뛰어나 노화 예방과 항암 효과가 있습니다." +
                "\n                                              ▼한라봉▼\n한라봉에 들어있는 황산화 물질인 카르티노이드 성분은 노화를 방지하는 데에 도움이 됩니다. 한라봉 겉껍질에는 항암작용에 도움을 주는 리모넨성분이, 속껍질에는 뇌졸중과 천식을 예방할 수 있는 헤스페리딘 성분이 포함되어 껍질까지 챙겨 먹는 게 좋습니다." +
                "\n                                                ▼귤▼\n항암효과 귤의 노란색에는 강한 항암 효능을 가진 크립토잔틴 같은 항산화 성분이 함유되어 있습니다. 속껍질에는 지방을 분해해주는 헤스페리딘 성분이 함유되어 있어 다이어트에도 도움을 줍니다." +
                "\n                                               ▼석류▼\n높은 구연산, 칼륨, 수분 함량이 몸속의 잉여 수분을 배출하고 신장의 기능을 개선해주기 때문에 고혈압, 고요산혈증, 신장 결석이 있는 사람에게 특히 더 좋습니다." +
                "\n                                               ▼레몬▼\n산화방지제, 비타민, 미네랄이 풍부한 레몬은 노폐물을 배출시키는 이뇨 효과가 있어. 해독 효과가 탁월하고 여러 가지 질병 예방에도 좋습니다." +
                "\n                                               ▼매실▼\n매실의 구연산 성분은 당질 대사를 촉진해 피로회복을 돕고, 피크리산 성분은 간 기능을 활발하게 하고, 담즙 분비를 도와 숙취해소에 좋습니다." +
                "\n                                              ▼토마토▼\n유기산이 신진대사를 촉진 시켜 피로 회복에도 효과적이며 몸에 좋은 성분들을 가지고 있음과 함께 변비 개선에도 큰 도움이 됩니다" +
                "\n                                               ▼자몽▼\n비타민 C가 풍부하며 감염균과 싸우고 면역계를 강화하고 염증을 줄여, 천식 그리고 골관절염과 류마티스 관절염을 예방하는 능력이 있습니다." +
                "\n                                               ▼망고▼\n 바나나의 10배 이상에 달하는 베타카로틴이 풍부하게 함유돼 있어 강력한 항산화 작용을 하기 때문에 노화방지에 좋습니다." +
                "\n                                               ▼포도▼\n함유된 레스베라트롤과 안토시아닌은 강력한 항암·항염 물질로 항암 효과가 있습니다." +
                "\n                                            ▼블루베리▼\n식이섬유가 풍부하여 장을 촉촉하게해 장 기능을 동적이게 해줍니다. 이로인해 배변을 부드럽게 만들어 변비 개선에 도움이 됩니다." +
                "\n                                               ▼수박▼\n수박에 함유된 성분 B1과 B6은 근육을 키우고 더 많은 에너지를 가지는데 도움을 줍니다." +
                "\n                                              ▼복숭아▼\n복숭아에 함유된 비타민C와 카테닌 그리고 베타카로틴은 멜라인 색소 형성을 억제하고 콜라겐 합성을 도와 보다 환하고 건강한 피부를 만들어줍니다." +
                "\n                                              ▼복분자▼\n남성호르몬인 테스토스테론의 분비를 촉진시키는 물질을 함유하고 잇어 정력을 향상시키고, 전립선 건강에도 도움을 줍니다." +
                "\n                                                ▼배▼\n칼륨 함량이 높아 고혈압을 유발하는 체내의 잔류 나트륨을 배출시켜 혈압을 조절해 주는데 효과적입니다." +
                "\n                                               ▼사과▼\n펙틴이 혈관에 샇인 나쁜 콜레스테롤들을 몸밖으로 배출시켜주고 좋은 콜레스테롤을 증가시켜 주기 때문에 고혈압이나 혈관질환에 좋습니다." +
                "\n                                               ▼유자▼\n펙틴과 리모넨 성분이 모세혈관을 튼튼하게 하고, 혈액 순환을 촉진시켜 고혈압 예방에 좋습니다.");
    }
    public class MyGalleryAdapter extends BaseAdapter {

        Context context;
        Integer[] posterID={R.drawable.janu, R.drawable.febu, R.drawable.march, R.drawable.april, R.drawable.mayy, R.drawable.june, R.drawable.july, R.drawable.august, R.drawable.eqptember, R.drawable.october, R.drawable.november, R.drawable.december};
        Integer[] posterI = { R.drawable.jan, R.drawable.feb,
                R.drawable.mar, R.drawable.apr, R.drawable.may, R.drawable.jun, R.drawable.jul, R.drawable.aug, R.drawable.sep, R.drawable.oct, R.drawable.nov, R.drawable.dec};


        public MyGalleryAdapter(Context c) {
            context = c;
        }

        public int getCount() {
            return posterID.length;
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageview = new ImageView(context);
            imageview.setLayoutParams(new Gallery.LayoutParams(200, 300));
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview.setPadding(5, 5, 5, 5);
            imageview.setImageResource(posterID[position]);

            final int pos = position;
            imageview.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    ImageView ivPoster = (ImageView) findViewById(R.id.ivPoster);
                    ivPoster.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ivPoster.setImageResource(posterI[pos]);
                    return false;
                }
            });

            return imageview;
        }

    }
}
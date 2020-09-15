package com.jalmaksa.bm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserChoiceSearch extends AppCompatActivity {
    //금천구는 없음
    // 도봉구
    String[] DB_T = {"방학동도깨비시장", "신창시장"};
    String[] DB_M = {"이마트", "홈플러스"};
    //강서구
    String[] GS_T = {"화곡본동시장", "송화시장"};
    String[] GS_M = {"이마트", "홈플러스"};
    //노원구
    String[] NW_T = {"상계중앙시장", "공릉동"};
    String[] NW_M = {"롯데백화점", "홈플러스"};
    //강남구
    String[] GN_T = {"도곡시장", "청담삼익시장"};
    String[] GN_M = {"롯데백화점", "이마트"};
    //광진구
    String[] GJ_T = {"자양골목시장", "노룬산골목시장"};
    String[] GJ_M = {"이마트", "롯데마트"};
    //영등포구
    String[] YP_T = {"대림중앙시장", "영등포전통시장"};
    String[] YP_M = {"이마트", "롯데백화점", "홈플러스"};
    //서대문구
    String[] SM_T = {"인왕시장", "영천시장"};
    String[] SM_M = {"현대백화점", "롯데슈퍼"};
    //관악구
    String[] GY_T = {"원당종합시장", "신원시장(신림1동)", "관악신사시장(신림4동)"};
    String[] GY_M = {"롯데백화점", "세이브"};
    //강북구
    String[] GB_T = {"수유재래시장", "숭인시장"};
    String[] GB_M = {"롯데백화점", "하나로클럽"};
    //마포구
    String[] MP_T = {"마포농수산물시장", "망원시장"};
    String[] MP_M = {"홈플러스", "농협하나로마트"};
    //중구
    String[] J_T = {"남대문시장", "서울중앙시장"};
    String[] J_M = {"롯데백화점", "신세계백화점", "이마트", "롯데마트"};
    //종로구
    String[] JRO_T = {"통인시장", "광장시장"};
    //용산구
    String[] YS_T = {"후암시장", "용문시장"};
    String[] YS_M = {"이마트", "농협"};
    //성동구
    String[] SD_T = {"뚝도시장", "금남시장"};
    String[] SD_M = {"이마트"};
    //동대문구
    String[] DDM_T = {"청량리종합시장", "경동시장"};
    String[] DDM_M = {"롯데백화점", "홈플러스"};
    //중랑구
    String[] JR_T = {"우림시장", "동원시장"};
    String[] JR_M = {"이마트", "홈플러스"};
    //성북구
    String[] SB_T = {"장위골목시장", "돈암제일시장"};
    String[] SB_M = {"이마트", "현대백화점"};
    //은평구
    String[] EP_T = {"대조시장", "대림시장"};
    String[] EP_M = {"2001아울렛", "이마트"};
    //양천구
    String[] YC_T = {"목3동시장", "신영시장"};
    String[] YC_M = {"이마트", "홈플러스"};
    //구로구
    String[] GR_T = {"남구로시장", "고척근린시장"};
    String[] GR_M = {"이마트", "롯데마트"};
    //동작구
    String[] DJ_T = {"남성시장"};
    String[] DJ_M = {"태평백화점"};
    //서초구
    String[] SC_T = {"방림시장"};
    String[] SC_M = {"신세계백화점", "하나로클럽", "뉴코아아울렛"};
    //송파구
    String[] SP_T = {"청담삼익시장", "마천중앙시장", "방이시장"};
    String[] SP_M = {"롯데백화점", "홈플러스"};
    //강동구
    String[] GD_T = {"암사종합시장"};
    String[] GD_M = {"이마트", "홈플러스"};

    Button bigmart, smallmart; //대형마트 버튼, 전통시장 버튼
    Button search;
    ImageButton  myp;
    ImageButton zimclickbtn;
    Button pagebtn;

    TextView choosemart, visitmart;
    Button[] buttongu = new Button[24];
    Button[] buttonmart = new Button[4];
    ImageButton[] imgbtn = new ImageButton[16];

    //db팀 위한 변수
    TextView DB_martname, DB_item, DB_price;
    private DBHelper mydb;
    int id = 0;

    String[] gu = {"강남구", "강동구", "강북구", "강서구",
            "구로구", "관악구", "광진구", "노원구", "도봉구", "동작구",
            "동대문구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구",
            "양천구", "영등포구", "용산구", "은평구", "중랑구", "중구", "종로구"};

    String[] imgstr = {"사과", "배추", "쇠고기", "닭고기", "오이", "달걀", "조기", "상추", "고등어", "무", "명태", "양파", "배", "돼지고기", "애호박", "오징어"};

    String imgApi = null;  //api 품목
    String guApi = null;  //api 자치구
    String martTypeApi = null; //api 마트종류
    String martNameApi = null; //api 마트이름

    //api에 필요한 변수들
    XmlPullParser xpp;
    String data;
    int j;
    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());

    String year = yearFormat.format(currentTime);
    String month = monthFormat.format(currentTime);

    LinearLayout searafterlatyout;
    ImageView home;
    TextView oktext; //검색 후 멘트
    private Bundle savedInstanceState;

    void reset() {
        smallmart.setVisibility(smallmart.INVISIBLE);
        bigmart.setVisibility(bigmart.INVISIBLE);
        buttonmart[0].setVisibility(buttonmart[0].INVISIBLE);
        buttonmart[1].setVisibility(buttonmart[1].INVISIBLE);
        buttonmart[2].setVisibility(buttonmart[2].INVISIBLE);
        buttonmart[3].setVisibility(buttonmart[3].INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersearchchoice);

        home=(ImageView)findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        myp = (ImageButton)findViewById(R.id.myp);
        myp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MyPage.class);
                startActivity(intent);
            }
        });

        //리니어
        searafterlatyout = (LinearLayout)findViewById(R.id.searafterlatyout);
        //검색버튼
        search = (Button) findViewById(R.id.search);

        //검색 후
        oktext = (TextView) findViewById(R.id.oktext);

        //db findviexbyld
        DB_martname = (TextView) findViewById(R.id.DB_martname);
        DB_item = (TextView) findViewById(R.id.DB_item);
        DB_price = (TextView) findViewById(R.id.DB_price);

        //찜페이지 토스트
        zimclickbtn = (ImageButton) findViewById(R.id.zimclickbtn);
        zimclickbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();

                if (mydb.insertZimlist(DB_martname.getText().toString(), DB_item.getText().toString(), DB_price.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "찜에 담겼습니다", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "추가되지 않았음", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //찜페이지로 가기
        pagebtn = (Button) findViewById(R.id.pagebtn);
        pagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShoppingList.class);
                startActivity(intent);
            }
        });
        //액션바사라지기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //차이
        //db findviexbyld
        DB_martname = (TextView) findViewById(R.id.DB_martname);
        DB_item = (TextView) findViewById(R.id.DB_item);
        DB_price = (TextView) findViewById(R.id.DB_price);

        //자치구 버튼 한번에 findView
        for (int i = 0; i < buttongu.length; i++) {
            String buttonId = "gu" + (i + 1);
            buttongu[i] = findViewById(getResources().getIdentifier(buttonId, "id", getPackageName()));
        }

        //마트이름 버튼 한번에 findView
        for (int i = 0; i < buttonmart.length; i++) {
            String buttonmartId = "namemart" + (i + 1);
            buttonmart[i] = findViewById(getResources().getIdentifier(buttonmartId, "id", getPackageName()));
        }

        //이미지버튼
        for (int i = 0; i < imgbtn.length; i++) {
            String buttonimg = "var" + (i + 1);
            imgbtn[i] = findViewById(getResources().getIdentifier(buttonimg, "id", getPackageName()));
        }

        imgbtn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), imgstr[0], Toast.LENGTH_SHORT).show();
                imgApi = imgstr[0];
            }
        });
        imgbtn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), imgstr[1], Toast.LENGTH_SHORT).show();
                imgApi = imgstr[1];
            }
        });
        imgbtn[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), imgstr[2], Toast.LENGTH_SHORT).show();
                imgApi = imgstr[2];
            }
        });
        imgbtn[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgApi = imgstr[3];
                Toast.makeText(getApplicationContext(), imgstr[3], Toast.LENGTH_SHORT).show();
            }
        });
        imgbtn[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgApi = imgstr[4];
                Toast.makeText(getApplicationContext(), imgstr[4], Toast.LENGTH_SHORT).show();
            }
        });
        imgbtn[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgApi = imgstr[5];
                Toast.makeText(getApplicationContext(), imgstr[5], Toast.LENGTH_SHORT).show();
            }
        });
        imgbtn[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgApi = imgstr[6];
                Toast.makeText(getApplicationContext(), imgstr[6], Toast.LENGTH_SHORT).show();
            }
        });
        imgbtn[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgApi = imgstr[7];
                Toast.makeText(getApplicationContext(), imgstr[7], Toast.LENGTH_SHORT).show();
            }
        });
        imgbtn[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgApi = imgstr[8];
                Toast.makeText(getApplicationContext(), imgstr[8], Toast.LENGTH_SHORT).show();
            }
        });
        imgbtn[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgApi = imgstr[9];
                Toast.makeText(getApplicationContext(), imgstr[9], Toast.LENGTH_SHORT).show();
            }
        });
        imgbtn[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgApi = imgstr[10];
                Toast.makeText(getApplicationContext(), imgstr[10], Toast.LENGTH_SHORT).show();
            }
        });
        imgbtn[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgApi = imgstr[11];
                Toast.makeText(getApplicationContext(), imgstr[11], Toast.LENGTH_SHORT).show();
            }
        });
        imgbtn[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgApi = imgstr[12];
                Toast.makeText(getApplicationContext(), imgstr[12], Toast.LENGTH_SHORT).show();
            }
        });
        imgbtn[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgApi = imgstr[13];
                Toast.makeText(getApplicationContext(), imgstr[13], Toast.LENGTH_SHORT).show();
            }
        });
        imgbtn[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgApi = imgstr[14];
                Toast.makeText(getApplicationContext(), imgstr[14], Toast.LENGTH_SHORT).show();
            }
        });

        imgbtn[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgApi = imgstr[15];
                Toast.makeText(getApplicationContext(), imgstr[15], Toast.LENGTH_SHORT).show();
            }
        });


        /*
        imgbtn[16].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgApi = imgstr[16];
                Toast.makeText(getApplicationContext(),imgstr[16],Toast.LENGTH_SHORT).show();
            }
        });*/


        bigmart = (Button) findViewById(R.id.bigmart);
        smallmart = (Button) findViewById(R.id.smallmart);
        choosemart = (TextView) findViewById(R.id.choosemart);
        visitmart = (TextView) findViewById(R.id.visitmart);

        //강남구
        buttongu[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[0], Toast.LENGTH_SHORT).show();
                guApi = gu[0];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        visitmart.setVisibility(visitmart.VISIBLE);
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(GN_M[0]);
                        buttonmart[1].setText(GN_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GN_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GN_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        visitmart.setVisibility(visitmart.VISIBLE);
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(GN_T[0]);
                        buttonmart[1].setText(GN_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GN_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GN_T[1];
                            }
                        });
                    }
                });
            }
        });
        //강동구
        buttongu[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[1], Toast.LENGTH_SHORT).show();
                guApi = gu[1];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        visitmart.setVisibility(visitmart.VISIBLE);
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(GD_M[0]);
                        buttonmart[1].setText(GD_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GD_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GD_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].INVISIBLE);
                        buttonmart[2].setVisibility(buttonmart[2].INVISIBLE);


                        buttonmart[0].setText(GD_T[0]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GD_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GD_T[1];
                            }
                        });
                        buttonmart[2].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[2].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GD_T[2];
                            }
                        });
                    }
                });
            }
        });

        //강북구
        buttongu[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[2], Toast.LENGTH_SHORT).show();
                guApi = gu[2];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(GB_M[0]);
                        buttonmart[1].setText(GB_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GB_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GB_M[1];
                            }
                        });

                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(GB_T[0]);
                        buttonmart[1].setText(GB_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GB_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GB_T[1];
                            }
                        });
                    }
                });
            }
        });

        //강서구
        buttongu[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[3], Toast.LENGTH_SHORT).show();
                guApi = gu[3];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(GS_M[0]);
                        buttonmart[1].setText(GS_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GS_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GS_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(GS_T[0]);
                        buttonmart[1].setText(GS_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GS_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GS_T[1];
                            }
                        });
                    }
                });
            }
        });

        //구로구
        buttongu[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[4], Toast.LENGTH_SHORT).show();
                guApi = gu[4];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(GR_M[0]);
                        buttonmart[1].setText(GR_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GR_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GR_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(GR_T[0]);
                        buttonmart[1].setText(GR_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GR_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GR_T[1];
                            }
                        });
                    }
                });
            }
        });

        //관악구
        buttongu[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[5], Toast.LENGTH_SHORT).show();
                guApi = gu[5];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(GY_M[0]);
                        buttonmart[1].setText(GY_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GY_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GY_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);
                        buttonmart[2].setVisibility(buttonmart[2].VISIBLE);

                        buttonmart[0].setText(GY_T[0]);
                        buttonmart[1].setText(GY_T[1]);
                        buttonmart[2].setText(GY_T[2]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GY_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GY_T[1];
                            }
                        });
                        buttonmart[2].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[2].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GY_T[2];
                            }
                        });

                    }
                });
            }
        });


        //광진구
        buttongu[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[6], Toast.LENGTH_SHORT).show();
                guApi = gu[6];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(GJ_M[0]);
                        buttonmart[1].setText(GJ_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GJ_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GJ_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(GJ_T[0]);
                        buttonmart[1].setText(GJ_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GJ_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = GJ_T[1];
                            }
                        });
                    }
                });
            }
        });

        //노원구
        buttongu[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[7], Toast.LENGTH_SHORT).show();
                guApi = gu[7];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(NW_M[0]);
                        buttonmart[1].setText(NW_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = NW_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = NW_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(NW_T[0]);
                        buttonmart[1].setText(NW_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = NW_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = NW_T[1];
                            }
                        });
                    }
                });
            }
        });

        //도봉구
        buttongu[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[8], Toast.LENGTH_SHORT).show();
                guApi = gu[8];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(DB_M[0]);
                        buttonmart[1].setText(DB_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = DB_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = DB_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(DB_T[0]);
                        buttonmart[1].setText(DB_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = DB_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = DB_T[1];
                            }
                        });
                    }
                });
            }
        });

        //동작구
        buttongu[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[9], Toast.LENGTH_SHORT).show();
                guApi = gu[9];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);

                        buttonmart[0].setText(DJ_M[0]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = DJ_M[0];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);

                        buttonmart[0].setText(DJ_T[0]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = DJ_T[0];
                            }
                        });
                    }
                });
            }
        });

        //동대문구
        buttongu[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[10], Toast.LENGTH_SHORT).show();
                guApi = gu[10];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(DDM_M[0]);
                        buttonmart[1].setText(DDM_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = DDM_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = DDM_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(DDM_T[0]);
                        buttonmart[1].setText(DDM_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = DDM_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = DDM_T[1];
                            }
                        });
                    }
                });
            }
        });

        //마포구
        buttongu[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[11], Toast.LENGTH_SHORT).show();
                guApi = gu[11];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(MP_M[0]);
                        buttonmart[1].setText(MP_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = MP_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = MP_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(MP_T[0]);
                        buttonmart[1].setText(MP_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = MP_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = MP_T[1];
                            }
                        });
                    }
                });
            }
        });

        //서대문구
        buttongu[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[12], Toast.LENGTH_SHORT).show();
                guApi = gu[12];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(SM_M[0]);
                        buttonmart[1].setText(SM_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SM_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SM_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(SM_T[0]);
                        buttonmart[1].setText(SM_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SM_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SM_T[1];
                            }
                        });
                    }
                });
            }
        });

        //서초구
        buttongu[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[13], Toast.LENGTH_SHORT).show();
                guApi = gu[13];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);
                        buttonmart[2].setVisibility(buttonmart[2].VISIBLE);

                        buttonmart[0].setText(SC_M[0]);
                        buttonmart[1].setText(SC_M[1]);
                        buttonmart[2].setText(SC_M[2]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SC_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SC_M[1];
                            }
                        });
                        buttonmart[2].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[2].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SC_M[2];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].INVISIBLE);
                        buttonmart[2].setVisibility(buttonmart[2].INVISIBLE);

                        buttonmart[0].setText(SC_T[0]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SC_T[0];
                            }
                        });

                    }
                });
            }
        });

        //성동구
        buttongu[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[14], Toast.LENGTH_SHORT).show();
                guApi = gu[14];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);

                        buttonmart[0].setText(SD_M[0]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SD_M[0];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(SD_T[0]);
                        buttonmart[1].setText(SD_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SD_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SD_T[1];
                            }
                        });
                    }
                });
            }
        });

        //성북구
        buttongu[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[15], Toast.LENGTH_SHORT).show();
                guApi = gu[15];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(SB_M[0]);
                        buttonmart[1].setText(SB_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SB_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SB_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(SB_T[0]);
                        buttonmart[1].setText(SB_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SB_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SB_T[1];
                            }
                        });
                    }
                });
            }
        });

        //송파구
        buttongu[16].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[16], Toast.LENGTH_SHORT).show();
                guApi = gu[16];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(SP_M[0]);
                        buttonmart[1].setText(SP_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SP_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SP_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);
                        buttonmart[2].setVisibility(buttonmart[2].VISIBLE);

                        buttonmart[0].setText(SP_T[0]);
                        buttonmart[1].setText(SP_T[1]);
                        buttonmart[2].setText(SP_T[2]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SP_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SP_T[1];
                            }
                        });
                        buttonmart[2].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[2].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = SP_T[2];
                            }
                        });
                    }
                });
            }
        });

        //양천구
        buttongu[17].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[17], Toast.LENGTH_SHORT).show();
                guApi = gu[17];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(YC_M[0]);
                        buttonmart[1].setText(YC_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = YC_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = YC_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(YC_T[0]);
                        buttonmart[1].setText(YC_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = YC_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = YC_T[1];
                            }
                        });
                    }
                });
            }
        });

        //영등포구
        buttongu[18].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[18], Toast.LENGTH_SHORT).show();
                guApi = gu[18];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);
                        buttonmart[2].setVisibility(buttonmart[2].VISIBLE);

                        buttonmart[0].setText(YP_M[0]);
                        buttonmart[1].setText(YP_M[1]);
                        buttonmart[2].setText(YP_M[2]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = YP_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = YP_M[1];
                            }
                        });
                        buttonmart[2].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[2].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = YP_M[2];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);
                        buttonmart[2].setVisibility(buttonmart[2].INVISIBLE);

                        buttonmart[0].setText(YP_T[0]);
                        buttonmart[1].setText(YP_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = YP_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = YP_T[1];
                            }
                        });
                    }
                });
            }
        });

        //용산구
        buttongu[19].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[19], Toast.LENGTH_SHORT).show();
                guApi = gu[19];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(YS_M[0]);
                        buttonmart[1].setText(YS_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = YS_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = YS_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(YS_T[0]);
                        buttonmart[1].setText(YS_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = YS_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = YS_T[1];
                            }
                        });
                    }
                });
            }
        });

        //은평구
        buttongu[20].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[20], Toast.LENGTH_SHORT).show();
                guApi = gu[20];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(EP_M[0]);
                        buttonmart[1].setText(EP_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = EP_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = EP_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(EP_T[0]);
                        buttonmart[1].setText(EP_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = EP_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = EP_T[1];
                            }
                        });
                    }
                });
            }
        });

        //중랑구
        buttongu[21].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[21], Toast.LENGTH_SHORT).show();
                guApi = gu[21];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(JR_M[0]);
                        buttonmart[1].setText(JR_M[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = JR_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = JR_M[1];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(JR_T[0]);
                        buttonmart[1].setText(JR_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = JR_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = JR_T[1];
                            }
                        });
                    }
                });
            }
        });

        //중구
        buttongu[22].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[22], Toast.LENGTH_SHORT).show();
                guApi = gu[22];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                bigmart.setVisibility(bigmart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                bigmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "대형마트";
                        Toast.makeText(getApplicationContext(), bigmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);
                        buttonmart[2].setVisibility(buttonmart[2].VISIBLE);
                        buttonmart[3].setVisibility(buttonmart[3].VISIBLE);

                        buttonmart[0].setText(J_M[0]);
                        buttonmart[1].setText(J_M[1]);
                        buttonmart[2].setText(J_M[2]);
                        buttonmart[3].setText(J_M[3]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = J_M[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = J_M[1];
                            }
                        });
                        buttonmart[2].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[2].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = J_M[2];
                            }
                        });
                        buttonmart[3].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[3].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = J_M[3];
                            }
                        });
                    }
                });

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);
                        buttonmart[2].setVisibility(buttonmart[2].INVISIBLE);
                        buttonmart[3].setVisibility(buttonmart[3].INVISIBLE);

                        buttonmart[0].setText(J_T[0]);
                        buttonmart[1].setText(J_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = J_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = J_T[1];
                            }
                        });
                    }
                });
            }
        });

        //종로구
        buttongu[23].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gu[23], Toast.LENGTH_SHORT).show();
                guApi = gu[23];
                reset();
                choosemart.setVisibility(choosemart.VISIBLE);
                smallmart.setVisibility(smallmart.VISIBLE);

                smallmart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        martTypeApi = "전통시장";
                        Toast.makeText(getApplicationContext(), smallmart.getText(), Toast.LENGTH_SHORT).show();
                        buttonmart[0].setVisibility(buttonmart[0].VISIBLE);
                        buttonmart[1].setVisibility(buttonmart[1].VISIBLE);

                        buttonmart[0].setText(JRO_T[0]);
                        buttonmart[1].setText(JRO_T[1]);

                        buttonmart[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[0].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = JRO_T[0];
                            }
                        });
                        buttonmart[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), buttonmart[1].getText(),
                                        Toast.LENGTH_SHORT).show();
                                martNameApi = JRO_T[1];
                            }
                        });
                    }
                });
            }
        });


    // DB구현

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                Cursor rs = mydb.getData(Value);
                id = Value;
                rs.moveToFirst();
                String n = rs.getString(rs.getColumnIndex(DBHelper.ZIMLIST_COLUMN_DB_MARTNAME));
                String i = rs.getString(rs.getColumnIndex(DBHelper.ZIMLIST_COLUMN_DB_ITEM));
                String p = rs.getString(rs.getColumnIndex(DBHelper.ZIMLIST_COLUMN_DB_PRICE));
                if (!rs.isClosed()) {
                    rs.close();
            }
            ImageButton zimclickbtn = (ImageButton) findViewById(R.id.zimclickbtn);
            zimclickbtn.setVisibility(View.INVISIBLE);

            DB_martname.setText((CharSequence) n);
            DB_item.setText((CharSequence) i);
            DB_price.setText((CharSequence) p);
        }
    }
}

    public void apiOnClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                oktext.setVisibility(oktext.VISIBLE);
                searafterlatyout.setVisibility(searafterlatyout.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data = getXmlData();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // text.setText(data);
                            }
                        });
                    }
                }).start();
                break;
        }
    }


    String getXmlData() {
        StringBuffer buffer=new StringBuffer();

        String queryUrl="http://openapi.seoul.go.kr:8088/637076724e616e6439307949697164/xml/ListNecessariesPricesService/1/1000/" + martNameApi + "/" + imgApi + "/" + year + "-" + month + "/" + martTypeApi + "/" + guApi;

        try {
            URL url=new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is=url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp=factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기
            String tag;

            xpp.next();
            int eventType=xpp.getEventType();

            String [] a =  {"하"};
            int b = a.length;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (b == 1) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            buffer.append("파싱 시작...\n\n");
                            break;

                        case XmlPullParser.START_TAG:
                            tag=xpp.getName();//테그 이름 얻어오기
                            if (tag.equals("row")) ;// 첫번째 검색결과
                            else if (tag.equals("M_NAME")) {
                                if (martTypeApi.equals("대형마트"))
                                    buffer.append("마트 이름 : ");
                                else
                                    buffer.append("시장 이름 : ");
                                buffer.append("");
                                xpp.next();
                                buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                //디비팀한테 줄스트링 밑에
                                String db_martname = xpp.getText();
                                DB_martname.setText("이름 :  " + db_martname);
                                buffer.append("\n"); //줄바꿈 문자 추가
                            } else if (tag.equals("A_NAME")) {
                                xpp.next();
                                buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                String db_item = xpp.getText();
                                DB_item.setText("품목 :  " + db_item);
                                buffer.append("\n"); //줄바꿈 문자 추가
                            } else if (tag.equals("M_GU_NAME")) {
                                xpp.next();
                                buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n"); //줄바꿈 문자 추가
                            } else if (tag.equals("A_PRICE")) {
                                xpp.next();
                                //정희추가
                                buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                String db_price = xpp.getText();
                                DB_price.setText("가격(원) :  " + db_price  +"원");
                                b++;
                                buffer.append("\n"); //줄바꿈 문자 추가
                            }
                            break;

                        case XmlPullParser.TEXT:
                            break;

                        case XmlPullParser.END_TAG:
                            tag=xpp.getName(); //테그 이름 얻어오기

                            if (tag.equals("row")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                            break;
                    }
                }
                // break;
                eventType=xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        buffer.append("파싱 끝\n");
        return buffer.toString();//StringBuffer 문자열 객체 반환

    }//getXmlData method...



}






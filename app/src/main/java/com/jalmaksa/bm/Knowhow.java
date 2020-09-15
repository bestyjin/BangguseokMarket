package com.jalmaksa.bm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Knowhow extends AppCompatActivity {
    EditText edit;
    TextView text;
    XmlPullParser xpp;
    ImageView home;
    ImageButton  myp;
    String data ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowhow);

        //액션바사리지기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //edit = (EditText) findViewById(R.id.edit);
        text = (TextView) findViewById(R.id.result);

        home = (ImageView)findViewById(R.id.home);
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                data = getXmlData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(data); //받아온 xml 적기
                    }
                });
            }
        }).start();

    }
/*
    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data = getXmlData();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(data); //받아온 xml 적기
                            }
                        });
                    }
                }).start();
                break;
        }
    }

*/

    String getXmlData() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n\n");
        //buffer.append("\n   품목   당일   1일전   1주일전   등락율 \n");
        // String str = edit.getText().toString();//EditText에 작성된 Text얻어오기
        //String location = URLEncoder.encode(str);
        //String query = "%EC%A0%84%EB%A0%A5%EB%A1%9C";


        String queryUrl = "http://211.237.50.150:7080/openapi/806ab0866b831fde6ade8f1759f0d883c17e43956cee00007e2548a4e7ecc9ed/xml/Grid_20171128000000000572_1/1/184";
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기
            String tag;

            xpp.next();
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//테그 이름 얻어오기

                        if (tag.equals("row")) ;// 첫번째 검색결과
                        else if (tag.equals("PRDLST_NM")) {
                            //buffer.append("품목 : ");
                            buffer.append("[");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append(" "); //줄바꿈 문자 추가
                        }else if (tag.equals("M_DISTCTNS")) {
                            //buffer.append("당일 : "); ㅁ
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("] \n\n "); //줄바꿈 문자 추가
                        }else if (tag.equals("EFFECT")) {
                            buffer.append("▼ 효능 ▼\n\n");
                            //buffer.append("\n▼\n");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        } else if (tag.equals("PURCHASE_MTH")) {
                            //buffer.append("1주일전 :");
                            buffer.append("▼ 구입요령 ▼\n\n");
                            //buffer.append("\n▼\n");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n "); //줄바꿈 문자 추가
                        } else if (tag.equals("COOK_MTH")) {
                            //buffer.append("등락율 :");
                            buffer.append("▼ 조리법 ▼\n\n");
                            //buffer.append("\n▼\n");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        }else if (tag.equals("TRT_MTH")) {
                            //buffer.append("등락율 :");
                            buffer.append("▼ 손질요령 ▼\n\n");
                            //buffer.append("\n▼\n");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n"); //줄바꿈 문자 추가
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //테그 이름 얻어오기

                        if (tag.equals("row")) buffer.append("\n\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //buffer.append("파싱 끝\n");
        return buffer.toString();//StringBuffer 문자열 객체 반환

    }//getXmlData method....
}

package com.jalmaksa.bm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class SearchAfterPage extends AppCompatActivity {
    EditText edit;
    TextView text;
    XmlPullParser xpp;
    ImageView home;
    ImageButton myp;

    //searchView
    SearchView search;
    Button usersearchchoice, googlemap;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchafter);

        home=(ImageView)findViewById(R.id.home);
        myp=(ImageButton)findViewById(R.id.myp);
        usersearchchoice = (Button)findViewById(R.id.usersearchchoice);
        googlemap = (Button)findViewById(R.id.googlemap);

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

        //액션바사리지기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //edit = (EditText) findViewById(R.id.edit);
        text = (TextView) findViewById(R.id.result);
        search = (SearchView)findViewById(R.id.search);

        usersearchchoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), UserChoiceSearch.class);
                startActivity(intent);
            }
        });

        googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getBaseContext(), MapsActivity.class);
                startActivity(intent1);
            }
        });
        /*
        //검색처리
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String str = search.getQuery().toString();
                Intent intent = new Intent(getApplicationContext(), UserChoiceSearch.class);
                intent.putExtra("str",str);
                startActivity(intent);

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
*/
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

    String getXmlData() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n");
        buffer.append("\n   품목   당일   1일전   1주일전   등락율 \n");
        buffer.append("============================== \n");

       // String str = edit.getText().toString();//EditText에 작성된 Text얻어오기
        //String location = URLEncoder.encode(str);
        //String query = "%EC%A0%84%EB%A0%A5%EB%A1%9C";

        String queryUrl = "http://www.kamis.or.kr/service/price/xml.do?action=dailyCountyList&p_cert_key=a564cfa2-1e5d-4c1d-a2ed-4762c47d74a6&p_cert_id=zz6647&p_returntype=xml&p_countycode=1101";
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

                        if (tag.equals("item")) ;// 첫번째 검색결과
                        else if (tag.equals("item_name")) {
                            //buffer.append("품목 : ");
                            buffer.append("");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append(" "); //줄바꿈 문자 추가
                        } else if (tag.equals("dpr1")) {
                            //buffer.append("당일 : ");
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append(" "); //줄바꿈 문자 추가
                        } else if (tag.equals("dpr2")) {
                            //buffer.append("1일전 :");
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append(" "); //줄바꿈 문자 추가
                        } else if (tag.equals("dpr3")) {
                            //buffer.append("1주일전 :");
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append(" "); //줄바꿈 문자 추가
                        } else if (tag.equals("value")) {
                            //buffer.append("등락율 :");
                            buffer.append(" ");
                            xpp.next();
                            if(xpp.getText().equals("0.0"))
                                buffer.append("-");
                            else
                                buffer.append("▼");
                            //buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append(" "); //줄바꿈 문자 추가
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //테그 이름 얻어오기

                        if (tag.equals("item")) buffer.append("\n\n");// 첫번째 검색결과종료..줄바꿈
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

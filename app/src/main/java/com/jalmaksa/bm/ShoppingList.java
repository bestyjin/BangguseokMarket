package com.jalmaksa.bm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShoppingList extends AppCompatActivity {
    private ListView myListView;
    DBHelper mydb;
    ArrayAdapter mAdapter;
    String price, content_martname, content_item, content_price, m;
    EditText total_p;
    int result;
    Button calButton;
    ImageButton share, myp;
    ImageView home;


    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clear();
        mAdapter.addAll(mydb.getZimlist());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        getSupportActionBar().hide();

        mydb = new DBHelper(this);
        ArrayList array_list = mydb.getZimlist();
        final ArrayList<String> items = new ArrayList<String>();

        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, array_list);   // 다중 선택가능

        myListView = (ListView) findViewById(R.id.listView1);
        myListView.setAdapter(mAdapter);

        // 삭제 버튼에 대한 이벤트 처리.

        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String[] array = new String[50];
                SparseBooleanArray checkedItems = myListView.getCheckedItemPositions();
                int count = mAdapter.getCount();
                array = mydb.getArray_seq();

                for (int i = count - 1; i >= 0; i--) {
                    if (checkedItems.get(i)) {  // 체크된 목록이 있으면
                        String tmp = array[i + 1];    // String으로 된 id값 String으로 받기
                        int id = Integer.parseInt(tmp);     // String id값 integer로 변환
                        mydb.deleteZimlist(id);     // id값 넣고 db 삭제

                        Toast.makeText(getApplicationContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                    }
                }
                myListView.clearChoices();     //선택된거 체크 풀기
                onResume();     // 자동 새로고침
            }
        });

        // 전체 선택 버튼에 대한 이벤트 처리.
        Button selectAllButton = (Button) findViewById(R.id.selectAll);
        selectAllButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count = mAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    myListView.setItemChecked(i, true);
            }
        }
    });

        // 전체 해제 버튼에 대한 이벤트 처리.
        Button selectNoneButton = (Button) findViewById(R.id.selectNone);
        selectNoneButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count = mAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    myListView.setItemChecked(i, false);
                }
            }
        });

    // 총액 계산 버튼에 대한 이벤트 처리
    total_p = (EditText)findViewById(R.id.total_p);
    calButton = (Button) findViewById(R.id.cal);
        calButton.setOnClickListener(new Button.OnClickListener() {
        public void onClick(View v) {
            result=0;

                int count = mAdapter.getCount();
                String[] array_p;
                int t;

                for(int i=0; i<count; i++) {
                    array_p = mydb.getArray_price();
                    price=array_p[i];
                    t=Integer.parseInt(removeStringNumber(price));
                    Log.i("price: ",price);
                    result=result+t;
                }
                total_p.setText(result+"원");
            }

        });

        home = (ImageView)findViewById(R.id.home);
        myp = (ImageButton)findViewById(R.id.myp);

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

        share=(ImageButton)findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m="";
                int count = mAdapter.getCount();
                String[] array_p;
                String[] array_m;
                String[] array_i;
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                for(int i=0; i<count; i++) {
                    array_m = mydb.getArray_martname();
                    content_martname=array_m[i];
                    Log.i("martname: ",content_martname);
                    //intent.putExtra(Intent.EXTRA_TEXT, content_martname);
                    array_i = mydb.getArray_item();
                    content_item=array_i[i];
                    Log.i("item: ",content_item);
                    array_p = mydb.getArray_price();
                    content_price=array_p[i];
                    Log.i("price: ",content_price);
                    m=m+"\n"+(i+1)+". "+content_item+"\n"+"    "+content_price+"\n"+"    "+content_martname;
                }
                intent.putExtra(Intent.EXTRA_SUBJECT, "- 쇼핑리스트(방구석마켓) ");
                intent.putExtra(Intent.EXTRA_TEXT, "\n"+m+"\n"+"\n사오세요~~~~~~♥");
                Intent chooser = Intent.createChooser(intent, "공유");
                startActivity(chooser);
            }
        });
    }
    public static String removeStringNumber(String str) {
        return str.replaceAll("[^0-9]", "");
    }
}
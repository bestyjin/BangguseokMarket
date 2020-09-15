package com.jalmaksa.bm;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class Question extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        getSupportActionBar().hide();


        // 부모 리스트
        ArrayList<HashMap<String, String>> groupData = new ArrayList<>();
        // 자식 리스트
        ArrayList<ArrayList<HashMap<String, String>>> childData = new ArrayList<>();

        // 부모 리스트에 요소를 추가한다.
        HashMap<String, String> groupA = new HashMap<>();
        groupA.put("ques", "Q. 다른 동네는 볼 수 없나요?");
        HashMap<String, String> groupB = new HashMap<>();
        groupB.put("ques", "Q. 찜리스트는 무엇인가요?");
        HashMap<String, String> groupC = new HashMap<>();
        groupC.put("ques", "Q. 쇼핑리스트는 무엇인가요?");
        HashMap<String, String> groupD = new HashMap<>();
        groupD.put("ques", "Q. 내가 알고 싶은 채소의 가격을 알 수 있나요?");
        HashMap<String, String> groupE = new HashMap<>();
        groupE.put("ques", "Q. 내가 선택한 재료로 만들 수 있는 요리가 궁금해요.");

        groupData.add(groupA);
        groupData.add(groupB);
        groupData.add(groupC);
        groupData.add(groupD);
        groupData.add(groupE);


        // 자식 리스트에 요소를 추가한다.
        ArrayList<HashMap<String, String>> childListA = new ArrayList<>();

        HashMap<String, String> childAA = new HashMap<>();
        childAA.put("ans", "A. 성북구 이외의 동네는 개발중에 있습니다." +
                "\n     빠른 시일 내에 추가하도록 하겠습니다.");
        childListA.add(childAA);
        childData.add(childListA);

        ArrayList<HashMap<String, String>> childListB = new ArrayList<>();

        HashMap<String, String> childB = new HashMap<>();
        childB.put("ans", "A. 원하시는 식품의 하트를 누르시면 찜리스트에서 모아볼 수 있습니다.");
        childListB.add(childB);
        childData.add(childListB);

        ArrayList<HashMap<String, String>> childListC = new ArrayList<>();

        HashMap<String, String> childC = new HashMap<>();
        childC.put("ans", "A. 찜리스트에 있는 목록을 이용하여 나만의 쇼핑리스트를 만들 수 있습니다. ");
        childListC.add(childC);
        childData.add(childListC);

        ArrayList<HashMap<String, String>> childListD = new ArrayList<>();

        HashMap<String, String> childD = new HashMap<>();
        childD.put("ans", "A. 검색창에 원하시는 채소명을 치시면 현재 가격 변동을 확인할 수 있습니다.");
        childListD.add(childD);
        childData.add(childListD);

        ArrayList<HashMap<String, String>> childListE = new ArrayList<>();

        HashMap<String, String> childE = new HashMap<>();
        childE.put("ans", "A. 메인화면 아래쪽 배너를 클릭하면 레시피를 찾아볼 수 있는 유튜브 채널로 이동합니다.");
        childListE.add(childE);
        childData.add(childListE);

        // 부모 리스트와 자식 리스트를 포함한 Adapter를 생성
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this, groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] {"ques"}, new int[] { android.R.id.text1},
                childData, android.R.layout.simple_expandable_list_item_2,
                new String[] {"ans"}, new int[] {android.R.id.text1});

        // ExpandableListView에 Adapter를 설정
        ExpandableListView listView
                = (ExpandableListView) findViewById(R.id.expandableListView);
        listView.setAdapter(adapter);
    }
}
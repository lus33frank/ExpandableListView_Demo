package com.frankchang.expandablelistview_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    // 元件
    private ExpandableListView list;
    // 變數
    private List<HashMap<String, Object>> groupData;
    private List<List<HashMap<String, Object>>> childData;
    private String[] aryGroup;
    private String[] aryChild1;
    private String[] aryChild2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();    // 畫面元件連結
        initData();     // 初始化資料
    }

    // 畫面元件連結
    private void findViews() {
        list = findViewById(R.id.expandablelist);
        // 標題監聽器
        list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String childItem = childData.get(groupPosition).get(childPosition).get("Child").toString();
                Toast.makeText(MainActivity.this, "Child Item : " + childItem, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    // 初始化資料
    private void initData() {
        // 取得陣列值
        aryGroup = getResources().getStringArray(R.array.aryGroup);
        aryChild1 = getResources().getStringArray(R.array.aryChild1);
        aryChild2 = getResources().getStringArray(R.array.aryChild2);

        // 處理資料集合
        groupData = new ArrayList<>();
        childData = new ArrayList<>();

        for (int i = 0; i < aryGroup.length; i++) {
            // Group
            HashMap<String, Object> groupObj = new HashMap<>();
            groupObj.put("Group", aryGroup[i]);
            groupData.add(groupObj);

            // Child
            List<HashMap<String, Object>> listMap = new ArrayList<>();
            switch (i) {
                case 0:
                    for (int j = 0; j < aryChild1.length; j++) {
                        HashMap<String, Object> childObj = new HashMap<>();
                        childObj.put("Child", aryChild1[j]);
                        listMap.add(childObj);
                    }
                    childData.add(listMap);
                    break;

                case 1:
                    for (int j = 0; j < aryChild2.length; j++) {
                        HashMap<String, Object> childObj = new HashMap<>();
                        childObj.put("Child", aryChild2[j]);
                        listMap.add(childObj);
                    }
                    childData.add(listMap);
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 設定 ExpandableList
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(this,
                groupData, R.layout.item_group, new String[]{"Group"}, new int[]{R.id.tvGroupTitle},
                childData, R.layout.item_child, new String[]{"Child"}, new int[]{R.id.tvChildItem});
        list.setAdapter(adapter);

        // 展開全部節點
        for (int i = 0; i < groupData.size(); i++) {
            list.expandGroup(i);
        }
    }

}

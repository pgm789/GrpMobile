package com.example.grpmobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ActivityAdapter activityAdapter;
    private List<ActivityItem> activityList;  // 修改这里，确保引用的是正确的 ActivityItem 类
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // 初始化视图
        etSearch = findViewById(R.id.etSearch);
        recyclerView = findViewById(R.id.recyclerViewActivities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 初始化活动列表
        activityList = new ArrayList<>();

        // 添加活动数据
        activityList.add(new ActivityItem(
                "Community Book Donation",
                "Collect books for children in need",
                "Downtown Library",
                "2025-09-20",
                "Ongoing",
                R.drawable.book,
                30,
                160));

        activityList.add(new ActivityItem(
                "Public Park Renovation Fund",
                "Help raise funds to improve local park facilities",
                "Green Park District Office",
                "2025-09-25",
                "Ongoing",
                R.drawable.park,
                60,
                120));

        activityList.add(new ActivityItem(
                "Food Drive",
                "Help the homeless with meals",
                "Community Center",
                "2025-08-15",
                "Completed",
                R.drawable.meal,
                100,
                100));

        // 设置适配器
        activityAdapter = new ActivityAdapter(activityList, item -> {
            // 点击活动条目，跳转到注册页面
            Intent intent = new Intent(UserActivity.this, RegistrationActivity.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("location", item.getLocation());
            intent.putExtra("date", item.getDate());
            intent.putExtra("status", item.getStatus());
            intent.putExtra("imageResId", item.getImageResId());
            startActivity(intent);
        });

        recyclerView.setAdapter(activityAdapter);

        // 搜索过滤功能
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterActivities(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    // 根据搜索条件过滤活动
    private void filterActivities(String query) {
        List<ActivityItem> filtered = new ArrayList<>();
        for (ActivityItem item : activityList) {
            if (item.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    item.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                    item.getLocation().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(item);
            }
        }
        activityAdapter.updateList(filtered);
    }
}

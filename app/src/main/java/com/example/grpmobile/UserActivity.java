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

    private RecyclerView recyclerView;  // RecyclerView to display activities
    private ActivityAdapter activityAdapter;  // Adapter to bind activity data to RecyclerView
    private List<ActivityItem> activityList;  // List to hold the activities
    private EditText etSearch;  // EditText for the search bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);  // Set the layout for this activity

        // Initialize views
        etSearch = findViewById(R.id.etSearch);  // Search bar for filtering activities
        recyclerView = findViewById(R.id.recyclerViewActivities);  // RecyclerView to show activity list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));  // Set LinearLayoutManager for RecyclerView

        // Initialize the activity list
        activityList = new ArrayList<>();

        // Add sample activities to the list
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

        // Set the adapter for RecyclerView
        activityAdapter = new ActivityAdapter(activityList, item -> {
            // When an activity item is clicked, navigate to RegistrationActivity
            Intent intent = new Intent(UserActivity.this, RegistrationActivity.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("location", item.getLocation());
            intent.putExtra("date", item.getDate());
            intent.putExtra("status", item.getStatus());
            intent.putExtra("imageResId", item.getImageResId());
            startActivity(intent);
        });

        recyclerView.setAdapter(activityAdapter);  // Set the adapter to the RecyclerView

        // Add text change listener to the search bar
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter activities when the user types in the search bar
                filterActivities(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    // Filter activities based on the search query
    private void filterActivities(String query) {
        List<ActivityItem> filtered = new ArrayList<>();
        // Iterate through the list of activities and check if the title, description, or location contains the search query
        for (ActivityItem item : activityList) {
            if (item.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    item.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                    item.getLocation().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(item);  // Add to filtered list if any of the fields match the query
            }
        }
        activityAdapter.updateList(filtered);  // Update the RecyclerView with the filtered list
    }
}
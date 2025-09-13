package com.example.grpmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private List<ActivityItem> activityList;  // List to store activity items
    private OnItemClickListener listener;     // Listener to handle item clicks

    // Interface to handle item click events
    public interface OnItemClickListener {
        void onItemClick(ActivityItem item);  // Method to handle item click
    }

    // Constructor for the adapter
    public ActivityAdapter(List<ActivityItem> activityList, OnItemClickListener listener) {
        this.activityList = activityList;
        this.listener = listener;
    }

    // Create a new view for each item in the list
    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity_card, parent, false);  // Inflate the item layout
        return new ActivityViewHolder(view);  // Return a new ViewHolder
    }

    // Bind data to the views in the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        ActivityItem item = activityList.get(position);  // Get the current activity item

        // Set text for various TextViews
        holder.tvTitle.setText(item.getTitle());
        holder.tvDescription.setText(item.getDescription());
        holder.tvLocation.setText("Location: " + item.getLocation());
        holder.tvDate.setText("Date: " + item.getDate());
        holder.tvStatus.setText(item.getStatus());

        // Set color for the status based on whether it's ongoing or not
        if ("Ongoing".equalsIgnoreCase(item.getStatus())) {
            holder.tvStatus.setTextColor(holder.itemView.getResources().getColor(android.R.color.holo_green_dark));
        } else {
            holder.tvStatus.setTextColor(holder.itemView.getResources().getColor(android.R.color.darker_gray));
        }

        // Set the activity image
        holder.imageActivity.setImageResource(item.getImageResId());

        // Handle item click event
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);  // Trigger the listener when an item is clicked
            }
        });
    }

    // Return the total number of items in the list
    @Override
    public int getItemCount() {
        return activityList.size();
    }

    // ViewHolder class to hold references to the views for an individual item
    public static class ActivityViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDescription, tvLocation, tvDate, tvStatus;  // Views for activity details
        ImageView imageActivity;  // Image view for activity image

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvActivityTitle);
            tvDescription = itemView.findViewById(R.id.tvActivityDescription);
            tvLocation = itemView.findViewById(R.id.tvActivityLocation);
            tvDate = itemView.findViewById(R.id.tvActivityDate);
            tvStatus = itemView.findViewById(R.id.tvActivityStatus);
            imageActivity = itemView.findViewById(R.id.imageActivity);
        }
    }

    // Method to update the list of activities, useful for filtering
    public void updateList(List<ActivityItem> filteredList) {
        activityList = filteredList;
        notifyDataSetChanged();  // Notify the adapter that the data has changed
    }
}

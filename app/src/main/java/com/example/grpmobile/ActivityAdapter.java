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

    private List<ActivityItem> activityList;  // 修改为正确的 ActivityItem 路径
    private OnItemClickListener listener;

    // 接口用于处理点击事件
    public interface OnItemClickListener {
        void onItemClick(ActivityItem item);  // 修改为正确的 ActivityItem 路径
    }

    // 构造方法
    public ActivityAdapter(List<ActivityItem> activityList, OnItemClickListener listener) {
        this.activityList = activityList;
        this.listener = listener;
    }

    // 创建视图
    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity_card, parent, false);  // 使用你的卡片布局
        return new ActivityViewHolder(view);
    }

    // 绑定视图
    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        ActivityItem item = activityList.get(position);

        holder.tvTitle.setText(item.getTitle());
        holder.tvDescription.setText(item.getDescription());
        holder.tvLocation.setText("Location: " + item.getLocation());
        holder.tvDate.setText("Date: " + item.getDate());
        holder.tvStatus.setText(item.getStatus());

        // 设置状态颜色
        if ("Ongoing".equalsIgnoreCase(item.getStatus())) {
            holder.tvStatus.setTextColor(holder.itemView.getResources().getColor(android.R.color.holo_green_dark));
        } else {
            holder.tvStatus.setTextColor(holder.itemView.getResources().getColor(android.R.color.darker_gray));
        }

        // 设置活动图片
        holder.imageActivity.setImageResource(item.getImageResId());

        // 点击事件
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    // 返回项数
    @Override
    public int getItemCount() {
        return activityList.size();
    }

    // ViewHolder 类，用来存储视图中的控件
    public static class ActivityViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDescription, tvLocation, tvDate, tvStatus;
        ImageView imageActivity;

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

    // 用于更新列表（过滤）
    public void updateList(List<ActivityItem> filteredList) {  // 修改为正确的 ActivityItem 路径
        activityList = filteredList;
        notifyDataSetChanged();
    }
}

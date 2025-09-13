package com.example.grpmobile;

public class ActivityItem {
    private String title;
    private String description;
    private String location;
    private String date;
    private String status;
    private int imageResId;
    private int currentDonation;
    private int targetDonation;

    // 构造函数
    public ActivityItem(String title, String description, String location, String date,
                        String status, int imageResId, int currentDonation, int targetDonation) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.status = status;
        this.imageResId = imageResId;
        this.currentDonation = currentDonation;
        this.targetDonation = targetDonation;
    }

    // Getter 方法
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getCurrentDonation() {
        return currentDonation;
    }

    public int getTargetDonation() {
        return targetDonation;
    }

    // Setter 方法（如果需要）
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setCurrentDonation(int currentDonation) {
        this.currentDonation = currentDonation;
    }

    public void setTargetDonation(int targetDonation) {
        this.targetDonation = targetDonation;
    }

    // calculate donate progress
    public int getDonationProgress() {
        if (targetDonation == 0) {
            return 0;
        }
        return (int) ((double) currentDonation / targetDonation * 100);
    }
}

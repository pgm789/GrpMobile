package com.example.grpmobile;

public class ActivityItem {
    private String title;            // Title of the activity
    private String description;      // Description of the activity
    private String location;         // Location where the activity will take place
    private String date;             // Date of the activity
    private String status;           // Status of the activity (e.g., ongoing, completed)
    private int imageResId;          // Resource ID for the image associated with the activity
    private int currentDonation;     // Current donation amount
    private int targetDonation;      // Target donation amount

    // Constructor
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

    // Getter methods
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

    // Setter methods (if needed)
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

    // Method to calculate the donation progress as a percentage
    public int getDonationProgress() {
        if (targetDonation == 0) {
            return 0;  // If there's no target, progress is 0%
        }
        return (int) ((double) currentDonation / targetDonation * 100);  // Return percentage of donation progress
    }
}

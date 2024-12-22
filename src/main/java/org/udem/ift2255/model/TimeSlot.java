package org.udem.ift2255.model;

public class TimeSlot {
    private String day;
    private String startTime;
    private String endTime;



    // Constructor accepting day, startTime, and endTime
    public TimeSlot(String day, String startTime, String endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Default constructor (required for JSON deserialization)
    public TimeSlot() {
    }

    // Getters and Setters
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

package com.blackdot.habits.Models;

import java.io.Serializable;

public class Habits implements Serializable {
    String phoneNumber, habitId;
    String habitName;
    int numberOfDays;

    int numberOfDaysLeft;
    int habitStatus;
    String habitStartDate, habitEndDate;

    public Habits(String habitName, int numberOfDays) {
        this.habitName = habitName;
        this.numberOfDays=numberOfDays;
    }

    public Habits() {

    }

    public int getNumberOfDaysLeft() {
        return numberOfDaysLeft;
    }

    public void setNumberOfDaysLeft(int numberOfDaysLeft) {
        this.numberOfDaysLeft = numberOfDaysLeft;
    }

    public String getHabitId() {
        return habitId;
    }

    public void setHabitId(String habitId) {
        this.habitId = habitId;
    }

    public int getHabitStatus() {
        return habitStatus;
    }

    public void setHabitStatus(int habitStatus) {
        this.habitStatus = habitStatus;
    }

    public String getHabitStartDate() {
        return habitStartDate;
    }

    public void setHabitStartDate(String habitStartDate) {
        this.habitStartDate = habitStartDate;
    }

    public String getHabitEndDate() {
        return habitEndDate;
    }

    public void setHabitEndDate(String habitEndDate) {
        this.habitEndDate = habitEndDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
}

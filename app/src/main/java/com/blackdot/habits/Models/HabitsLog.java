package com.blackdot.habits.Models;


public class HabitsLog {


    String habitId, HabitPerformDate;

    public String getHabitId() {
        return habitId;
    }

    public void setHabitId(String habitId) {
        this.habitId = habitId;
    }

    public String getHabitPerformDate() {
        return HabitPerformDate;
    }

    public void setHabitPerformDate(String habitPerformDate) {
        HabitPerformDate = habitPerformDate;
    }

    public int getHabitAction() {
        return HabitAction;
    }

    public void setHabitAction(int habitAction) {
        HabitAction = habitAction;
    }

    public int getNumberOfDaysLeft() {
        return numberOfDaysLeft;
    }

    public void setNumberOfDaysLeft(int numberOfDaysLeft) {
        this.numberOfDaysLeft = numberOfDaysLeft;
    }

    int HabitAction, numberOfDaysLeft;
}

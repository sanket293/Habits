package com.blackdot.habits.Models;

public class PredefineHabits {
    String habitName;
    int numberOfDays;
    public PredefineHabits(String habitName, int numberOfDays) {
        this.habitName = habitName;
        this.numberOfDays = numberOfDays;
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

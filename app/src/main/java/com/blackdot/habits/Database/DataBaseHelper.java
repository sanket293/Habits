package com.blackdot.habits.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.blackdot.habits.Common.Constants;
import com.blackdot.habits.Models.Habits;
import com.blackdot.habits.Models.UserLogin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    private Context context;
    public static final String DATABASE_NAME = Constants.DATABASE_NAME;
    private static DataBaseHelper instance;
    private static final int DATABASE_VERSION = 1;
    private static SQLiteDatabase sqliteDb;

    // constructor
    DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                   int version) {
        super(context, name, factory, version);
        this.context = context;

    }

    // singleton instance
    public static final DataBaseHelper getInstance(Context context) {
        initialize(context, DATABASE_NAME);
        return instance;  // instance is name of the singleton object
    }

    private static void initialize(Context context, String databaseName) {
        if (instance == null) {

            if (!checkDatabase(context, databaseName)) {

                try {
                    copyDataBase(context, databaseName);
                } catch (IOException e) {

                    System.out.println(databaseName
                            + " does not exists ");
                }
            }

            instance = new DataBaseHelper(context, databaseName, null,
                    DATABASE_VERSION);
            sqliteDb = instance.getWritableDatabase();

            System.out.println("instance of  " + databaseName + " created ");
        }
    }

    public static boolean checkDatabase(Context aContext, String databaseName) {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = getDatabasePath(aContext, databaseName);
            try {
                checkDB = SQLiteDatabase.openDatabase(myPath, null,
                        SQLiteDatabase.OPEN_READWRITE);
                checkDB.close();

            } catch (Exception e) {
                Log.e(Constants.LOG_DATABASE, "check database fn" + e.getMessage());
            }
        } catch (SQLiteException e) {

            System.out.println(databaseName + " does not exists");
        }

        return checkDB != null ? true : false;
    }

    private static String getDatabasePath(Context aContext, String databaseName) {
        return "/data/data/" + aContext.getPackageName() + "/databases/"
                + databaseName;
    }

    private static void copyDataBase(Context aContext, String databaseName)
            throws IOException {

        InputStream myInput = aContext.getAssets().open(databaseName);

        String outFileName = getDatabasePath(aContext, databaseName);

        File f = new File("/data/data/" + aContext.getPackageName()
                + "/databases/");
        if (!f.exists())
            f.mkdir();

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

        System.out.println(databaseName + " copied");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    ////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////   CRUD OPERATIONS  /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////   INSERT OPERATIONS  /////////////////////////////////////////

    public boolean addUser(UserLogin user) {

        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }

            if (user == null) {
                return false;
            }

            String name = user.getName();
            String password = user.getPassword();
            String email = user.getEmail();
            String phoneNumber = user.getPhoneNumber();


//            String query = "insert into User(Name, Password, Email, PhoneNumber) values('" + name + "','" + password + "','" + email + "','" + phoneNumber + "');";
            String query = "insert into " + Constants.DB_TABLE_USERLOGIN + "(" + Constants.DB_USERLOGIN_NAME + ", " + Constants.DB_USERLOGIN_PASSWORD + ", " + Constants.DB_USERLOGIN_EMAIL + ", " + Constants.DB_USERLOGIN_PHONE_NUMBER + ") values('" + name + "','" + password + "','" + email + "','" + phoneNumber + "');";
            Log.w(Constants.LOG_DATABASE, "add User: " + query);
            sqliteDb = instance.getWritableDatabase();
            sqliteDb.execSQL(query);
            return true;

        } catch (Exception e) {
            Log.e(Constants.LOG_DATABASE, "add user function" + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public static boolean addNewHabit(Habits newHabit) {

        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }

            if (newHabit == null) {
                return false;
            }

            String habitId = newHabit.getHabitId();
            String phoneNumber = newHabit.getPhoneNumber();
            String habitName = newHabit.getHabitName();
            int numberOfDays = newHabit.getNumberOfDays();
            int numberOfDaysLeft = newHabit.getNumberOfDaysLeft();
            String habitStartDate = newHabit.getHabitStartDate();
            String habitEndDate = newHabit.getHabitEndDate();
            int habitStatus = newHabit.getHabitStatus();

            String query = "insert into " + Constants.DB_TABLE_HABITS + "(" + Constants.DB_HABITS_HABIT_ID + "," + Constants.DB_HABITS_PHONE_NUMBER + ", " + Constants.DB_HABITS_HABIT_NAME + "," + Constants.DB_HABITS_NUMBER_OF_DAYS_LEFT + "," + Constants.DB_HABITS_NUMBER_OF_DAYS + "," + Constants.DB_HABITS_HABIT_START_DATE + "," + Constants.DB_HABITS_HABIT_END_DATE + "," + Constants.DB_HABITS_HABIT_STATUS + ") values('" + habitId + "','" + phoneNumber + "','" + habitName + "','" + numberOfDaysLeft + "','" + numberOfDays + "','" + habitStartDate + "','" + habitEndDate + "','" + habitStatus + "');";
            Log.w(Constants.LOG_DATABASE, "add new habit: " + query);
            sqliteDb = instance.getWritableDatabase();
            sqliteDb.execSQL(query);
            return true;

        } catch (Exception e) {
            Log.e(Constants.LOG_DATABASE, "add new habit function" + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }


    ///////////////////////////   SEARCH OPERATIONS  /////////////////////////////////////////
    public static boolean checkCredentials(String phoneNumber, String password) {


        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            String query = "select * from " + Constants.DB_TABLE_USERLOGIN + " where " + Constants.DB_USERLOGIN_PHONE_NUMBER + "='" + phoneNumber + "' and " + Constants.DB_USERLOGIN_PASSWORD + "='" + password + "';";
            Log.w(Constants.LOG_DATABASE, "Check Credentials: " + query);
            Cursor cursor = sqliteDb.rawQuery(query, null);

            if (cursor != null) {

                while (cursor.moveToNext()) {
                    if (cursor.moveToFirst()) {
                        if (cursor.getCount() == 1) {
                            cursor.close();
                            return true;
                        }
                    }
                }
                cursor.close();
                return false;
            } else {
                Log.e(Constants.LOG_DATABASE, "cursor is null at check credential function");
                return false;
            }

        } catch (Exception e) {
            Log.e(Constants.LOG_DATABASE, "check credential function" + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public static UserLogin getUserInfo(String phoneNumber) {


        UserLogin userLogin = null;
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            String query = "select * from " + Constants.DB_TABLE_USERLOGIN + " where  " + Constants.DB_USERLOGIN_PHONE_NUMBER + "='" + phoneNumber + "';";
            Log.w(Constants.LOG_DATABASE, "Check Credentials: " + query);
            Cursor cursor = sqliteDb.rawQuery(query, null);

            if (cursor != null) {
                userLogin = new UserLogin();

                while (cursor.moveToNext()) {
                    if (cursor.moveToFirst()) {

                        userLogin.setName(cursor.getString(cursor.getColumnIndex(Constants.DB_USERLOGIN_NAME)));
                        userLogin.setPassword(cursor.getString(cursor.getColumnIndex(Constants.DB_USERLOGIN_PASSWORD)));
                        userLogin.setEmail(cursor.getString(cursor.getColumnIndex(Constants.DB_USERLOGIN_EMAIL)));
                        userLogin.setPhoneNumber(cursor.getString(cursor.getColumnIndex(Constants.DB_USERLOGIN_PHONE_NUMBER)));

                    }
                }
                cursor.close();
            } else {
                Log.e(Constants.LOG_DATABASE, "cursor is null at check credential function");
            }

        } catch (Exception e) {
            Log.e(Constants.LOG_DATABASE, "check credential function" + e.getMessage());
            e.printStackTrace();
        }
        return userLogin;

    }

    public boolean isPhoneNumberAvailable(String phoneNumber) {

        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            String query = "select * from " + Constants.DB_TABLE_USERLOGIN + " where " + Constants.DB_USERLOGIN_PHONE_NUMBER + "='" + phoneNumber + "';";
            Log.w(Constants.LOG_DATABASE, "Check if phone number is available: " + query);
            Cursor cursor = sqliteDb.rawQuery(query, null);

            if (cursor != null) {

                if (cursor.getCount() >= 1) {
                    cursor.close();
                    return false;
                } else {
                    return true;
                }

            } else {
                Log.e(Constants.LOG_DATABASE, "cursor is null at isPhoneNumberAvailable function");
                return true;
            }
        } catch (Exception e) {
            Log.e(Constants.LOG_DATABASE, "isPhoneNumberAvailable function" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static List<Habits> getUserHabitList(String phoneNumber, Context context) {

        List<Habits> habitsList =
                habitsList = new ArrayList<>();

        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            String query = "select * from " + Constants.DB_TABLE_HABITS + " where " + Constants.DB_USERLOGIN_PHONE_NUMBER + "='" + phoneNumber + "';";
            Log.w(Constants.LOG_DATABASE, "get user habit list: " + query);
            Cursor cursor = sqliteDb.rawQuery(query, null);

//todo fix this if possible
            if (cursor != null) {

//                while (cursor.moveToNext()) {
//                    if (cursor.moveToFirst()) {
//
//                        int numberOfDays = cursor.getInt(cursor.getColumnIndex(Constants.DB_HABITS_NUMBER_OF_DAYS));
//                        String habitName = cursor.getString(cursor.getColumnIndex(Constants.DB_HABITS_HABIT_NAME));
//
//                        Habits habits = new Habits(habitName, numberOfDays);
//
//
//                        habitsList.add(habits); //add the item
//
//
//                    }
//                }
//


                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    int numberOfDays = cursor.getInt(cursor.getColumnIndex(Constants.DB_HABITS_NUMBER_OF_DAYS));
                    String habitName = cursor.getString(cursor.getColumnIndex(Constants.DB_HABITS_HABIT_NAME));
                    String habitId = cursor.getString(cursor.getColumnIndex(Constants.DB_HABITS_HABIT_ID));
                    int numberOfDaysLeft = cursor.getInt(cursor.getColumnIndex(Constants.DB_HABITS_NUMBER_OF_DAYS_LEFT));
                    Habits habits = new Habits(habitName, numberOfDays);
                    habits.setHabitId(habitId);
                    habits.setNumberOfDaysLeft(numberOfDaysLeft);
                    habitsList.add(habits); //add the item
                    cursor.moveToNext();

                }


                cursor.close();
            } else {
                Log.e(Constants.LOG_DATABASE, "cursor is null at check get user  habit list");
            }


        } catch (Exception e) {
            Log.e(Constants.LOG_DATABASE, "isPhoneNumberAvailable function" + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return habitsList;
    }

    public Habits getHabitDetails(String habitId) {


        Habits habitDetails = null;
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            String query = "select * from " + Constants.DB_TABLE_HABITS + " where " + Constants.DB_HABITS_HABIT_ID + " ='" + habitId + "';";
            Log.w(Constants.LOG_DATABASE, "get habit details: " + query);
            Cursor cursor = sqliteDb.rawQuery(query, null);

            if (cursor != null) {
                habitDetails = new Habits();

                while (cursor.moveToNext()) {
                    if (cursor.moveToFirst()) {
                        if (cursor.getCount() == 1) {

                            String phoneNumber = cursor.getString(cursor.getColumnIndex(Constants.DB_HABITS_PHONE_NUMBER));
                            int numberOfDays = cursor.getInt(cursor.getColumnIndex(Constants.DB_HABITS_NUMBER_OF_DAYS));
                            int numberOfDaysLeft = cursor.getInt(cursor.getColumnIndex(Constants.DB_HABITS_NUMBER_OF_DAYS_LEFT));
                            String habitName = cursor.getString(cursor.getColumnIndex(Constants.DB_HABITS_HABIT_NAME));
                            int habitStatus = cursor.getInt(cursor.getColumnIndex(Constants.DB_HABITS_HABIT_STATUS));
                            String habitStartDate = cursor.getString(cursor.getColumnIndex(Constants.DB_HABITS_HABIT_START_DATE));
                            String habitEndDate = cursor.getString(cursor.getColumnIndex(Constants.DB_HABITS_HABIT_END_DATE));

                            habitDetails.setPhoneNumber(phoneNumber);
                            habitDetails.setNumberOfDays(numberOfDays);
                            habitDetails.setNumberOfDaysLeft(numberOfDaysLeft);
                            habitDetails.setHabitName(habitName);
                            habitDetails.setHabitStatus(habitStatus);
                            habitDetails.setHabitStartDate(habitStartDate);
                            habitDetails.setHabitEndDate(habitEndDate);
                        }
                    }
                }
                cursor.close();
            } else {
                Log.e(Constants.LOG_DATABASE, "cursor is null at get habit details function");
            }

        } catch (Exception e) {
            Log.e(Constants.LOG_DATABASE, "get habit details" + e.getMessage());
            e.printStackTrace();
        }
        return habitDetails;

    }
    ///////////////////////////   UPDATE OPERATIONS  /////////////////////////////////////////

    public boolean resetPassword(String phoneNumber, String password) {

        try {

            String query = "UPDATE " + Constants.DB_TABLE_USERLOGIN + " SET " + Constants.DB_USERLOGIN_PASSWORD + " = '" + password + "' WHERE " + Constants.DB_USERLOGIN_PHONE_NUMBER + " = '" + phoneNumber + "' ";
            Log.w(Constants.LOG_DATABASE, "reset password: " + query);
            sqliteDb.execSQL(query);
            return true;

        } catch (Exception e) {
            Log.e(Constants.LOG_DATABASE, "reset password function" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateDailyTaskPerformance(Habits habits, boolean isHabitPerformed) {

        try {
            String query = "";
            if (isHabitPerformed) {
                String habitId = habits.getHabitId();
                int numberOfDaysLeft = habits.getNumberOfDaysLeft();

                query = "UPDATE " + Constants.DB_TABLE_HABITS + " SET " + Constants.DB_HABITS_NUMBER_OF_DAYS_LEFT + " = '" + numberOfDaysLeft + "' WHERE " + Constants.DB_HABITS_HABIT_ID + " = '" + habitId + "' ";
            } else {
                String habitId = habits.getHabitId();
                int numberOfDaysLeft = habits.getNumberOfDays(); // set same number of days
                String habitStartDate = habits.getHabitStartDate();
                String habitEndDate = habits.getHabitEndDate();

                query = "UPDATE " + Constants.DB_TABLE_HABITS + " SET " + Constants.DB_HABITS_NUMBER_OF_DAYS_LEFT + " = '" + numberOfDaysLeft + "'," + Constants.DB_HABITS_HABIT_START_DATE + "='" + habitStartDate + "'," + Constants.DB_HABITS_HABIT_END_DATE + "='" + habitEndDate + "' WHERE " + Constants.DB_HABITS_HABIT_ID + " = '" + habitId + "' ";

            }

            Log.w(Constants.LOG_DATABASE, "update daily task  perform : " + query);
            sqliteDb.execSQL(query);
            return true;

        } catch (Exception e) {
            Log.e(Constants.LOG_DATABASE, "update daily task  perform fn" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


}
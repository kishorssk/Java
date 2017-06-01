package inninc.nieplacementcellapp;

/**
 * Created by Pradyumna1 on 2/9/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StudentDBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "studentManager";

    // Profiles table name
    private static final String TABLE_PRO = "Students";

    // Profiles Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "my_name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DOB = "dob";
    private static final String KEY_USN = "usn";
    private static final String KEY_BRANCH = "branch";
    private static final String KEY_CGPA = "cgpa";
    private static final String KEY_BE_PERCENT = "bepercent";
    private static final String KEY_PUC = "puc";
    private static final String KEY_SCHOOL = "school";
    private static final String KEY_REGISTERED = "registered";
    private static final String KEY_PASSWORD = "password";

    public StudentDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ProfileS_TABLE = "CREATE TABLE " + TABLE_PRO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," 
                + KEY_NAME + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_EMAIL + " TEXT," 
                + KEY_DOB + " TEXT,"
                + KEY_USN + " TEXT,"
                + KEY_BRANCH + " TEXT,"
                + KEY_CGPA + " TEXT,"
                + KEY_BE_PERCENT + " TEXT,"
                + KEY_PUC + " TEXT,"
                + KEY_SCHOOL + " TEXT,"
                + KEY_REGISTERED + " TEXT,"
                + KEY_PASSWORD + " TEXT"
                + ")";
        db.execSQL(CREATE_ProfileS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRO);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Profile
    void addProfile(Student person) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, person.getName()); // Profile Name
        values.put(KEY_PHONE, person.getPhone()); // Profile Phone
        values.put(KEY_EMAIL, person.getEmail()); // Profile Name
        values.put(KEY_DOB, person.getDob()); // Profile Phone
        values.put(KEY_USN, person.getUsn()); // Profile Name
        values.put(KEY_BRANCH, person.getBranch());
        values.put(KEY_CGPA, person.getCgpa());
        values.put(KEY_BE_PERCENT, person.getCgpaPercent());
        values.put(KEY_PUC, person.getPucMarks());
        values.put(KEY_SCHOOL, person.getSchoolMarks());
        values.put(KEY_REGISTERED, "true");
        values.put(KEY_PASSWORD, person.getPassword());


        // Inserting Row
        db.insert(TABLE_PRO, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Profile
    Student getProfile() {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor resultSet = db.rawQuery("Select * from Students", null);
        resultSet.moveToFirst();
        if(resultSet.getCount()==0){

            return null;
        }
        else {
            Student person = new Student();
            person.setName(resultSet.getString(1));
            person.setPhone(resultSet.getString(2));
            person.setEmail(resultSet.getString(3));
            person.setDob(resultSet.getString(4));
            person.setUsn(resultSet.getString(5));
            person.setBranch(resultSet.getString(6));
            person.setCgpa(resultSet.getString(7));
            person.setCgpaPercent(resultSet.getString(8));
            person.setPucMarks(resultSet.getString(9));
            person.setSchoolMarks(resultSet.getString(10));
            person.setPassword(resultSet.getString(12));
            return person;
        }
    }

    String getKeyRegistered(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor resultSet = db.rawQuery("Select * from Students", null);

        if (resultSet != null)
            resultSet.moveToFirst();


        if(resultSet.getCount()==0){

            return null;
        }
        else {
            String result = resultSet.getString(1);
            Log.e("result",result);

            return result;
        }
    }



    // Deleting single Profile
    public void deleteProfile(String USN) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRO, KEY_USN + " = ?",
                new String[]{String.valueOf(USN)});
        db.close();
    }


}

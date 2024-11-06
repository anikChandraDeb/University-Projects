package com.example.bloodbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table User(id integer primary key autoincrement,name TEXT, mail TEXT, password TEXT,birthdate TEXT,bloodgroup  TEXT,phone  TEXT,district  TEXT,lastdonatedate  TEXT,countdonateblood  TEXT,type  TEXT,approved TEXT)");
        DB.execSQL("create Table Login(id integer primary key autoincrement,name TEXT, mail TEXT, password TEXT,birthdate TEXT,bloodgroup  TEXT,phone  TEXT,district  TEXT,lastdonatedate  TEXT,countdonateblood  TEXT,type  TEXT,approved TEXT)");
        DB.execSQL("create Table BloodPost(id integer primary key autoincrement, mail TEXT, patientdisease TEXT,bloodgroup TEXT,hospital  TEXT,address  TEXT,donatedate  TEXT,approved TEXT)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Userdetails");
    }
    public Boolean insertuserdata(String name, String mail, String password, String birthdate, String bloodgroup, String phone, String district, String lastdonatedate, String countdonateblood, String type, String approved)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("mail", mail);
        contentValues.put("password", password);
        contentValues.put("birthdate", birthdate);
        contentValues.put("bloodgroup", bloodgroup);
        contentValues.put("phone", phone);
        contentValues.put("district", district);
        contentValues.put("lastdonatedate", lastdonatedate);
        contentValues.put("countdonateblood", countdonateblood);
        contentValues.put("type", type);
        contentValues.put("approved", approved);
        long result=DB.insert("User", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean insertlogindata(String name, String mail, String password, String birthdate, String bloodgroup, String phone, String district, String lastdonatedate, String countdonateblood, String type, String approved)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("mail", mail);
        contentValues.put("password", password);
        contentValues.put("birthdate", birthdate);
        contentValues.put("bloodgroup", bloodgroup);
        contentValues.put("phone", phone);
        contentValues.put("district", district);
        contentValues.put("lastdonatedate", lastdonatedate);
        contentValues.put("countdonateblood", countdonateblood);
        contentValues.put("type", type);
        contentValues.put("approved", approved);
        long result=DB.insert("Login", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean insertbloodpostdata( String mail, String patientdisease,String bloodgroup,String hospital, String address, String donatedate,  String approved)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("mail", mail);
        contentValues.put("patientdisease", patientdisease);

        contentValues.put("bloodgroup", bloodgroup);
        contentValues.put("hospital", hospital);
        contentValues.put("address", address);
        contentValues.put("donatedate", donatedate);

        contentValues.put("approved", approved);
        long result=DB.insert("BloodPost", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean insertuserdataDelete(String name,  String dob)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);

        contentValues.put("Dob", dob);
        long result=DB.insert("Delete_Log", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean updateuserdata(String name, String mail, String password, String birthdate, String bloodgroup, String phone, String district, String lastdonatedate, String countdonateblood, String type, String approved)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("mail", mail);
        contentValues.put("password", password);
        contentValues.put("birthdate", birthdate);
        contentValues.put("bloodgroup", bloodgroup);
        contentValues.put("phone", phone);
        contentValues.put("district", district);
        contentValues.put("lastdonatedate", lastdonatedate);
        contentValues.put("countdonateblood", countdonateblood);
        contentValues.put("type", type);
        contentValues.put("approved", approved);

            long result = DB.update("User", contentValues, "mail=?", new String[]{mail});
            if (result == -1) {
                return false;
            } else {
                return true;
            }

    }
    public Boolean updatebloodpostdata( String mail,  String approved)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("approved", approved);

        long result = DB.update("BloodPost", contentValues, "mail=?", new String[]{mail});
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    public Boolean updateuserdata( String mail,  String approved)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("approved", approved);

        long result = DB.update("User", contentValues, "mail=?", new String[]{mail});
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    public Boolean deletedata (String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    public Cursor getuserdata ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from User", null);
        return cursor;
    }
    public Cursor getlogindata ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Login", null);
        return cursor;
    }
    public Cursor getbloodpostdata ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from BloodPost", null);
        return cursor;
    }
    public Cursor getlogindatabymail (String mail)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Login where mail = ?", new String[]{mail});
        return cursor;
    }
    public Cursor getuserdatabymail (String mail)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from User where mail = ?", new String[]{mail});
        return cursor;
    }
    public void dropTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Execute SQL command to drop the table
            db.execSQL("DELETE FROM " + tableName);
        } catch (SQLException e) {
            // Handle any exceptions
            e.printStackTrace();
        } finally {
            // Close the database connection
            db.close();
        }
    }
}
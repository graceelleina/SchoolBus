package com.example.schoolbus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "LoginSQL.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(id integer PRIMARY KEY AUTOINCREMENT, email text, username text, phone text, password text)");
        db.execSQL("CREATE TABLE Buses(BusID integer PRIMARY KEY AUTOINCREMENT, BusName text, BusType text, BusPlate text, BusImage text)");
        db.execSQL("CREATE TABLE Reservations(TransactionID integer PRIMARY KEY AUTOINCREMENT, BusID text, id text, Date text, FOREIGN KEY(id) REFERENCES user(id),  FOREIGN KEY(BusID) REFERENCES Buses(BusID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS Buses");
        db.execSQL("DROP TABLE IF EXISTS Reservations");
        onCreate(db);
    }


    public Boolean insertUser(String email, String username, String phone, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("username", username);
        contentValues.put("phone", phone);
        contentValues.put("password", password);
        long insert = db.insert("user", null, contentValues);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean insertBus(String name, String type, String plate, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BusName", name);
        contentValues.put("BusType", type);
        contentValues.put("BusPlate", plate);
        contentValues.put("BusImage", image);
        long insert = db.insert("Buses", null, contentValues);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean insertDetail(String userId, String tanggal, String busId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", userId);
        contentValues.put("BusID", busId);
        contentValues.put("Date", tanggal);
        long insert = db.insert("Reservations", null, contentValues);
        if (insert == -1) {
            return false;
        }
       else {
           return true;
       }
    }

    public Boolean updateUsername(String emailExtra, String usernamesChage){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("username", usernamesChage);

        db.update("user", contentValues, "email = ?", new String[]{emailExtra});

        db.close();

        return true;
    }

    public Boolean deleteUser(String emailExtra){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("user", "email = ?", new String[]{emailExtra});

        db.close();

        return true;
    }

    public Boolean checkRegister(String email, String username, String phone, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email = ? AND username = ? AND phone = ? AND password = ?", new String[]{email, username, phone, password});
        if (cursor.getCount() > 0 ) {
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email = ? AND password = ?", new String[]{email, password});

        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getReadUsername(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email = ?", new String[]{email});
        cursor.moveToFirst();

        String id = cursor.getString(0);
        String mail = cursor.getString(1);
        String username = cursor.getString(2);
        String phone = cursor.getString(3);

        return username;

    }

    public String getReadPhone(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email = ?", new String[]{email});
        cursor.moveToFirst();

        String id = cursor.getString(0);
        String mail = cursor.getString(1);
        String username = cursor.getString(2);
        String phone = cursor.getString(3);

        return phone;

    }

    public String getReadPhonebyUsername(String usernames)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ?", new String[]{usernames});
        cursor.moveToFirst();

        String id = cursor.getString(0);
        String mail = cursor.getString(1);
        String username = cursor.getString(2);
        String phone = cursor.getString(3);

        return phone;

    }

    public String getReadUserid(String usernames)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ?", new String[]{usernames});
        cursor.moveToFirst();

        String id = cursor.getString(0);
        String mail = cursor.getString(1);
        String username = cursor.getString(2);
        String phone = cursor.getString(3);

        return id;
    }

    public String getBusid(String BusesName)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Buses WHERE BusName = ?", new String[]{BusesName});
        cursor.moveToFirst();

        String busId = cursor.getString(0);
        String productNames = cursor.getString(1);
        String productRating = cursor.getString(2);
        String productImage = cursor.getString(3);
        String productDescription = cursor.getString(4);

        return busId;
    }

    public Cursor viewData(String idUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Reservations WHERE id =?", new String[]{idUser});
        return cursor;
    }

    public String getBusname(String busID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Buses WHERE BusID = ?", new String[]{busID});
        cursor.moveToFirst();

        String busesName = cursor.getString(1);

        return busesName;

    }

    public String getBusPlate(String busID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Buses WHERE BusID = ?", new String[]{busID});
        cursor.moveToFirst();

        String busesPlate = cursor.getString(3);

        return busesPlate;
    }

    public String getBusType(String busID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Buses WHERE BusID = ?", new String[]{busID});
        cursor.moveToFirst();

        String fromGetBusType = cursor.getString(2);

        return fromGetBusType;
    }
}


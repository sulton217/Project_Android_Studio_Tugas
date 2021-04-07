package com.example.loginsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "login.db"; //nama database
    private static final String TABLE_NAME ="login"; //nama tabel
    private static final String TABLE_AKUN_LOGIN ="akun_login"; //nama tabel
    private static final int DATABASE_VERSION = 1; //versi database
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+ //Query untuk Drop Tabel
            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Nama VARCHAR(50)," +
            "NoHP VARCHAR(20),"+
            "Jurusan VARCHAR(50));";
    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME; //query untuk Drop Tabel
    private Context context;
    //-------------------------------------------------------------------------------------------------------
    //tabel_akun_query
    private static final String CREATE_TABLE_AKUN = "CREATE TABLE "+TABLE_AKUN_LOGIN+ //Query untuk Drop Tabel
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username VARCHAR(20)," +
            "password VARCHAR(20));";
    private static final String DROP_TABLE_AKUN ="DROP TABLE IF EXISTS "+TABLE_AKUN_LOGIN; //query untuk Drop Tabel

    //private Context context;
//---------------------------------------------------------------------------------------------------------------
    public dbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); //membuat database
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
            db.execSQL(CREATE_TABLE_AKUN);
        } catch (Exception e) {
            Message.message(context, "" + e);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            Message.message(context,"OnUpgrade"); // menampilkan toast onUpgrade
            db.execSQL(DROP_TABLE);         //drop tabel/menghapus tabel dengan mengggunakan query DROP_TABLE
            db.execSQL(DROP_TABLE_AKUN);         //drop tabel/menghapus tabel dengan mengggunakan query DROP_TABLE_AKUN
            onCreate(db);                   //memanggil proses oncreate
        }catch (Exception e) {
            Message.message(context, "" + e); // menampilkan toast eror
        }
    }

    public long InsertData2(String username,String password,String jurusan) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Nama",username);
        values.put("NoHP",password);
        values.put("Jurusan",jurusan);

        long id = db.insert(TABLE_NAME, null, values);
        return id;
    }

    public long InsertData(String username,String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username",username);
        values.put("password",password);

        long id = db.insert(TABLE_AKUN_LOGIN, null, values);
        return id;
    }
    public int delete(String username) {
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs ={username};
        int count =db.delete(TABLE_NAME,"Nama=?",whereArgs);
        return count;
    }
    public int update(String idnya, String username,String password , String jurusan ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", idnya);
        values.put("Jurusan", jurusan);
        values.put("Nama", username);
        values.put("NoHP", password);


        String[] whereArgs = {idnya};
        int count = db.update(TABLE_NAME,values,"id =?",whereArgs);
        //int count = db.delete(TABLE_NAME, "id= ?", whereArgs);
        return count;
    }
    public boolean Login (String username,String password) throws SQLException {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_AKUN_LOGIN + " WHERE username=? AND password=?",new String[]{username,password});
        if (mCursor !=null){
            if (mCursor.getCount()>0)
            {
                return true;
            }
        }return false;
    }
}

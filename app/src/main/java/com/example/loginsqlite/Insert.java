package com.example.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Insert extends AppCompatActivity {
    private EditText edit_user, edit_password , edit_jurusan ;
    private String user, pass,juru, isiDB1 ;
    private TextView textDB1;
    private Cursor cursor;
    dbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        textDB1 = (TextView) findViewById(R.id.textDB);
        edit_user = (EditText) findViewById(R.id.edit_user3);
        edit_password = (EditText) findViewById(R.id.edit_pass3);
        edit_jurusan = (EditText) findViewById(R.id.edit_jurusan3);
        textDB1.setMovementMethod(new ScrollingMovementMethod());
        dbHelper = new dbHelper(this);
        tampilData();
    }

    private void tampilData() {
        isiDB1="";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cursor = db.query("login", null, null, null, null, null, null);

        int i = 0;
        try {
            while (cursor.moveToNext()) {
                cursor.moveToPosition(i);
                isiDB1+=" ID          :" + cursor.getString(cursor.getColumnIndex("id")) + "\n" +
                        " NAMA        :" + cursor.getString(cursor.getColumnIndex("Nama")) + "\n" +
                        " Nomor HP:" + cursor.getString(cursor.getColumnIndex("NoHP")) + "\n" +
                        " Jurusan     :" + cursor.getString(cursor.getColumnIndex("Jurusan")) + "\n" +
                         "--------------------------------------------------\n";
                i++;
            }
        } finally {
            textDB1.setText(isiDB1); // menampilkan isi string isiDB yang telah diisi dengan data dari database
            cursor.close(); //menutup akses cursor
        }
    }

    public void insert(View view) {
        user = edit_user.getText().toString();
        pass = edit_password.getText().toString();
        juru = edit_jurusan.getText().toString();
        if (user.isEmpty()||pass.isEmpty()||juru.isEmpty()) {
            Message.message(getApplicationContext(), "Jangan kosong i data mahasiswa");
        }else{
            long id = dbHelper.InsertData2(user,pass,juru);
            if(id<=0){
                Message.message(getApplicationContext(),"Data Gagal Di Tambahkan");
                edit_user.setText("");
                edit_password.setText("");
                edit_jurusan.setText("");
            }else {
                Message.message(getApplicationContext(), "Data Berhasil Di Tambahkan");
                edit_user.setText("");
                edit_password.setText("");
                edit_jurusan.setText("");
            }tampilData();

        }
    }

    public void logout(View view) {
        Intent intent = new Intent(Insert.this,Action.class);
        startActivity(intent);
    }
}

package com.example.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Update extends AppCompatActivity {
    private EditText edit_user, edit_password, edit_jurusan;
    private String user, pass, isiDB,jurus, no , idnya;
    private TextView textDB;
    private Cursor cursor;
    private Spinner spinnersearch;
    dbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        textDB = (TextView) findViewById(R.id.textdb3);
        edit_jurusan = (EditText) findViewById(R.id.edit_jurusan4);
        edit_user = (EditText) findViewById(R.id.edit_user4);
        edit_password = (EditText) findViewById(R.id.edit_pass4);
        spinnersearch = (Spinner) findViewById(R.id.spinner_search);
        textDB.setMovementMethod(new ScrollingMovementMethod());
        setEnable(false);
        tampilData();
    }

    private void tampilData() {
        isiDB = "";
        dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.query("login", null, null, null, null, null, null);

        int i = 0;
        try {
            while (cursor.moveToNext()) {
                cursor.moveToPosition(i);
                isiDB += "ID          :" + cursor.getString(cursor.getColumnIndex("id")) + "\n" +
                        "Nama         :" + cursor.getString(cursor.getColumnIndex("Nama")) + "\n" +
                        "Nomor HP:" + cursor.getString(cursor.getColumnIndex("NoHP")) + "\n" +
                        " Jurusan     :" + cursor.getString(cursor.getColumnIndex("Jurusan")) + "\n" +
                        "--------------------------------------------------\n";
                i++;
            }
        } finally {
            textDB.setText(isiDB); // menampilkan isi string isiDB yang telah diisi dengan data dari database
            tampilSpinner();
            cursor.close(); //menutup akses cursor
        }

    }
    private void tampilSpinner () {
        ArrayList<String> arraySpinner = new ArrayList<>(); //membuat arrayList untuk isi Spinner
        dbHelper = new dbHelper(this); //inisialisasi database
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // membuka database
        cursor = db.query("login", null, null, null, null, null, null); // memanggil parameter query red
        int i = 0;
        try {
            while (cursor.moveToNext()) {
                cursor.moveToPosition(i);
                arraySpinner.add(cursor.getString(1)); //menambahakan data dari database ke array
                i++;
            }
        } finally {
            ArrayAdapter<String> adapterarray = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinner);
            spinnersearch.setAdapter(adapterarray); //mengisi spinner dengan array list yang telah terisi data
            spinnersearch.setSelection(0);  //mengatur data yang terpilih pada spinner ke posisi 0
            cursor.close(); //menutup akses cursor
        }
    }

    public void update (View view){
        jurus = edit_jurusan.getText().toString();
        user = edit_user.getText().toString();
        pass= edit_password.getText().toString();
        if (jurus.isEmpty()||user.isEmpty()||pass.isEmpty()) {
            Message.message(getApplicationContext(), "Masukkan data mahasiswa dahulu");
        }else{
            int a=dbHelper.update(idnya,user,pass,jurus);
            if(a<=0){
                Message.message(getApplicationContext(),"Gagal");
                edit_jurusan.setText("");
                edit_user.setText("");
                edit_password.setText("");
            }else {
                Message.message(getApplicationContext(),"Berhasil");
                edit_jurusan.setText("");
                edit_user.setText("");
                edit_password.setText("");
                setEnable(false);
            }tampilData();
        }
    }

    public void search (View view) {
        user = spinnersearch.getSelectedItem().toString();
        String[] namaArgs ={user};
        dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.query("login", null, "Nama = ?", namaArgs, null, null, null);

        int i = 0;
        try {
            while (cursor.moveToNext()) {
                cursor.moveToPosition(i);
                jurus = cursor.getString(cursor.getColumnIndex("Jurusan"));
                user = cursor.getString(cursor.getColumnIndex("Nama"));
                pass = cursor.getString(cursor.getColumnIndex("NoHP"));
                idnya = cursor.getString(cursor.getColumnIndex("id"));
                i++;
            }
        }catch (Exception e){
            Message.message(getApplicationContext(),"Error"+e);
        } finally {
            edit_jurusan.setText(jurus);
            edit_user.setText(user);
            edit_password.setText(pass);// mengambil data String baru dari Database
            setEnable(true);
            cursor.close(); //menutup akses cursor
        }
    }

    private void setEnable(Boolean b) {
        edit_jurusan.setEnabled(b);
        edit_user.setEnabled(b);
        edit_password.setEnabled(b);

    }

    public void logout(View view) {
        Intent intent = new Intent(Update.this,Action.class);
        startActivity(intent);
    }
}
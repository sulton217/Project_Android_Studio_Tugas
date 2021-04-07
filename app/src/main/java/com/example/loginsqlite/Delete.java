package com.example.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Delete extends AppCompatActivity {
    private String isiDB;
    private TextView textDB;
    private Cursor cursor;
    private Spinner spinnerdelete;
    dbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        textDB = (TextView) findViewById(R.id.textdb2);
        spinnerdelete = (Spinner) findViewById(R.id.spinner_delete2);
        textDB.setMovementMethod(new ScrollingMovementMethod());
        dbHelper = new dbHelper(this);
        tampilData();
    }

    private void tampilData() {
        isiDB="";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.query("login", null, null, null, null, null, null);

        int i = 0;
        try {
            while (cursor.moveToNext()) {
                cursor.moveToPosition(i);
                isiDB+=" ID       :" + cursor.getString(cursor.getColumnIndex("id")) + "\n" +
                        " Nama        :" + cursor.getString(cursor.getColumnIndex("Nama")) + "\n" +
                        " Nomor HP:" + cursor.getString(cursor.getColumnIndex("NoHP")) + "\n" +
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

    private void tampilSpinner() {
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
            spinnerdelete.setAdapter(adapterarray); //mengisi spinner dengan array list yang telah terisi data
            spinnerdelete.setSelection(0);  //mengatur data yang terpilih pada spinner ke posisi 0
            cursor.close(); //menutup akses cursor
        }
    }

    public void delete(View view) {
        String name = spinnerdelete.getSelectedItem().toString();
        if (name.isEmpty()){
            Message.message(getApplicationContext(),"Enter Data");
        }else {
            int a=dbHelper.delete(name);
            if (a<=0){
                Message.message(getApplicationContext(),"Unsuccessfull ");
                spinnerdelete.setSelection(0);
            }else {
                Message.message(this,"DELETED");
                spinnerdelete.setSelection(0);
            }
        }
        tampilData();
    }

    public void logout(View view) {
        Intent intent = new Intent(Delete.this,Action.class);
        startActivity(intent);
    }
}

package com.example.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class Register extends AppCompatActivity {
    private EditText edit_user, edit_password ;
    private String user, pass , isiDB10 ;
    dbHelper dbHelper;
    private TextView textDB10;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edit_user = (EditText) findViewById(R.id.edit_user2);
        edit_password = (EditText) findViewById(R.id.edit_pass2);
        textDB10 = (TextView) findViewById(R.id.textDB10);
        textDB10.setMovementMethod(new ScrollingMovementMethod());
        dbHelper = new dbHelper(this);
        tampilData();
    }

    public void register(View view) {
        Intent intent = new Intent(Register.this,Login.class);
        startActivity(intent);
    }

    public void login(View view) {
        user = edit_user.getText().toString();
        pass= edit_password.getText().toString();
        if (user.isEmpty()||pass.isEmpty()) {
            Message.message(getApplicationContext(), "Input Username dan Password");
        }else{
            long id = dbHelper.InsertData(user,pass);




            if(id<=0){
                Message.message(getApplicationContext(),"Insert Unsuccessfull");
                edit_user.setText("");
                edit_password.setText("");
            }else {
                Message.message(getApplicationContext(), "Insert Successfull");
                edit_user.setText("");
                edit_password.setText("");
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        }
    }

    private void tampilData() {
        isiDB10="";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cursor = db.query("akun_login", null, null, null, null, null, null);

        int i = 0;
        try {
            while (cursor.moveToNext()) {
                cursor.moveToPosition(i);
                isiDB10+=" ID      :" + cursor.getString(cursor.getColumnIndex("ID")) + "\n" +
                        " Username    :" + cursor.getString(cursor.getColumnIndex("username")) + "\n" +
                        "--------------------------------------------------\n";
                i++;
            }
        } finally {
            textDB10.setText(isiDB10); // menampilkan isi string isiDB yang telah diisi dengan data dari database
            cursor.close(); //menutup akses cursor
        }
    }

}

package com.example.sta_op_hulp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sta_op_hulp.Database.DatabaseHelper;
import com.example.sta_op_hulp.Model.Gebruiker;
import com.example.sta_op_hulp.zorgpersoneel.client.zorgpersoneelActivity;
//import com.example.sta_op_hulp.Database.Inserttestdata;


import static android.content.ContentValues.TAG;
import static com.example.sta_op_hulp.Database.DataContract.Gebruiker.TABLE_Gebruikers;

public class MainActivity extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView mDisplayDate, error;
    private Button loginbtn;
    private EditText pincodeEDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbtn = findViewById(R.id.login);
        error = findViewById(R.id.errormsg);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
               login();
               // new Inserttestdata(root); //insert test data
            }
        });
        pincodeEDT = findViewById(R.id.textInputPindcode);
        pincodeEDT.setText("1002");
        mDisplayDate = (TextView) findViewById(R.id.textInputgeboortedatum);
        mDisplayDate.setText("12-1-2021");

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = month + "-" + day + "-" + year;
                mDisplayDate.setText(date);
            }
        };

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogDateEdit();
            }
        });

    }

    private void login() {
        try {
            DatabaseHelper dbH = new DatabaseHelper(this);
            SQLiteDatabase db = dbH.getReadableDatabase();
            String datum = mDisplayDate.getText().toString();

            int pincode = Integer.parseInt(pincodeEDT.getText().toString());
            String sql = "SELECT * FROM " + TABLE_Gebruikers + " where geboortedatum = '" + datum + "' and gebruikersnummer = " + pincode + "";
            System.out.println(sql);
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                do {

                    int gebruikersnummer = cursor.getInt(cursor.getColumnIndex("gebruikersnummer"));
                    int rol = cursor.getInt(cursor.getColumnIndex("rol"));
                    String geboortedatum = cursor.getString(cursor.getColumnIndex("rol"));
                    String naam = cursor.getString(cursor.getColumnIndex("naam"));
                    int inactiefmelding = cursor.getInt(cursor.getColumnIndex("inactiefmelding"));
                    Gebruiker g = new Gebruiker(gebruikersnummer, rol, inactiefmelding, naam, geboortedatum);
                    if (g.getRol() < 1) {
                        Intent i = new Intent(this, ClientActivity.class);
                        i.putExtra("gebruiker", (Parcelable) g);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(this, zorgpersoneelActivity.class);
                        i.putExtra("zorgpersoneel", (Parcelable) g);
                        startActivity(i);
                    }
                }
                while (cursor.moveToNext());
            } else {
                Toast.makeText(this, "De ingevoerde combinatie bestaat niet", Toast.LENGTH_SHORT).show();
                System.out.println("Ingevoerde gegevens zijn onjuist");
                error.setText("Ingevoerde gegevens zijn onjuist");
            }

        } catch (NullPointerException nullPointerException) {
            System.out.println("ERROR");
        } catch (NumberFormatException nfe) {
            error.setText("vul alle invoervelden volledig in");
        }
    }

    // dialog om datum in te stellen
    private void openDialogDateEdit() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
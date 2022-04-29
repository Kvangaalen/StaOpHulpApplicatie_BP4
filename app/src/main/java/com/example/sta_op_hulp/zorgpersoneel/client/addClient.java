package com.example.sta_op_hulp.zorgpersoneel.client;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sta_op_hulp.Database.DatabaseHelper;
import com.example.sta_op_hulp.R;

import static com.example.sta_op_hulp.Database.DataContract.Gebruiker.TABLE_Gebruikers;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_AKKOORD;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_CLIENTNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_PERSONEELSNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.TABLE_ZorgpersoneelClienten;
import static com.example.sta_op_hulp.zorgpersoneel.client.zorgpersoneelActivity.g;

public class addClient extends AppCompatActivity {
    private Button opslaan;
    private EditText edtclientnummer;
    private String clientnummer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        edtclientnummer = findViewById(R.id.edittextclientnummer);
        opslaan = findViewById(R.id.btnSendrequest);

        clientnummer = edtclientnummer.getText().toString();
        opslaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendRequest();
            }
        });

    }

    private void SendRequest() {
        DatabaseHelper dbH = new DatabaseHelper(this);
        SQLiteDatabase db = dbH.getWritableDatabase();
        try {
            if (checkUserExists() && !checkUserExistsZorgpersoneelClient()){

                    clientnummer = edtclientnummer.getText().toString();
                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_NAME_CLIENTNUMMER, clientnummer);
                    cv.put(COLUMN_NAME_PERSONEELSNUMMER, g.getGebruikersnummer());
                    cv.put(COLUMN_NAME_AKKOORD, 0);
                    long result = db.insert(TABLE_ZorgpersoneelClienten, null, cv);
                    if (result > 0) {
                        Toast.makeText(this, "Verzoek is verstuurd.", Toast.LENGTH_SHORT).show();
                    }


            } else {
                if (!checkUserExists()){
                } else {
                    Toast.makeText(this, "Er is al een verzoek versuurd.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (NumberFormatException NFE) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    private Boolean checkUserExists() {
        DatabaseHelper dbH = new DatabaseHelper(this);
        SQLiteDatabase db = dbH.getReadableDatabase();
        clientnummer = edtclientnummer.getText().toString();
        if (!clientnummer.isEmpty()){
            try {
                String sql = "SELECT * FROM " + TABLE_Gebruikers + " where gebruikersnummer = " + clientnummer ;
                System.out.println(sql);
                Cursor cursor = db.rawQuery(sql, null);

                if (cursor.moveToFirst()) {
                    do {
                        return true;
                    }
                    while (cursor.moveToNext());
                } else {
                    Toast.makeText(this, "De ingevoerde clientnummer bestaat niet.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (Exception e) {
                System.out.println(e+"ERROR");
            }
            finally {
                db.close();
            }
        } else {
            Toast.makeText(this, "Vul alle invoervelden volledig in", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    private Boolean checkUserExistsZorgpersoneelClient() {
        DatabaseHelper dbH = new DatabaseHelper(this);
        SQLiteDatabase db = dbH.getReadableDatabase();
        clientnummer = edtclientnummer.getText().toString();
        try {
            String sql = "SELECT * FROM " + TABLE_ZorgpersoneelClienten + " where " + COLUMN_NAME_CLIENTNUMMER +" = " + clientnummer +" and  " + COLUMN_NAME_PERSONEELSNUMMER + " = " + g.getGebruikersnummer();
            System.out.println(sql);
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                do {
                    return true;
                }
                while (cursor.moveToNext());
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e+"ERROR");
        }
        finally {
            db.close();
        }
        return null;
    }
}
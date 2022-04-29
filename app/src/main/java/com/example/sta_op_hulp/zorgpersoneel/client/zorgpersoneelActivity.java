package com.example.sta_op_hulp.zorgpersoneel.client;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sta_op_hulp.Database.DatabaseHelper;
import com.example.sta_op_hulp.Model.Gebruiker;
import com.example.sta_op_hulp.Model.ZorgpersoneelClient;
import com.example.sta_op_hulp.R;
import com.example.sta_op_hulp.zorgpersoneel.inactiviteitClient.clientinactiviteitoverzicht;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_AKKOORD;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_PERSONEELSNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.TABLE_ZorgpersoneelClienten;

public class zorgpersoneelActivity extends AppCompatActivity {
    private static Context context;
    private RecyclerView recyclerView;
    private AdapterClientOverzicht adapter;
    public static Gebruiker g;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zorgpersoneel);

        g =  getIntent().getExtras().getParcelable("zorgpersoneel");
        System.out.println("name123"+g.getGebruikersnummer());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterClientOverzicht(this, getClientZorgpersoneel(this));
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClient();
            }
        });
        adapter.setOnMoreButtonClickListener(new AdapterClientOverzicht.OnMoreButtonClickListener() {
            @Override
            public void onItemClick(View view, ZorgpersoneelClient obj, MenuItem item) {
                if (item.getTitle().equals("Verwijderen")) {
                    deleteClient(obj);
                }
            }
        });

        adapter.setOnItemClickListener(new AdapterClientOverzicht.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ZorgpersoneelClient obj, int item) {
                openClientInactiviteit(obj);
            }
        });




    }
    private void openClientInactiviteit(ZorgpersoneelClient obj){
        Intent i = new Intent(this, clientinactiviteitoverzicht.class);
        i.putExtra("zorgpersoneelClient", (Parcelable) obj);
        startActivity(i);
    }

    private void addClient() {
        Intent i = new Intent(this, addClient.class);
        i.putExtra("activiteit", (Parcelable) g);
        startActivity(i);
    }

    private void deleteClient(ZorgpersoneelClient obj){

        try {

            DatabaseHelper dbH = new DatabaseHelper(this);
            SQLiteDatabase db = dbH.getReadableDatabase();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Verwijderen bevestigen");
            builder.setMessage("Weet je het zeker?");

            // verwijder button
            builder.setPositiveButton("Verwijderen", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        long result = db.delete(TABLE_ZorgpersoneelClienten, "clientnummer=? AND persooneelnummer=?", new String[] {String.valueOf(obj.getClientnummer()), String.valueOf(obj.getPersoneelnummer())});
                        if (result > 1) {

                        }
                    } catch (Exception e) {
                        System.out.println("ERROR" + e);
                    } finally {
                        db.close();
                        dialog.dismiss();

                        finish();
                        startActivity(getIntent());
                    }
                }
            });
            // annuleer button
            builder.setNegativeButton("Annuleren", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (NullPointerException nullPointerException) {
            System.out.println("ERROR");
        } finally {

        }
    }

    // ophallen van de voorraad
    public List<ZorgpersoneelClient> getClientZorgpersoneel(zorgpersoneelActivity overviewVoorraadFragment) {
        List<ZorgpersoneelClient> LVZorgpersoneelClient = new ArrayList<>();
        DatabaseHelper dbH = new DatabaseHelper(this);
        SQLiteDatabase db = dbH.getReadableDatabase();
        try {
            String sql = "select * from " + TABLE_ZorgpersoneelClienten + " where " + COLUMN_NAME_PERSONEELSNUMMER + " = '" +  g.getGebruikersnummer() + "' and " + COLUMN_NAME_AKKOORD + " = 1";
            System.out.println(sql);
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    int persooneelnummer = cursor.getInt(cursor.getColumnIndex("persooneelnummer"));
                    int clientnummer = cursor.getInt(cursor.getColumnIndex("clientnummer"));
                    int akkoord = cursor.getInt(cursor.getColumnIndex("akkoord"));
                    System.out.println(persooneelnummer);

                    com.example.sta_op_hulp.Model.ZorgpersoneelClient zc = new ZorgpersoneelClient(persooneelnummer, clientnummer, akkoord);

                    LVZorgpersoneelClient.add(zc);
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e);
        } finally {
            db.close();
        }
        return LVZorgpersoneelClient;
    }
}
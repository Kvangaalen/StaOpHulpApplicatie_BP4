package com.example.sta_op_hulp.zorgpersoneel.inactiviteitClient;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sta_op_hulp.Database.DatabaseHelper;
import com.example.sta_op_hulp.Model.Activiteit;
import com.example.sta_op_hulp.Model.ZorgpersoneelClient;
import com.example.sta_op_hulp.R;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.sta_op_hulp.Database.DataContract.Activiteit.TABLE_Activiteiten;
import static com.example.sta_op_hulp.Database.DataContract.Gebruiker.COLUMN_NAME_GEBRUIKERSNUMMER;

public class clientinactiviteitoverzicht extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterClientActiviteitenOverzicht adapter;
    private ZorgpersoneelClient zorgpersoneelClient;
    private TextView emptyView,totaaltimeTV, totaaltijdHeader;
    private String time;
    private int sum = 0;
    public clientinactiviteitoverzicht() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientinactiviteitoverzicht);

        totaaltijdHeader = (TextView) findViewById(R.id.totaaltijdHeader);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            zorgpersoneelClient = extras.getParcelable("zorgpersoneelClient");
            System.out.println("zorgpersoneelClient" + zorgpersoneelClient.getClientnummer());
        }


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterClientActiviteitenOverzicht(this, getClienData(this));
        recyclerView.setAdapter(adapter);
        totaaltimeTV = (TextView) findViewById(R.id.totaaltimeTV);

        totaaltimeTV.setText(String.format("%d:%02d", sum / 3600, (sum % 3600) / 60, (sum % 60)));
        emptyView = (TextView) findViewById(R.id.empty_view);

        if (getClienData(this).isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            totaaltijdHeader.setVisibility(View.GONE);
            totaaltimeTV.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            totaaltijdHeader.setVisibility(View.VISIBLE);
            totaaltimeTV.setVisibility(View.VISIBLE);
        }


    }


    public List<Activiteit> getClienData(clientinactiviteitoverzicht overviewVoorraadFragment) {
        List<Activiteit> VoorpraadPrducten = new ArrayList<>();
        DatabaseHelper dbH = new DatabaseHelper(this);
        SQLiteDatabase db = dbH.getReadableDatabase();
        Duration diff;
        try {
            String sql = "select * from " + TABLE_Activiteiten + " where " + COLUMN_NAME_GEBRUIKERSNUMMER + "= " + zorgpersoneelClient.getClientnummer() + "";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    int gebruikersnummer = cursor.getInt(cursor.getColumnIndex("gebruikersnummer"));
                    int stoelnummer = cursor.getInt(cursor.getColumnIndex("stoelnummer"));
                    String tijdzit = cursor.getString(cursor.getColumnIndex("tijdzit"));
                    String tijdopstaan = cursor.getString(cursor.getColumnIndex("tijdopstaan"));
                    String datum = cursor.getString(cursor.getColumnIndex("datum"));
                    Activiteit v = new Activiteit(gebruikersnummer, stoelnummer, tijdzit, tijdopstaan, datum);
                    VoorpraadPrducten.add(v);
                    LocalTime t1 = LocalTime.parse(tijdzit);
                    LocalTime t2 = LocalTime.parse(tijdopstaan);
                    diff = Duration.between(t1, t2);

                    int totaalmin = Math.toIntExact(diff.getSeconds());

                    sum = totaalmin + sum;
                    System.out.println(sum);
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e);
        } finally {
            db.close();
        }
        return VoorpraadPrducten;
    }

}
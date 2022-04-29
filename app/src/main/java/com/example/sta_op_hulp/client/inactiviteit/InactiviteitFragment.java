package com.example.sta_op_hulp.client.inactiviteit;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sta_op_hulp.Database.DatabaseHelper;
import com.example.sta_op_hulp.Model.Activiteit;
import com.example.sta_op_hulp.R;
import com.example.sta_op_hulp.databinding.FragmentHomeBinding;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.sta_op_hulp.ClientActivity.g;
import static com.example.sta_op_hulp.Database.DataContract.Activiteit.COLUMN_NAME_GEBRUIKERSNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.Activiteit.TABLE_Activiteiten;

public class InactiviteitFragment extends Fragment {

    private FragmentHomeBinding binding;
    private static Context context;
    private View root;
    private RecyclerView recyclerView;
    private AdapterActiviteit adapter;
    private TextView emptyView,totaaltimeTV,totaaltijdHeader ;
    private String time;
    private int sum = 0;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterActiviteit(getContext(), getInactiviteit(this));
        recyclerView.setAdapter(adapter);
        totaaltijdHeader = (TextView) root.findViewById(R.id.totaaltijdHeader);
        emptyView = (TextView) root.findViewById(R.id.empty_view);

        totaaltimeTV = (TextView) root.findViewById(R.id.totaaltimeTV);

        totaaltimeTV.setText(String.format("%d:%02d", sum / 3600, (sum % 3600) / 60, (sum % 60)));

        if (getInactiviteit(this).isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            totaaltimeTV.setVisibility(View.GONE);
            totaaltijdHeader.setVisibility(View.GONE);

        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            totaaltimeTV.setVisibility(View.VISIBLE);
            totaaltijdHeader.setVisibility(View.VISIBLE);
        }

        return root;
    }

    // ophallen van de voorraad
    public List < Activiteit > getInactiviteit(InactiviteitFragment overviewVoorraadFragment) {
        List < Activiteit > VoorpraadPrducten = new ArrayList < > ();
        DatabaseHelper dbH = new DatabaseHelper(this.getContext());
        SQLiteDatabase db = dbH.getReadableDatabase();
        try {
            String sql = "select * from " + TABLE_Activiteiten + " where " + COLUMN_NAME_GEBRUIKERSNUMMER + " = " + g.getGebruikersnummer() + "";
            Cursor cursor = db.rawQuery(sql, null);
            Duration diff;
            if (cursor.moveToFirst()) {
                do {
                    int gebruikersnummer = cursor.getInt(cursor.getColumnIndex("gebruikersnummer"));
                    int stoelnummer = cursor.getInt(cursor.getColumnIndex("stoelnummer"));
                    String tijdzit = cursor.getString(cursor.getColumnIndex("tijdzit"));
                    String tijdopstaan = cursor.getString(cursor.getColumnIndex("tijdopstaan"));
                    String datum = cursor.getString(cursor.getColumnIndex("datum"));
                    Activiteit v = new Activiteit(gebruikersnummer, stoelnummer, tijdzit, tijdopstaan, datum);
                    LocalTime t1 = LocalTime.parse(tijdzit);
                    LocalTime t2 = LocalTime.parse(tijdopstaan);
                    diff = Duration.between(t1, t2);

                    int totaalmin = Math.toIntExact(diff.getSeconds());

                    sum = totaalmin + sum;

                    VoorpraadPrducten.add(v);
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
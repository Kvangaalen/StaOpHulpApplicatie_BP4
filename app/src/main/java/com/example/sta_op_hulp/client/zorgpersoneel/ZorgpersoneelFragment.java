package com.example.sta_op_hulp.client.zorgpersoneel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sta_op_hulp.ClientActivity;
import com.example.sta_op_hulp.Database.DatabaseHelper;
import com.example.sta_op_hulp.Model.ZorgpersoneelClient;
import com.example.sta_op_hulp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_AKKOORD;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_CLIENTNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.TABLE_ZorgpersoneelClienten;

public class ZorgpersoneelFragment < setOnItemClickListener > extends Fragment {

    private static Context context;
    private RecyclerView recyclerView;
    private AdapterZorgpersoneel adapter;
    private TextView textFabIcon;
    private FloatingActionButton fab;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_overzichtverpleegkundige, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        int profile_counts = getProfilesCount();
        fab = root.findViewById(R.id.fab);
        textFabIcon = root.findViewById(R.id.textFabIcon);
        textFabIcon.setText(String.valueOf(profile_counts));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterZorgpersoneel(getContext(), getZorgpersoneelClient(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnMoreButtonClickListener(new AdapterZorgpersoneel.OnMoreButtonClickListener() {
            @Override
            public void onItemClick(View view, ZorgpersoneelClient obj, MenuItem item) {
                if (item.getTitle().equals("Verwijderen")) {
                    verwijderenZorgpersoneel(obj);
                }
            }
        });

        adapter.setOnItemClickLongListener(new AdapterZorgpersoneel.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ZorgpersoneelClient obj, int item) {
                Navigation.findNavController(view).navigate(R.id.action_nav_zorgpersoneel_to_viewRequestFragment);
            }

        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_zorgpersoneel_to_viewRequestFragment);
            }
        });

        //LinearLayout button Overzicht voorraad
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_zorgpersoneel_to_viewRequestFragment);
            }
        });

        return root;
    }

    private void verwijderenZorgpersoneel(ZorgpersoneelClient obj) {
        try {
            DatabaseHelper dbH = new DatabaseHelper(getContext());
            SQLiteDatabase db = dbH.getReadableDatabase();

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Verwijderen bevestigen");
            builder.setMessage("Weet je het zeker?");
            // verwijder button
            builder.setPositiveButton("Verwijderen", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        long result = db.delete(TABLE_ZorgpersoneelClienten, "clientnummer=? AND persooneelnummer=?", new String[] {
                                String.valueOf(obj.getClientnummer()), String.valueOf(obj.getPersoneelnummer())
                        });
                        if (result == -1) {
                            Toast.makeText(getContext(), "Failed to Delete.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "zorgpersoneel is verwijderd" + obj.getClientnummer() + obj.getPersoneelnummer(), Toast.LENGTH_SHORT).show();

                            //Navigation.findNavController(root).navigate(R.id.action_nav_winkellijst_self);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR" + e);
                    } finally {
                        db.close();
                        dialog.dismiss();
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
        }

    }

    public int getProfilesCount() {
        String countQuery = "select * from " + TABLE_ZorgpersoneelClienten + " where " + COLUMN_NAME_CLIENTNUMMER + " = '" + ClientActivity.g.getGebruikersnummer() + "' and " + COLUMN_NAME_AKKOORD + " = 0";
        DatabaseHelper dbH = new DatabaseHelper(this.getContext());
        SQLiteDatabase db = dbH.getReadableDatabase();
        int count = 0;
        try {
            Cursor cursor = db.rawQuery(countQuery, null);
            count = cursor.getCount();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return count;
    }

    // ophallen van de voorraad
    public List < ZorgpersoneelClient > getZorgpersoneelClient(ZorgpersoneelFragment overviewVoorraadFragment) {
        List < ZorgpersoneelClient > LVZorgpersoneelClient = new ArrayList < > ();
        DatabaseHelper dbH = new DatabaseHelper(this.getContext());
        SQLiteDatabase db = dbH.getReadableDatabase();
        try {
            String sql = "select * from " + TABLE_ZorgpersoneelClienten + " where " + COLUMN_NAME_CLIENTNUMMER + " = '" + ClientActivity.g.getGebruikersnummer() + "' and " + COLUMN_NAME_AKKOORD + " = 1";
            System.out.println(sql);
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    int persooneelnummer = cursor.getInt(cursor.getColumnIndex("persooneelnummer"));
                    int clientnummer = cursor.getInt(cursor.getColumnIndex("clientnummer"));
                    int akkoord = cursor.getInt(cursor.getColumnIndex("akkoord"));
                    ZorgpersoneelClient zc = new ZorgpersoneelClient(persooneelnummer, clientnummer, akkoord);
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
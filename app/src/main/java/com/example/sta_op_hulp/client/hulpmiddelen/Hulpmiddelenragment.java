package com.example.sta_op_hulp.client.hulpmiddelen;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.sta_op_hulp.Database.DataContract;
import com.example.sta_op_hulp.Database.DatabaseHelper;
import com.example.sta_op_hulp.Model.Hulpmiddel;
import com.example.sta_op_hulp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import static com.example.sta_op_hulp.ClientActivity.g;
import static com.example.sta_op_hulp.Database.DataContract.Activiteit.COLUMN_NAME_STOELNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.Hulpmiddel.COLUMN_NAME_GEBRUIKERSNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.Hulpmiddel.COLUMN_NAME_GRADEN;
import static com.example.sta_op_hulp.Database.DataContract.Hulpmiddel.TABLE_Hulpmiddelen;

public class Hulpmiddelenragment extends Fragment {

    private static Context context;
    View root;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private AdapterHulpmiddelen adapter;
    private TextView emptyView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_hulpmiddelen, container, false);
        floatingActionButton = root.findViewById(R.id.add_button);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        emptyView = (TextView) root.findViewById(R.id.empty_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterHulpmiddelen(getContext(), getHulpmiddelen(this));
        recyclerView.setAdapter(adapter);

        if (getHulpmiddelen(this).isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        adapter.setOnItemClickListener(new AdapterHulpmiddelen.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Hulpmiddel obj, int item) {
                openSettingHulpmiddel(obj);
            }
        });

        adapter.setOnItemClickLongListener(new AdapterHulpmiddelen.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Hulpmiddel obj, int item) {
                openSettingHulpmiddel(obj);
            }
        });

        adapter.setOnMoreButtonClickListener(new AdapterHulpmiddelen.OnMoreButtonClickListener() {
            @Override
            public void onItemClick(View view, Hulpmiddel obj, MenuItem item) {
                verwijderenHulpmiddel(obj);
            }

        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scancode();
            }
        });

        return root;
    }

    private void verwijderenHulpmiddel(Hulpmiddel obj) {

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
                        long result = db.delete(TABLE_Hulpmiddelen, "stoelnummer=?", new String[] {
                                String.valueOf(obj.getStoelnummer())
                        });
                        if (result == -1) {
                            Toast.makeText(getContext(), "Failed to Delete.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Hulpmiddel is verwijderd" + obj.getStoelnummer(), Toast.LENGTH_SHORT).show();

                            Navigation.findNavController(root).navigate(R.id.action_nav_hulpmiddelen_self);
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

    // open van barcodescanner
    private void scancode() {
        IntentIntegrator integrator = new IntentIntegrator(this.getActivity()).forSupportFragment(this);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scan QR van hulpmiddel");
        integrator.setBeepEnabled(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.initiateScan();
    }

    // return van scan
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                System.out.println("RESULT CODE" + result.getContents());
                checkHulpmiddelExists(result.getContents());
            }
        }
    }

    private void checkHulpmiddelExists(String contents) {
        DatabaseHelper dbH = new DatabaseHelper(this.getContext());
        SQLiteDatabase db = dbH.getReadableDatabase();
        try {

            String sql = "select * from " + TABLE_Hulpmiddelen + " Where  stoelnummer = " + contents + "";
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {

                int gebruikersnummer = cursor.getInt(cursor.getColumnIndex("gebruikersnummer"));
                if (g.getGebruikersnummer() == gebruikersnummer) {
                    Toast.makeText(getContext(), "Staat al in de lijst.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Dit hulpmiddel is door iemand anders tegevoegd.", Toast.LENGTH_SHORT).show();
                }
                while (cursor.moveToNext());
            } else {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_STOELNUMMER, contents);
                cv.put(DataContract.Gebruiker.COLUMN_NAME_GEBRUIKERSNUMMER, g.getGebruikersnummer());
                cv.put(COLUMN_NAME_GRADEN, 0);
                long result = db.insert(TABLE_Hulpmiddelen, null, cv);
                Navigation.findNavController(root).navigate(R.id.action_nav_hulpmiddelen_self);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public List < Hulpmiddel > getHulpmiddelen(Hulpmiddelenragment overviewVoorraadFragment) {
        List < Hulpmiddel > Hulpmiddelen = new ArrayList < > ();
        DatabaseHelper dbH = new DatabaseHelper(this.getContext());
        SQLiteDatabase db = dbH.getReadableDatabase();

        try {
            String sql = "select * from " + TABLE_Hulpmiddelen + " where " + COLUMN_NAME_GEBRUIKERSNUMMER + " = '" + g.getGebruikersnummer() + "'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    int stoelnummer = cursor.getInt(cursor.getColumnIndex("stoelnummer"));
                    int gebruikersnummer = cursor.getInt(cursor.getColumnIndex("gebruikersnummer"));
                    int graden = cursor.getInt(cursor.getColumnIndex("hoeveelheid"));
                    Hulpmiddel v = new Hulpmiddel(stoelnummer, gebruikersnummer, graden);
                    Hulpmiddelen.add(v);
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e);
        } finally {
            db.close();
        }
        return Hulpmiddelen;
    }

    private void openSettingHulpmiddel(Hulpmiddel obj) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("hulpmiddelen", obj);
        Navigation.findNavController(root).navigate(R.id.editHulpmiddelFragment, bundle);

    }
}
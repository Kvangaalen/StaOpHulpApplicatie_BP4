package com.example.sta_op_hulp.client.zorgpersoneel;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.sta_op_hulp.Database.DataContract;
import com.example.sta_op_hulp.Database.DatabaseHelper;
import com.example.sta_op_hulp.Model.ZorgpersoneelClient;
import com.example.sta_op_hulp.R;

import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_AKKOORD;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_CLIENTNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_PERSONEELSNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.TABLE_ZorgpersoneelClienten;

public class MangeRequestFragment extends Fragment {

    private int personeelnummer, clientnummer;
    private Button accept, reject;
    private View root;
    private TextView textViewVerzorgendenaam;
    public MangeRequestFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mange_request, container, false);
        ZorgpersoneelClient zc = getArguments().getParcelable("Product");

        textViewVerzorgendenaam = root.findViewById((R.id.TVnaamNurse));

        reject = root.findViewById(R.id.btnremove);
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectRequest();
            }
        });
        getZorgpersoneelnaam(zc);
        accept = root.findViewById(R.id.btnaccept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptRequest();
            }
        });

        return root;
    }

    private void getZorgpersoneelnaam(ZorgpersoneelClient zc) {
        DatabaseHelper dbH = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbH.getReadableDatabase();

        String sql = "select * from " + DataContract.Gebruiker.TABLE_Gebruikers + " Where  gebruikersnummer = '" + zc.getPersoneelnummer() + "'";
        Cursor cursor = db.rawQuery(sql, null);
        // ophalen van productnaam
        if (cursor.moveToFirst()) {
            do {
                String naam = cursor.getString(cursor.getColumnIndex("naam"));
                textViewVerzorgendenaam.setText(naam);

            } while (cursor.moveToNext());
        } else {
            textViewVerzorgendenaam.setText(String.valueOf("Gebruiker onbekend"));
        }
        cursor.close();
    }

    private void rejectRequest() {
        DatabaseHelper dbH = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbH.getReadableDatabase();
        ZorgpersoneelClient zc = getArguments().getParcelable("Product");
        try {
            long result = db.delete(TABLE_ZorgpersoneelClienten, "clientnummer=? AND persooneelnummer=?", new String[] {
                    String.valueOf(zc.getClientnummer()), String.valueOf(zc.getPersoneelnummer())
            });
            if (result == -1) {
                Toast.makeText(getContext(), "Failed to Delete.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "zorgpersoneel is verwijderd" + zc.getClientnummer() + zc.getPersoneelnummer(), Toast.LENGTH_SHORT).show();
                Navigation.findNavController(root).navigate(R.id.action_mangeRequestFragment_to_nav_zorgpersoneel);
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e);
        } finally {
            db.close();
        }
    }

    private void acceptRequest() {
        ZorgpersoneelClient zc = getArguments().getParcelable("Product");
        System.out.println("personeel" + zc.getPersoneelnummer());
        System.out.println("client" + zc.getClientnummer());

        DatabaseHelper dbH = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbH.getWritableDatabase();
        try {
            personeelnummer = zc.getPersoneelnummer();
            clientnummer = zc.getClientnummer();

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME_PERSONEELSNUMMER, personeelnummer);
            cv.put(COLUMN_NAME_CLIENTNUMMER, clientnummer);
            cv.put(COLUMN_NAME_AKKOORD, 1);
            long result = db.update(TABLE_ZorgpersoneelClienten, cv, "clientnummer=? AND persooneelnummer=?", new String[] {
                    String.valueOf(clientnummer), String.valueOf(personeelnummer)
            });

            if (result == -1) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Navigation.findNavController(root).navigate(R.id.action_mangeRequestFragment_to_nav_zorgpersoneel);
                Toast.makeText(getContext(), "Gelukt, toegevoegde aan mijn zorgpersoneel ", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException NFE) {
            Toast.makeText(getContext(), "Vul alle invoervelden in", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }

}
package com.example.sta_op_hulp.client.settings;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sta_op_hulp.Database.DataContract;
import com.example.sta_op_hulp.Database.DatabaseHelper;
import com.example.sta_op_hulp.R;

import static com.example.sta_op_hulp.ClientActivity.g;

public class ClientSettingFragment extends Fragment {
    private EditText naam;
    private Switch meldingenswitch;
    private EditText editTextNumber;
    private Button btnopslaan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settingclient, container, false);

        naam = view.findViewById(R.id.edittextnaam);
        naam.setText(g.getNaam());
        btnopslaan = view.findViewById(R.id.btnOpslaan);
        meldingenswitch = view.findViewById(R.id.switch1);
        editTextNumber = view.findViewById(R.id.editTextNumber);
        editTextNumber.setText(String.valueOf(g.getInactiefmelding()));
        naam.setEnabled(false);
        int meldingtime = g.getInactiefmelding();


        if (meldingtime == 0) {
            meldingenswitch.setChecked(false);
            editTextNumber.setEnabled(false);
        } else {
            meldingenswitch.setChecked(true);
        }

        btnopslaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String meldingtijd = editTextNumber.getText().toString();
                updateClientSettings(meldingtijd);

            }
        });

        meldingenswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editTextNumber.setEnabled(true);
                    System.out.println("checked");
                } else {
                    editTextNumber.setEnabled(false);
                    System.out.println("unchecked");
                    updateClientSettings("0");
                }
            }
        });

        editTextNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });

        return view;
    }

    private void updateClientSettings(String meldingtijd) {
        DatabaseHelper dbH = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbH.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataContract.Gebruiker.COLUMN_NAME_INACTIEFMELDING, meldingtijd);
        int meldingtijdint = Integer.parseInt(meldingtijd);
        try {
            // controleren of lijstnaam niet leeg is.
            if (!meldingtijd.isEmpty()) {
                // opslaan in productlijst table
                long result = db.update(DataContract.Gebruiker.TABLE_Gebruikers, cv, "gebruikersnummer=?", new String[] {
                        String.valueOf(g.getGebruikersnummer())
                });
                if (result > 0) {
                    g.setInactiefmelding(meldingtijdint);
                    Toast.makeText(getContext(), "De tijd is aangepast", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            editTextNumber.setText(String.valueOf(meldingtijd));
        }
    }

}
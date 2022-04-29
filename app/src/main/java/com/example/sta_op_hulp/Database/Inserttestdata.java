package com.example.sta_op_hulp.Database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import static com.example.sta_op_hulp.Database.DataContract.Activiteit.COLUMN_NAME_DATUM;
import static com.example.sta_op_hulp.Database.DataContract.Activiteit.COLUMN_NAME_STOELNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.Activiteit.COLUMN_NAME_TIJDOPSTAAN;
import static com.example.sta_op_hulp.Database.DataContract.Activiteit.COLUMN_NAME_TIJDZIT;
import static com.example.sta_op_hulp.Database.DataContract.Activiteit.TABLE_Activiteiten;
import static com.example.sta_op_hulp.Database.DataContract.Gebruiker.COLUMN_NAME_GEBOORTEDAUM;
import static com.example.sta_op_hulp.Database.DataContract.Gebruiker.COLUMN_NAME_GEBRUIKERSNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.Gebruiker.COLUMN_NAME_INACTIEFMELDING;
import static com.example.sta_op_hulp.Database.DataContract.Gebruiker.COLUMN_NAME_NAAM;
import static com.example.sta_op_hulp.Database.DataContract.Gebruiker.COLUMN_NAME_ROL;
import static com.example.sta_op_hulp.Database.DataContract.Gebruiker.TABLE_Gebruikers;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_AKKOORD;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_CLIENTNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.COLUMN_NAME_PERSONEELSNUMMER;
import static com.example.sta_op_hulp.Database.DataContract.ZorgpersoneelClient.TABLE_ZorgpersoneelClienten;

public class Inserttestdata {

    public Inserttestdata(View view) {
        insertClient(view);

        inserZorgpersoneel(view);

        inserZorgpersoneel(view);

        insertZorgpersoneelClient(view);

        instertActiviteit(view);

    }

    private void inserZorgpersoneel(View view) {
        DatabaseHelper dbH = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbH.getWritableDatabase();
        try {
            ContentValues cv1 = new ContentValues();
            cv1.put(COLUMN_NAME_GEBRUIKERSNUMMER, 2002);
            cv1.put(COLUMN_NAME_ROL, 1);
            cv1.put(COLUMN_NAME_NAAM, "Margje Morrenhof");
            cv1.put(COLUMN_NAME_GEBOORTEDAUM, "12-1-2021");
            cv1.put(COLUMN_NAME_INACTIEFMELDING, 0);
            long result = db.insert(TABLE_Gebruikers, null, cv1);

            ContentValues  cv2= new ContentValues();
            cv2.put(COLUMN_NAME_GEBRUIKERSNUMMER, 2003);
            cv2.put(COLUMN_NAME_ROL, 1);
            cv2.put(COLUMN_NAME_NAAM, "Bregje Jansen");
            cv2.put(COLUMN_NAME_GEBOORTEDAUM, "12-1-2021");
            cv2.put(COLUMN_NAME_INACTIEFMELDING, 0);
            result = db.insert(TABLE_Gebruikers, null, cv2);

            ContentValues cv3 = new ContentValues();
            cv3.put(COLUMN_NAME_GEBRUIKERSNUMMER, 2004);
            cv3.put(COLUMN_NAME_ROL, 1);
            cv3.put(COLUMN_NAME_NAAM, "Lieke de Vries");
            cv3.put(COLUMN_NAME_GEBOORTEDAUM, "12-1-2021");
            cv3.put(COLUMN_NAME_INACTIEFMELDING, 0);
            result = db.insert(TABLE_Gebruikers, null, cv3);

            ContentValues cv4 = new ContentValues();
            cv4.put(COLUMN_NAME_GEBRUIKERSNUMMER, 2005);
            cv4.put(COLUMN_NAME_ROL, 1);
            cv4.put(COLUMN_NAME_NAAM, "Mark Jacobs");
            cv4.put(COLUMN_NAME_GEBOORTEDAUM, "12-1-2021");
            cv4.put(COLUMN_NAME_INACTIEFMELDING, 0);
            result = db.insert(TABLE_Gebruikers, null, cv4);


            if (result > 0) {
                System.out.println("inserZorgpersoneel gelukt");
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    private void instertActiviteit(View view) {

        DatabaseHelper dbH = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbH.getWritableDatabase();
        try{
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME_GEBRUIKERSNUMMER, 1002);
            cv.put(COLUMN_NAME_STOELNUMMER, 120);
            cv.put(COLUMN_NAME_TIJDZIT, "12:40");
            cv.put(COLUMN_NAME_TIJDOPSTAAN, "14:12");
            cv.put(COLUMN_NAME_DATUM, "15-06-2021");
            long result = db.insert(TABLE_Activiteiten, null, cv);

            ContentValues cv2 = new ContentValues();
            cv2.put(COLUMN_NAME_GEBRUIKERSNUMMER, 1002);
            cv2.put(COLUMN_NAME_STOELNUMMER, 120);
            cv2.put(COLUMN_NAME_TIJDZIT, "10:33");
            cv2.put(COLUMN_NAME_TIJDOPSTAAN, "11:38");
            cv2.put(COLUMN_NAME_DATUM, "15-06-2021");
            result = db.insert(TABLE_Activiteiten, null, cv2);

            ContentValues cv3 = new ContentValues();
            cv3.put(COLUMN_NAME_GEBRUIKERSNUMMER, 1002);
            cv3.put(COLUMN_NAME_STOELNUMMER, 120);
            cv3.put(COLUMN_NAME_TIJDZIT, "09:20");
            cv3.put(COLUMN_NAME_TIJDOPSTAAN, "10:06");
            cv3.put(COLUMN_NAME_DATUM, "15-06-2021");
            result = db.insert(TABLE_Activiteiten, null, cv3);
            // long result = db.delete(TABLE_Gebruikers, "gebruikersnummer=?", new String[]{String.valueOf(pincode)});
            if (result > 0) {
                System.out.println("instertActiviteit gelukt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }

    private void insertZorgpersoneelClient(View view) {
        DatabaseHelper dbH = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbH.getWritableDatabase();
        try {
            ContentValues cv1 = new ContentValues();
            cv1.put(COLUMN_NAME_AKKOORD, 1);
            cv1.put(COLUMN_NAME_PERSONEELSNUMMER, 2002);
            cv1.put(COLUMN_NAME_CLIENTNUMMER, 1002);
            long result = db.insert(TABLE_ZorgpersoneelClienten, null, cv1);

            ContentValues cv2 = new ContentValues();
            cv2.put(COLUMN_NAME_AKKOORD, 1);
            cv2.put(COLUMN_NAME_PERSONEELSNUMMER, 2003);
            cv2.put(COLUMN_NAME_CLIENTNUMMER, 1002);
            result = db.insert(TABLE_ZorgpersoneelClienten, null, cv2);

            ContentValues cv3 = new ContentValues();
            cv3.put(COLUMN_NAME_AKKOORD, 0);
            cv3.put(COLUMN_NAME_PERSONEELSNUMMER, 2004);
            cv3.put(COLUMN_NAME_CLIENTNUMMER, 1002);
            result = db.insert(TABLE_ZorgpersoneelClienten, null, cv3);

            ContentValues cv4 = new ContentValues();
            cv4.put(COLUMN_NAME_AKKOORD, 0);
            cv4.put(COLUMN_NAME_PERSONEELSNUMMER, 2005);
            cv4.put(COLUMN_NAME_CLIENTNUMMER, 1002);

            result = db.insert(TABLE_ZorgpersoneelClienten, null, cv4);

            ContentValues cv5 = new ContentValues();
            cv5.put(COLUMN_NAME_AKKOORD, 0);
            cv5.put(COLUMN_NAME_PERSONEELSNUMMER, 2002);
            cv5.put(COLUMN_NAME_CLIENTNUMMER, 1003);
            result = db.insert(TABLE_ZorgpersoneelClienten, null, cv5);
            if (result > 0) {
                System.out.println("insertZorgpersoneelClient gelukt");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    private void insertClient(View view) {
        DatabaseHelper dbH = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbH.getWritableDatabase();
        try
        {
            int pincode = 1002;
            String naam = "Tom Sengers";
            int melding = 30;
            int rol = 0;
            String datum = "12-1-2021";

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME_GEBRUIKERSNUMMER, pincode);
            cv.put(COLUMN_NAME_ROL, rol);
            cv.put(COLUMN_NAME_NAAM, naam);
            cv.put(COLUMN_NAME_GEBOORTEDAUM, datum);
            cv.put(COLUMN_NAME_INACTIEFMELDING, melding);
            long result = db.insert(TABLE_Gebruikers, null, cv);

            if (result > 0) {
                 pincode = 1003;
                 naam = "Ria de Groot";
                 melding = 30;
                 rol = 0;
                 datum = "12-1-2021";

                 cv = new ContentValues();
                cv.put(COLUMN_NAME_GEBRUIKERSNUMMER, pincode);
                cv.put(COLUMN_NAME_ROL, rol);
                cv.put(COLUMN_NAME_NAAM, naam);
                cv.put(COLUMN_NAME_GEBOORTEDAUM, datum);
                cv.put(COLUMN_NAME_INACTIEFMELDING, melding);
                result = db.insert(TABLE_Gebruikers, null, cv);
                if (result > 0) {
                    System.out.println("insertClient gelukt");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.close();
        }
    }

}

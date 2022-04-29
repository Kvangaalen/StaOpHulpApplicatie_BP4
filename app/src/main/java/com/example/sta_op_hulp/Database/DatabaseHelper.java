package com.example.sta_op_hulp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Datastaop.db";
    private Context context;

    private static final String SQL_CREATE_GEBRUIKER = "create table " + DataContract.Gebruiker.TABLE_Gebruikers + " ("
            + DataContract.Gebruiker.COLUMN_NAME_GEBRUIKERSNUMMER + " text, "
            + DataContract.Gebruiker.COLUMN_NAME_ROL + " text, "
            + DataContract.Gebruiker.COLUMN_NAME_GEBOORTEDAUM + " text, "
            + DataContract.Gebruiker.COLUMN_NAME_NAAM + " text, "
            + DataContract.Gebruiker.COLUMN_NAME_INACTIEFMELDING + " text)";

    private static final String SQL_CREATE_ACTIVITEIT = "create table " + DataContract.Activiteit.TABLE_Activiteiten + " ("
            + DataContract.Activiteit.COLUMN_NAME_GEBRUIKERSNUMMER + " text, "
            + DataContract.Activiteit.COLUMN_NAME_STOELNUMMER+ " text, "
            + DataContract.Activiteit.COLUMN_NAME_TIJDZIT+ " text, "
            + DataContract.Activiteit.COLUMN_NAME_TIJDOPSTAAN + " text, "
            + DataContract.Activiteit.COLUMN_NAME_DATUM + " text)";

    private static final String SQL_CREATE_HULPMIDDEL= "create table " + DataContract.Hulpmiddel.TABLE_Hulpmiddelen + " ("
            + DataContract.Hulpmiddel.COLUMN_NAME_STOELNUMMER+ " text, "
            + DataContract.Hulpmiddel.COLUMN_NAME_GEBRUIKERSNUMMER+ " text, "
            + DataContract.Hulpmiddel.COLUMN_NAME_GRADEN + " text)";

    private static final String SQL_CREATE_ZorgpersoneelClient= "create table " + DataContract.ZorgpersoneelClient.TABLE_ZorgpersoneelClienten + " ("
            + DataContract.ZorgpersoneelClient.COLUMN_NAME_PERSONEELSNUMMER+ " text, "
            + DataContract.ZorgpersoneelClient.COLUMN_NAME_CLIENTNUMMER+ " text, "
            + DataContract.ZorgpersoneelClient.COLUMN_NAME_AKKOORD + " text)";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_GEBRUIKER);
        db.execSQL(SQL_CREATE_ACTIVITEIT);
        db.execSQL(SQL_CREATE_HULPMIDDEL);
        db.execSQL(SQL_CREATE_ZorgpersoneelClient);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

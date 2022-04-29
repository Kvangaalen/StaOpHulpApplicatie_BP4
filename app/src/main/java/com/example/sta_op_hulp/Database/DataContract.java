package com.example.sta_op_hulp.Database;

import android.provider.BaseColumns;

public class DataContract {

    private DataContract(){ }

    public static class Gebruiker implements BaseColumns {
        public static final String TABLE_Gebruikers = "gebruiker";
        public static final String COLUMN_NAME_GEBRUIKERSNUMMER = "gebruikersnummer";
        public static final String COLUMN_NAME_ROL = "rol";
        public static final String COLUMN_NAME_GEBOORTEDAUM = "geboortedatum";
        public static final String COLUMN_NAME_NAAM = "naam";
        public static final String COLUMN_NAME_INACTIEFMELDING = "inactiefmelding";
    }

    public static class Hulpmiddel implements BaseColumns {
        public static final String TABLE_Hulpmiddelen = "hulpmiddelen";
        public static final String COLUMN_NAME_STOELNUMMER = "stoelnummer";
        public static final String COLUMN_NAME_GEBRUIKERSNUMMER = "gebruikersnummer";
        public static final String COLUMN_NAME_GRADEN = "hoeveelheid";
    }

    public static class Activiteit implements BaseColumns {
        public static final String TABLE_Activiteiten = "activiteiten";
        public static final String COLUMN_NAME_STOELNUMMER = "stoelnummer";
        public static final String COLUMN_NAME_GEBRUIKERSNUMMER = "gebruikersnummer";
        public static final String COLUMN_NAME_TIJDZIT = "tijdzit";
        public static final String COLUMN_NAME_TIJDOPSTAAN = "tijdopstaan";
        public static final String COLUMN_NAME_DATUM = "datum";
    }
    public static class ZorgpersoneelClient implements BaseColumns {
        public static final String TABLE_ZorgpersoneelClienten = "zorgpersooneelclient";
        public static final String COLUMN_NAME_PERSONEELSNUMMER = "persooneelnummer";
        public static final String COLUMN_NAME_CLIENTNUMMER = "clientnummer";
        public static final String COLUMN_NAME_AKKOORD = "akkoord";
    }


}

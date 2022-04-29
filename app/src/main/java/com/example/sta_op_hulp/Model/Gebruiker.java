package com.example.sta_op_hulp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Gebruiker implements Parcelable {
    private int gebruikersnummer, rol, inactiefmelding;
    private String naam, geboortedatum;

    public Gebruiker(int gebruikersnummer, int rol, int inactiefmelding, String naam, String geboortedatum){
        this.gebruikersnummer = gebruikersnummer;
        this.rol = rol;
        this.inactiefmelding = inactiefmelding;
        this.naam = naam;
        this.geboortedatum = geboortedatum;

    }

    protected Gebruiker(Parcel in) {
        gebruikersnummer = in.readInt();
        rol = in.readInt();
        inactiefmelding = in.readInt();
        naam = in.readString();
        geboortedatum = in.readString();
    }

    public static final Creator<Gebruiker> CREATOR = new Creator<Gebruiker>() {
        @Override
        public Gebruiker createFromParcel(Parcel in) {
            return new Gebruiker(in);
        }

        @Override
        public Gebruiker[] newArray(int size) {
            return new Gebruiker[size];
        }
    };

    public int getGebruikersnummer() {
        return gebruikersnummer;
    }

    public void setGebruikersnummer(int gebruikersnummer) {
        this.gebruikersnummer = gebruikersnummer;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(String geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public int getInactiefmelding() {
        return inactiefmelding;
    }

    public void setInactiefmelding(int inactiefmelding) {
        this.inactiefmelding = inactiefmelding;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(gebruikersnummer);
        dest.writeInt(rol);
        dest.writeInt(inactiefmelding);
        dest.writeString(naam);
        dest.writeString(geboortedatum);
    }
}

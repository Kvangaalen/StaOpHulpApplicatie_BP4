package com.example.sta_op_hulp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Hulpmiddel extends ArrayList<String> implements Parcelable {
    private int stoelnummer, gebruikersnummer, graden;

    public Hulpmiddel(int stoelnummer, int gebruikersnummer, int graden){
        this.stoelnummer = stoelnummer;
        this.gebruikersnummer = gebruikersnummer;
        this.graden = graden;

    }

    protected Hulpmiddel(Parcel in) {
        stoelnummer = in.readInt();
        gebruikersnummer = in.readInt();
        graden = in.readInt();
    }

    public static final Creator<Hulpmiddel> CREATOR = new Creator<Hulpmiddel>() {
        @Override
        public Hulpmiddel createFromParcel(Parcel in) {
            return new Hulpmiddel(in);
        }

        @Override
        public Hulpmiddel[] newArray(int size) {
            return new Hulpmiddel[size];
        }
    };

    public int getStoelnummer() {
        return stoelnummer;
    }

    public void setStoelnummer(int stoelnummer) {
        this.stoelnummer = stoelnummer;
    }

    public int getGebruikersnummer() {
        return gebruikersnummer;
    }

    public void setGebruikersnummer(int gebruikersnummer) {
        this.gebruikersnummer = gebruikersnummer;
    }

    public int getGraden() {
        return graden;
    }

    public void setGraden(int graden) {
        this.graden = graden;
    }

    @NonNull
    @NotNull
    @Override
    public Stream<String> stream() {
        return null;
    }

    @NonNull
    @NotNull
    @Override
    public Stream<String> parallelStream() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stoelnummer);
        dest.writeInt(gebruikersnummer);
        dest.writeInt(graden);
    }
}

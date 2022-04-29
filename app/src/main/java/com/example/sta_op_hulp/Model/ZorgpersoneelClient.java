package com.example.sta_op_hulp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.stream.Stream;

public class ZorgpersoneelClient extends ArrayList<String> implements Parcelable {
    private int personeelnummer, clientnummer;
    private int akkoord;

    public ZorgpersoneelClient(int personeelnummer, int clientnummer, int akkoord){
        this.personeelnummer = personeelnummer;
        this.clientnummer = clientnummer;
        this.akkoord = akkoord;

    }

    protected ZorgpersoneelClient(Parcel in) {
        personeelnummer = in.readInt();
        clientnummer = in.readInt();
        akkoord = in.readInt();
    }

    public static final Creator<ZorgpersoneelClient> CREATOR = new Creator<ZorgpersoneelClient>() {
        @Override
        public ZorgpersoneelClient createFromParcel(Parcel in) {
            return new ZorgpersoneelClient(in);
        }

        @Override
        public ZorgpersoneelClient[] newArray(int size) {
            return new ZorgpersoneelClient[size];
        }
    };

    public int getPersoneelnummer() {
        return personeelnummer;
    }

    public void setPersoneelnummer(int personeelnummer) {
        this.personeelnummer = personeelnummer;
    }

    public int getClientnummer() {
        return clientnummer;
    }

    public void setClientnummer(int clientnummer) {
        this.clientnummer = clientnummer;
    }

    public int isAkkoord() {
        return akkoord;
    }

    public void setAkkoord(int akkoord) {
        this.akkoord = akkoord;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(personeelnummer);
        dest.writeInt(clientnummer);
        dest.writeInt(akkoord);
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
}

package com.asana.rebel.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by Amr ElMadah on 10/31/17.
 */
public class Fact implements Parcelable {

    @Expose
    private String fact;

    public String getFact() {
        return fact;
    }

    public Fact() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fact);
    }

    protected Fact(Parcel in) {
        this.fact = in.readString();
    }

    public static final Creator<Fact> CREATOR = new Creator<Fact>() {
        @Override
        public Fact createFromParcel(Parcel source) {
            return new Fact(source);
        }

        @Override
        public Fact[] newArray(int size) {
            return new Fact[size];
        }
    };
}

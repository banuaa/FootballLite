package com.tekkom.footballlite.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Team implements Parcelable {
    @SerializedName("idTeam")
    private String idTeam;
    @SerializedName("strTeam")
    private String strTeam;
    @SerializedName("strStadium")
    private String strStadium;
    @SerializedName("strDescriptionEN")
    private String strDescriptionEN;
    @SerializedName("strTeamBadge")
    private String strTeamBadge;

    public void setIdTeam(String idTeam) {
        this.idTeam = idTeam;
    }

    public void setStrTeam(String strTeam) {
        this.strTeam = strTeam;
    }

    public void setStrStadium(String strStadium) {
        this.strStadium = strStadium;
    }

    public void setStrDescriptionEN(String strDescriptionEN) { this.strDescriptionEN = strDescriptionEN; }

    public void setStrTeamBadge(String strTeamBadge) {
        this.strTeamBadge = strTeamBadge;
    }

    public Team() {
    }

    public Team(String idTeam, String strTeam, String strStadium, String strDescriptionEN, String strTeamBadge) {
        this.idTeam = idTeam;
        this.strTeam = strTeam;
        this.strStadium = strStadium;
        this.strDescriptionEN = strDescriptionEN;
        this.strTeamBadge = strTeamBadge;
    }

    public String getIdTeam() { return idTeam; }

    public String getStrTeam() { return strTeam; }

    public String getStrStadium() { return strStadium; }

    public String getStrDescriptionEN() { return strDescriptionEN; }

    public  String getStrTeamBadge() { return strTeamBadge; }

    @Override
    public int describeContents(){ return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idTeam);
        dest.writeString(strTeam);
        dest.writeString(strStadium);
        dest.writeString(strDescriptionEN);
        dest.writeString(strTeamBadge);
    }

    protected Team(Parcel in) {
        this.idTeam = in.readString();
        this.strTeam = in.readString();
        this.strStadium = in.readString();
        this.strDescriptionEN = in.readString();
        this.strTeamBadge = in.readString();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel source) { return new Team(source); }

        @Override
        public Team[] newArray(int size) { return new Team[size]; }
    };
}

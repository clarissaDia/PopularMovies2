package com.example.android.popularmovies2.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailers implements Parcelable {

    private int mMovieId;
    private String mTrailerName;
    private String mTrailerKey;
    private String mTrailerSite;
    private String mTrailerUrl;


    public Trailers (int movieId,String trailerName, String trailerKey, String trailerSite, String trailerUrl){
        this.mMovieId = movieId;
        this.mTrailerName = trailerName;
        this.mTrailerKey = trailerKey;
        this.mTrailerSite = trailerSite;
        this.mTrailerUrl = trailerUrl;

    }
private Trailers (Parcel parcel){
        mMovieId = parcel.readInt();
        mTrailerName = parcel.readString();
        mTrailerKey = parcel.readString();
        mTrailerSite = parcel.readString();
        mTrailerUrl = parcel.readString();

}
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mMovieId);
        parcel.writeString(mTrailerName);
parcel.writeString(mTrailerKey);
parcel.writeString(mTrailerSite);
parcel.writeString(mTrailerUrl);

    }

    public static final Parcelable.Creator<Trailers> CREATOR = new Parcelable.Creator<Trailers>(){

        @Override
        public Trailers createFromParcel(Parcel parcel) {
            return new Trailers(parcel);
        }

        @Override
        public Trailers[] newArray(int i) {
            return new Trailers[i];
        }
    };
public int getMovieId (){
    return mMovieId;
}
    public String getTrailerName (){
        return mTrailerName;
    }

    public String getTrailerKey (){
        return mTrailerKey;
    }

    public String getTrailerSite (){
        return mTrailerSite;
    }

    public String getTrailerUrl (){
        return mTrailerUrl;
    }

}

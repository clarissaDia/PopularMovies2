package com.example.android.popularmovies2.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "movies_table")
public class Movies implements Parcelable {

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel parcel) {
            return new Movies(parcel);
        }

        @Override
        public Movies[] newArray(int i) {
            return new Movies[i];
        }
    };
    @ColumnInfo(name = "movie_id")
private  int mMovieId;
    @ColumnInfo(name = "title")
    private String mTitle;
    @ColumnInfo(name = "synopsis")
    private String mSynopsis;
    @ColumnInfo(name = "release_date")
    private String mReleaseDate;
    @ColumnInfo (name = "average_vote")
    private String mAverageVote;
    @ColumnInfo(name = "image")
    private String mImage;
    @PrimaryKey(autoGenerate = true)
    private int dataBaseId;

    @Ignore
    public Movies(String title, String synopsis, String releaseDate, String averageVote, String image) {

        this.mTitle = title;
        this.mSynopsis = synopsis;
        this.mReleaseDate = releaseDate;
        this.mAverageVote = averageVote;
        this.mImage = image;


    }

    public Movies(String title, String synopsis, String releaseDate, String averageVote, String image, int movieId) {

        this.mTitle = title;
        this.mSynopsis = synopsis;
        this.mReleaseDate = releaseDate;
        this.mAverageVote = averageVote;
        this.mImage = image;
        this.mMovieId = movieId;

    }

    private Movies(Parcel parcel) {
        mTitle = parcel.readString();
        mSynopsis = parcel.readString();
        mReleaseDate = parcel.readString();
        mAverageVote = parcel.readString();
        mImage = parcel.readString();
        mMovieId = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mTitle);
        parcel.writeString(mSynopsis);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mAverageVote);
        parcel.writeString(mImage);
        parcel.writeInt(mMovieId);
    }

    public int getDataBaseId (){
        return dataBaseId;
    }
public void setDataBaseId(int dataBaseId){
        this.dataBaseId = dataBaseId;

}
    public String getTitle() {
        return mTitle;
    }

    public void setTile(String title) {
        this.mTitle = title;
    }

    public String getSynopsis() {

        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        this.mSynopsis = synopsis;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public String getAverageVote() {
        return mAverageVote;
    }

    public void setAverageVote(String averageVote) {
        this.mAverageVote = averageVote;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }

    public  int getMovieId (){
        return mMovieId;
    }
    public void setMovieId (int movieId){
        this.mMovieId = movieId;
    }
}
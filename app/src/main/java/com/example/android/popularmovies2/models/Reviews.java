package com.example.android.popularmovies2.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Reviews implements Parcelable {

    private final String mAuthor;
    private final String mContent;

    public Reviews (String author, String content){
        mAuthor = author;
        mContent = content;
    }

    private Reviews (Parcel parcel){
        mAuthor = parcel.readString();
        mContent = parcel.readString();

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(mAuthor);
        parcel.writeString(mContent);

    }

    public static final Parcelable.Creator<Reviews> CREATOR = new Parcelable.Creator<Reviews>(){

        @Override
        public Reviews createFromParcel(Parcel parcel) {
            return new Reviews(parcel);
        }

        @Override
        public Reviews[] newArray(int i) {
            return new Reviews[i];
        }
    };

public String getAuthor (){
    return mAuthor;
}
public String getContent (){
    return mContent;
}
}

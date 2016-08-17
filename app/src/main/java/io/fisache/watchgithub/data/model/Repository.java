package io.fisache.watchgithub.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public class Repository implements Parcelable {
    public static final Parcelable.Creator<Repository> CREATOR = new Parcelable.Creator<Repository>() {
        public Repository createFromParcel(Parcel source) {
            return new Repository(source);
        }

        public Repository[] newArray(int size) {
            return new Repository[size];
        }
    };

    public long id;
    public String name;
    public String url;
    public int star_count;
    public int fork_count;
    public boolean forked;
    @Nullable  public String origin_url;

    public Repository() {

    }

    protected Repository(Parcel in) {
        id = in.readLong();
        name = in.readString();
        url = in.readString();
        star_count = in.readInt();
        fork_count = in.readInt();
        forked = (in.readInt() == 1 ? true : false);
        origin_url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeInt(star_count);
        dest.writeInt(fork_count);
        dest.writeInt((forked ? 1 : 0));
    }
}

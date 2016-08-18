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
    public String html_url;
    public String desc;
    public int star_count;
    public int fork_count;
    public boolean fork;

    public Repository() {

    }

    protected Repository(Parcel in) {
        id = in.readLong();
        name = in.readString();
        html_url = in.readString();
        desc = in.readString();
        star_count = in.readInt();
        fork_count = in.readInt();
        fork = (in.readInt() == 1 ? true : false);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(html_url);
        dest.writeString(desc);
        dest.writeInt(star_count);
        dest.writeInt(fork_count);
        dest.writeInt((fork ? 1 : 0));
    }
}

package io.fisache.watchgithub.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public class User implements Parcelable {
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    public String login;
    public long id;
    public String avartar_url;
    public String email;
    @Nullable public String desc;

    public User() {

    }

    protected User(Parcel in) {
        this.login = in.readString();
        this.id = in.readLong();
        this.avartar_url = in.readString();
        this.email = in.readString();
        this.desc = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeLong(id);
        dest.writeString(avartar_url);
        dest.writeString(email);
        dest.writeString(desc);
    }
}

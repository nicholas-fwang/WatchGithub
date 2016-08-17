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
    public long id;
    public String login;
    public String name;
    public String avatar_url;
    public String email;
    @Nullable public String desc;

    public User(long id, String login, String name, String avatar_url, String email, String desc) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.avatar_url = avatar_url;
        this.email = email;
        this.desc = desc;
    }

    protected User(Parcel in) {
        this.id = in.readLong();
        this.login = in.readString();
        this.name = in.readString();
        this.avatar_url = in.readString();
        this.email = in.readString();
        this.desc = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(login);
        dest.writeString(name);
        dest.writeString(avatar_url);
        dest.writeString(email);
        dest.writeString(desc);
    }
}

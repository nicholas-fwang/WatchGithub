package io.fisache.watchgithub.data.sqlbrite;

import android.provider.BaseColumns;

public class UsersPersistenceContract {

    public static String[] projection = {
            UserEntry.COLUMN_NAME_ENTRY_ID,
            UserEntry.COLUMN_NAME_LOGIN,
            UserEntry.COLUMN_NAME_NAME,
            UserEntry.COLUMN_NAME_AVATAR_URL,
            UserEntry.COLUMN_NAME_EMAIL,
            UserEntry.COLUMN_NAME_FOLLOWERS,
            UserEntry.COLUMN_NAME_TYPE,
            UserEntry.COLUMN_NAME_DESC
    };

    public UsersPersistenceContract() {

    }

    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_LOGIN = "login";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AVATAR_URL = "avatar_url";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_FOLLOWERS = "followers";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_DESC = "desc";
    }

}

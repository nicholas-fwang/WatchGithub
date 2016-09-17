package io.fisache.watchgithub.service.sqlbrite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsersDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Users.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String LONG_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UsersPersistenceContract.UserEntry.TABLE_NAME + " (" +
                    UsersPersistenceContract.UserEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    UsersPersistenceContract.UserEntry.COLUMN_NAME_ENTRY_ID + LONG_TYPE + COMMA_SEP +
                    UsersPersistenceContract.UserEntry.COLUMN_NAME_LOGIN + TEXT_TYPE + COMMA_SEP +
                    UsersPersistenceContract.UserEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    UsersPersistenceContract.UserEntry.COLUMN_NAME_AVATAR_URL + TEXT_TYPE + COMMA_SEP +
                    UsersPersistenceContract.UserEntry.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    UsersPersistenceContract.UserEntry.COLUMN_NAME_FOLLOWERS + INTEGER_TYPE + COMMA_SEP +
                    UsersPersistenceContract.UserEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    UsersPersistenceContract.UserEntry.COLUMN_NAME_DESC + TEXT_TYPE +
                    " )";

    public UsersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}

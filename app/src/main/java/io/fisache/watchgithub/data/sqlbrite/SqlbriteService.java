package io.fisache.watchgithub.data.sqlbrite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;


import io.fisache.watchgithub.data.BaseService;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.data.sqlbrite.UsersPersistenceContract.UserEntry;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SqlbriteService implements BaseService {


    private Context context;
    private Func1<Cursor, User> userMapperFunction;
    private final BriteDatabase databaseHelper;

    public SqlbriteService(Context context, UsersDbHelper usersDbHelper, Func1<Cursor, User> userMapperFunction) {
        this.context = context;
        this.userMapperFunction = userMapperFunction;

        SqlBrite sqlBrite = SqlBrite.create();
        databaseHelper = sqlBrite.wrapDatabaseHelper(usersDbHelper, Schedulers.io());
    }

    @Override
    public Observable<List<User>> getUsers() {
        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", UsersPersistenceContract.projection),
                UserEntry.TABLE_NAME);
        return databaseHelper.createQuery(UserEntry.TABLE_NAME, sql)
                .mapToList(userMapperFunction);
    }

    @Override
    public Observable<User> getUser(long userId) {
        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?",
                TextUtils.join(",", UsersPersistenceContract.projection), UserEntry.TABLE_NAME, UserEntry.COLUMN_NAME_ENTRY_ID);
        return databaseHelper.createQuery(UserEntry.TABLE_NAME, sql, Long.toString(userId))
                .mapToOneOrDefault(userMapperFunction, null);
    }

    public Observable<List<User>> searchUsersWithPattern(String pattern) {
        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE \'%%" + pattern + "%%\'",
                TextUtils.join(",", UsersPersistenceContract.projection), UserEntry.TABLE_NAME, UserEntry.COLUMN_NAME_LOGIN);
        return databaseHelper.createQuery(UserEntry.TABLE_NAME, sql)
                .mapToList(userMapperFunction);
    }

    @Override
    public void saveUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_ENTRY_ID, user.id);
        values.put(UserEntry.COLUMN_NAME_LOGIN, user.login);
        values.put(UserEntry.COLUMN_NAME_NAME, user.name);
        values.put(UserEntry.COLUMN_NAME_AVATAR_URL, user.avatar_url);
        values.put(UserEntry.COLUMN_NAME_EMAIL, user.email);
        values.put(UserEntry.COLUMN_NAME_FOLLOWERS, user.followers);
        values.put(UserEntry.COLUMN_NAME_TYPE, user.type);
        values.put(UserEntry.COLUMN_NAME_DESC, user.desc);
        databaseHelper.insert(UserEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_NAME, user.name);
        values.put(UserEntry.COLUMN_NAME_AVATAR_URL, user.avatar_url);
        values.put(UserEntry.COLUMN_NAME_EMAIL, user.email);
        values.put(UserEntry.COLUMN_NAME_FOLLOWERS, user.followers);
        values.put(UserEntry.COLUMN_NAME_TYPE, user.type);

        String selection = UserEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {Long.toString(user.id)};
        databaseHelper.update(UserEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    @Override
    public void updateDesc(User user) {
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_DESC, user.desc);

        String selection = UserEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {Long.toString(user.id)};
        databaseHelper.update(UserEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    @Override
    public void deleteAllUser() {
        databaseHelper.delete(UserEntry.TABLE_NAME, null);
    }

    @Override
    public void deleteUser(long userId) {
        String selection = UserEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {Long.toString(userId)};
        databaseHelper.delete(UserEntry.TABLE_NAME, selection, selectionArgs);
    }
}

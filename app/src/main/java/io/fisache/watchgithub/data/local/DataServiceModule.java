package io.fisache.watchgithub.data.local;

import android.app.Application;
import android.database.Cursor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.data.cache.CacheService;
import io.fisache.watchgithub.data.model.User;
import io.fisache.watchgithub.data.sqlbrite.SqlbriteService;
import io.fisache.watchgithub.data.sqlbrite.UsersDbHelper;
import rx.functions.Func1;
import io.fisache.watchgithub.data.sqlbrite.UsersPersistenceContract.UserEntry;

@Module
public class DataServiceModule {

    @Provides
    @Singleton
    CacheService provideCacheService(Application application) {
        return new CacheService(application);
    }

    @Provides
    @Singleton
    UsersDbHelper provideUsersDbHelper(Application application) {
        return new UsersDbHelper(application);
    }

    @Provides
    @Singleton
    Func1<Cursor, User> provideUserMapperFunction() {
        return new Func1<Cursor, User>() {
            @Override
            public User call(Cursor c) {
                long id = c.getInt(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_ENTRY_ID));
                String login = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_LOGIN));
                String name = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_NAME));
                String avatar_url = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_AVATAR_URL));
                String email = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_EMAIL));
                int followers = c.getInt(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_FOLLOWERS));
                String type = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_TYPE));
                String desc = c.getString(c.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_DESC));
                return new User(id, login, name, avatar_url, email, followers, type, desc);
            }
        };
    }

    @Provides
    @Singleton
    SqlbriteService provideSqlbriteService(Application application, UsersDbHelper usersDbHelper,Func1<Cursor, User> userMapperFunction) {
        return new SqlbriteService(application, usersDbHelper, userMapperFunction);
    }
}

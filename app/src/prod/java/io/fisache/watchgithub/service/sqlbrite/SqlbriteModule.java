package io.fisache.watchgithub.service.sqlbrite;

import android.app.Application;
import android.database.Cursor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.fisache.watchgithub.data.BaseService;
import io.fisache.watchgithub.data.cache.CacheService;
import io.fisache.watchgithub.data.model.User;
import rx.functions.Func1;
import io.fisache.watchgithub.service.sqlbrite.UsersPersistenceContract.UserEntry;

@Module
public class SqlbriteModule {

    @Provides
    @Singleton
    CacheService provideCacheService() {
        return new CacheService();
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
    BaseService provideSqlbriteService(Application application, UsersDbHelper usersDbHelper, Func1<Cursor, User> userMapperFunction) {
        return new SqlbriteService(application, usersDbHelper, userMapperFunction);
    }
}

package com.android.template.injection.application.module;

import androidx.room.Room;
import android.content.Context;

import com.android.template.injection.qualifier.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import template.android.com.data.dao.mapper.DbMapper;
import template.android.com.data.dao.mapper.DbMapperImpl;
import template.android.com.data.dao.ExampleDao;
import template.android.com.data.dao.database.AppDatabase;
import template.android.com.domain.utils.string.StringUtils;

@Module
public final class DbModule {

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@ForApplication final Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "example-db")
                   .build();
    }

    @Provides
    @Singleton
    ExampleDao provideExampleDao(final AppDatabase appDatabase) {
        return appDatabase.exampleDao();
    }

    @Provides
    @Singleton
    DbMapper provideDbMapper(final StringUtils stringUtils) {
        return new DbMapperImpl(stringUtils);
    }

    public interface Exposes {

        ExampleDao exampleDao();
    }
}

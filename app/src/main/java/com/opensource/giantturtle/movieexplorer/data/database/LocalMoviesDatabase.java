package com.opensource.giantturtle.movieexplorer.data.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

//Define each table here as entities
@Database(entities = {ModelSavedMovie.class,ModelCachedMovie.class}, version = 1)
public abstract class LocalMoviesDatabase extends RoomDatabase {

    private static LocalMoviesDatabase instance;

    public abstract DaoBookmarkedMovies savedProjectsDao();

    public abstract DaoCachedMovies cachedProjectsDao();

    public static synchronized LocalMoviesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    LocalMoviesDatabase.class, "movies_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };




}

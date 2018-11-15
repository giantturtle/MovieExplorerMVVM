package com.opensource.giantturtle.movieexplorer.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoCachedMovies {

    @Insert
    void insert(ModelCachedMovie cachedGitHubRepo);

    @Insert
    void saveToCache(List<ModelCachedMovie> listOfGitHubProjects);

    @Update
    void update(ModelCachedMovie cachedGitHubRepo);

    @Delete
    void delete(ModelCachedMovie cachedGitHubRepo);

    @Query("DELETE FROM cashed_movies")
    void deleteAllCachedRepos();

    @Query("SELECT * FROM cashed_movies")
    LiveData<List<ModelCachedMovie>> getAllCachedFromDb();

    @Query("DELETE FROM cashed_movies WHERE primKey NOT IN (SELECT primKey FROM cashed_movies ORDER BY primKey LIMIT :cacheLimit)")
    void trimCacheTable(int cacheLimit);

    @Query("SELECT * FROM cashed_movies WHERE movieId = :movieId LIMIT 1")
    ModelBaseMovie getMovieById(String movieId);
}

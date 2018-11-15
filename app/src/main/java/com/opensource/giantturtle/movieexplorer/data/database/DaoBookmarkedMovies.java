package com.opensource.giantturtle.movieexplorer.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoBookmarkedMovies {

    @Insert
    void insert(ModelSavedMovie savedMovie);

    @Update
    void update(ModelSavedMovie baseGitHubRepo);

    @Delete
    void delete(ModelSavedMovie baseGitHubRepo);

    @Query("DELETE FROM saved_movies")
    void deleteAllSavedRepos();

    @Query("SELECT * FROM saved_movies ORDER BY primKey DESC")
    LiveData<List<ModelSavedMovie>> getAllSavedProjects();

}

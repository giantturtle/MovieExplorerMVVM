package com.opensource.giantturtle.movieexplorer.ui.savedscreen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.opensource.giantturtle.movieexplorer.data.DataRepository;
import com.opensource.giantturtle.movieexplorer.data.database.ModelSavedMovie;

import java.util.List;

public class SavedMoviesActivityViewModel extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<List<ModelSavedMovie>> allSavedRepos;

    public SavedMoviesActivityViewModel(@NonNull Application application) {
        super(application);
        repository = DataRepository.getInstance(application);
        allSavedRepos = repository.getAllSavedProjects();
    }

    void delete(ModelSavedMovie savedMovie) {
        repository.deleteSavedMovie(savedMovie);
    }

    void deleteAllSavedRepos() {
        repository.deleteAllSavedMovies();
    }

    LiveData<List<ModelSavedMovie>> getAllSavedRepos() {
        return allSavedRepos;
    }
}

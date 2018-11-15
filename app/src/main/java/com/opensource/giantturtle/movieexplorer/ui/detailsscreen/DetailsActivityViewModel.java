package com.opensource.giantturtle.movieexplorer.ui.detailsscreen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.opensource.giantturtle.movieexplorer.data.DataRepository;
import com.opensource.giantturtle.movieexplorer.data.database.ModelBaseMovie;
import com.opensource.giantturtle.movieexplorer.data.database.ModelSavedMovie;

public class DetailsActivityViewModel extends AndroidViewModel {
    private DataRepository repository;


    public DetailsActivityViewModel(@NonNull Application application) {
        super(application);
        repository = DataRepository.getInstance(application);
    }


    void bookmarkMovie(ModelBaseMovie cachedMovie) {
        repository.bookmarkMovie(cachedMovie);
    }

    void deleteBookmark(ModelSavedMovie savedMovie) {
        repository.deleteSavedMovie(savedMovie);
    }





}
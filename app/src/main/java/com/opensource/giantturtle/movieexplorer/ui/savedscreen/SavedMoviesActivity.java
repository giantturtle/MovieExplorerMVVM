package com.opensource.giantturtle.movieexplorer.ui.savedscreen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.opensource.giantturtle.movieexplorer.R;
import com.opensource.giantturtle.movieexplorer.data.database.ModelSavedMovie;
import com.opensource.giantturtle.movieexplorer.utils.Utils;

import java.util.List;

public class SavedMoviesActivity extends AppCompatActivity implements SavedMoviesAdapter.IClickCallback {

    private SavedMoviesActivityViewModel savedMoviesActivityViewModel;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_projects);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab_delete_all_saved_projects);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.delete_all_snack, Snackbar.LENGTH_LONG)
                        .setAction("YES", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                savedMoviesActivityViewModel.deleteAllSavedRepos();
                            }
                        }).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view_save);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final SavedMoviesAdapter adapter = new SavedMoviesAdapter(this);
        recyclerView.setAdapter(adapter);

        savedMoviesActivityViewModel = ViewModelProviders.of(this).get(SavedMoviesActivityViewModel.class);
        savedMoviesActivityViewModel.getAllSavedRepos().observe(this, new Observer<List<ModelSavedMovie>>() {

            //Triggered every time data in corresponding note table database is changed

            @Override
            public void onChanged(@Nullable List<ModelSavedMovie> savedGitHubReposList) {
                adapter.setList(savedGitHubReposList);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        recyclerView.setAdapter(null);
        super.onDestroy();
    }

    @Override
    public void onRowClicked(ModelSavedMovie savedProject, SavedMoviesAdapter.ClickActionSaved clickActionSaved) {
        //Toast.makeText(this, "Row clicked: position = "+position, Toast.LENGTH_SHORT).show();
        switch (clickActionSaved) {
            case DELETE_SAVED:
                Toast.makeText(this, getString(R.string.project_deleted_toast), Toast.LENGTH_SHORT).show();
                savedMoviesActivityViewModel.delete(savedProject);
                break;
            case GO_TO_DETAILS:
                startActivity(Utils.createDeatailsIntent(SavedMoviesActivity.this, savedProject));
                break;

            default:
                break;
        }
    }
}

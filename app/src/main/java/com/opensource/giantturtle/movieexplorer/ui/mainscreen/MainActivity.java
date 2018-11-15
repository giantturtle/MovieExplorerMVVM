package com.opensource.giantturtle.movieexplorer.ui.mainscreen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.opensource.giantturtle.movieexplorer.R;
import com.opensource.giantturtle.movieexplorer.data.database.ModelCachedMovie;
import com.opensource.giantturtle.movieexplorer.ui.savedscreen.SavedMoviesActivity;
import com.opensource.giantturtle.movieexplorer.utils.Utils;
import com.opensource.giantturtle.movieexplorer.utils.WebServiceMessage;

import java.util.Collections;
import java.util.List;

import pl.tajchert.sample.DotsTextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainAdapter.ItemClickCallback {

    private MainActivityViewModel mainActivityViewModel;
    SearchView searchView;
    RecyclerView recyclerView;
    private DotsTextView waitingDotsMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(getString(R.string.toolbar_subtitle));
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        waitingDotsMain =  findViewById(R.id.dots_main_screen);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final MainAdapter adapter = new MainAdapter(this);
        recyclerView.setAdapter(adapter);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getAllCachedProjects().observe(this, new Observer<List<ModelCachedMovie>>() {
            @Override
            public void onChanged(@Nullable List<ModelCachedMovie> cachedGitHubRepos) {
                adapter.setList(cachedGitHubRepos);
            }
        });
        mainActivityViewModel.getWebServiceStatus().observe(this, new Observer<WebServiceMessage>() {
            @Override
            public void onChanged(@Nullable WebServiceMessage webServiceStatus) {
                switch (webServiceStatus) {
                    case UPDATING_STATUS:
                        Toast.makeText(MainActivity.this, getString(R.string.updating_notification_main),
                                Toast.LENGTH_SHORT).show();
                        showDots();
                        break;
                    case ON_FAILURE:
                        Snackbar.make(findViewById(R.id.drawer_layout), R.string.fail_to_load_main,
                                Snackbar.LENGTH_LONG)
                                .setAction("n f", null).show();
                        stopDots();
                        break;
                    case ON_RESPONSE_SUCCESS:
                        stopDots();
                        break;
                    case ON_RESPONSE_NOTHING_FOUND:
                        Snackbar.make(findViewById(R.id.drawer_layout), getString(R.string.nothing_found_main,
                                mainActivityViewModel.getSearchTerm()),
                                Snackbar.LENGTH_LONG)
                                .setAction("n f", null).show();
                        stopDots();
                        adapter.setList(Collections.<ModelCachedMovie>emptyList());
                        break;
                    case ON_RESPONSE_NO_MORE_RESULTS:
                        Snackbar.make(findViewById(R.id.drawer_layout), R.string.no_more_main, Snackbar.LENGTH_LONG)
                                .setAction("n f", null).show();
                        stopDots();
                        break;
                    default:
                        break;
                }
            }
        });

        showDialogIfFirstRun();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void showDots(){
        if (waitingDotsMain.getVisibility()==View.GONE)waitingDotsMain.setVisibility(View.VISIBLE);
        waitingDotsMain.showAndPlay();
    }

    private void stopDots(){
        if (waitingDotsMain.getVisibility()==View.VISIBLE){
            waitingDotsMain.hideAndStop();
            waitingDotsMain.setVisibility(View.GONE);
        }
    }

    public void showDialogIfFirstRun() {
        if(mainActivityViewModel.isFirstRun()) {
            mainActivityViewModel.setFirstRunFlagOff();
            showDialog();
        }
    }

    private void showDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.disclaimer)).setMessage(getString(R.string.app_disclaimer))
                .setCancelable(false)
                .setNeutralButton(R.string.ok_app_info_dialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        recyclerView.setAdapter(null);
        searchView.setOnQueryTextListener(null);
        searchView=null;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_screen, menu);
        searchView = (SearchView) menu.findItem(R.id.search_toolbar).getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mainActivityViewModel.searchGitHub(s);
                searchView.clearFocus();
                recyclerView.scrollToPosition(0);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.bookmarks) {
            Intent intent = new Intent(MainActivity.this, SavedMoviesActivity.class);
            startActivity(intent);

        } if (id == R.id.splash_screen) {
            showDialog();

        } else if (id == R.id.other_apps) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=2GiantTurtle"));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
            else {
                Toast.makeText(this, R.string.no_play_store, Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.about_author) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://giant2turtle.wordpress.com/about/"));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }

        }else if (id == R.id.share_app) {
            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setChooserTitle(R.string.chooser_dialog_title)
                    .setText("http://play.google.com/store/apps/details?id=" + getPackageName())
                    .startChooser();
        }
        else if (id == R.id.source_code){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //TODO set here new repository link
            intent.setData(Uri.parse("https://github.com/giantturtle/RepoExplorerMVVM"));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        }
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRowClicked(ModelCachedMovie cachedProject, MainAdapter.MainClickType mainClickType) {
        switch (mainClickType) {
            case BOOKMARK_PROJECT:
                Toast.makeText(this, getString(R.string.project_bookmarked), Toast.LENGTH_SHORT).show();
                mainActivityViewModel.bookmarkProject(cachedProject);
                break;
            case SHOW_MORE:
                mainActivityViewModel.loadMore();
                break;

            case GO_TO_DETAILS:
                startActivity(Utils.createDeatailsIntent(MainActivity.this,cachedProject));
                break;
            default:
                break;
        }
    }
}

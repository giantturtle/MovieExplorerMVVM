package com.opensource.giantturtle.movieexplorer.ui.detailsscreen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.opensource.giantturtle.movieexplorer.R;
import com.opensource.giantturtle.movieexplorer.utils.Utils;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    //private CustomTabsIntent customTabsIntent;
    String parentActivityId;
    private DetailsActivityViewModel detailsActivityViewModel;
    Intent parentIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //initCustomTabs();

        //TODO pass the primKey and parentActivityName trough the intent and get cached or saved movie from database and set the details
        //put acquiering logic in the detailsActivityViewModel

        ImageView ownerAvatar = findViewById(R.id.iv_owner_avatar);
        TextView originalMovieTitleTv = findViewById(R.id.original_title_tv_details);
        TextView movieTitleTv = findViewById(R.id.title_tv_details);
        TextView popularityTv = findViewById(R.id.popularity_tv_details);
        TextView originalLanguageTv = findViewById(R.id.or_lang_tv_details);
        TextView averageVoteTv = findViewById(R.id.average_vote_tv_details);
        TextView voteCountTv = findViewById(R.id.vote_count_tv_details);
        TextView releaseDateTv = findViewById(R.id.release_date_tv_details);
        TextView overviewTv = findViewById(R.id.overview_tv_details);
        Button seeReviews = findViewById(R.id.view_code_btn_details);
        Button actionButtonDetails = findViewById(R.id.action_details);


        parentIntent = getIntent();
        parentActivityId = parentIntent.getStringExtra("parentActivity");
        Log.d("parentAct", parentActivityId);
        Glide.with(this).load(parentIntent.getStringExtra("posterPath")).into(ownerAvatar);
        movieTitleTv.setText(parentIntent.getStringExtra("title"));
        originalMovieTitleTv.setText(getString(R.string.original_movie_name, parentIntent.getStringExtra("originalTitle")));
        popularityTv.setText(getString(R.string.popularity_details, Double.toString(parentIntent.getDoubleExtra("popularity",0))));
        originalLanguageTv.setText(getString(R.string.original_language_details, parentIntent.getStringExtra("originalLanguage")));
        averageVoteTv.setText(getString(R.string.score_details, Float.toString(parentIntent.getFloatExtra("voteAverage",0f))));
        voteCountTv.setText(getString(R.string.vote_count_details, Integer.toString(parentIntent.getIntExtra("voteCount",0))));
        releaseDateTv.setText(getString(R.string.release_date_details, parentIntent.getStringExtra("prettyReleasedDate")));
        overviewTv.setText(getString(R.string.project_description_details, parentIntent.getStringExtra("overview")));
        seeReviews.setOnClickListener(this);
        if (parentActivityId.equals("MainActivity")){
            actionButtonDetails.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bookmarks_24dp, 0, 0, 0);
            actionButtonDetails.setText(R.string.bookmark_button_text);
        }
        else if (parentActivityId.equals("SavedMoviesActivity")){
            actionButtonDetails.setCompoundDrawablesWithIntrinsicBounds(R.drawable.delete_24dp, 0, 0, 0);
            actionButtonDetails.setText(R.string.delete_button_text);
        }
        actionButtonDetails.setOnClickListener(this);

        detailsActivityViewModel = ViewModelProviders.of(this).get(DetailsActivityViewModel.class);

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

    /*private void initCustomTabs() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        builder.setShowTitle(true);
        customTabsIntent = builder.build();
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_details:
                if (parentActivityId.equals("MainActivity")){
                    detailsActivityViewModel.bookmarkMovie(Utils.movieFromIntent(parentIntent));
                    Toast.makeText(this, "Bookmarked", Toast.LENGTH_SHORT).show();
                }
                else if (parentActivityId.equals("SavedMoviesActivity")) {
                    Snackbar.make(v, R.string.delete_movie_snack, Snackbar.LENGTH_LONG)
                            .setAction("YES", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    detailsActivityViewModel.deleteBookmark(Utils.movieFromIntent(parentIntent));
                                    Toast.makeText(DetailsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            }).show();
                }
                break;

                case R.id.view_code_btn_details:
                //TODO see reviews;
                break;
            default:
                break;
        }
    }
}

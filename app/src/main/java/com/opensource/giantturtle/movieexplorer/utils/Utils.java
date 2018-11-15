package com.opensource.giantturtle.movieexplorer.utils;

import android.app.Activity;
import android.content.Intent;

import com.opensource.giantturtle.movieexplorer.data.database.ModelBaseMovie;
import com.opensource.giantturtle.movieexplorer.data.database.ModelSavedMovie;
import com.opensource.giantturtle.movieexplorer.ui.detailsscreen.DetailsActivity;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Utils {

    public static Intent createDeatailsIntent(Activity parentActivity, ModelBaseMovie clickedProject) {

        Intent myIntent = new Intent(parentActivity, DetailsActivity.class);
        myIntent.putExtra("parentActivity", parentActivity.getClass().getSimpleName());
        myIntent.putExtra("posterPath", clickedProject.getPosterPath());
        myIntent.putExtra("voteCount", clickedProject.getVoteCount());
        myIntent.putExtra("overview", clickedProject.getOverview());
        myIntent.putExtra("originalLanguage", clickedProject.getOriginalLanguage());
        myIntent.putExtra("voteAverage", clickedProject.getVoteAverage());
        myIntent.putExtra("title", clickedProject.getTitle());
        myIntent.putExtra("popularity", clickedProject.getPopularity());
        myIntent.putExtra("primKey", clickedProject.getPrimKey());
        myIntent.putExtra("releaseDate", clickedProject.getReleaseDate());
        myIntent.putExtra("prettyReleasedDate", clickedProject.getPrettyReleaseDate());
        myIntent.putExtra("movieId", clickedProject.getMovieId());
        myIntent.putExtra("originalTitle", clickedProject.getOriginalTitle());
        return myIntent;
    }

    public static ModelSavedMovie convertType(ModelBaseMovie baseMovie) {
        //Convert project from cached to saved, because Room needs to have two class entities to save in two different tables
        ModelSavedMovie modelSavedMovie = new ModelSavedMovie();
        modelSavedMovie.setPosterPath(baseMovie.getPosterPath());
        modelSavedMovie.setVoteCount(baseMovie.getVoteCount());
        modelSavedMovie.setOriginalLanguage(baseMovie.getOriginalLanguage());
        modelSavedMovie.setOverview(baseMovie.getOverview());
        modelSavedMovie.setTitle(baseMovie.getTitle());
        modelSavedMovie.setPopularity(baseMovie.getPopularity());
        modelSavedMovie.setVoteAverage(baseMovie.getVoteAverage());
        modelSavedMovie.setReleaseDate(baseMovie.getReleaseDate());
        modelSavedMovie.setMovieId(baseMovie.getMovieId());
        return modelSavedMovie;
    }

    public static ModelSavedMovie movieFromIntent(Intent intent) {
        ModelSavedMovie modelSavedMovie = new ModelSavedMovie();
        modelSavedMovie.setPosterPath(intent.getStringExtra("posterPath"));
        modelSavedMovie.setVoteCount(intent.getIntExtra("voteCount", 0));
        modelSavedMovie.setOriginalLanguage(intent.getStringExtra("originalLanguage"));
        modelSavedMovie.setOverview(intent.getStringExtra("overview"));
        modelSavedMovie.setTitle(intent.getStringExtra("title"));
        modelSavedMovie.setPopularity(intent.getDoubleExtra("popularity",0));
        modelSavedMovie.setVoteAverage(intent.getFloatExtra("voteAverage",0));
        modelSavedMovie.setReleaseDate(intent.getStringExtra("title"));
        modelSavedMovie.setMovieId(intent.getIntExtra("movieId",0));
        modelSavedMovie.setPrimKey(intent.getIntExtra("primKey",0));
        return modelSavedMovie;
    }

    public static String convertToPrettyTime(String dateStr)   {
        if (dateStr!=null){
            try{
                PrettyTime prettyTime = new PrettyTime();
                SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date convertedDate = sourceFormat.parse(dateStr);
                return prettyTime.format(convertedDate);
            } catch (ParseException parseException){
                return dateStr;
            }
        }
        else return "N/A";
    }
}

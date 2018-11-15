package com.opensource.giantturtle.movieexplorer.data.database;

import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.opensource.giantturtle.movieexplorer.utils.Utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//base class for Room database entities
public class ModelBaseMovie {

    @PrimaryKey(autoGenerate = true)
    private int primKey;
    private  int movieId;
    private String title;
    private String overview;
    private int voteCount;
    private double popularity;
    private float voteAverage;
    private String posterPath;
    private String releaseDate;
    private String originalLanguage;
    private String originalTitle;

    //pretty date and time, not to be stored in database
    @Ignore
    private String prettyReleaseDate;

    public ModelBaseMovie() {
    }
    public int getPrimKey() {
        return primKey;
    }

    public int getMovieId() {
        return movieId;
    }
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public String getPrettyReleaseDate() {
        return prettyReleaseDate;
    }


    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setPrimKey(int primKey) {
        this.primKey = primKey;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        this.prettyReleaseDate = Utils.convertToPrettyTime(releaseDate);
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

}

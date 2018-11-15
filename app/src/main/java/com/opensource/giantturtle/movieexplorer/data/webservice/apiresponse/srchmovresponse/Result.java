package com.opensource.giantturtle.movieexplorer.data.webservice.apiresponse.srchmovresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.opensource.giantturtle.movieexplorer.data.webservice.TheMovieDBClient;

import java.util.List;



public class Result {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("popularity")
    @Expose
    private double popularity;

    @SerializedName("vote_average")
    @Expose
    private float voteAverage;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return TheMovieDBClient.POSTER_PATH_BASE_URL + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<Integer> getGenre_ids() {
        return genreIds;
    }

    public String getGenreIds(){
        return genreIds.toString();
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genreIds = genre_ids;
    }
}

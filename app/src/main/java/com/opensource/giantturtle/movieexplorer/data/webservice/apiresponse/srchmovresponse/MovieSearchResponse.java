package com.opensource.giantturtle.movieexplorer.data.webservice.apiresponse.srchmovresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.opensource.giantturtle.movieexplorer.data.webservice.apiresponse.srchmovresponse.Result;

import java.util.ArrayList;


public class MovieSearchResponse {

    @SerializedName("results")
    @Expose
    private ArrayList<Result> results;

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("total_results")
    @Expose
    private int total_results;

    @SerializedName("total_pages")
    @Expose
    private int total_pages;

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "results=" + results +
                ", page=" + page +
                ", total_results=" + total_results +
                ", total_pages=" + total_pages +
                '}';
    }
}

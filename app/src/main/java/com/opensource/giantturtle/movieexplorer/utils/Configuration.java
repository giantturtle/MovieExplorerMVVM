package com.opensource.giantturtle.movieexplorer.utils;

public interface Configuration {
    //utility interface for easier configuration of the app
    int FRESH_TIMEOUT_IN_MINUTES = 12 * 60;
    String API_KEY = "ac1d8d58dce25f8e2a990112e062a448";
    String DEFAULT_SEARCH_TERM = "space";
    int DEFAULT_CACHE_ITEMS_SIZE = 20;
}

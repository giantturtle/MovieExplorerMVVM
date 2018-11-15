package com.opensource.giantturtle.movieexplorer.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.opensource.giantturtle.movieexplorer.data.database.DaoBookmarkedMovies;
import com.opensource.giantturtle.movieexplorer.data.database.DaoCachedMovies;
import com.opensource.giantturtle.movieexplorer.data.database.ModelBaseMovie;
import com.opensource.giantturtle.movieexplorer.data.database.ModelCachedMovie;
import com.opensource.giantturtle.movieexplorer.data.database.LocalMoviesDatabase;
import com.opensource.giantturtle.movieexplorer.data.database.ModelSavedMovie;
import com.opensource.giantturtle.movieexplorer.data.webservice.TheMovieDBClient;
import com.opensource.giantturtle.movieexplorer.data.webservice.apiresponse.srchmovresponse.MovieSearchResponse;
import com.opensource.giantturtle.movieexplorer.data.webservice.apiresponse.srchmovresponse.Result;
import com.opensource.giantturtle.movieexplorer.utils.Configuration;
import com.opensource.giantturtle.movieexplorer.utils.Utils;
import com.opensource.giantturtle.movieexplorer.utils.WebServiceMessage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRepository {
    private static DataRepository dataRepositoryInstance;
    private DaoBookmarkedMovies savedProjectsDao;
    private DaoCachedMovies cachedProjectsDao;
    private Retrofit retrofit;
    private TheMovieDBClient theMovieDBClient;
    private SharedPreferences sharedPreferences;
    private MutableLiveData<WebServiceMessage> webServiceCallStatus = new MutableLiveData<>();

    public static synchronized DataRepository getInstance(Application application) {
        if (dataRepositoryInstance == null) {
            dataRepositoryInstance = new DataRepository(application);
        }
        return dataRepositoryInstance;
    }

    private DataRepository(Application application) {
        LocalMoviesDatabase database = LocalMoviesDatabase.getInstance(application);
        cachedProjectsDao = database.cachedProjectsDao();
        savedProjectsDao = database.savedProjectsDao();
        sharedPreferences = application.getSharedPreferences("theMovieDbSharedPref", Context.MODE_PRIVATE);
    }

    public boolean isFirstRun(){
        return sharedPreferences.getBoolean("isFirstRun",true);
    }

    public void setFirstRunOff () {
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putBoolean("isFirstRun",false);
        myEditor.apply();
    }

    public String getLastSavedSearchTerm() {
        return sharedPreferences.getString("lastSearchTerm", Configuration.DEFAULT_SEARCH_TERM);
    }

    private void saveLastSearchTerm(String searchTerm){
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putString("lastSearchTerm",searchTerm);
        myEditor.apply();
    }

    private  void setLastRefreshedDate (Date date){
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putString("lastRefreshDate",date.toString());
        myEditor.apply();
    }

    private Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, -Configuration.FRESH_TIMEOUT_IN_MINUTES);
        return cal.getTime();
    }

    public boolean isCacheFreshEnough (Date date){
        String lastRefreshedDate = sharedPreferences.getString("lastRefreshDate", null) ;
        if (lastRefreshedDate==null) return false; //not fresh if there isn't any
        else {
            return new Date(lastRefreshedDate).compareTo(getMaxRefreshTime(date))>0; //comparison
        }
    }

    public void searchMovies(final String searchTerm, final int pageNumber) {
        webServiceCallStatus.setValue(WebServiceMessage.UPDATING_STATUS);
        final List<ModelCachedMovie> cachedMoviesList = new ArrayList<ModelCachedMovie>();
        if (retrofit==null || theMovieDBClient ==null){//because using only GitHubClientService
            retrofit = new Retrofit.Builder().baseUrl(TheMovieDBClient.BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).build();
            theMovieDBClient = retrofit.create(TheMovieDBClient.class);
        }
        Call<MovieSearchResponse> call = theMovieDBClient.searchMovies(Configuration.API_KEY ,searchTerm, pageNumber);
        call.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                //Log.d("respBody",response.body().toString());
                //todo kontrolisi ovde logiku jer u response ima field total results and pages  i na osnovu toga mogu da izbacim WebServiceMessage
                if (response.body().getResults()!=null){
                    ArrayList<Result> resultsList = response.body().getResults();
                    for (int i = 0; i < resultsList.size(); i++) {
                        Result result = resultsList.get(i);
                        ModelCachedMovie current = new ModelCachedMovie();
                        current.setOverview(result.getOverview());
                        current.setTitle(result.getTitle());
                        current.setPopularity(result.getPopularity());
                        current.setMovieId(result.getId());
                        current.setPosterPath(result.getPosterPath());
                        current.setReleaseDate(result.getReleaseDate());
                        current.setOriginalLanguage(result.getOriginalLanguage());
                        current.setVoteCount(result.getVoteCount());
                        current.setVoteAverage(result.getVoteAverage());
                        current.setOriginalTitle(result.getOriginalTitle());
                        cachedMoviesList.add(current);
                    }
                }

                if (!cachedMoviesList.isEmpty()) {
                    boolean clearPreviousCache;
                    if (pageNumber==1) {
                        clearPreviousCache =true;
                        saveLastSearchTerm(searchTerm);
                    }
                    else clearPreviousCache = false;
                    cacheProjectsList(cachedMoviesList, clearPreviousCache);
                    setLastRefreshedDate(new Date());//set current date as last refreshed
                    webServiceCallStatus.postValue(WebServiceMessage.ON_RESPONSE_SUCCESS);
                } else  {
                    if (pageNumber==1)webServiceCallStatus.postValue(WebServiceMessage.ON_RESPONSE_NOTHING_FOUND);
                    else webServiceCallStatus.postValue(WebServiceMessage.ON_RESPONSE_NO_MORE_RESULTS);
                }
            }
            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                //Log.e("wentWrong", t.getMessage());
                webServiceCallStatus.postValue(WebServiceMessage.ON_FAILURE);
            }
        });
    }

    public LiveData<List<ModelCachedMovie>> getAllCachedProjects() {
        return cachedProjectsDao.getAllCachedFromDb();
    }

    public LiveData<List<ModelSavedMovie>> getAllSavedProjects() {
        return savedProjectsDao.getAllSavedProjects();
    }

    public MutableLiveData<WebServiceMessage> getWebServiceMessage() {
        return webServiceCallStatus;
    }

    public void clearWebServiceMessage() {
        //to get fresh webServiceCallStatus when opening app which is not cleared from androids cache memory
        webServiceCallStatus=new MutableLiveData<>();
    }

    private void cacheProjectsList(List<ModelCachedMovie> cachedGitHubRepoList, boolean clearCache) {
        new SaveListToCacheAsyncTask(cachedProjectsDao, clearCache).execute(cachedGitHubRepoList);
    }

    public void trimCacheTable() {
        new TrimCacheAsyncTask(cachedProjectsDao).execute();
    }


    private static class SaveListToCacheAsyncTask extends AsyncTask<List<ModelCachedMovie>, Void, Void> {
        private DaoCachedMovies cachedProjectsDao;
        boolean clearCacheBeforeAdding;

        private SaveListToCacheAsyncTask(DaoCachedMovies cachedProjectsDao, boolean clearCacheBeforeAdding) {
            this.cachedProjectsDao = cachedProjectsDao;
            this.clearCacheBeforeAdding = clearCacheBeforeAdding;
        }

        @Override
        protected Void doInBackground(List<ModelCachedMovie>... cachedGitHubReposList) {
            if (clearCacheBeforeAdding) {
                cachedProjectsDao.deleteAllCachedRepos();
            }
            cachedProjectsDao.saveToCache(cachedGitHubReposList[0]);
            return null;
        }
    }

    private static class TrimCacheAsyncTask extends AsyncTask<ModelCachedMovie, Void, Void> {
        private DaoCachedMovies cachedProjectsDao;

        private TrimCacheAsyncTask(DaoCachedMovies cachedProjectsDao) {
            this.cachedProjectsDao = cachedProjectsDao;
        }
        @Override
        protected Void doInBackground(ModelCachedMovie... cachedGitHubRepos) {
            cachedProjectsDao.trimCacheTable(Configuration.DEFAULT_CACHE_ITEMS_SIZE);
            return null;
        }
    }

    private static class SavedTableAsyncTask extends AsyncTask<ModelSavedMovie, Void, Void> {
        private DaoBookmarkedMovies savedProjectsDao;
        private ActionTypeSaved actionType;

        private SavedTableAsyncTask(DaoBookmarkedMovies savedProjectsDao, ActionTypeSaved actionType) {
            this.savedProjectsDao = savedProjectsDao;
            this.actionType = actionType;
        }
        @Override
        protected Void doInBackground(ModelSavedMovie... savedMovie) {
            switch (actionType) {
                case DELETE_SAVED:
                    savedProjectsDao.delete(savedMovie[0]);
                    break;
                case INSERT_BOOKMARK:
                    savedProjectsDao.insert(savedMovie[0]);
                    break;
                case DELETE_ALL_SAVED:
                    savedProjectsDao.deleteAllSavedRepos();
                    break;
                case UPDATE_SAVED:
                    savedProjectsDao.update(savedMovie[0]);
                    break;
                default:
                    break;
            }
            return null;
        }
    }

    public void bookmarkMovie(ModelBaseMovie baseGitHubProject) {
        new SavedTableAsyncTask(savedProjectsDao, ActionTypeSaved.INSERT_BOOKMARK).
                execute(Utils.convertType(baseGitHubProject));
    }

    public void deleteSavedMovie(ModelSavedMovie savedMovie) {
        new SavedTableAsyncTask(savedProjectsDao, ActionTypeSaved.DELETE_SAVED).execute(savedMovie);
    }

    public void deleteAllSavedMovies() {
        new SavedTableAsyncTask(savedProjectsDao, ActionTypeSaved.DELETE_ALL_SAVED).execute();
    }

    private enum ActionTypeSaved {
        DELETE_SAVED,
        DELETE_ALL_SAVED,
        UPDATE_SAVED,
        INSERT_BOOKMARK
    }
}

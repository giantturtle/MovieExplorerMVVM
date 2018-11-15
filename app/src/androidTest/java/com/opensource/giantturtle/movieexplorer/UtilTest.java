package com.opensource.giantturtle.movieexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;
import com.opensource.giantturtle.movieexplorer.data.database.ModelCachedMovie;
import com.opensource.giantturtle.movieexplorer.data.database.ModelSavedMovie;
import com.opensource.giantturtle.movieexplorer.ui.mainscreen.MainActivity;
import com.opensource.giantturtle.movieexplorer.ui.savedscreen.SavedMoviesActivity;
import com.opensource.giantturtle.movieexplorer.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class UtilTest {

    @Test
    public void containsCorectExtras()  {
        MainActivity mainActivity = mock(MainActivity.class);
        ModelCachedMovie modelCachedMovie = new ModelCachedMovie();
        modelCachedMovie.setOverview("test123");
        Intent intent = Utils.createDeatailsIntent(mainActivity, modelCachedMovie);
        assertNotNull(intent);
        Bundle extras = intent.getExtras();
        assertNotNull(extras);
        assertEquals("test123", extras.getString("overview"));

        SavedMoviesActivity savedMoviesActivity = mock (SavedMoviesActivity.class);
        ModelSavedMovie savedGitHubProject = new ModelSavedMovie();
        savedGitHubProject.setOverview("test321");
        Intent intent2 = Utils.createDeatailsIntent(savedMoviesActivity, savedGitHubProject);
        assertNotNull(intent2);
        Bundle extras2 = intent2.getExtras();
        assertNotNull(extras2);
        assertEquals("test321", extras2.getString("overview"));
    }

    @Test
    public void createsCorrectMovie(){
        Intent intent = new Intent();
        intent.putExtra("posterPath", "test123");
        intent.putExtra("voteCount", 123);
        intent.putExtra("primKey", 123);
        ModelSavedMovie modelSavedMovie = Utils.movieFromIntent(intent);

        assertEquals("test123", modelSavedMovie.getPosterPath());
        assertEquals(123, modelSavedMovie.getVoteCount());
        assertEquals(123, modelSavedMovie.getPrimKey());
    }
}

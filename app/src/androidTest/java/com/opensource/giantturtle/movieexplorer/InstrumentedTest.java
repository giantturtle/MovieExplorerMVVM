package com.opensource.giantturtle.movieexplorer;

import android.app.Application;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.opensource.giantturtle.movieexplorer.ui.mainscreen.MainActivityViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    private MainActivityViewModel mainActivityViewModel;
    private Application application;

    /*@Before
    public void setUp()  {
        MockitoAnnotations.initMocks(this);// required for the "@Mock" annotations
        application = (Application) InstrumentationRegistry.getTargetContext().getApplicationContext();
        mainActivityViewModel = new MainActivityViewModel(application);
    }

    @Test
    public void loadMore_increaseCounter() {
        mainActivityViewModel.loadMore();
        assertEquals(2, mainActivityViewModel.pageCounter);

    }*/


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.opensource.giantturtle.movieexplorer", appContext.getPackageName());
    }
}

package com.opensource.giantturtle.movieexplorer;

import com.opensource.giantturtle.movieexplorer.data.database.ModelBaseMovie;
import com.opensource.giantturtle.movieexplorer.utils.Utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsChangeProjectTypeTest {

    @Test
    public void changedProjectType() {
        ModelBaseMovie modelBaseMovie = new ModelBaseMovie();
        modelBaseMovie.setOverview("test123");
        assertEquals("test123", Utils.convertType(modelBaseMovie).getOverview());
    }

    @Test
    public void convertToPretyTime(){
        String malformedDate = "2016-12T12:26:11Z";
        String correctDate = "2016-12-20T12:26:11Z";
        String nullDate = null;
        assertEquals(malformedDate, Utils.convertToPrettyTime(malformedDate));
        assertEquals("N/A", Utils.convertToPrettyTime(nullDate));
        assertThat(correctDate, not(equalTo(Utils.convertToPrettyTime(correctDate))));

    }
}
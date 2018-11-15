package com.opensource.giantturtle.movieexplorer.data.database;

import android.arch.persistence.room.Entity;

@Entity(tableName = "cashed_movies")
public class ModelCachedMovie extends ModelBaseMovie {
    //need to have this sub class because ROOM supports only one Entity per class
}

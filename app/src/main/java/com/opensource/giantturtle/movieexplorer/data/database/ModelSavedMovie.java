package com.opensource.giantturtle.movieexplorer.data.database;

import android.arch.persistence.room.Entity;

@Entity(tableName = "saved_movies")
public class ModelSavedMovie extends ModelBaseMovie {
    //need to have this sub class because ROOM supports only one Entity per class
}

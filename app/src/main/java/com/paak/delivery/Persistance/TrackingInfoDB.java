package com.paak.delivery.Persistance;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.paak.delivery.Model.TrackingInfo;


@Database(entities = {TrackingInfo.class}, version = 1, exportSchema = false)
public abstract class TrackingInfoDB extends RoomDatabase {

    public abstract DataAccessObject getDAO();
}

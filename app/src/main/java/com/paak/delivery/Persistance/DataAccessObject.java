package com.paak.delivery.Persistance;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.paak.delivery.Model.TrackingInfo;
import java.util.List;


@Dao
public interface DataAccessObject {

 @Insert
 void saveInfo (TrackingInfo info);

@Query("SELECT * FROM TrackingInfo")
 List<TrackingInfo> getTrackingInfo();

 @Delete
 int deleteInfo(List<TrackingInfo> info);




}


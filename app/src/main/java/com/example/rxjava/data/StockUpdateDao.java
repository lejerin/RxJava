package com.example.rxjava.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface StockUpdateDao {
//    @Query("SELECT * FROM stock")
//    List<StockUpdateRoom> getAll();
//
//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    List<StockUpdateRoom> loadAllByIds(int[] userIds);
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    StockUpdateRoom findByName(String first, String last);

    @Update
    void update(StockUpdateRoom data);

    @Insert
    Completable insert(StockUpdateRoom user);

    @Delete
    void delete(StockUpdateRoom data);
}
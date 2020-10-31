package com.example.rxjava.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {StockUpdateRoom.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StockUpdateDao stockUpdateDao();
    private static AppDatabase mAppDatabase;

    // 싱글튼 패턴을 유지해야 데이터의 일치성을 보장할 수 있다
    public static AppDatabase getInstance(Context context) {
        if(mAppDatabase==null){
            mAppDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    "stock").build();
        }
        return mAppDatabase;
    }
}
package com.example.passwordkeeper.db;


import androidx.room.Room;
import androidx.room.RoomOpenHelper;

import  android.content.*;


public class DBStarter {

    private static DB db = null;


    public static synchronized DB getInstance(Context context) {



        if(db == null) db = Room.databaseBuilder(context, DB.class, "db").fallbackToDestructiveMigration().build();


        return db;

    }



}

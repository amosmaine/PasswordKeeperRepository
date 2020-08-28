package com.example.passwordkeeper.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Code.class}, version = 1, exportSchema = false)
public abstract class DB extends RoomDatabase {

    public abstract CodeDAO codeDao();
}

package com.example.passwordkeeper.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Code {

    @PrimaryKey(autoGenerate = true)
    public int cid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "pwd")
    public String code;

}

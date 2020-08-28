package com.example.passwordkeeper.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CodeDAO {

    @Query("SELECT * FROM code")
    List<Code> getAll();

    @Query("SELECT * FROM code WHERE cid IN (:codeIds)")
    List<Code> loadAllByIds(int[] codeIds);

    @Query("SELECT * FROM code WHERE name LIKE :name LIMIT 1")
    Code findByName(String name);



    @Insert
    void insertAll(Code... codes);

    @Delete
    void delete(Code code);
}

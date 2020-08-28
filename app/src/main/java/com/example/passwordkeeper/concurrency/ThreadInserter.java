package com.example.passwordkeeper.concurrency;



import com.example.passwordkeeper.db.Code;
import com.example.passwordkeeper.db.DB;

public class ThreadInserter extends Thread {

    private Code codeToInsert;
    private DB db;

    public ThreadInserter(Code initializer, DB db){
        codeToInsert = initializer;
        this.db = db;
    }

    public void run(){

            //insert and stop
            db.codeDao().insertAll(codeToInsert);


    }
}

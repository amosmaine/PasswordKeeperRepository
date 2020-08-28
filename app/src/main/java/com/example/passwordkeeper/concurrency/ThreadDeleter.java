package com.example.passwordkeeper.concurrency;

import android.content.Context;
import android.content.Intent;
import com.example.passwordkeeper.activity.ListActivity;
import com.example.passwordkeeper.db.Code;
import com.example.passwordkeeper.db.DB;

public class ThreadDeleter extends Thread {

    private String name;
    private DB db;
    private Context context;

    public ThreadDeleter(Context context, DB db, String name){
        this.db = db;
        this.name = name;
        this.context = context;

    }

    public void run(){

        //delete
        Code code = db.codeDao().findByName(name);
        db.codeDao().delete(code);
        Intent intent = new Intent(context, ListActivity.class);
        context.startActivity(intent);



    }
}

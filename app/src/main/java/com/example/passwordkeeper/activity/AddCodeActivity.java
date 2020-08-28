package com.example.passwordkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.passwordkeeper.R;
import com.example.passwordkeeper.concurrency.ThreadInserter;
import com.example.passwordkeeper.db.Code;
import com.example.passwordkeeper.db.DB;
import com.example.passwordkeeper.db.DBStarter;
import com.example.passwordkeeper.security.SecurityUtils;



public class AddCodeActivity extends AppCompatActivity {

    private DB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_code);
        Thread thread = new Thread(){
            public void run(){
                //you need to get the instance of the database
                db = DBStarter.getInstance(getApplicationContext());

            }
        };

        thread.start();
        final Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //after some check, add (name, code) to the db

                String name = ((TextView) findViewById(R.id.name)).getText().toString();
                String code = ((TextView) findViewById(R.id.code)).getText().toString();
                if(name == null || code == null){

                    Toast.makeText(getApplicationContext(),
                            "Values are null", Toast.LENGTH_SHORT).show();

                }else if(name.trim().isEmpty() || code.trim().isEmpty()){

                    Toast.makeText(getApplicationContext(),
                            "Values are null", Toast.LENGTH_SHORT).show();

                }else{
                        //if all working correctly
                        //need to store them in the db

                        boolean isValid = true;
                        for(int i=0; i<name.length(); ++i){

                            if(!Character.isDigit(name.charAt(i)) && !Character.isAlphabetic(name.charAt(i)) && name.charAt(i) != ' '  && name.charAt(i) != '.'  && name.charAt(i) != '@'){
                                isValid = false;
                            }


                        }

                        if(!isValid){

                            Toast.makeText(getApplicationContext(),
                                    "name: only number and letters, space, @, .", Toast.LENGTH_SHORT).show();

                            return;

                        }



                        Code codeToStore = new Code();
                        try {
                            codeToStore.code = SecurityUtils.encrypt(code, getApplicationContext());
                            codeToStore.name = name;
                            ThreadInserter inserter = new ThreadInserter(codeToStore, db);
                            inserter.start();
                            Toast.makeText(getApplicationContext(),
                                    "Values to be inserted", Toast.LENGTH_SHORT).show();

                        }catch(Exception e){

                            //security/incompatibility issue, so stop stop stop

                            Toast.makeText(getApplicationContext(),
                                    "Security/incompatibility issue, stop the action", Toast.LENGTH_SHORT).show();

                            return;


                        }


                }

            }
        });
        final Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //just go back to ListActivity
                Intent intent = new Intent(getBaseContext().getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });



    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        //just close the database if needed
        if( db.isOpen()) db.close();
    }
}

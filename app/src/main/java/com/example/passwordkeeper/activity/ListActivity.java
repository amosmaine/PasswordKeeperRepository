package com.example.passwordkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.passwordkeeper.R;
import com.example.passwordkeeper.concurrency.ThreadDeleter;
import com.example.passwordkeeper.db.Code;
import com.example.passwordkeeper.db.DB;
import com.example.passwordkeeper.db.DBStarter;
import com.example.passwordkeeper.security.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private DB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //open the db connection
        Thread thread = new Thread() {
            public void run() {
                db = DBStarter.getInstance(getApplicationContext());
                List<Code> codes = db.codeDao().getAll();


                final ArrayList<String> codesInString = new ArrayList<>();
                for (Code c : codes) {

                    try {
                        String toAdd = c.name + ": " + SecurityUtils.decrypt(c.code, getApplicationContext());
                        codesInString.add(toAdd);

                    } catch (Exception e) {

                        //if the decrypt raises an exception, security/incompatibility issue, so stop stop stop

                        Toast.makeText(getApplicationContext(),
                                "Security/incompatibility issue, stop the action", Toast.LENGTH_SHORT).show();

                        return;


                    }


                }


                //now on ui thread

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI

                        final SwipeMenuListView listView = findViewById(R.id.list);
                        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, codesInString);
                        listView.setAdapter(arrayAdapter);

                        SwipeMenuCreator creator = new SwipeMenuCreator() {

                            @Override
                            public void create(SwipeMenu menu) {


                                // create "delete" item
                                SwipeMenuItem deleteItem = new SwipeMenuItem(
                                        getApplicationContext());
                                // set item background
                                deleteItem.setBackground(new ColorDrawable(Color.rgb(192,
                                        192, 192)));
                                // set item width
                                deleteItem.setWidth(dp2px(getApplicationContext(), 90));


                                // set a icon
                                deleteItem.setTitle("Delete");
                                deleteItem.setTitleColor(Color.BLACK);
                                deleteItem.setTitleSize(17);
                                // add to menu
                                menu.addMenuItem(deleteItem);

                            }
                        };

// set creator
                        listView.setMenuCreator(creator);

                        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                                switch (index) {
                                    case 0:
                                        // close
                                        //need to cancel what you pressed

                                        String pressed = listView.getItemAtPosition(position).toString();
                                        String name = pressed.substring(0, pressed.indexOf(":"));
                                        ThreadDeleter deleter = new ThreadDeleter(ListActivity.this, db, name);
                                        deleter.start();


                                        break;

                                }
                                // false : close the menu; true : not close the menu
                                return false;
                            }
                        });

                    }
                });




            }


        };

        thread.start();












        final Button addCodeButton = findViewById(R.id.rightAddButton);
        addCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //simple transition to addCodeActivity

                Intent intent = new Intent(getBaseContext().getApplicationContext(), AddCodeActivity.class);
                startActivity(intent);


            }
        });


    }






    public static int dp2px(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, metrics);/*from   w ww  .ja  va2s.co  m*/
    }








}

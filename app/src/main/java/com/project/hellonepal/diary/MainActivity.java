package com.project.hellonepal.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.hellonepal.R;
import com.project.hellonepal.diary.content.CreateDiaryItem;
import com.project.hellonepal.diary.content.DiaryList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Add to today's diary",
                        Toast.LENGTH_SHORT).show();

                Intent createDiary = new Intent(MainActivity.this, CreateDiaryItem.class);
                startActivity(createDiary);
            }
        });


        Button viewDiary = ((Button) findViewById(R.id.showDiary));
        viewDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Viewing Diary List",
                        Toast.LENGTH_SHORT).show();

                Intent diaryList = new Intent(MainActivity.this, DiaryList.class);
                startActivity(diaryList);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.searchView) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}

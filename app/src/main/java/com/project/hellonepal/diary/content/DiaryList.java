package com.project.hellonepal.diary.content;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.project.hellonepal.R;
import com.project.hellonepal.diary.model.Diary;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * Created by rojo on 2/24/17.
 */

public class DiaryList extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diaries_list);

        RecyclerView recyclerView = ((RecyclerView) findViewById(R.id.diary_rec_view));

        List<Diary> diaryItems = new ArrayList<>();

        addItemsTo(diaryItems);

        DiaryAdapter diaryAdapter = new DiaryAdapter(diaryItems);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(diaryAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void addItemsTo(List<Diary> diaryItems) {

        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//
//        realm.where(Diary.class).findAll().deleteAllFromRealm();
//
//        Diary diary = realm.createObject(Diary.class);
//        diary.setTitle("My First Item in Diary");
//        String content10x = "Here's a simple content. \n";
//
//        for (int i = 0; i < 5; i++) {
//            content10x = content10x.concat(content10x);
//        }
//
//        diary.setContent(content10x);
//        diary.setDate(date);
//
//        Diary diary1 = realm.createObject(Diary.class);
//        diary1.setTitle("My Second Item in Diary");
//        String content = "Here's another simple content. \n";
//
//        for (int i = 0; i < 5; i++) {
//            content = content.concat(content);
//
//        }
//
//        diary1.setContent(content);
//        diary1.setDate(date);
//
//        realm.commitTransaction();
        RealmQuery<Diary> query = realm.where(Diary.class);

        RealmResults<Diary> results = query.findAllSorted("date", Sort.DESCENDING);


        for (Diary result : results) {
            diaryItems.add(result);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

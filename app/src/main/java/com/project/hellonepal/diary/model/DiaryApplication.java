package com.project.hellonepal.diary.model;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DiaryApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(configuration);
    }
}

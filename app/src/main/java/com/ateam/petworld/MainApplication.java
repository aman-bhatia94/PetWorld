package com.ateam.petworld;

import android.app.Application;
import android.content.Context;

import com.ateam.petworld.factory.ClientFactory;
import com.ateam.petworld.models.Sitter;

import java.util.List;

public class MainApplication extends Application {

    private static Context context;
    static List<Sitter> sitterList;

    public static List<Sitter> getSitterList() {
        return sitterList;
    }

    //    static List<Ow>
    public void onCreate() {
        super.onCreate();
        MainApplication.context = getApplicationContext();
        ClientFactory.init(this);
//        SitterDataService sitterDataService = new SitterDataService(ClientFactory.appSyncClient());
//        sitterList = sitterDataService.searchSitters();
    }

    public static Context getAppContext() {
        return MainApplication.context;
    }
}

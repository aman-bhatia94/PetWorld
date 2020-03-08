package com.ateam.petworld.services;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ateam.petworld.interfaces.LocationApiWebService;
import com.ateam.petworld.models.Location;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class LocationIQRESTService extends Service {


    static final String BASE_URL = "https://us1.locationiq.com/v1/";
    static final String api_key = "e7570b7ec4b574";
    static Retrofit retrofit = null;

    private final IBinder binder = new LocationIQBinder();

    public class LocationIQBinder extends Binder{
        LocationIQRESTService getLocationRESTServiceBinder(){
            return LocationIQRESTService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    public void fetchUserLocation(){

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        LocationApiWebService locationApiWebService = retrofit.create(LocationApiWebService.class);
        Call<Location> call = locationApiWebService.getUserLocation(api_key)
    }
}

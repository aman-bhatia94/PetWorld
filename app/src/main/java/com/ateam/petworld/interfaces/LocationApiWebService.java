package com.ateam.petworld.interfaces;

import com.ateam.petworld.models.LocationIQREST;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationApiWebService {


    @GET("reverse.php")
    Call<LocationIQREST> getUserLocation(@Query("key") String apiKey, @Query("lat") String latitude, @Query("lon") String longitude, @Query("format") String format);

    @GET("autocomplete.php")
    Call<List<LocationIQREST>> getAllPossibleLocations(@Query("key") String apiKey, @Query("q") String location, @Query("countrycodes") String countryCode);

}

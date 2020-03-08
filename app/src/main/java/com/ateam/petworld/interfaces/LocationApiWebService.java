package com.ateam.petworld.interfaces;
import com.ateam.petworld.models.Location;
import com.ateam.petworld.models.LocationIQREST;
import com.ateam.petworld.models.PossibleLocations;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface LocationApiWebService {


    @GET("reverse.php")
    Call<LocationIQREST> getUserLocation(@Query("api_key") String apiKey, @Query("lat") String latitude, @Query("lon") String longitude, @Query("format") String format);

    @GET("autocomplete.php")
    Call<PossibleLocations> getAllPossibleLocations(@Query("api_key") String apiKey,@Query("location") String location);



}

package com.ateam.petworld.services;

import android.util.Log;

import com.ateam.petworld.interfaces.LocationApiWebService;
import com.ateam.petworld.models.Location;
import com.ateam.petworld.models.LocationIQREST;
import com.ateam.petworld.models.PossibleLocations;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LocationIQRESTService {

    static final String BASE_URL = "https://us1.locationiq.com/v1/";
    static final String api_key = "e7570b7ec4b574";
    static Retrofit retrofit = null;
    Location location;
    List<Location> possibleLocations;

    public Location fetchUserLocation(String longitude, String latitude) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        LocationApiWebService locationApiWebService = retrofit.create(LocationApiWebService.class);
        Call<LocationIQREST> call = locationApiWebService.getUserLocation(api_key, latitude, longitude, "json");
        call.enqueue(new Callback<LocationIQREST>() {
            @Override
            public void onResponse(Call<LocationIQREST> call, Response<LocationIQREST> response) {
                location = new Location();
                location.setId(response.body().getId());
                location.setLatitude(response.body().getLatitude());
                location.setLongitude(response.body().getLongitude());
                location.setDisplayName(response.body().getDisplayName());
                location.setHouseNumber(response.body().getHouseNumber());
                location.setNeighbourhood(response.body().getNeighbourhood());
                location.setRoad(response.body().getRoad());
                location.setCity(response.body().getCity());
                location.setCountry(response.body().getCountry());
                location.setState(response.body().getState());
                location.setPostcode(response.body().getPostcode());
                location.setName(response.body().getName());
                location.setSuburb(response.body().getSuburb());
                location.setDisplayPlace(response.body().getDisplayPlace());
                location.setDisplayName(response.body().getDisplayName());


            }

            @Override
            public void onFailure(Call<LocationIQREST> call, Throwable t) {
                Log.e("error", t.toString());
                location = null;
            }
        });
        return location;
    }

    public List<Location> autoCompleteLocations(String userLocation, String countryCode) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        LocationApiWebService locationApiWebService = retrofit.create(LocationApiWebService.class);
        Call<PossibleLocations> call = locationApiWebService.getAllPossibleLocations(api_key, userLocation, countryCode);
        call.enqueue(new Callback<PossibleLocations>() {
            @Override
            public void onResponse(Call<PossibleLocations> call, Response<PossibleLocations> response) {
                possibleLocations = (List<Location>) response.body();
            }

            @Override
            public void onFailure(Call<PossibleLocations> call, Throwable t) {
                Log.e("error", t.toString());
                possibleLocations = null;
            }
        });
        return possibleLocations;
    }
}

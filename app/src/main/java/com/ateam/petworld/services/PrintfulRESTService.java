package com.ateam.petworld.services;

import android.content.Context;
import android.util.Log;

import com.ateam.petworld.activities.SearchLocation;
import com.ateam.petworld.interfaces.PrintfulApiWebService;
import com.ateam.petworld.models.Country;
import com.ateam.petworld.models.PrintfulCountriesREST;
import com.ateam.petworld.models.PrintfulCountriesResultsREST;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class PrintfulRESTService {

    private static final String BASE_URL = "https://api.printful.com/";
    private static Retrofit retrofit = null;
    private List<Country> countries;

    public PrintfulRESTService() {
        this.countries = null;
    }

    public List<Country> fetchAllCountries(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        countries = new ArrayList<>();
        PrintfulApiWebService printfulApiWebService = retrofit.create(PrintfulApiWebService.class);
        Call<PrintfulCountriesResultsREST> call = printfulApiWebService.getAllCountries();
        call.enqueue(new Callback<PrintfulCountriesResultsREST>() {
            @Override
            public void onResponse(Call<PrintfulCountriesResultsREST> call, Response<PrintfulCountriesResultsREST> response) {
                List<PrintfulCountriesREST> countriesRESTList = response.body().getCountriesRESTList();
                for (PrintfulCountriesREST eachObj : countriesRESTList) {
                    countries.add(new Country(eachObj.getName(), eachObj.getCode()));
                }
                runOnUiThread(() -> {
                    if (context instanceof SearchLocation) {
                        ((SearchLocation) context).setCountryList(countries);
                    }
                });
            }

            @Override
            public void onFailure(Call<PrintfulCountriesResultsREST> call, Throwable t) {
                Log.e("error", t.toString());
                countries = null;
            }
        });
        return countries;
    }

}

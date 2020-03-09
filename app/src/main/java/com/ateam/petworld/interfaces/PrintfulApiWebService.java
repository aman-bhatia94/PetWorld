package com.ateam.petworld.interfaces;

import com.ateam.petworld.models.PrintfulCountriesResultsREST;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PrintfulApiWebService {

    @GET("countries")
    Call<PrintfulCountriesResultsREST> getAllCountries();

}

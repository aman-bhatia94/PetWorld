package com.ateam.petworld.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrintfulCountriesResultsREST {

    @SerializedName("result")
    private List<PrintfulCountriesREST> countriesRESTList;

    public PrintfulCountriesResultsREST(List<PrintfulCountriesREST> countriesRESTList) {
        this.countriesRESTList = countriesRESTList;
    }

    public List<PrintfulCountriesREST> getCountriesRESTList() {
        return countriesRESTList;
    }

    public void setCountriesRESTList(List<PrintfulCountriesREST> countriesRESTList) {
        this.countriesRESTList = countriesRESTList;
    }
}

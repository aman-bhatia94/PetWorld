package com.ateam.petworld.models;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("name")
    private String name;

    @SerializedName("house_number")
    private String houseNumber;

    @SerializedName("road")
    private String road;

    @SerializedName("neighbourhood")
    private String neighbourhood;

    @SerializedName("suburb")
    private String suburb;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("postcode")
    private String postcode;

    @SerializedName("country")
    private String country;

    @SerializedName("country_code")
    private String country_code;

    public Address(String name, String houseNumber, String road, String neighbourhood, String suburb, String city, String state, String postcode, String country, String country_code) {
        this.name = name;
        this.houseNumber = houseNumber;
        this.road = road;
        this.neighbourhood = neighbourhood;
        this.suburb = suburb;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
        this.country = country;
        this.country_code = country_code;
    }

    public String getName() {
        return name;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getRoad() {
        return road;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }

    public String getCountry_code() {
        return country_code;
    }
}

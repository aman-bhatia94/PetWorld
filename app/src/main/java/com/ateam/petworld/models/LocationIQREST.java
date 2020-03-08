package com.ateam.petworld.models;
import com.google.gson.annotations.SerializedName;

public class LocationIQREST {

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("display_place")
    private String displayPlace;

    @SerializedName("display_address")
    private String displayAddress;

    @SerializedName("lat")
    private String latitude;

    @SerializedName("lon")
    private String longitude;

    @SerializedName("address")
    private Address address;

    public LocationIQREST(String displayName, String displayPlace, String displayAddress, String latitude, String longitude, Address address) {
        this.displayName = displayName;
        this.displayPlace = displayPlace;
        this.displayAddress = displayAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayPlace() {
        return displayPlace;
    }

    public String getDisplayAddress() {
        return displayAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public Address getAddress() {
        return address;
    }



}

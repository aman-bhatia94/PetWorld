package com.ateam.petworld.models;
import com.google.gson.annotations.SerializedName;

public class LocationIQREST {

    @SerializedName("place_id")
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return address.getName();
    }

    public String getHouseNumber() {
        return address.getHouseNumber();
    }

    public String getRoad() {
        return address.getRoad();
    }

    public String getNeighbourhood() {
        return address.getNeighbourhood();
    }

    public String getSuburb() {
        return address.getSuburb();
    }

    public String getCity() {
        return address.getCity();
    }

    public String getState() {
        return address.getState();
    }

    public String getPostcode() {
        return address.getPostcode();
    }

    public String getCountry() {
        return address.getCountry();
    }

    public String getCountry_code() {
        return address.getCountry_code();
    }

}

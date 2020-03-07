package com.amplifyframework.datastore.generated.model;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/**
 * This is an auto generated class representing the Location type in your schema.
 */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Locations")
public final class Location implements Model {
    public static final QueryField ID = field("id");
    public static final QueryField LONGITUDE = field("longitude");
    public static final QueryField LATITUDE = field("latitude");
    public static final QueryField DISPLAY_NAME = field("displayName");
    public static final QueryField DISPLAY_PLACE = field("displayPlace");
    public static final QueryField DISPLAY_ADDRESS = field("displayAddress");
    public static final QueryField NAME = field("name");
    public static final QueryField HOUSE_NO = field("houseNo");
    public static final QueryField NEIGHBOURHOOD = field("neighbourhood");
    public static final QueryField ROAD = field("road");
    public static final QueryField SUBURB = field("suburb");
    public static final QueryField CITY = field("city");
    public static final QueryField STATE = field("state");
    public static final QueryField POST_CODE = field("postCode");
    public static final QueryField COUNTRY = field("country");
    public static final QueryField COUNTRY_CODE = field("countryCode");
    private final @ModelField(targetType = "ID", isRequired = true)
    String id;
    private final @ModelField(targetType = "String", isRequired = true)
    String longitude;
    private final @ModelField(targetType = "String", isRequired = true)
    String latitude;
    private final @ModelField(targetType = "String", isRequired = true)
    String displayName;
    private final @ModelField(targetType = "String", isRequired = true)
    String displayPlace;
    private final @ModelField(targetType = "String", isRequired = true)
    String displayAddress;
    private final @ModelField(targetType = "Owner")
    @HasMany(associatedWith = "location", type = Owner.class)
    List<Owner> owner = null;
    private final @ModelField(targetType = "Sitter")
    @HasMany(associatedWith = "location", type = Sitter.class)
    List<Sitter> sitter = null;
    private final @ModelField(targetType = "String")
    String name;
    private final @ModelField(targetType = "String")
    String houseNo;
    private final @ModelField(targetType = "String")
    String neighbourhood;
    private final @ModelField(targetType = "String")
    String road;
    private final @ModelField(targetType = "String")
    String suburb;
    private final @ModelField(targetType = "String")
    String city;
    private final @ModelField(targetType = "String")
    String state;
    private final @ModelField(targetType = "String")
    String postCode;
    private final @ModelField(targetType = "String")
    String country;
    private final @ModelField(targetType = "String")
    String countryCode;

    private Location(String id, String longitude, String latitude, String displayName, String displayPlace, String displayAddress, String name, String houseNo, String neighbourhood, String road, String suburb, String city, String state, String postCode, String country, String countryCode) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.displayName = displayName;
        this.displayPlace = displayPlace;
        this.displayAddress = displayAddress;
        this.name = name;
        this.houseNo = houseNo;
        this.neighbourhood = neighbourhood;
        this.road = road;
        this.suburb = suburb;
        this.city = city;
        this.state = state;
        this.postCode = postCode;
        this.country = country;
        this.countryCode = countryCode;
    }

    public static LongitudeStep builder() {
        return new Builder();
    }

    /**
     * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
     * This is a convenience method to return an instance of the object with only its ID populated
     * to be used in the context of a parameter in a delete mutation or referencing a foreign key
     * in a relationship.
     *
     * @param id the id of the existing item this instance will represent
     * @return an instance of this model with only ID populated
     * @throws IllegalArgumentException Checks that ID is in the proper format
     **/
    public static Location justId(String id) {
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
            throw new IllegalArgumentException(
                    "Model IDs must be unique in the format of UUID. This method is for creating instances " +
                            "of an existing object with only its ID field for sending as a mutation parameter. When " +
                            "creating a new object, use the standard builder method and leave the ID field blank."
            );
        }
        return new Location(
                id,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public String getId() {
        return id;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
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

    public List<Owner> getOwner() {
        return owner;
    }

    public List<Sitter> getSitter() {
        return sitter;
    }

    public String getName() {
        return name;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getRoad() {
        return road;
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

    public String getPostCode() {
        return postCode;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            Location location = (Location) obj;
            return ObjectsCompat.equals(getId(), location.getId()) &&
                    ObjectsCompat.equals(getLongitude(), location.getLongitude()) &&
                    ObjectsCompat.equals(getLatitude(), location.getLatitude()) &&
                    ObjectsCompat.equals(getDisplayName(), location.getDisplayName()) &&
                    ObjectsCompat.equals(getDisplayPlace(), location.getDisplayPlace()) &&
                    ObjectsCompat.equals(getDisplayAddress(), location.getDisplayAddress()) &&
                    ObjectsCompat.equals(getName(), location.getName()) &&
                    ObjectsCompat.equals(getHouseNo(), location.getHouseNo()) &&
                    ObjectsCompat.equals(getNeighbourhood(), location.getNeighbourhood()) &&
                    ObjectsCompat.equals(getRoad(), location.getRoad()) &&
                    ObjectsCompat.equals(getSuburb(), location.getSuburb()) &&
                    ObjectsCompat.equals(getCity(), location.getCity()) &&
                    ObjectsCompat.equals(getState(), location.getState()) &&
                    ObjectsCompat.equals(getPostCode(), location.getPostCode()) &&
                    ObjectsCompat.equals(getCountry(), location.getCountry()) &&
                    ObjectsCompat.equals(getCountryCode(), location.getCountryCode());
        }
    }

    @Override
    public int hashCode() {
        return new StringBuilder()
                .append(getId())
                .append(getLongitude())
                .append(getLatitude())
                .append(getDisplayName())
                .append(getDisplayPlace())
                .append(getDisplayAddress())
                .append(getName())
                .append(getHouseNo())
                .append(getNeighbourhood())
                .append(getRoad())
                .append(getSuburb())
                .append(getCity())
                .append(getState())
                .append(getPostCode())
                .append(getCountry())
                .append(getCountryCode())
                .toString()
                .hashCode();
    }

    public CopyOfBuilder copyOfBuilder() {
        return new CopyOfBuilder(id,
                longitude,
                latitude,
                displayName,
                displayPlace,
                displayAddress,
                name,
                houseNo,
                neighbourhood,
                road,
                suburb,
                city,
                state,
                postCode,
                country,
                countryCode);
    }

    public interface LongitudeStep {
        LatitudeStep longitude(String longitude);
    }


    public interface LatitudeStep {
        DisplayNameStep latitude(String latitude);
    }


    public interface DisplayNameStep {
        DisplayPlaceStep displayName(String displayName);
    }


    public interface DisplayPlaceStep {
        DisplayAddressStep displayPlace(String displayPlace);
    }


    public interface DisplayAddressStep {
        BuildStep displayAddress(String displayAddress);
    }


    public interface BuildStep {
        Location build();

        BuildStep id(String id) throws IllegalArgumentException;

        BuildStep name(String name);

        BuildStep houseNo(String houseNo);

        BuildStep neighbourhood(String neighbourhood);

        BuildStep road(String road);

        BuildStep suburb(String suburb);

        BuildStep city(String city);

        BuildStep state(String state);

        BuildStep postCode(String postCode);

        BuildStep country(String country);

        BuildStep countryCode(String countryCode);
    }


    public static class Builder implements LongitudeStep, LatitudeStep, DisplayNameStep, DisplayPlaceStep, DisplayAddressStep, BuildStep {
        private String id;
        private String longitude;
        private String latitude;
        private String displayName;
        private String displayPlace;
        private String displayAddress;
        private String name;
        private String houseNo;
        private String neighbourhood;
        private String road;
        private String suburb;
        private String city;
        private String state;
        private String postCode;
        private String country;
        private String countryCode;

        @Override
        public Location build() {
            String id = this.id != null ? this.id : UUID.randomUUID().toString();

            return new Location(
                    id,
                    longitude,
                    latitude,
                    displayName,
                    displayPlace,
                    displayAddress,
                    name,
                    houseNo,
                    neighbourhood,
                    road,
                    suburb,
                    city,
                    state,
                    postCode,
                    country,
                    countryCode);
        }

        @Override
        public LatitudeStep longitude(String longitude) {
            Objects.requireNonNull(longitude);
            this.longitude = longitude;
            return this;
        }

        @Override
        public DisplayNameStep latitude(String latitude) {
            Objects.requireNonNull(latitude);
            this.latitude = latitude;
            return this;
        }

        @Override
        public DisplayPlaceStep displayName(String displayName) {
            Objects.requireNonNull(displayName);
            this.displayName = displayName;
            return this;
        }

        @Override
        public DisplayAddressStep displayPlace(String displayPlace) {
            Objects.requireNonNull(displayPlace);
            this.displayPlace = displayPlace;
            return this;
        }

        @Override
        public BuildStep displayAddress(String displayAddress) {
            Objects.requireNonNull(displayAddress);
            this.displayAddress = displayAddress;
            return this;
        }

        @Override
        public BuildStep name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public BuildStep houseNo(String houseNo) {
            this.houseNo = houseNo;
            return this;
        }

        @Override
        public BuildStep neighbourhood(String neighbourhood) {
            this.neighbourhood = neighbourhood;
            return this;
        }

        @Override
        public BuildStep road(String road) {
            this.road = road;
            return this;
        }

        @Override
        public BuildStep suburb(String suburb) {
            this.suburb = suburb;
            return this;
        }

        @Override
        public BuildStep city(String city) {
            this.city = city;
            return this;
        }

        @Override
        public BuildStep state(String state) {
            this.state = state;
            return this;
        }

        @Override
        public BuildStep postCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        @Override
        public BuildStep country(String country) {
            this.country = country;
            return this;
        }

        @Override
        public BuildStep countryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
         * This should only be set when referring to an already existing object.
         *
         * @param id id
         * @return Current Builder instance, for fluent method chaining
         * @throws IllegalArgumentException Checks that ID is in the proper format
         **/
        public BuildStep id(String id) throws IllegalArgumentException {
            this.id = id;

            try {
                UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
            } catch (Exception exception) {
                throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                        exception);
            }

            return this;
        }
    }


    public final class CopyOfBuilder extends Builder {
        private CopyOfBuilder(String id, String longitude, String latitude, String displayName, String displayPlace, String displayAddress, String name, String houseNo, String neighbourhood, String road, String suburb, String city, String state, String postCode, String country, String countryCode) {
            super.id(id);
            super.longitude(longitude)
                    .latitude(latitude)
                    .displayName(displayName)
                    .displayPlace(displayPlace)
                    .displayAddress(displayAddress)
                    .name(name)
                    .houseNo(houseNo)
                    .neighbourhood(neighbourhood)
                    .road(road)
                    .suburb(suburb)
                    .city(city)
                    .state(state)
                    .postCode(postCode)
                    .country(country)
                    .countryCode(countryCode);
        }

        @Override
        public CopyOfBuilder longitude(String longitude) {
            return (CopyOfBuilder) super.longitude(longitude);
        }

        @Override
        public CopyOfBuilder latitude(String latitude) {
            return (CopyOfBuilder) super.latitude(latitude);
        }

        @Override
        public CopyOfBuilder displayName(String displayName) {
            return (CopyOfBuilder) super.displayName(displayName);
        }

        @Override
        public CopyOfBuilder displayPlace(String displayPlace) {
            return (CopyOfBuilder) super.displayPlace(displayPlace);
        }

        @Override
        public CopyOfBuilder displayAddress(String displayAddress) {
            return (CopyOfBuilder) super.displayAddress(displayAddress);
        }

        @Override
        public CopyOfBuilder name(String name) {
            return (CopyOfBuilder) super.name(name);
        }

        @Override
        public CopyOfBuilder houseNo(String houseNo) {
            return (CopyOfBuilder) super.houseNo(houseNo);
        }

        @Override
        public CopyOfBuilder neighbourhood(String neighbourhood) {
            return (CopyOfBuilder) super.neighbourhood(neighbourhood);
        }

        @Override
        public CopyOfBuilder road(String road) {
            return (CopyOfBuilder) super.road(road);
        }

        @Override
        public CopyOfBuilder suburb(String suburb) {
            return (CopyOfBuilder) super.suburb(suburb);
        }

        @Override
        public CopyOfBuilder city(String city) {
            return (CopyOfBuilder) super.city(city);
        }

        @Override
        public CopyOfBuilder state(String state) {
            return (CopyOfBuilder) super.state(state);
        }

        @Override
        public CopyOfBuilder postCode(String postCode) {
            return (CopyOfBuilder) super.postCode(postCode);
        }

        @Override
        public CopyOfBuilder country(String country) {
            return (CopyOfBuilder) super.country(country);
        }

        @Override
        public CopyOfBuilder countryCode(String countryCode) {
            return (CopyOfBuilder) super.countryCode(countryCode);
        }
    }

}

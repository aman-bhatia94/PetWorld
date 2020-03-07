package com.ateam.petworld.services;

import android.util.Log;

import com.amazonaws.amplify.generated.graphql.CreateLocationMutation;
import com.amazonaws.amplify.generated.graphql.GetLocationQuery;
import com.amazonaws.amplify.generated.graphql.GetOwnerQuery;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.ateam.petworld.models.Location;
import com.ateam.petworld.models.Owner;

import java.util.Objects;

import javax.annotation.Nonnull;

import type.CreateLocationInput;

public class LocationDataService {
    private AWSAppSyncClient awsAppSyncClient;

    public LocationDataService(AWSAppSyncClient awsAppSyncClient) {
        this.awsAppSyncClient = awsAppSyncClient;
    }

    public void createLocation(Location location) {
        CreateLocationInput requestObj = CreateLocationInput.builder()
                .id(location.getId())
                .displayName(location.getDisplayName())
                .displayPlace("")
                .displayAddress("")
                .latitude(String.valueOf(location.getLatitude()))
                .longitude(String.valueOf(location.getLongitude()))
                .build();
        awsAppSyncClient.mutate(CreateLocationMutation.builder().input(requestObj).build()).enqueue(
                new GraphQLCall.Callback<CreateLocationMutation.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<CreateLocationMutation.Data> response) {
                        assert response.data() != null;
                        Log.i("Results", "Added Todo" + Objects.requireNonNull(response.data().createLocation()).id());
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.e("Error", e.toString());
                    }
                }
        );
    }

    public Location getLocationById(String locationId) {
        Location responseData = new Location();
        awsAppSyncClient.query(GetLocationQuery.builder().id(locationId).build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(new GraphQLCall.Callback<GetLocationQuery.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<GetLocationQuery.Data> response) {

                        GetLocationQuery.GetLocation locationQueryResponse = response.data() != null ? response.data().getLocation() : null;
                        if (locationQueryResponse == null)
                            return;

                        responseData.setId(locationQueryResponse.id());
                        responseData.setDisplayAddress(locationQueryResponse.displayAddress());
                        responseData.setCity(locationQueryResponse.city());
                        responseData.setCountry(locationQueryResponse.country());
                        responseData.setCountryCode(locationQueryResponse.countryCode());
                        responseData.setDisplayName(locationQueryResponse.displayName());
                        responseData.setHouseNumber(locationQueryResponse.houseNo());
                        responseData.setLatitude(Double.parseDouble(locationQueryResponse.latitude()));
                        responseData.setLongitude(Double.parseDouble(locationQueryResponse.longitude()));
                        responseData.setDisplayPlace(locationQueryResponse.displayPlace());
                        responseData.setName(locationQueryResponse.name());
                        responseData.setNeighbourhood(locationQueryResponse.neighbourhood());
                        responseData.setPostcode(locationQueryResponse.postCode());
                        responseData.setRoad(locationQueryResponse.road());
                        responseData.setState(locationQueryResponse.state());
                        responseData.setSuburb(locationQueryResponse.suburb());

                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.e("Error", e.toString());
                    }
                }
                );
        return responseData;
    }


}

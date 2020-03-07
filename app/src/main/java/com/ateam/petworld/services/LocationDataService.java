package com.ateam.petworld.services;

import android.util.Log;

import com.amazonaws.amplify.generated.graphql.CreateLocationMutation;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.ateam.petworld.models.Location;

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
}

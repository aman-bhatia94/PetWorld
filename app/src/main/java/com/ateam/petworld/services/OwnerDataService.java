package com.ateam.petworld.services;

import android.content.Context;
import android.util.Log;

import com.amazonaws.amplify.generated.graphql.CreateOwnerMutation;
import com.amazonaws.amplify.generated.graphql.GetOwnerQuery;
import com.amazonaws.amplify.generated.graphql.ListOwnersQuery;
import com.amazonaws.amplify.generated.graphql.UpdateOwnerMutation;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.ateam.petworld.activities.Profile;
import com.ateam.petworld.activities.RegisterActivity;
import com.ateam.petworld.models.Location;
import com.ateam.petworld.models.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import type.CreateOwnerInput;
import type.UpdateOwnerInput;

public class OwnerDataService {

    private AWSAppSyncClient awsAppSyncClient;

    public OwnerDataService(AWSAppSyncClient awsAppSyncClient) {
        this.awsAppSyncClient = awsAppSyncClient;
    }

    public void createOwner(Owner owner, Context context) {
        CreateOwnerInput requestObj = CreateOwnerInput.builder()
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .emailId(owner.getEmailId())
                .phoneNumber(owner.getPhoneNumber())
                .password("123")
                .ownerLocationId(owner.getLocation().getId())
                .build();
        awsAppSyncClient.mutate(CreateOwnerMutation.builder().input(requestObj).build()).enqueue(
                new GraphQLCall.Callback<CreateOwnerMutation.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<CreateOwnerMutation.Data> response) {
                        Log.i("Results", "Added Todo" + (response.data() != null ? Objects.requireNonNull(response.data().createOwner()).id() : null));
                        owner.setId(response.data().createOwner().id());
                        if (context instanceof RegisterActivity){
                            ((RegisterActivity)context).goToDashboard(owner);
                        }
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.e("Error", e.toString());
                    }
                }
        );
    }

    public Owner getOwner(Owner owner, Context context) {
        Owner responseData = new Owner();
        awsAppSyncClient.query(GetOwnerQuery.builder().id(owner.getId()).build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(new GraphQLCall.Callback<GetOwnerQuery.Data>() {
                             @Override
                             public void onResponse(@Nonnull Response<GetOwnerQuery.Data> response) {
                                 GetOwnerQuery.GetOwner ownerQueryResponse = response.data() != null ? response.data().getOwner() : null;
                                 if (ownerQueryResponse == null)
                                     return;
                                 responseData.setFirstName(ownerQueryResponse.firstName());
                                 responseData.setLastName(ownerQueryResponse.lastName());
                                 responseData.setPhoneNumber(ownerQueryResponse.phoneNumber());
                                 responseData.setEmailId(ownerQueryResponse.emailId());
                                 responseData.setPassword(ownerQueryResponse.password());
                                 responseData.setId(ownerQueryResponse.id());
                                 Location location = new Location();
                                 if (ownerQueryResponse.location() == null)
                                     return;
                                 location.setId(ownerQueryResponse.location().id());
                                 location.setLatitude(Double.parseDouble((ownerQueryResponse.location()).latitude()));
                                 location.setLongitude(Double.parseDouble((ownerQueryResponse.location()).longitude()));
                                 location.setDisplayName(ownerQueryResponse.location().displayName());
                                 responseData.setLocation(location);

                                 if (context instanceof Profile) {
                                     ((Profile) context).setOwner(responseData);
                                 }
                             }

                             @Override
                             public void onFailure(@Nonnull ApolloException e) {
                                 Log.e("Error", e.toString());
                             }
                         }
                );
        return responseData;
    }

    public void updateOwner(Owner owner) {
        UpdateOwnerInput requestObj = UpdateOwnerInput.builder()
                .id(owner.getId())
                .displayImage(owner.getDisplayImage())
                .emailId(owner.getEmailId())
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .ownerLocationId(owner.getLocation().getId())
                .password(owner.getPassword())
                .phoneNumber(owner.getPhoneNumber())
                .build();
        awsAppSyncClient.mutate(UpdateOwnerMutation.builder().input(requestObj).build()).enqueue(new GraphQLCall.Callback<UpdateOwnerMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<UpdateOwnerMutation.Data> response) {

            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {

            }
        });
    }

    public List<Owner> searchOwners() {
        List<Owner> responseData = new ArrayList<>();
        awsAppSyncClient.query(ListOwnersQuery.builder()
                .build())
                .responseFetcher(AppSyncResponseFetchers.NETWORK_ONLY)
                .enqueue(new GraphQLCall.Callback<ListOwnersQuery.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<ListOwnersQuery.Data> response) {
                        assert response.data() != null;
                        List<ListOwnersQuery.Item> ownerQueryResponse = Objects.requireNonNull(response.data().listOwners()).items();
                        assert ownerQueryResponse != null;
                        for (ListOwnersQuery.Item item : ownerQueryResponse) {
                            Owner eachOwner = new Owner();
                            eachOwner.setId(item.id());
                            eachOwner.setEmailId(item.emailId());
                            eachOwner.setPhoneNumber(item.phoneNumber());
                            eachOwner.setFirstName(item.firstName());
                            eachOwner.setLastName(item.lastName());
                            eachOwner.setPassword(item.password());
                            responseData.add(eachOwner);
                        }
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.e("Error", e.toString());
                    }
                });
        return responseData;
    }
}

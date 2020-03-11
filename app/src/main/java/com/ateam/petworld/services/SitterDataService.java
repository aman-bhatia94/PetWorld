package com.ateam.petworld.services;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.amazonaws.amplify.generated.graphql.CreateSitterMutation;
import com.amazonaws.amplify.generated.graphql.GetSitterQuery;
import com.amazonaws.amplify.generated.graphql.ListSittersQuery;
import com.amazonaws.amplify.generated.graphql.UpdateSitterMutation;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.ateam.petworld.activities.Profile;
import com.ateam.petworld.activities.SearchSitters;
import com.ateam.petworld.models.Location;
import com.ateam.petworld.models.Sitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import type.CreateSitterInput;
import type.UpdateSitterInput;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class SitterDataService {
    private AWSAppSyncClient awsAppSyncClient;


    public SitterDataService(AWSAppSyncClient awsAppSyncClient) {
        this.awsAppSyncClient = awsAppSyncClient;
    }

    public Sitter createSitter(Sitter sitter) {
        CreateSitterInput requestObj = CreateSitterInput.builder()
                .firstName(sitter.getFirstName())
                .lastName(sitter.getLastName())
                .emailId(sitter.getEmailId())
                .phoneNumber(sitter.getPhoneNumber())
                .password("123")
                .sitterLocationId(sitter.getLocation().getId())
                .displayImage(sitter.getDisplayImage())
                .payPerDay(sitter.getPayPerDay())
                .build();
        awsAppSyncClient.mutate(CreateSitterMutation.builder().input(requestObj).build()).enqueue(
                new GraphQLCall.Callback<CreateSitterMutation.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<CreateSitterMutation.Data> response) {
                        Log.i("Results", "Added Todo" + (response.data() != null ? Objects.requireNonNull(response.data().createSitter()).id() : null));
                        sitter.setId(response.data() != null ? response.data().createSitter().id() : null);
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.e("Error", e.toString());
                    }
                }
        );
        return sitter;
    }

    public Sitter getSitter(Sitter sitter, Context context) {
        Sitter responseData = new Sitter();
        awsAppSyncClient.query(GetSitterQuery.builder().id(sitter.getId()).build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(new GraphQLCall.Callback<GetSitterQuery.Data>() {
                             @Override
                             public void onResponse(@Nonnull Response<GetSitterQuery.Data> response) {
                                 GetSitterQuery.GetSitter ownerQueryResponse = response.data() != null ? response.data().getSitter() : null;
                                 if (ownerQueryResponse == null)
                                     return;
                                 responseData.setFirstName(ownerQueryResponse.firstName());
                                 responseData.setLastName(ownerQueryResponse.lastName());
                                 responseData.setPhoneNumber(ownerQueryResponse.phoneNumber());
                                 responseData.setEmailId(ownerQueryResponse.emailId());
                                 responseData.setId(ownerQueryResponse.id());
                                 responseData.setPayPerDay(ownerQueryResponse.payPerDay());
                                 Location location = new Location();
                                 if (ownerQueryResponse.location() == null)
                                     return;
                                 location.setId(Objects.requireNonNull(ownerQueryResponse.location()).id());
                                 location.setLatitude(Double.parseDouble(Objects.requireNonNull(ownerQueryResponse.location()).latitude()));
                                 location.setLongitude(Double.parseDouble(Objects.requireNonNull(ownerQueryResponse.location()).longitude()));
                                 location.setDisplayName(Objects.requireNonNull(ownerQueryResponse.location()).displayName());
                                 responseData.setLocation(location);

                                 if (context instanceof Profile){
                                     ((Profile)context).setSitter(responseData);
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

    public List<Sitter> searchSitters(Context context) {
        List<Sitter> responseData = new ArrayList<>();
        awsAppSyncClient.query(ListSittersQuery.builder()
                .build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(new GraphQLCall.Callback<ListSittersQuery.Data>() {
                             @RequiresApi(api = Build.VERSION_CODES.N)
                             @Override
                             public void onResponse(@Nonnull Response<ListSittersQuery.Data> response) {
                                 assert response.data() != null;
                                 List<ListSittersQuery.Item> sitterQueryResponse = Objects.requireNonNull(response.data().listSitters()).items();
                                 assert sitterQueryResponse != null;
                                 for (ListSittersQuery.Item item : sitterQueryResponse) {
                                     Sitter eachSitter = new Sitter();
                                     eachSitter.setId(item.id());
                                     eachSitter.setEmailId(item.emailId());
                                     eachSitter.setPhoneNumber(item.phoneNumber());
                                     eachSitter.setPassword(item.password());
                                     eachSitter.setFirstName(item.firstName());
                                     eachSitter.setLastName(item.lastName());
                                     eachSitter.setPayPerDay(item.payPerDay());
                                     Location location = new Location();
                                     if (item.location() == null)
                                         return;
                                     location.setId(Objects.requireNonNull(item.location()).id());
                                     location.setLatitude(Double.parseDouble(Objects.requireNonNull(item.location()).latitude()));
                                     location.setLongitude(Double.parseDouble(Objects.requireNonNull(item.location()).longitude()));
                                     location.setDisplayName(Objects.requireNonNull(item.location()).displayName());
                                     eachSitter.setLocation(location);
                                     responseData.add(eachSitter);
                                 }
                                 runOnUiThread(() -> {
                                     if (context instanceof SearchSitters) {
                                         ((SearchSitters) context).setSearchSitterResult(responseData);
                                     }
                                 });

                             }

                             @Override
                             public void onFailure(@Nonnull ApolloException e) {
                                 Log.e("Error", e.toString());
                             }
                         }
                );
        return responseData;
    }

    public void updateSitter(Sitter sitter) {
        UpdateSitterInput requestObj = UpdateSitterInput.builder()
                .id(sitter.getId())
                .displayImage(sitter.getDisplayImage())
                .emailId(sitter.getEmailId())
                .firstName(sitter.getFirstName())
                .lastName(sitter.getLastName())
                .sitterLocationId(sitter.getLocation().getId())
                .password(sitter.getPassword())
                .phoneNumber(sitter.getPhoneNumber())
                .payPerDay(sitter.getPayPerDay())
                .build();
        awsAppSyncClient.mutate(UpdateSitterMutation.builder().input(requestObj).build()).enqueue(new GraphQLCall.Callback<UpdateSitterMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<UpdateSitterMutation.Data> response) {

            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {

            }
        });
    }
}

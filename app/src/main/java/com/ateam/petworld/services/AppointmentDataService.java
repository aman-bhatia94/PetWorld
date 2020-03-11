package com.ateam.petworld.services;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.amazonaws.amplify.generated.graphql.CreateAppointmentMutation;
import com.amazonaws.amplify.generated.graphql.ListAppointmentsQuery;
import com.amazonaws.amplify.generated.graphql.OnUpdateAppointmentSubscription;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.AppSyncSubscriptionCall;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.ateam.petworld.activities.OwnerDashboard;
import com.ateam.petworld.activities.SitterDashboard;
import com.ateam.petworld.models.Appointments;
import com.ateam.petworld.models.Owner;
import com.ateam.petworld.models.Sitter;
import com.ateam.petworld.utils.ListFunctions;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import type.CreateAppointmentInput;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class AppointmentDataService {
    private AWSAppSyncClient awsAppSyncClient;
    private AppSyncSubscriptionCall subscriptionWatcher;
    List<Appointments> responseData;
    private AppSyncSubscriptionCall.Callback subCallback = new AppSyncSubscriptionCall.Callback() {
        @Override
        public void onResponse(@Nonnull Response response) {
            Log.i("Subscribe", response.data() != null ? response.data().toString() : null);
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }

        @Override
        public void onCompleted() {
            Log.i("Completed", "Subscription completed");
        }
    };

    public AppointmentDataService(AWSAppSyncClient awsAppSyncClient) {
        this.awsAppSyncClient = awsAppSyncClient;
    }

    private void subscribe() {
        OnUpdateAppointmentSubscription subscription = OnUpdateAppointmentSubscription.builder().build();
        subscriptionWatcher = awsAppSyncClient.subscribe(subscription);
        subscriptionWatcher.execute(subCallback);
    }

    public Appointments createAppointment(Appointments appointment) {
        CreateAppointmentInput requestObj = CreateAppointmentInput.builder()
                .appointmentOwnerId(appointment.getOwnerId())
                .appointmentSitterId(appointment.getSitterId())
                .startDate(appointment.getAppointmentStartDate())
                .endDate(appointment.getAppointmentEndDate())
                .totalAmount(appointment.getTotalAmount())
                .build();
        awsAppSyncClient.mutate(CreateAppointmentMutation.builder().input(requestObj).build()).enqueue(
                new GraphQLCall.Callback<CreateAppointmentMutation.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<CreateAppointmentMutation.Data> response) {
                        Log.i("Results", "Added Todo" + (response.data() != null ? Objects.requireNonNull(response.data().createAppointment()).id() : null));
                        appointment.setId(response.data().createAppointment().id());
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.e("Error", e.toString());
                    }
                }
        );
        return appointment;
    }


    public List<Appointments> getAllAppointments(Context context) {

        responseData = new ArrayList<>();
        awsAppSyncClient.query(ListAppointmentsQuery.builder()
                .build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(new GraphQLCall.Callback<ListAppointmentsQuery.Data>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(@Nonnull Response<ListAppointmentsQuery.Data> response) {
                        assert response.data() != null;
                        List<ListAppointmentsQuery.Item> appointmentQueryResponse = Objects.requireNonNull(response.data().listAppointments()).items();
                        assert appointmentQueryResponse != null;
                        for (ListAppointmentsQuery.Item item : appointmentQueryResponse) {
                            Appointments eachAppointment = new Appointments();
                            Sitter sitter = new Sitter();
                            if (item.sitter() != null) {
                                sitter.setId(item.sitter().id());
                                sitter.setPassword(item.sitter().password());
                                sitter.setEmailId(item.sitter().emailId());
                                sitter.setPayPerDay(item.sitter().payPerDay());
                                sitter.setFirstName(item.sitter().firstName());
                                sitter.setLastName(item.sitter().lastName());
                                sitter.setPhoneNumber(item.sitter().phoneNumber());
                                eachAppointment.setSitterId(item.sitter().id());
                            }

                            Owner owner = new Owner();
                            if (item.owner() != null) {
                                owner.setId(item.owner().id());
                                owner.setPassword(item.owner().password());
                                owner.setEmailId(item.owner().emailId());
                                owner.setFirstName(item.owner().firstName());
                                owner.setLastName(item.owner().lastName());
                                owner.setPhoneNumber(item.owner().phoneNumber());
                                eachAppointment.setOwnerId(item.owner().id());
                            }

                            eachAppointment.setId(item.id());
                            eachAppointment.setAppointmentStartDate(item.startDate());
                            eachAppointment.setAppointmentEndDate(item.endDate());
                            eachAppointment.setTotalAmount(item.totalAmount());
                            eachAppointment.setOwner(owner);
                            eachAppointment.setSitter(sitter);

                            responseData.add(eachAppointment);
                        }
                        responseData = responseData
                                .stream()
                                .filter(ListFunctions.distinctByKeys(Appointments::getId))

                                .collect(Collectors.toList());

                        runOnUiThread(() -> {
                            try {
                                if (context instanceof OwnerDashboard) {

                                    ((OwnerDashboard) context).setOwnerAppointmentList(responseData);
                                }

                                if (context instanceof SitterDashboard) {
                                    ((SitterDashboard) context).setSitterAppointmentList(responseData);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.e("Error", e.toString());
                    }
                });
        return responseData;
    }

}

package com.ateam.petworld.services;

import android.util.Log;

import com.amazonaws.amplify.generated.graphql.CreateAppointmentMutation;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.ateam.petworld.models.Appointments;

import java.util.Objects;

import javax.annotation.Nonnull;

import type.CreateAppointmentInput;

public class AppointmentDataService {
    private AWSAppSyncClient awsAppSyncClient;

    public AppointmentDataService(AWSAppSyncClient awsAppSyncClient) {
        this.awsAppSyncClient = awsAppSyncClient;
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

}

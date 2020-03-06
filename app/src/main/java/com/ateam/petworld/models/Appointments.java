package com.ateam.petworld.models;

public class Appointments {

    //appointment id
    private int id;

    //owner
    private Owner owner;

    //sitterId
    private Sitter sitter;

    private String appointmentStartDate;
    private String appointmentEndDate;

    private boolean upcomingAppointment;


    public int getId() {
        return id;
    }

    public Owner getOwner() {
        return owner;
    }

    public Sitter getSitter() {
        return sitter;
    }

    public String getAppointmentStartDate() {
        return appointmentStartDate;
    }

    public String getAppointmentEndDate() {
        return appointmentEndDate;
    }

    public boolean isUpcomingAppointment(){
        return upcomingAppointment;
    }
}

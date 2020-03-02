package com.ateam.petworld.models;

public class Appointments {

    //appointment id
    private int id;


    //owner
    private Owner owner;

    //sitterId
    private Sitter sitter;

    private String appointmentDate;
    private String appointMentTime;

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

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointMentTime() {
        return appointMentTime;
    }

    public boolean isUpcomingAppointment(){
        return upcomingAppointment;
    }
}

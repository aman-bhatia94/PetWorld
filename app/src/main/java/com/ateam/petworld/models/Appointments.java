package com.ateam.petworld.models;

public class Appointments {

    private String id;
    private Owner owner;
    private Sitter sitter;
    private String ownerId;
    private String sitterId;
    private String appointmentStartDate;
    private String appointmentEndDate;
    private boolean upcomingAppointment;
    private double totalAmount;

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getSitterId() {
        return sitterId;
    }

    public void setSitterId(String sitterId) {
        this.sitterId = sitterId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Sitter getSitter() {
        return sitter;
    }

    public void setSitter(Sitter sitter) {
        this.sitter = sitter;
    }

    public String getAppointmentStartDate() {
        return appointmentStartDate;
    }

    public void setAppointmentStartDate(String appointmentStartDate) {
        this.appointmentStartDate = appointmentStartDate;
    }

    public String getAppointmentEndDate() {
        return appointmentEndDate;
    }

    public void setAppointmentEndDate(String appointmentEndDate) {
        this.appointmentEndDate = appointmentEndDate;
    }

    public boolean isUpcomingAppointment() {
        return upcomingAppointment;
    }

    public void setUpcomingAppointment(boolean upcomingAppointment) {
        this.upcomingAppointment = upcomingAppointment;
    }
}

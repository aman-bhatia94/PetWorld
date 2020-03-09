package com.ateam.petworld.models;

import java.util.List;

public class PossibleLocations {

    List<Location> possibleLocations;

    public PossibleLocations(List<Location> possibleLocations) {
        this.possibleLocations = possibleLocations;
    }

    public List<Location> getPossibleLocations() {
        return possibleLocations;
    }
}

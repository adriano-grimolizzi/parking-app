package com.grimolizzi.ParkingApp.model;

import lombok.Data;

import java.util.Date;

@Data
public class ParkingSpot {

    private PossibleCarType possibleCarType;
    private boolean isInUse;
    private String licensePlateOfOccupyingCar;
    private Date timeOfArrival;

    public ParkingSpot(PossibleCarType possibleCarType) {
        this.possibleCarType = possibleCarType;
        this.isInUse = false;
    }
}

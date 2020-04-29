package com.grimolizzi.ParkingApp.model;

import lombok.Data;

import java.util.Date;

@Data
public class ParkingSpot {

    private String code;
    private PossibleCarType possibleCarType;

    private String licensePlateOfOccupyingCar;
    private Date timeOfArrival;
    private boolean inUse = false;

    public ParkingSpot(PossibleCarType possibleCarType) {
        this.possibleCarType = possibleCarType;
    }

    public ParkingSpot(String code, PossibleCarType possibleCarType) {
        this.code = code;
        this.possibleCarType = possibleCarType;
    }

    public void handleArrivalRequest(ArrivalRequest arrivalRequest) {
        this.inUse = true;
        this.licensePlateOfOccupyingCar = arrivalRequest.getCarLicensePlate();
        this.timeOfArrival = arrivalRequest.getArrivalDate();
    }

    public void reset() {
        this.licensePlateOfOccupyingCar = null;
        this.inUse = false;
        this.timeOfArrival = null;
    }
}

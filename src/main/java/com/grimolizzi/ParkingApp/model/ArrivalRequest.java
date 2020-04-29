package com.grimolizzi.ParkingApp.model;

import lombok.Data;

import java.util.Date;

@Data
public class ArrivalRequest {

    private PossibleCarType possibleCarType;

    private String carLicensePlate;
    private Date arrivalDate;

    public ArrivalRequest(
            PossibleCarType possibleCarType,
            String carLicensePlate,
            Date arrivalDate) {
        this.possibleCarType = possibleCarType;
        this.carLicensePlate = carLicensePlate;
        this.arrivalDate = arrivalDate;
    }
}

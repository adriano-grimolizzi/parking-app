package com.grimolizzi.ParkingApp.model;

import lombok.Data;

import java.util.Date;

@Data
public class DepartureRequest {

    private String carLicensePlate;
    private Date departureDate;

    public DepartureRequest() {}

    public DepartureRequest(String carLicensePlate, Date departureDate) {
        this.departureDate = departureDate;
        this.carLicensePlate = carLicensePlate;
    }
}

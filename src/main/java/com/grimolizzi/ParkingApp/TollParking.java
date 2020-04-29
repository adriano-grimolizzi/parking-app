package com.grimolizzi.ParkingApp;

import com.grimolizzi.ParkingApp.billingPolicies.BillingPolicy;
import com.grimolizzi.ParkingApp.errorHandling.CarIsNotPresentException;
import com.grimolizzi.ParkingApp.model.ArrivalRequest;
import com.grimolizzi.ParkingApp.model.DepartureRequest;
import com.grimolizzi.ParkingApp.model.ParkingSpot;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
public class TollParking {

    private List<ParkingSpot> parkingSpotList = new ArrayList<>();
    private BillingPolicy billingPolicy;

    public TollParking() { }

    public TollParking(BillingPolicy billingPolicy) {
        this.billingPolicy = billingPolicy;
    }

    public boolean handleArrival(ArrivalRequest request) {
        boolean requestHasBeenHandled = false;

        Optional<ParkingSpot> parkingSpotOptional = this.parkingSpotList.stream()
                .filter(parkingSpot -> !parkingSpot.isInUse())
                .filter(parkingSpot -> parkingSpot.getPossibleCarType().equals(request.getPossibleCarType()))
                .findAny();

        if (parkingSpotOptional.isPresent()) {
            ParkingSpot parkingSpot = parkingSpotOptional.get();
            parkingSpot.setInUse(true);
            parkingSpot.setLicensePlateOfOccupyingCar(request.getCarLicensePlate());
            parkingSpot.setTimeOfArrival(request.getArrivalDate());
            requestHasBeenHandled = true;
        }
        return requestHasBeenHandled;
    }

    public long handleDeparture(DepartureRequest request) throws CarIsNotPresentException {

        Date arrivalDate;

        Optional<ParkingSpot> parkingSpotOptional = this.parkingSpotList
                .stream()
                .filter(parkingSpot -> parkingSpot.getLicensePlateOfOccupyingCar().equals(request.getCarLicensePlate()))
                .findFirst();

        if (parkingSpotOptional.isPresent()) {
            ParkingSpot parkingSpot = parkingSpotOptional.get();
            parkingSpot.setInUse(false);
            parkingSpot.setLicensePlateOfOccupyingCar(null);
            arrivalDate = parkingSpot.getTimeOfArrival();
            parkingSpot.setTimeOfArrival(null);
        } else {
            throw new CarIsNotPresentException();
        }

        return this.billingPolicy.computeBill(arrivalDate, request.getDepartureDate());
    }
}

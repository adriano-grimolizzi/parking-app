package com.grimolizzi.ParkingApp;

import com.grimolizzi.ParkingApp.billingPolicies.BillingPolicy;
import com.grimolizzi.ParkingApp.errorHandling.CarIsNotPresentException;
import com.grimolizzi.ParkingApp.errorHandling.NoAvailableSpotException;
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

    public String handleArrival(ArrivalRequest request) throws NoAvailableSpotException {

        Optional<ParkingSpot> parkingSpotOptional = this.parkingSpotList.stream()
                .filter(parkingSpot -> !parkingSpot.isInUse()
                        && parkingSpot.getPossibleCarType().equals(request.getPossibleCarType()))
                .findAny();

        if (parkingSpotOptional.isPresent()) {
            ParkingSpot parkingSpot = parkingSpotOptional.get();
            parkingSpot.handleArrivalRequest(request);
            return parkingSpot.getCode();
        } else {
            throw new NoAvailableSpotException();
        }
    }

    public long handleDeparture(DepartureRequest request) throws CarIsNotPresentException {

        Optional<ParkingSpot> parkingSpotOptional = this.parkingSpotList
                .stream()
                .filter(parkingSpot -> parkingSpot.getLicensePlateOfOccupyingCar().equals(request.getCarLicensePlate()))
                .findFirst();

        if (parkingSpotOptional.isPresent()) {
            ParkingSpot parkingSpot = parkingSpotOptional.get();
            Date timeOfArrival = parkingSpot.getTimeOfArrival();
            parkingSpot.reset();
            return this.billingPolicy.computeBill(timeOfArrival, request.getDepartureDate());
        } else {
            throw new CarIsNotPresentException();
        }
    }
}

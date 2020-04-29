package com.grimolizzi.ParkingApp;

import com.grimolizzi.ParkingApp.billingPolicies.HourlyBillingPolicy;
import com.grimolizzi.ParkingApp.errorHandling.CarIsNotPresentException;
import com.grimolizzi.ParkingApp.model.DepartureRequest;
import com.grimolizzi.ParkingApp.model.ParkingSpot;
import com.grimolizzi.ParkingApp.model.PossibleCarType;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TollParkingDepartureTest {

    Date timeOfArrival = new Date(1575190800000L);    // Sun Dec 01 10:00:00 CET 2019
    Date timeOfDeparture = new Date(1575198000000L);  // Sun Dec 01 12:00:00 CET 2019

    @Test
    public void shouldCorrectlyHandleHappyPath() throws CarIsNotPresentException {

        TollParking tollParking = new TollParking();
        tollParking.setBillingPolicy(new HourlyBillingPolicy(1));

        ParkingSpot parkingSpot = new ParkingSpot(PossibleCarType.GASOLINE);
        parkingSpot.setInUse(true);
        parkingSpot.setLicensePlateOfOccupyingCar("ASDF00");
        parkingSpot.setTimeOfArrival(timeOfArrival);

        tollParking.getParkingSpotList().add(parkingSpot);

        DepartureRequest request = new DepartureRequest("ASDF00", timeOfDeparture);

        // the handle request method should return true
        assertEquals(2, tollParking.handleDeparture(request));

        // the parking spot should be set to not in use
        assertFalse(parkingSpot.isInUse());
    }

    @Test
    public void shouldThrowExceptionIfListIsEmpty() {

        TollParking tollParking = new TollParking();

        DepartureRequest request = new DepartureRequest("ASDF00", new Date());

        assertThrows(CarIsNotPresentException.class, () -> tollParking.handleDeparture(request));
    }

    @Test
    public void shouldThrowExceptionIfLicensePlateDoesNotMatch() {

        TollParking tollParking = new TollParking();

        ParkingSpot parkingSpot = new ParkingSpot(PossibleCarType.ELECTRIC_20KW);
        parkingSpot.setInUse(true);
        parkingSpot.setLicensePlateOfOccupyingCar("ASDF01");
        tollParking.getParkingSpotList().add(parkingSpot);

        DepartureRequest request = new DepartureRequest("ASDF00", new Date());

        assertThrows(CarIsNotPresentException.class, () -> tollParking.handleDeparture(request));
    }
}

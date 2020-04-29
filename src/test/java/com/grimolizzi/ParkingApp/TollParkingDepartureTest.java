package com.grimolizzi.ParkingApp;

import com.grimolizzi.ParkingApp.billingPolicies.HourlyBillingPolicy;
import com.grimolizzi.ParkingApp.errorHandling.CarIsNotPresentException;
import com.grimolizzi.ParkingApp.model.DepartureRequest;
import com.grimolizzi.ParkingApp.model.ParkingSpot;
import com.grimolizzi.ParkingApp.model.PossibleCarType;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class TollParkingDepartureTest {

    Date timeOfArrival = new Date(1575190800000L);    // Sun Dec 01 10:00:00 CET 2019
    Date timeOfDeparture = new Date(1575198000000L);  // Sun Dec 01 12:00:00 CET 2019

    @Test
    public void shouldCorrectlyHandleDepartureRequest() throws CarIsNotPresentException {

        TollParking tollParking = new TollParking();
        tollParking.setBillingPolicy(new HourlyBillingPolicy(1));

        ParkingSpot parkingSpot = new ParkingSpot(PossibleCarType.GASOLINE);
        parkingSpot.setInUse(true);
        parkingSpot.setLicensePlateOfOccupyingCar("ASDF00");
        parkingSpot.setTimeOfArrival(timeOfArrival);

        tollParking.getParkingSpotList().add(parkingSpot);

        DepartureRequest request = new DepartureRequest("ASDF00", timeOfDeparture);

        assertTrue("the handle request method should return true",
                tollParking.handleDeparture(request) == 2);

        assertFalse("the parking spot should be set to not in use",
                parkingSpot.isInUse());
    }

    @Test
    public void shouldReturnExceptionIfListIsEmpty() {

        TollParking tollParking = new TollParking();

        DepartureRequest request = new DepartureRequest("ASDF00", new Date());

        assertThrows(CarIsNotPresentException.class, () -> tollParking.handleDeparture(request));
    }

    @Test
    public void shouldReturnExceptionIfLicensePlateDoesNotMatch() {

        TollParking tollParking = new TollParking();

        ParkingSpot parkingSpot = new ParkingSpot(PossibleCarType.ELECTRIC_20KW);
        parkingSpot.setInUse(true);
        parkingSpot.setLicensePlateOfOccupyingCar("ASDF01");
        tollParking.getParkingSpotList().add(parkingSpot);

        DepartureRequest request = new DepartureRequest("ASDF00", new Date());

        assertThrows(CarIsNotPresentException.class, () -> tollParking.handleDeparture(request));
    }
}

package com.grimolizzi.ParkingApp;

import com.grimolizzi.ParkingApp.billingPolicies.HourlyBillingPolicy;
import com.grimolizzi.ParkingApp.errorHandling.CarIsNotPresentException;
import com.grimolizzi.ParkingApp.errorHandling.NoAvailableSpotException;
import com.grimolizzi.ParkingApp.model.ArrivalRequest;
import com.grimolizzi.ParkingApp.model.DepartureRequest;
import com.grimolizzi.ParkingApp.model.ParkingSpot;
import com.grimolizzi.ParkingApp.model.PossibleCarType;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class TollParkingTest {

    Date timeOfArrival = new Date(1575190800000L);    // Sun Dec 01 10:00:00 CET 2019
    Date timeOfDeparture = new Date(1575198000000L);  // Sun Dec 01 12:00:00 CET 2019

    @Test
    public void shouldCorrectlyHandleDepartureRequest() throws CarIsNotPresentException, NoAvailableSpotException {

        TollParking tollParking = new TollParking();
        tollParking.setBillingPolicy(new HourlyBillingPolicy(2));

        tollParking.getParkingSpotList().add(new ParkingSpot("Code01", PossibleCarType.GASOLINE));

        ArrivalRequest arrivalRequest = new ArrivalRequest(PossibleCarType.GASOLINE, "QWER01", timeOfArrival);

        DepartureRequest departureRequest = new DepartureRequest("QWER01", timeOfDeparture);

        assertEquals("the 'handleArrival' method should return the correct parking spot code",
                "Code01", tollParking.handleArrival(arrivalRequest));

        assertTrue("the handle departure method should return 4",
                tollParking.handleDeparture(departureRequest) == 4);
    }
}

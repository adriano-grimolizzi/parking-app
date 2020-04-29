package com.grimolizzi.ParkingApp;

import com.grimolizzi.ParkingApp.model.ArrivalRequest;
import com.grimolizzi.ParkingApp.model.ParkingSpot;
import com.grimolizzi.ParkingApp.model.PossibleCarType;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class TollParkingArrivalTest {

    @Test
    public void shouldCorrectlyHandleArrivalRequest() {

        TollParking tollParking = new TollParking();

        tollParking.getParkingSpotList().add(new ParkingSpot(PossibleCarType.GASOLINE));

        assertTrue("We should only have one element in the spot list...",
                tollParking.getParkingSpotList().size() == 1);
        assertTrue("...and that element should not be in use",
                !tollParking.getParkingSpotList().get(0).isInUse());

        ArrivalRequest request = new ArrivalRequest(PossibleCarType.GASOLINE, "QWER01", new Date());

        assertTrue("the 'handleArrival' method should return true",
                tollParking.handleArrival(request));
        assertTrue("the spot should now be in use",
                tollParking.getParkingSpotList().get(0).isInUse());
        assertEquals("the license plate should be correctly registered",
                request.getCarLicensePlate(),
                tollParking.getParkingSpotList().get(0).getLicensePlateOfOccupyingCar());
        assertEquals("the license plate should be correctly registered",
                request.getArrivalDate(),
                tollParking.getParkingSpotList().get(0).getTimeOfArrival());
    }

    @Test
    public void shouldReturnFalseIfListIsEmpty() {

        TollParking tollParking = new TollParking();

        ArrivalRequest request = new ArrivalRequest(PossibleCarType.GASOLINE, "QWER01", new Date());

        assertFalse("the 'handleArrival' method should return false",
                tollParking.handleArrival(request));
    }

    @Test
    public void shouldReturnFalseIfListDoesNotHaveCorrectType() {

        TollParking tollParking = new TollParking();

        tollParking.getParkingSpotList().add(new ParkingSpot(PossibleCarType.ELECTRIC_20KW));
        tollParking.getParkingSpotList().add(new ParkingSpot(PossibleCarType.ELECTRIC_50KW));

        ArrivalRequest request = new ArrivalRequest(PossibleCarType.GASOLINE, "QWER01", new Date());

        assertFalse("the 'handleArrival' method should return false",
                tollParking.handleArrival(request));
    }

    @Test
    public void shouldReturnFalseIfAllSpotAreInUse() {

        TollParking tollParking = new TollParking();

        ParkingSpot parkingSpot = new ParkingSpot(PossibleCarType.ELECTRIC_20KW);
        parkingSpot.setInUse(true);
        tollParking.getParkingSpotList().add(parkingSpot);

        ArrivalRequest request = new ArrivalRequest(PossibleCarType.ELECTRIC_20KW, "QWER01", new Date());

        assertFalse("the 'handleArrival' method should return false",
                tollParking.handleArrival(request));
    }
}

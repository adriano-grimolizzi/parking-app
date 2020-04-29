package com.grimolizzi.ParkingApp;

import com.grimolizzi.ParkingApp.errorHandling.NoAvailableSpotException;
import com.grimolizzi.ParkingApp.model.ArrivalRequest;
import com.grimolizzi.ParkingApp.model.ParkingSpot;
import com.grimolizzi.ParkingApp.model.PossibleCarType;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TollParkingArrivalTest {

    @Test
    public void shouldCorrectlyHandleArrivalRequest() throws NoAvailableSpotException {

        TollParking tollParking = new TollParking();

        List<ParkingSpot> mockedList = tollParking.getParkingSpotList();

        mockedList.add(new ParkingSpot("E50", PossibleCarType.ELECTRIC_20KW));
        mockedList.add(new ParkingSpot("1AG", PossibleCarType.GASOLINE));
        mockedList.add(new ParkingSpot("E20", PossibleCarType.ELECTRIC_50KW));

        ArrivalRequest request = new ArrivalRequest(PossibleCarType.GASOLINE, "QWER01", new Date());

        // "the 'handleArrival' method should return the correct parking spot code",
        assertEquals("1AG", tollParking.handleArrival(request));
        // the spot at index 1 should be in use...
        assertTrue(tollParking.getParkingSpotList().get(1).isInUse());
        // ...while the number 0 and 2 should NOT be in use...
        assertFalse(tollParking.getParkingSpotList().get(0).isInUse());
        assertFalse(tollParking.getParkingSpotList().get(2).isInUse());
        // the license plate should be correctly registered
        assertEquals(request.getCarLicensePlate(),
                tollParking.getParkingSpotList().get(1).getLicensePlateOfOccupyingCar());
        // "the arrival time should be correctly registered"
        assertEquals(request.getArrivalDate(),
                tollParking.getParkingSpotList().get(1).getTimeOfArrival());
    }

    @Test
    public void shouldThrowExceptionIfListIsEmpty() {

        TollParking tollParking = new TollParking();

        ArrivalRequest request = new ArrivalRequest(PossibleCarType.GASOLINE, "QWER01", new Date());

        assertThrows(NoAvailableSpotException.class, () -> tollParking.handleArrival(request));
    }

    @Test
    public void shouldThrowExceptionIfListDoesNotHaveCorrectType() {

        TollParking tollParking = new TollParking();

        tollParking.getParkingSpotList().add(new ParkingSpot(PossibleCarType.ELECTRIC_20KW));
        tollParking.getParkingSpotList().add(new ParkingSpot(PossibleCarType.ELECTRIC_50KW));

        ArrivalRequest request = new ArrivalRequest(PossibleCarType.GASOLINE, "QWER01", new Date());

        assertThrows(NoAvailableSpotException.class, () -> tollParking.handleArrival(request));
    }

    @Test
    public void shouldThrowExceptionIfAllSpotAreInUse() {

        TollParking tollParking = new TollParking();

        ParkingSpot parkingSpot = new ParkingSpot(PossibleCarType.ELECTRIC_20KW);
        parkingSpot.setInUse(true);
        tollParking.getParkingSpotList().add(parkingSpot);

        ArrivalRequest request = new ArrivalRequest(PossibleCarType.ELECTRIC_20KW, "QWER01", new Date());

        assertThrows(NoAvailableSpotException.class, () -> tollParking.handleArrival(request));
    }
}

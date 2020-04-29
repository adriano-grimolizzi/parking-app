package com.grimolizzi.ParkingApp;

import com.grimolizzi.ParkingApp.errorHandling.NoAvailableSpotException;
import com.grimolizzi.ParkingApp.model.ArrivalRequest;
import com.grimolizzi.ParkingApp.model.ParkingSpot;
import com.grimolizzi.ParkingApp.model.PossibleCarType;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class TollParkingArrivalTest {

    @Test
    public void shouldCorrectlyHandleArrivalRequest() throws NoAvailableSpotException {

        TollParking tollParking = new TollParking();

        List<ParkingSpot> mockedList = tollParking.getParkingSpotList();

        mockedList.add(new ParkingSpot("E50", PossibleCarType.ELECTRIC_20KW));
        mockedList.add(new ParkingSpot("1AG", PossibleCarType.GASOLINE));
        mockedList.add(new ParkingSpot("E20", PossibleCarType.ELECTRIC_50KW));

        ArrivalRequest request = new ArrivalRequest(PossibleCarType.GASOLINE, "QWER01", new Date());

        assertEquals("the 'handleArrival' method should return the correct parking spot code",
                "1AG", tollParking.handleArrival(request));
        assertTrue("the spot at index 1 should be in use...",
                tollParking.getParkingSpotList().get(1).isInUse());
        assertFalse("...while the number 0...",
                tollParking.getParkingSpotList().get(0).isInUse());
        assertFalse("...and number 2 should NOT be in use",
                tollParking.getParkingSpotList().get(2).isInUse());
        assertEquals("the license plate should be correctly registered",
                request.getCarLicensePlate(),
                tollParking.getParkingSpotList().get(1).getLicensePlateOfOccupyingCar());
        assertEquals("the arrival time should be correctly registered",
                request.getArrivalDate(),
                tollParking.getParkingSpotList().get(1).getTimeOfArrival());
    }

    @Test
    public void shouldReturnExceptionIfListIsEmpty() {

        TollParking tollParking = new TollParking();

        ArrivalRequest request = new ArrivalRequest(PossibleCarType.GASOLINE, "QWER01", new Date());

        assertThrows(NoAvailableSpotException.class, () -> tollParking.handleArrival(request));
    }

    @Test
    public void shouldReturnExceptionIfListDoesNotHaveCorrectType() {

        TollParking tollParking = new TollParking();

        tollParking.getParkingSpotList().add(new ParkingSpot(PossibleCarType.ELECTRIC_20KW));
        tollParking.getParkingSpotList().add(new ParkingSpot(PossibleCarType.ELECTRIC_50KW));

        ArrivalRequest request = new ArrivalRequest(PossibleCarType.GASOLINE, "QWER01", new Date());

        assertThrows(NoAvailableSpotException.class, () -> tollParking.handleArrival(request));
    }

    @Test
    public void shouldReturnExceptionIfAllSpotAreInUse() {

        TollParking tollParking = new TollParking();

        ParkingSpot parkingSpot = new ParkingSpot(PossibleCarType.ELECTRIC_20KW);
        parkingSpot.setInUse(true);
        tollParking.getParkingSpotList().add(parkingSpot);

        ArrivalRequest request = new ArrivalRequest(PossibleCarType.ELECTRIC_20KW, "QWER01", new Date());

        assertThrows(NoAvailableSpotException.class, () -> tollParking.handleArrival(request));
    }
}

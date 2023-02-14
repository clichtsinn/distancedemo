package com.cl.distancedemo;

import com.cl.distancedemo.distance.DistanceController;
import com.cl.distancedemo.distance.DistanceResponseDTO;
import com.cl.distancedemo.station.Station;

import com.cl.distancedemo.station.StationService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DistanceControllerTest {

    @Autowired
    private DistanceController distanceController;

    @Autowired
    private StationService stationService = mock(StationService.class);

    @Before
    public void setUp() {
        distanceController = new DistanceController();
        TestUtils.injectObjects(distanceController, "stationService", stationService);
    }

    @Test
    public void getDistance() throws Exception {

        String testFrom = "FF";
        String testTo = "BLS";
        Station stationFrom = new Station();
        stationFrom.setDs100("FF");
        stationFrom.setName("Frankfurt(Main)Hbf");
        stationFrom.setLongitude(new BigDecimal(8.663789));
        stationFrom.setLatitude(new BigDecimal(50.107145));
        Station stationTo = new Station();
        stationTo.setDs100("BLS");
        stationTo.setName("Berlin Hbf");
        stationTo.setLongitude(new BigDecimal(13.369545));
        stationTo.setLatitude(new BigDecimal(52.525592));

        when(stationService.getStationByDs100(testFrom)).thenReturn(stationFrom);
        when(stationService.getStationByDs100(testTo)).thenReturn(stationTo);

        final ResponseEntity<DistanceResponseDTO> response = distanceController.getDistance(testFrom, testTo);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        DistanceResponseDTO responseDTO = response.getBody();
        assertNotNull(responseDTO);
        assertEquals("Frankfurt(Main)Hbf", responseDTO.getFrom());
        assertEquals("Berlin Hbf", responseDTO.getTo());
        assertEquals(423L, (long) responseDTO.getDistance());
        assertEquals("km", responseDTO.getUnit());
    }

    @Test
    public void noStation() throws Exception {

        String testFrom = "from";
        String testTo = "to";

        final ResponseEntity<DistanceResponseDTO> response = distanceController.getDistance(testFrom, testTo);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

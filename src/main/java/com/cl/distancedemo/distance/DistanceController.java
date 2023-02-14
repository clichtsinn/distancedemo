package com.cl.distancedemo.distance;

import com.cl.distancedemo.station.Station;
import com.cl.distancedemo.station.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/distance")
public class DistanceController {

    private static final Logger log = LoggerFactory.getLogger(DistanceController.class);

    @Autowired
    StationService stationService;

    @GetMapping("/{fromDs100}/{toDs100}")
    public ResponseEntity<DistanceResponseDTO> getDistance(@PathVariable String fromDs100, @PathVariable String toDs100) {

        DistanceResponseDTOFactory responseDTOFactory = new DistanceResponseDTOFactory();
        Station fromStation = stationService.getStationByDs100(fromDs100);
        Station toStation = stationService.getStationByDs100(toDs100);

        if (fromStation == null || toStation == null) {
            log.warn("Exception: Station " + fromDs100 + " or " + toDs100 + " does not exist.");
            return ResponseEntity.notFound().build();
        }

        DistanceResponseDTO responseDTO = responseDTOFactory.createDistanceResponseDTO(fromStation, toStation);
        log.info("Success: Found distance between station " + fromStation.getDs100() + " and " + toStation.getDs100() + ".");

        return ResponseEntity.ok(responseDTO);
    }
}

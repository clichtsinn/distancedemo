package com.cl.distancedemo.station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public Long save(Station station) {

        return stationRepository.save(station).getId();
    }

    public Station getStationByDs100(String ds100) {

        Optional<Station> optionalStation = stationRepository.getByDs100(ds100);
        return optionalStation.orElse(null);
    }
}

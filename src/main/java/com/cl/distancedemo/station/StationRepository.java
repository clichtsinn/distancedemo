package com.cl.distancedemo.station;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    Optional<Station> getByDs100(String ds100);

}

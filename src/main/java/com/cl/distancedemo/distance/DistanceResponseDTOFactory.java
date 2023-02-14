package com.cl.distancedemo.distance;

import com.cl.distancedemo.station.Station;

import java.math.BigDecimal;

public class DistanceResponseDTOFactory {

    private static final String UNIT = "km";

    public DistanceResponseDTO createDistanceResponseDTO(Station from, Station to) {

        DistanceResponseDTO responseDTO = new DistanceResponseDTO();
        responseDTO.setFrom(from.getName());
        responseDTO.setTo(to.getName());
        Long distance = haversineDistance(from.getLongitude(), from.getLatitude(), to.getLongitude(), to.getLatitude());
        responseDTO.setDistance(distance);
        responseDTO.setUnit(UNIT);
        return responseDTO;
    }

    private Long haversineDistance(BigDecimal fromLong, BigDecimal fromLat, BigDecimal toLong, BigDecimal toLat) {

        Double radius = Double.valueOf(6371);

        Double longi1 = Math.toRadians(fromLong.doubleValue());
        Double longi2 = Math.toRadians(toLong.doubleValue());
        Double lati1 = Math.toRadians(fromLat.doubleValue());
        Double lati2 = Math.toRadians(toLat.doubleValue());
        Double diffLongi = longi2 - longi1;
        Double diffLati = lati2 - lati1;
        Double havLongi = Math.pow(Math.sin(diffLongi/2), 2);
        Double havLati = Math.pow(Math.sin(diffLati/2), 2);
        Double distanceDouble = radius * 2 * Math.asin(Math.sqrt(havLati + Math.cos(lati1) * Math.cos(lati2) * havLongi));
        Long distanceLong = distanceDouble.longValue();

        return distanceLong;
    }
}

package com.cl.distancedemo.start;

import com.cl.distancedemo.station.Station;
import com.cl.distancedemo.station.StationService;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Service
public class StartService {

    private static final Logger log = LoggerFactory.getLogger(StartService.class);

    @Autowired
    private StationService stationService;

    private static final String CSVLOCATION = "src/main/resources/D_Bahnhof_2020_alle.csv";
    private static final String DS100 = "DS100";
    private static final String NAME = "NAME";
    private static final String VERKEHR = "Verkehr";
    private static final String LONGITUDE = "Laenge";
    private static final String LATITUDE = "Breite";
    private static final String FV = "FV";

    @PostConstruct
    public void init() throws IOException, ParseException {

        log.info("Starting initialization.");

        Reader reader = new FileReader(CSVLOCATION);
        Iterable<CSVRecord> records = CSVFormat.newFormat(';')
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(reader);
        for (CSVRecord record : records) {
            if (record.get(VERKEHR).equals(FV)) {

                String ds100 = record.get(DS100);
                String name = record.get(NAME);
                String longitude = record.get(LONGITUDE);
                String latitude = record.get(LATITUDE);

                if (ds100.contains(",")) {
                    while (ds100.contains(",")) {
                        String[] ds100s = ds100.split(",", 2);
                        setStation(ds100s[0], name, longitude, latitude);
                        ds100 = ds100s[1];
                    }
                }
                setStation(ds100, name, longitude, latitude);
            }
        }
        log.info("Initialization finished.");
    }

    private void setStation(String ds100, String name, String longitude, String latitude) throws ParseException {
        Station station = new Station();
        station.setDs100(ds100);
        station.setName(name);
        BigDecimal longiDeci = new BigDecimal((Double) NumberFormat.getNumberInstance(Locale.GERMANY).parse(longitude));
        station.setLongitude(longiDeci);
        BigDecimal latiDeci = new BigDecimal((Double) NumberFormat.getNumberInstance(Locale.GERMANY).parse(latitude));
        station.setLatitude(latiDeci);
        stationService.save(station);
    }
}

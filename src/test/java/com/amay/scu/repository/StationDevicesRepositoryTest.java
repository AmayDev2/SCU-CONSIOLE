package com.amay.scu.repository;

import com.amay.scu.database.DatabaseConnector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class StationDevicesRepositoryTest {

    @Test
    void testGetAllStationDevices() {
        StationDevicesRepository stationDevicesRepository =StationDevicesRepository.getInstance();
        var data=stationDevicesRepository.getStationDevices();
        data.forEach(System.out::println);
        assertNotNull(data);


    }

    @Test
    void testGetAllTOMDevices() {
        TomDevicesRepository stationDevicesRepository =TomDevicesRepository.getInstance();
        var data=stationDevicesRepository.getTomDevices();
        data.forEach(System.out::println);
        assertNotNull(data);


    }

    @Test
    void testGetAllAGDevices() {
        AGDevicesRepository stationDevicesRepository =AGDevicesRepository.getInstance();
        var data=stationDevicesRepository.getAGDevices();
        data.forEach(System.out::println);
        assertNotNull(data);


    }

}
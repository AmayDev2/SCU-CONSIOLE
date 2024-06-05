package com.amay.scu.repository;

import com.amay.scu.database.DatabaseConnector;
import com.amay.scu.dto.StationDevicesDTO;
import com.amay.scu.dto.TOMDevicesDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TomDevicesRepository {
    private static TomDevicesRepository Instance;
    private static Connection connection = null;

    private TomDevicesRepository() {

//        throw new IllegalStateException("Utility class");
    }

    public static TomDevicesRepository getInstance() {

        if (Instance == null) {
            try {
                connection = DatabaseConnector.getConnection();
                Instance = new TomDevicesRepository();
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
        return Instance;
    }

    public boolean closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    return false;
    }


    public ResultSet getStationDeviceData(String equipId) {
        String query = "SELECT * FROM station_devices WHERE equip_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, equipId);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<TOMDevicesDTO> getTomDevices() {
        List<TOMDevicesDTO> tomDevices = new ArrayList<>();
        String query = "SELECT * FROM TOM_devices";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TOMDevicesDTO dto = new TOMDevicesDTO();
                dto.setEquipId(resultSet.getString("equip_id"));
                dto.setDeviceType(resultSet.getString("device_type"));
                dto.setReaderConnected(resultSet.getBoolean("reader_connected"));
                dto.setPrinterConnected(resultSet.getBoolean("printer_connected"));
                dto.setScannerConnected(resultSet.getBoolean("scanner_connected"));
                dto.setPduConnected(resultSet.getBoolean("pdu_connected"));
                dto.setUpsConnected(resultSet.getBoolean("ups_connected"));
                dto.setCashDrawerConnected(resultSet.getBoolean("cash_drawer_connected"));
                dto.setCardProcessMode(resultSet.getInt("card_process_mode"));
                dto.setSaleMode(resultSet.getInt("sale_mode"));
                tomDevices.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tomDevices;
    }

}

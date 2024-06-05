package com.amay.scu.repository;

import com.amay.scu.database.DatabaseConnector;
import com.amay.scu.dto.AGDevicesDTO;
import com.amay.scu.dto.TOMDevicesDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AGDevicesRepository {
    private static AGDevicesRepository Instance;
    private static Connection connection = null;

    private AGDevicesRepository() {

//        throw new IllegalStateException("Utility class");
    }

    public static AGDevicesRepository getInstance() {

        if (Instance == null) {
            try {
                connection = DatabaseConnector.getConnection();
                Instance = new AGDevicesRepository();
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


    public List<AGDevicesDTO> getAGDevices() {
        List<AGDevicesDTO> agDevices = new ArrayList<>();
        String query = "SELECT * FROM AG_devices";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AGDevicesDTO dto = new AGDevicesDTO();
                dto.setEquipId(resultSet.getString("equip_id"));
                dto.setGateType(resultSet.getString("gate_type"));
                dto.setGcuStatus(resultSet.getInt("gcu_status"));
                dto.setReader1Connected(resultSet.getBoolean("reader1_connected"));
                dto.setReader2Connected(resultSet.getBoolean("reader2_connected"));
                dto.setScanner1Connected(resultSet.getBoolean("scanner1_connected"));
                dto.setScanner2Connected(resultSet.getBoolean("scanner2_connected"));
                dto.setUpsConnected(resultSet.getBoolean("ups_connected"));
                dto.setAisleMode(resultSet.getInt("aisle_mode"));
                dto.setFlapMode(resultSet.getInt("flap_mode"));
                dto.setSpecialMode(resultSet.getInt("special_mode"));
                agDevices.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agDevices;
    }

}

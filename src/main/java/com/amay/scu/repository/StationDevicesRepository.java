package com.amay.scu.repository;

import com.amay.scu.database.DatabaseConnector;
import com.amay.scu.dto.StationDevicesDTO;
import com.amay.scu.enums.Zone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StationDevicesRepository {
    private static StationDevicesRepository Instance;
    private static Connection connection = null;

    private StationDevicesRepository() {

//        throw new IllegalStateException("Utility class");
    }

    public static StationDevicesRepository getInstance() {

        if (Instance == null) {
            try {
                connection = DatabaseConnector.getConnection();
                Instance = new StationDevicesRepository();
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


    public Optional<String> getCurrentStationName() {
        String query = "SELECT station_name FROM station LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return Optional.ofNullable(rs.getString("station_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }




    private ResultSet getAllStationDevices() {
        String query = "SELECT * FROM station_equipment";
//        String query = "SELECT * FROM station_equipment se LEFT JOIN device_location dl ON se.equipment_id = dl.device_name";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<StationDevicesDTO> getStationDevices() {
        List<StationDevicesDTO> stationDevices = new ArrayList<>();
        try {
            ResultSet resultSet = this.getAllStationDevices();
            while (resultSet.next()) {
                StationDevicesDTO dto = new StationDevicesDTO();
                dto.setEquipName(resultSet.getString("equipment_name").contains("TR") || resultSet.getString("equipment_name")
                        .contains("AG")?resultSet.getString("equipment_name").substring(0,2):resultSet.getString("equipment_name").substring(0,3));
                dto.setEquipType(resultSet.getString("equipment_type"));
                dto.setEquipId(resultSet.getString("equipment_id"));
                dto.setEquipIp(resultSet.getString("ip_address"));
                dto.setZone(Zone.TOM_ZONE_ONE.getByZone(resultSet.getString("layout_zone")));

//                dto.setScuConnected(resultSet.getInt("scu_connected"));
//                dto.setCcuConnected(resultSet.getInt("ccu_connected"));
//                dto.setFareTableVer(resultSet.getString("fare_table_ver"));
//                dto.setUsersVer(resultSet.getString("users_ver"));
//                dto.setSoftwareVer(resultSet.getString("software_ver"));
//                dto.setBlacklistVer(resultSet.getString("blacklist_ver"));
//                dto.setCalendarVer(resultSet.getString("calendar_ver"));
//                dto.setQrKeyVer(resultSet.getString("qr_key_ver"));
//                dto.setTicketVer(resultSet.getString("ticket_ver"));
//                dto.setOperationMode(resultSet.getInt("operation_mode"));
//                dto.setLastTxn(resultSet.getString("last_txn"));
                stationDevices.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stationDevices;
    }

}

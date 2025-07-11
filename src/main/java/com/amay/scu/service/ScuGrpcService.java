package com.amay.scu.service;


import com.amay.scu.enums.EquipmentType;
import com.amay.scu.enums.StationSpecialMode;
import com.amay.scu.enums.TripType;
import com.amay.scu.grpc.GrpcConfig;
import com.amay.scu.grpc.ScuGrpcConfig;
import com.amay.scu.report.controller.*;
import com.amay.scu.test_grpc_service.SCUService;
//import org.amaytechnosystems.SCUServiceGrpc;
import com.google.protobuf.ListValue;
import com.google.protobuf.Value;
import org.amaytechnosystems.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public enum ScuGrpcService  {
    INSTANCE;

    Logger logger= LoggerFactory.getLogger(ScuGrpcService.class);

    private SCUServiceGrpc.SCUServiceBlockingStub blockingStub=null;

    private SCUService scuService=null;

    public void  ScuGrpcService(SCUServiceGrpc.SCUServiceBlockingStub asyncStub ) {
        this.blockingStub=asyncStub;
    }

    public void reconnect() {
        blockingStub= ScuGrpcConfig.reconnect();
    }

    // Send some ConsoleStream messages
    public String sendMessage(SCUFareMediaRequestV1 requestV1) {
     System.out.println("Sending message to server: "+requestV1);
        SCUFareMediaResponseV1 response=this.blockingStub.getFareMedia(requestV1);
        System.out.println("Response from server: "+response);
        return response.getScuFareMediaData().getFareMediaCount().getFree();

    }
    public String getTotalRevenue(String fromDate) {
        try{
        SCURevenueReportRequestV1 request=SCURevenueReportRequestV1.newBuilder()
                .setFromDate(fromDate)
                .build();

        System.out.println("Sending message to server: "+request);
        SCURevenueReportResponseV1 response=this.blockingStub.getRevenue(request);
        System.out.println("Response from server: "+response);
        return  response.getRevenueData().getRevenue().getQrRevenue()+"-"+response.getRevenueData().getRevenue().getCscRevenue()+"-"+response.getRevenueData().getRevenue().getTotalRevenue()+"-"+response.getLastTransactionTime()+"-"+response.getRevenueData().getRevenue().getAgEntry()+"-"+response.getRevenueData().getRevenue().getAgExit();
        }catch (Exception e){
            e.printStackTrace();
            return "0-0-0-0-0-0";
        }
    }


    public String getTotalRevenue(String deviceId, String fromDate) {
        try{
            SCURevenueReportRequestV1 request=SCURevenueReportRequestV1.newBuilder()
                    .setDevice(ADevice.newBuilder().setDeviceId(deviceId).build())
                    .setFromDate(fromDate)
                    .build();

            System.out.println("Sending message to server: "+request);
            SCURevenueReportResponseV1 response=this.blockingStub.getRevenue(request);
            System.out.println("Response from server: "+response);
            return  response.getRevenueData().getRevenue().getQrRevenue()+"-"+response.getRevenueData().getRevenue().getCscRevenue()+"-"+response.getRevenueData().getRevenue().getTotalRevenue()+"-"+response.getLastTransactionTime()+"-"+response.getRevenueData().getRevenue().getAgEntry()+"-"+response.getRevenueData().getRevenue().getAgExit();
        }catch (Exception e){
            e.printStackTrace();
            return "0-0-0-0-0-0";
        }
    }
    public String getStockReport(String equipmentId, String s) {
        try{
        SCUStockRequestV1 requestV1=SCUStockRequestV1.newBuilder()
                .setStockData(SCUStockDataV1.newBuilder()
                        .setOperator(AOperator.newBuilder().setShiftId(s).build())
                        .setDevice(ADevice.newBuilder()
                        .setDeviceId(equipmentId).build()).build())
                .build();

        System.out.println("Sending message to server for stock: "+requestV1);
        SCUStockResponseV1 response=this.blockingStub.getStocks(requestV1);
        System.out.println("Response from server for stock: "+response);
        return String.valueOf(response.getStockData().getQrStock())+"-"+String.valueOf(response.getStockData().getQrSales())+"-"+String.valueOf(response.getStockData().getCscStock())+"-"+String.valueOf(response.getStockData().getCscSales());
        }catch (Exception e){
            e.printStackTrace();
            return "0-0-0-0";
        }
    }

    public void setStationCurrentMode(StationSpecialMode specialMode) {
        try {
         logger.info("Selected command: " + specialMode.name());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StationSpecialMode getStationCurrentMode() {
        StationSpecialMode specialMode=null;
        try {
            specialMode=StationSpecialMode.STATION_CLOSED;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return specialMode;
    }



    //response observer
    public void shutdown() {
        GrpcConfig.shutdown();
    }

    public List<RidershipReport> getRidershipReport(ListValue filters ) {
        try {
            RevenueReportListRequest request = RevenueReportListRequest.newBuilder()
                    .setReports(filters)
                    .setRequestMetaData(RequestMetaData.newBuilder()
                            .setRequestId("ridership-report-request")
                            .setRequestTime(String.valueOf(System.currentTimeMillis()))
                            .build())
                    .build();
            System.out.println("Sending ridership report request to server: " + request);
            RevenueReportListResponse response = this.blockingStub.getRevenueReports(request);
            System.out.println("Response from server for ridership Report: " + response);
            if (!response.getResponseMetaData().getErrorCode().equals("200")) {
                throw new RuntimeException("Error fetching ridership report: " + response.getResponseMetaData().getErrorMessage());
            }
            System.out.println("ridership report fetched successfully.");
            // Assuming response.getRevenueData().getReportsList() returns a list of RevenueReport objects
            if (response.getReports().getValuesList().isEmpty()) {
                System.out.println("No ridership reports available.");
                return List.of(); // Return an empty list if no reports are available
            }
            System.out.println("Number of ridership reports fetched: " + response.getReports().getValuesList().size());
            // Return the list of RevenueReport objects
            // Assuming RevenueReportListResponse has a method getReports() that returns a list of RevenueReport objects
            return convertFromListValueRidership(response.getReports());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch revenue report: " + e.getMessage());
        }

    }


    public List<RevenueReport> getRevenueReport(ListValue filters){

        try {

            RevenueReportListRequest request = RevenueReportListRequest.newBuilder().setReports(filters).build();


            System.out.println("Sending revenue report request to server: " + request);
            RevenueReportListResponse response = this.blockingStub.getRevenueReports(request);
            System.out.println("Response from server for Revenue Report: " + response);
            if (!response.getResponseMetaData().getErrorCode().equals("200")) {
                throw new RuntimeException("Error fetching revenue report: " + response.getResponseMetaData().getErrorMessage());
            }
            System.out.println("Revenue report fetched successfully.");
            // Assuming response.getRevenueData().getReportsList() returns a list of RevenueReport objects
            if (response.getReports().getValuesList().isEmpty()) {
                System.out.println("No revenue reports available.");
                return List.of(); // Return an empty list if no reports are available
            }
            System.out.println("Number of revenue reports fetched: " + response.getReports().getValuesList().size());
            // Return the list of RevenueReport objects
            // Assuming RevenueReportListResponse has a method getReports() that returns a list of RevenueReport objects
            return convertFromListValue(response.getReports());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch revenue report: " + e.getMessage());
        }

    }

    public List<RidershipReport> convertFromListValueRidership(ListValue listValue) {
        List<RidershipReport> dtoList = new ArrayList<>();

        for (Value rowValue : listValue.getValuesList()) {
            ListValue row = rowValue.getListValue();
            List<Value> values = row.getValuesList();

            RidershipReport dto = new RidershipReport();
            dto.setEquipmentId(values.get(0).getStringValue());
            dto.setStatus(values.get(1).getStringValue());
            dto.setEquipmentType(values.get(2).getStringValue());
            dto.setTime(values.get(3).getStringValue());
            dto.setTicketId(values.get(4).getStringValue());
            dtoList.add(dto);
        }

        return dtoList;
    }

    public List<RidershipReportPerDay> convertFromListValueRidershipPerDay(ListValue listValue) {
        List<RidershipReportPerDay> dtoList = new ArrayList<>();

        for (Value rowValue : listValue.getValuesList()) {
            ListValue row = rowValue.getListValue();
            List<Value> values = row.getValuesList();

            RidershipReportPerDay dto = new RidershipReportPerDay();

            dto.setDate(values.get(0).getStringValue());
            dto.setStation(values.get(1).getStringValue());

            dto.setEntryQR((int) values.get(2).getNumberValue());
            dto.setExitQR((int) values.get(3).getNumberValue());

            dto.setEntryNCMC((int) values.get(4).getNumberValue());
            dto.setExitNCMC((int) values.get(5).getNumberValue());

            dto.setEntryMQR((int) values.get(6).getNumberValue());
            dto.setExitMQR((int) values.get(7).getNumberValue());

            dto.setTotalEntry((int) values.get(8).getNumberValue());
            dto.setTotalExit((int) values.get(9).getNumberValue());

            dto.setTotalRidership((int) values.get(10).getNumberValue());

            dtoList.add(dto);
        }

        return dtoList;
    }

    public List<RidershipReportPerHour> convertFromListValueRidershipPerHour(ListValue listValue) {
        HashMap<String, RidershipReportPerHour> dtoMap = new HashMap<>();

        for (Value rowValue : listValue.getValuesList()) {
            ListValue row = rowValue.getListValue();
            List<Value> values = row.getValuesList();

            this.setTimeFrame(Objects.requireNonNull(dtoMap.computeIfAbsent(values.get(0).getStringValue(),
                    key->new RidershipReportPerHour(values.get(0).getStringValue()))), values);
        }
        List<RidershipReportPerHour> ridershipReportHours = new ArrayList<>(dtoMap.values());

        ridershipReportHours.forEach(x-> {
            x.setTotalEntry(x.getRidershipReportHours().stream().mapToInt(RidershipReportHour::getEntryCount).sum());
            x.setTotalExit(x.getRidershipReportHours().stream().mapToInt(RidershipReportHour::getExitCount).sum());
        });

        return ridershipReportHours;
    }

    private void setTimeFrame(RidershipReportPerHour dto, List<Value> values) {

        for (RidershipReportHour ridershipReportHour:dto.getRidershipReportHours()) {
            if( ridershipReportHour.getTimeSlot().equals(values.get(1).getStringValue())) {
                ridershipReportHour.setEntryCount((int) values.get(2).getNumberValue());
                ridershipReportHour.setExitCount((int) values.get(3).getNumberValue());
            }
        }
    }

    public List<RevenueReport> convertFromListValue(ListValue listValue) {
        List<RevenueReport> dtoList = new ArrayList<>();

        for (Value rowValue : listValue.getValuesList()) {
            ListValue row = rowValue.getListValue();
            List<Value> values = row.getValuesList();

            RevenueReport dto = new RevenueReport();
            dto.setTicketId(values.get(0).getStringValue());
            dto.setStation(values.get(1).getStringValue());
            dto.setEquipmentType(EquipmentType.EFO.getType(values.get(2).getStringValue()));
            dto.setEquipmentId(values.get(3).getStringValue());
            dto.setTripType(TripType.SJT.getType(values.get(4).getStringValue()));
            dto.setFareMedia(values.get(5).getStringValue());
            dto.setPaymentMode(values.get(6).getStringValue());
            dto.setAmount(Double.parseDouble(values.get(7).getStringValue()));
            // For ticketTime: parse the formatted string back to a timestamp (optional)
            // If original time is not available, you may skip or convert it back using SimpleDateFormat
            dto.setTicketTime((long) values.get(8).getNumberValue()); // Optional: you may ignore setting this if unneeded
            dto.setTransactionType(values.get(9).getStringValue());

            dtoList.add(dto);
        }

        return dtoList;
    }



    public String isAuthenticated(String username, String password) {
        try {
            SCUAuthenticationRequest request = SCUAuthenticationRequest.newBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .build();

            System.out.println("Sending authentication request to server: " + request);
            SCUAuthenticationResponse response = this.blockingStub.getAuthentication(request);
            System.out.println("Response from server for Authentication: " + response);
            if (response.hasResponseMetaData() && response.getResponseMetaData().getErrorCode().equals("200")) {
                System.out.println("User authenticated successfully.");
                return response.getToken();
            } else {
                System.out.println("Authentication failed.");
                throw new RuntimeException(response.getResponseMetaData().getErrorMessage());
            }
        } catch (Exception exception){
            throw  new RuntimeException(exception.getMessage());

        }
    }

    public List<RidershipReportPerHour> getRidershipParHourReport(ListValue filters) {
        try {
            RevenueReportListRequest request = RevenueReportListRequest.newBuilder()
                    .setReports(filters)
                    .setRequestMetaData(RequestMetaData.newBuilder()
                            .setRequestId("per-hour-ridership-report-request")
                            .setRequestTime(String.valueOf(System.currentTimeMillis()))
                            .build())
                    .build();

            System.out.println("Sending ridership report request to server: " + request);
            RevenueReportListResponse response = this.blockingStub.getRevenueReports(request);
            System.out.println("Response from server for ridership Report: " + response);
            if (!response.getResponseMetaData().getErrorCode().equals("200")) {
                throw new RuntimeException("Error fetching ridership report: " + response.getResponseMetaData().getErrorMessage());
            }
            System.out.println("per-hour-ridership report fetched successfully.");
            // Assuming response.getRevenueData().getReportsList() returns a list of RevenueReport objects
            if (response.getReports().getValuesList().isEmpty()) {
                System.out.println("No ridership reports available.");
                return List.of(); // Return an empty list if no reports are available
            }
            System.out.println("Number of ridership reports fetched: " + response.getReports().getValuesList().size());
            // Return the list of RevenueReport objects
            // Assuming RevenueReportListResponse has a method getReports() that returns a list of RevenueReport objects
            return convertFromListValueRidershipPerHour(response.getReports());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch revenue report: " + e.getMessage());
        }


    }

    public List<RidershipReportPerDay> getRidershipParDayReport(ListValue filters) {
        try {
            RevenueReportListRequest request = RevenueReportListRequest.newBuilder()
                    .setReports(filters)
                    .setRequestMetaData(RequestMetaData.newBuilder()
                            .setRequestId("per-day-ridership-report-request")
                            .setRequestTime(String.valueOf(System.currentTimeMillis()))
                            .build())
                    .build();

            System.out.println("Sending ridership report request to server: " + request);
            RevenueReportListResponse response = this.blockingStub.getRevenueReports(request);
            System.out.println("Response from server for ridership Report: " + response);
            if (!response.getResponseMetaData().getErrorCode().equals("200")) {
                throw new RuntimeException("Error fetching ridership report: " + response.getResponseMetaData().getErrorMessage());
            }
            System.out.println("per-day-ridership report fetched successfully.");
            // Assuming response.getRevenueData().getReportsList() returns a list of RevenueReport objects
            if (response.getReports().getValuesList().isEmpty()) {
                System.out.println("No ridership reports available.");
                return List.of(); // Return an empty list if no reports are available
            }
            System.out.println("Number of ridership reports fetched: " + response.getReports().getValuesList().size());
            // Return the list of RevenueReport objects
            // Assuming RevenueReportListResponse has a method getReports() that returns a list of RevenueReport objects
            return convertFromListValueRidershipPerDay(response.getReports());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch revenue report: " + e.getMessage());
        }


    }

    public List<ShiftReport> getShiftReport(ListValue filters) {
        try {
            RevenueReportListRequest request = RevenueReportListRequest.newBuilder()
                    .setReports(filters)
                    .setRequestMetaData(RequestMetaData.newBuilder()
                            .setRequestId("shift-report-request")
                            .setRequestTime(String.valueOf(System.currentTimeMillis()))
                            .build())
                    .build();

            System.out.println("Sending shift report request to server: " + request);
            RevenueReportListResponse response = this.blockingStub.getRevenueReports(request);
            System.out.println("Response from server for shift Report: " + response);
            if (!response.getResponseMetaData().getErrorCode().equals("200")) {
                throw new RuntimeException("Error fetching shift report: " + response.getResponseMetaData().getErrorMessage());
            }
            System.out.println("shift report fetched successfully.");
            // Assuming response.getRevenueData().getReportsList() returns a list of RevenueReport objects
            if (response.getReports().getValuesList().isEmpty()) {
                System.out.println("No ridership reports available.");
                return List.of(); // Return an empty list if no reports are available
            }
            System.out.println("Number of ridership reports fetched: " + response.getReports().getValuesList().size());
            // Return the list of RevenueReport objects
            // Assuming RevenueReportListResponse has a method getReports() that returns a list of RevenueReport objects
            return convertFromListValueShiftReport(response.getReports());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch revenue report: " + e.getMessage());
        }
    }

    private List<ShiftReport> convertFromListValueShiftReport(ListValue reports) {
        List<ShiftReport> dtoList = new ArrayList<>();

        for (Value rowValue : reports.getValuesList()) {
            ListValue row = rowValue.getListValue();
            List<Value> values = row.getValuesList();

            ShiftReport dto = new ShiftReport();

            dto.setShiftId(values.get(0).getStringValue());
            dto.setOperatorId(values.get(1).getStringValue());
            dto.setDate(values.get(2).getStringValue());
            dto.setLoginTime(values.get(3).getStringValue());
            dto.setLogoutTime(values.get(4).getStringValue());

//            dto.setRefundedCount((int) values.get(5).getNumberValue());
//            dto.setRefundedAmount(values.get(6).getNumberValue());
//
//            dto.setCanceledCount((int) values.get(7).getNumberValue());
//            dto.setCanceledAmount(values.get(8).getNumberValue());
//
//            dto.setReplacedCount((int) values.get(9).getNumberValue());
//            dto.setReplacedAmount(values.get(10).getNumberValue());
//
//            dto.setAdjustedCount((int) values.get(11).getNumberValue());
//            dto.setAdjustedAmount(values.get(12).getNumberValue());

            dto.setQR((int) values.get(13).getNumberValue());
            dto.setCASH((int) values.get(14).getNumberValue());
            dto.setUPI((int) values.get(15).getNumberValue());
            dto.setPOS((int) values.get(16).getNumberValue());
            dto.setRevenue((int) values.get(17).getNumberValue());

            dto.setEquipmentId(values.get(18).getStringValue());
            dto.setEquipmentType(EquipmentType.EFO.getType(values.get(19).getStringValue()));

            dtoList.add(dto);
        }

        return dtoList;
    }

}

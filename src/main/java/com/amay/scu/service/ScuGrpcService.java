package com.amay.scu.service;


import com.amay.scu.enums.StationSpecialMode;
import com.amay.scu.grpc.GrpcConfig;
import com.amay.scu.grpc.ScuGrpcConfig;
import com.amay.scu.test_grpc_service.SCUService;
//import org.amaytechnosystems.SCUServiceGrpc;
import org.amaytechnosystems.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
        return  response.getRevenueData().getRevenue().getQrRevenue()+"-"+response.getRevenueData().getRevenue().getCscRevenue()+"-"+response.getRevenueData().getRevenue().getTotalRevenue()+"-"+response.getLastTransactionTime();
        }catch (Exception e){
            e.printStackTrace();
            return "0-0-0-0";
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
            return  response.getRevenueData().getRevenue().getQrRevenue()+"-"+response.getRevenueData().getRevenue().getCscRevenue()+"-"+response.getRevenueData().getRevenue().getTotalRevenue()+"-"+response.getLastTransactionTime();
        }catch (Exception e){
            e.printStackTrace();
            return "0-0-0-0";
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



    //  response observer
    public void shutdown() {
        GrpcConfig.shutdown();
    }


}

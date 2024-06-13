package com.amay.scu;

import org.network.monitorandcontrol.MonitorAndControlGrpc;
import org.network.monitorandcontrol.RequestType;
import org.network.monitorandcontrol.ag.AGDeviceInfo;
import org.network.monitorandcontrol.ag.AGModeControl;
import org.network.monitorandcontrol.ag.AGParameterVersion;
import org.network.monitorandcontrol.ag.AGPeripheralStatus;
import org.network.monitorandcontrol.ag.AGProtocol;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GateClient {

    private final ManagedChannel channel;
    private final MonitorAndControlGrpc.MonitorAndControlStub stub;
    private StreamObserver<AGProtocol> streamClientRequest;
    private final AGProtocol.Builder request_data = AGProtocol.newBuilder();

    public GateClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.stub = MonitorAndControlGrpc.newStub(channel);
    }

    public StreamObserver<AGProtocol> getServerResponseObserver() {
        return new StreamObserver<AGProtocol>() {

            @Override
            public void onNext(AGProtocol value) {
                switch (value.getCommandType()) {
                    case GET_DEVICE_INFO:
                        // send device info
                        break;
                    case GET_PERIPHERAL_STATUS:
                        // send peripheral status
                        break;
                    case GET_DIVICE_VERSIONS:
                        // send parameter versions
                        break;
                    case MODE_CONTROL:
                        System.out.println("Received Control Command From Server...");
                        try {
                            AGModeControl mode = value.getRequestData().unpack(AGModeControl.class);
                            System.out.println("Operation Mode: " + mode.getOperationMode().toString());
                            System.out.println("Aisle Mode: " + mode.getAisleMode().toString());
                            System.out.println("Flap Mode: " + mode.getFlapMode().toString());
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }
                    default:
                        System.out.println(value.getStatusCode());
                        break;
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println();
                System.out.println("Connection Error: " + t.getMessage());
                streamClientRequest = null;
                if (t instanceof io.grpc.StatusRuntimeException) {
                    io.grpc.StatusRuntimeException statusRuntimeException = (io.grpc.StatusRuntimeException) t;
                    if (statusRuntimeException.getStatus().getCode() == io.grpc.Status.Code.CANCELLED) {
                        System.out.println("Request was cancelled: " + t.getMessage());
                    } else {
                        System.out.println("Error: " + t.getMessage());
                    }
//                    MonitoringServer.generateLog("Client Error :" + t.getMessage(), true);
                } else {
                    System.out.println("Error: " + t.getMessage());
                }
                try {
                    Thread.sleep(2000);
                    sendRequest(RequestType.UNRECOGNIZED, null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCompleted() {
                System.out.println("Server completed sending responses.");
            }
        };
    }

    public static void makeRequestCycle(String equipid, int i) {
        try {
            GateClient client = new GateClient("10.40.111.90", 8000);
            AGDeviceInfo deviceinfo = AGDeviceInfo.newBuilder()
                    .setEquipId(equipid)
                    .setEquipName("TEST-AG-" + equipid)
                    .build();
            client.sendRequest(RequestType.DEVICE_INFO, Any.pack(deviceinfo));
            Thread.sleep(10000);
            AGPeripheralStatus peripheral = AGPeripheralStatus.newBuilder()
                    .setScuConnected(i % 2 == 0)
                    .setCcuConnected(i % 2 == 1)
                    .setReader1Connected(i % 3 == 0)
                    .setScanner1Connected(i % 5 == 0)
                    .build();
            client.sendRequest(RequestType.PERIPHERAL_STATUS, Any.pack(peripheral));
            Thread.sleep(1000);
            AGParameterVersion version_check = AGParameterVersion.newBuilder()
                    .setAgSoftware("1.0.2.4")
                    .setQrKey(Integer.toString(i))
                    .build();
            client.sendRequest(RequestType.PARAMETER_VERSION, Any.pack(version_check));
            Thread.sleep(1000);
            client.streamClientRequest.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(RequestType request_type, Any data) {
        if (streamClientRequest == null) {
            streamClientRequest = stub.agStream(getServerResponseObserver());
        }
        try {
            request_data.setRequestData(data);
            request_data.setRequestType(request_type);

            System.out.println("Sending Data");
            AGProtocol request = request_data.build();
            streamClientRequest.onNext(request);
        } catch (Exception e) {
            System.out.println("Error sending request: " + e.getMessage());
        }
    }

    public void endConnection() {
        if (streamClientRequest != null) {
            streamClientRequest.onCompleted();
        }
        channel.shutdown();
    }
}

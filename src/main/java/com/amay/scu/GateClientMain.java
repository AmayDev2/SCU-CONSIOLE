package com.amay.scu;

import java.util.Random;
import java.util.Scanner;

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

public class GateClientMain {
	
	private final ManagedChannel channel;
	private final MonitorAndControlGrpc.MonitorAndControlStub stub;
	private StreamObserver<AGProtocol> streamClientRequest;
	private final AGProtocol.Builder request_data = AGProtocol.newBuilder();
	  
	public GateClientMain(String host, int port) {
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
					//send device info
					break;
				case GET_PERIPHERAL_STATUS:
					//send peripheral status
					break;
				case GET_DIVICE_VERSIONS:
					//send parameter versions
					break;
				case MODE_CONTROL:
					System.out.println("Recieved Control Command From Server...");
					try {
						AGModeControl mode = value.getRequestData().unpack(AGModeControl.class);
						System.out.println("Operation Mode : "+mode.getOperationMode().toString());
						System.out.println("Aisle Mode : "+mode.getAisleMode().toString());
						System.out.println("Flap Mode : "+mode.getFlapMode().toString());
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
                System.out.println();
                System.out.println("Connection Error: " + t.getMessage());
                streamClientRequest = null;
                try {
      			  Thread.sleep(2000);
      			  sendRequest(RequestType.UNRECOGNIZED, null);
      		  } catch (InterruptedException e) {
      			  // TODO Auto-generated catch block
      			  e.printStackTrace();
      		  }
            }

            @Override
            public void onCompleted() {
            	System.out.println("Server completed sending responses.");
            }
        };
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
		streamClientRequest.onCompleted();
		}
	
	public static void main(String[] args) {
		GateClientMain client = new GateClientMain("10.40.111.90", 8000);
		Scanner scanner = new Scanner(System.in);
		
		boolean alive = true;
		Random rand = new Random();
		int i = 0;
		AGDeviceInfo deviceinfo = AGDeviceInfo.newBuilder()
				.setEquipId("AG-01")
				.setEquipName("TEST-AG-00001")
				.build();
		client.sendRequest(RequestType.DEVICE_INFO, Any.pack(deviceinfo));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (i<10000) {
			int message = rand.nextInt(1, 4);
			System.out.println(message);
			
			switch (message) {
			case 1:
//				System.out.println("Sending Gate Info");
				AGDeviceInfo device_info = AGDeviceInfo.newBuilder()
					.setEquipId("AG-01")
					.setEquipName("TEST-AG-00001")
					.build();
				client.sendRequest(RequestType.DEVICE_INFO, Any.pack(device_info));
				break;
			case 2:
				AGPeripheralStatus peripheral = AGPeripheralStatus.newBuilder()
                      .setScuConnected(i%3 == 0)
                      .setCcuConnected(i%3 == 0)
                      .build();
                  client.sendRequest(RequestType.PERIPHERAL_STATUS, Any.pack(peripheral));
                  break;
			case 3:
				AGParameterVersion version_check = AGParameterVersion.newBuilder()
				.setAgSoftware("1.0.2.4")
				.build();
				client.sendRequest(RequestType.PARAMETER_VERSION, Any.pack(version_check));
				break;
			default:
				System.out.println("Invalid input. Please enter a valid message type.");
				break;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		client.endConnection();
		scanner.close();
	}
}

package com.amay.scu.clients;

import java.util.Scanner;

import org.network.monitorandcontrol.MonitorAndControlGrpc;
import org.network.monitorandcontrol.RequestType;
import org.network.monitorandcontrol.tom.TOMDeviceInfo;
import org.network.monitorandcontrol.tom.TOMParameterVersion;
import org.network.monitorandcontrol.tom.TOMPeripheralStatus;
import org.network.monitorandcontrol.tom.TOMProtocol;

import com.google.protobuf.Any;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class EFIClientContentiousPhe {
	private final ManagedChannel channel;
	private final MonitorAndControlGrpc.MonitorAndControlStub stub;
	private StreamObserver<TOMProtocol> streamClientRequest;
	private final TOMProtocol.Builder request_data = TOMProtocol.newBuilder();
	  
	public EFIClientContentiousPhe(String host, int port) {
		this.channel = ManagedChannelBuilder.forAddress(host, port)
				.usePlaintext()
				.build();
		this.stub = MonitorAndControlGrpc.newStub(channel);
	  }
	
	public StreamObserver<TOMProtocol> getServerResponseObserver() {
		return new StreamObserver<TOMProtocol>() {
			
			@Override
			public void onNext(TOMProtocol value) {
				
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
					System.out.println("Received Control Command From Server...");
//					try {
//						AGModeControl mode = value.getRequestData().unpack(AGModeControl.class);
//						System.out.println("Operation Mode : "+mode.getOperationMode().toString());
//						System.out.println("Aisle Mode : "+mode.getAisleMode().toString());
//						System.out.println("Flap Mode : "+mode.getFlapMode().toString());
//					} catch (InvalidProtocolBufferException e) {
//						e.printStackTrace();
//					}
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
            streamClientRequest = stub.tomStream(getServerResponseObserver());
        }
        try {
      	  request_data.setRequestData(data);
          request_data.setRequestType(request_type);

          TOMProtocol request = request_data.build();
          streamClientRequest.onNext(request);
          } catch (Exception e) {
          System.out.println("Error sending request: " + e.getMessage());
          }
        }
	
	public void endConnection() {
		streamClientRequest.onCompleted();
		}
	
	public static void main(String[] args) throws InterruptedException {
		EFIClientContentiousPhe client = new EFIClientContentiousPhe("192.168.137.100", 8000);
		Scanner scanner = new Scanner(System.in);
		
		boolean alive = true;
		TOMDeviceInfo device_info = TOMDeviceInfo.newBuilder()
				.setEquipId("TOM1")
				.setDeviceType("TOM")
				.setTomIp("192.168.1.1")
				.setEquipName("TOM1")
				.build();
		client.sendRequest(RequestType.DEVICE_INFO, Any.pack(device_info));
		while (alive) {
			System.out.println("Enter message to send to server (or '0' to quit): ");
			int message =scanner.nextInt();
			Thread.sleep(100);
			
			switch (message) {
			case 0:
				alive = false;
				break;
			case 1:
				TOMDeviceInfo device_info1 = TOMDeviceInfo.newBuilder()
						.setEquipId("TOM1")
						.setDeviceType("TOM")
						.setTomIp("192.168.1.1")
						.setEquipName("TOM1")
					.build();
				client.sendRequest(RequestType.DEVICE_INFO, Any.pack(device_info1));
				break;
			case 2:
				TOMPeripheralStatus peripheral = TOMPeripheralStatus.newBuilder()
//                      .setScuConnected(true)
//                      .setCcuConnected(true)
                      .build();
                  client.sendRequest(RequestType.PERIPHERAL_STATUS, Any.pack(peripheral));

				  try {
					  Thread.sleep(100);
				  }catch (Exception e){
					  e.printStackTrace();
				  }
				TOMPeripheralStatus peripheral1 = TOMPeripheralStatus.newBuilder()
//                      .setScuConnected(true)
//                      .setCcuConnected(true)
						.build();
				client.sendRequest(RequestType.PERIPHERAL_STATUS, Any.pack(peripheral1));
				break;
			case 3:
				TOMParameterVersion version_check = TOMParameterVersion.newBuilder()
				.setTomSoftware("1.0.2.4")
				.build();
				client.sendRequest(RequestType.PARAMETER_VERSION, Any.pack(version_check));
				break;
			default:
				System.out.println("Invalid input. Please enter a valid message type.");
				break;
			}
		}
		client.endConnection();
		scanner.close();
	}
}

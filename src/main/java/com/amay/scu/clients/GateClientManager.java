package com.amay.scu.clients;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GateClientManager {

    private static final int NUMBER_OF_CLIENTS = 10000;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CLIENTS);

        for (int i = 0; i < NUMBER_OF_CLIENTS; i++) {
            int clientId = i;
            executorService.submit(() -> {
//                GateClient client = new GateClient(HOST, PORT);
                String equipId = String.format("03%03d", clientId);  // e.g., 03000, 03001, ...
                GateClient.makeRequestCycle(equipId, clientId);
//                client.endConnection();
            });
            if (clientId == 1000 || clientId == 2000 || clientId == 3000 || clientId == 4000 || clientId == 5000 || clientId == 6000 || clientId == 7000|| clientId == 8000 || clientId == 9000 || clientId == 10000) {
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        }

        // Properly shut down the executor service after all tasks are complete
        executorService.shutdown();
    }
}

package com.amay.scu.command;

import com.amay.scu.grpc.GrpcConfig;
import com.amay.scu.service.GrpcService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommandTest {

    @Test
    void testCommand() {
        // Call the method to test


        GrpcService grpcService = new GrpcService(GrpcConfig.getAsyncStub());
        grpcService.initialConnectionRequest(null);

        // Add assertions based on what you expect to happen
        // For example, if initialConnectionRequest modifies some state, check that here
        // e.g., assertEquals(expected, actual);
    }
}

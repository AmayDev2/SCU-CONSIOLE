module com.amay.scu {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javax.websocket.api;
    requires tyrus.client;
    requires spring.webflux;
    requires reactor.core;
    requires redis.clients.jedis;
    requires org.slf4j;
    requires jakarta.annotation;
    requires io.grpc;
    requires io.grpc.stub;
    requires io.grpc.protobuf;
    requires protobuf.java;

    opens com.amay.scu to javafx.fxml;
    exports com.amay.scu;
    exports com.amay.scu.controller.station_map;
    exports com.amay.scu.controller;
    opens com.amay.scu.controller to javafx.fxml;
}
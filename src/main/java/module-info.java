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
    requires java.sql;
    requires static lombok;
    requires java.desktop;

    opens com.amay.scu to javafx.fxml;
    exports com.amay.scu;
    exports com.amay.scu.grpc;


    exports com.amay.scu.controller;
    exports org.network.monitorandcontrol.ag;
    exports org.network.monitorandcontrol.tom;
    exports org.network.monitorandcontrol.scu_console;

    opens com.amay.scu.controller to javafx.fxml;
    exports com.amay.scu.clients;
    opens com.amay.scu.clients to javafx.fxml;
}
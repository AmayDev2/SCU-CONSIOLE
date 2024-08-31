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
    requires com.fasterxml.jackson.databind;
    requires com.google.common;

    opens com.amay.scu to javafx.fxml;
    exports com.amay.scu;
    exports com.amay.scu.grpc;


    exports com.amay.scu.controller;
    exports org.network.monitorandcontrol.ag;
    exports org.network.monitorandcontrol.tom;
    exports org.network.monitorandcontrol.scu_console;
    exports org.network.monitorandcontrol;
    exports com.amay.scu.controller.components;
    exports com.amay.scu.enums;
    exports com.amay.scu.model;
    exports com.amay.scu.clients;
    exports org.amaytechnosystems;
    opens com.amay.scu.grpc to com.fasterxml.jackson.databind;


    opens com.amay.scu.model to com.fasterxml.jackson.databind;
    opens com.amay.scu.controller to javafx.fxml;
    opens com.amay.scu.controller.components to javafx.fxml;
    opens com.amay.scu.clients to javafx.fxml;
    opens com.amay.scu.enums to com.fasterxml.jackson.databind;
}
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MonitoringAndControl.proto

package org.network.monitorandcontrol;

public final class MonitoringAndControl {
  private MonitoringAndControl() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\032MonitoringAndControl.proto\022\024org.monito" +
      "ring.proto\032\031google/protobuf/any.proto\032\tT" +
      "OM.proto\032\010AG.proto2\300\001\n\021MonitorAndControl" +
      "\022S\n\010AGStream\022\037.org.monitoring.proto.AGRe" +
      "quest\032 .org.monitoring.proto.AGResponse\"" +
      "\000(\0010\001\022V\n\tTOMStream\022 .org.monitoring.prot" +
      "o.TOMRequest\032!.org.monitoring.proto.TOMR" +
      "esponse\"\000(\0010\001B!\n\035org.network.monitorandc" +
      "ontrolP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.AnyProto.getDescriptor(),
          org.network.monitorandcontrol.tom.TOM.getDescriptor(),
          org.network.monitorandcontrol.ag.AG.getDescriptor(),
        });
    com.google.protobuf.AnyProto.getDescriptor();
    org.network.monitorandcontrol.tom.TOM.getDescriptor();
    org.network.monitorandcontrol.ag.AG.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}

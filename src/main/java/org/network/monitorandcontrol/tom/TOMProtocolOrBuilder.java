// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TOM.proto

package org.network.monitorandcontrol.tom;

public interface TOMProtocolOrBuilder extends
    // @@protoc_insertion_point(interface_extends:org.monitoring.proto.TOMProtocol)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *status code of the operation
   * </pre>
   *
   * <code>int32 status_code = 1;</code>
   * @return The statusCode.
   */
  int getStatusCode();

  /**
   * <pre>
   *error code for the operation
   * </pre>
   *
   * <code>string error_msg = 2;</code>
   * @return The errorMsg.
   */
  java.lang.String getErrorMsg();
  /**
   * <pre>
   *error code for the operation
   * </pre>
   *
   * <code>string error_msg = 2;</code>
   * @return The bytes for errorMsg.
   */
  com.google.protobuf.ByteString
      getErrorMsgBytes();

  /**
   * <pre>
   *send by the client for the server to identify the request type
   * </pre>
   *
   * <code>.org.monitoring.proto.RequestType request_type = 3;</code>
   * @return The enum numeric value on the wire for requestType.
   */
  int getRequestTypeValue();
  /**
   * <pre>
   *send by the client for the server to identify the request type
   * </pre>
   *
   * <code>.org.monitoring.proto.RequestType request_type = 3;</code>
   * @return The requestType.
   */
  org.network.monitorandcontrol.RequestType getRequestType();

  /**
   * <pre>
   *send by the server to the client to perform changes in the euipment
   * </pre>
   *
   * <code>.org.monitoring.proto.CommandType command_type = 4;</code>
   * @return The enum numeric value on the wire for commandType.
   */
  int getCommandTypeValue();
  /**
   * <pre>
   *send by the server to the client to perform changes in the euipment
   * </pre>
   *
   * <code>.org.monitoring.proto.CommandType command_type = 4;</code>
   * @return The commandType.
   */
  org.network.monitorandcontrol.CommandType getCommandType();

  /**
   * <pre>
   *data associated with the operation
   * </pre>
   *
   * <code>.google.protobuf.Any request_data = 5;</code>
   * @return Whether the requestData field is set.
   */
  boolean hasRequestData();
  /**
   * <pre>
   *data associated with the operation
   * </pre>
   *
   * <code>.google.protobuf.Any request_data = 5;</code>
   * @return The requestData.
   */
  com.google.protobuf.Any getRequestData();
  /**
   * <pre>
   *data associated with the operation
   * </pre>
   *
   * <code>.google.protobuf.Any request_data = 5;</code>
   */
  com.google.protobuf.AnyOrBuilder getRequestDataOrBuilder();
}

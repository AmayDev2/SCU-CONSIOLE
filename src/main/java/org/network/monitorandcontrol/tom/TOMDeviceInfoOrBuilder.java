// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TOM.proto

package org.network.monitorandcontrol.tom;

public interface TOMDeviceInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:org.monitoring.proto.TOMDeviceInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string equip_name = 1;</code>
   * @return The equipName.
   */
  java.lang.String getEquipName();
  /**
   * <code>string equip_name = 1;</code>
   * @return The bytes for equipName.
   */
  com.google.protobuf.ByteString
      getEquipNameBytes();

  /**
   * <code>string equip_id = 2;</code>
   * @return The equipId.
   */
  java.lang.String getEquipId();
  /**
   * <code>string equip_id = 2;</code>
   * @return The bytes for equipId.
   */
  com.google.protobuf.ByteString
      getEquipIdBytes();

  /**
   * <pre>
   *colon seperated last_txn:last_sync
   * </pre>
   *
   * <code>string scu_last_txn_sync = 3;</code>
   * @return The scuLastTxnSync.
   */
  java.lang.String getScuLastTxnSync();
  /**
   * <pre>
   *colon seperated last_txn:last_sync
   * </pre>
   *
   * <code>string scu_last_txn_sync = 3;</code>
   * @return The bytes for scuLastTxnSync.
   */
  com.google.protobuf.ByteString
      getScuLastTxnSyncBytes();

  /**
   * <pre>
   *colon seperated last_txn:last_sync
   * </pre>
   *
   * <code>string ccu_last_txn_sync = 4;</code>
   * @return The ccuLastTxnSync.
   */
  java.lang.String getCcuLastTxnSync();
  /**
   * <pre>
   *colon seperated last_txn:last_sync
   * </pre>
   *
   * <code>string ccu_last_txn_sync = 4;</code>
   * @return The bytes for ccuLastTxnSync.
   */
  com.google.protobuf.ByteString
      getCcuLastTxnSyncBytes();

  /**
   * <code>string tom_ip = 5;</code>
   * @return The tomIp.
   */
  java.lang.String getTomIp();
  /**
   * <code>string tom_ip = 5;</code>
   * @return The bytes for tomIp.
   */
  com.google.protobuf.ByteString
      getTomIpBytes();
}

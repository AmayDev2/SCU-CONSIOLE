// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TOM.proto

package org.network.monitorandcontrol.tom;

/**
 * <pre>
 *TOMModeControl is to control gate from SCU / CCU
 * </pre>
 *
 * Protobuf type {@code org.monitoring.proto.TOMModeControl}
 */
public  final class TOMModeControl extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:org.monitoring.proto.TOMModeControl)
    TOMModeControlOrBuilder {
private static final long serialVersionUID = 0L;
  // Use TOMModeControl.newBuilder() to construct.
  private TOMModeControl(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private TOMModeControl() {
    operationMode_ = 0;
    specialMode_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new TOMModeControl();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private TOMModeControl(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {
            int rawValue = input.readEnum();

            operationMode_ = rawValue;
            break;
          }
          case 16: {

            cardProcessMode_ = input.readBool();
            break;
          }
          case 24: {

            qrSaleMode_ = input.readBool();
            break;
          }
          case 32: {
            int rawValue = input.readEnum();

            specialMode_ = rawValue;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.network.monitorandcontrol.tom.TOM.internal_static_org_monitoring_proto_TOMModeControl_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.network.monitorandcontrol.tom.TOM.internal_static_org_monitoring_proto_TOMModeControl_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.network.monitorandcontrol.tom.TOMModeControl.class, org.network.monitorandcontrol.tom.TOMModeControl.Builder.class);
  }

  public static final int OPERATION_MODE_FIELD_NUMBER = 1;
  private int operationMode_;
  /**
   * <code>.org.monitoring.proto.OperationMode operation_mode = 1;</code>
   * @return The enum numeric value on the wire for operationMode.
   */
  public int getOperationModeValue() {
    return operationMode_;
  }
  /**
   * <code>.org.monitoring.proto.OperationMode operation_mode = 1;</code>
   * @return The operationMode.
   */
  public org.network.monitorandcontrol.OperationMode getOperationMode() {
    @SuppressWarnings("deprecation")
    org.network.monitorandcontrol.OperationMode result = org.network.monitorandcontrol.OperationMode.valueOf(operationMode_);
    return result == null ? org.network.monitorandcontrol.OperationMode.UNRECOGNIZED : result;
  }

  public static final int CARD_PROCESS_MODE_FIELD_NUMBER = 2;
  private boolean cardProcessMode_;
  /**
   * <code>bool card_process_mode = 2;</code>
   * @return The cardProcessMode.
   */
  public boolean getCardProcessMode() {
    return cardProcessMode_;
  }

  public static final int QR_SALE_MODE_FIELD_NUMBER = 3;
  private boolean qrSaleMode_;
  /**
   * <code>bool qr_sale_mode = 3;</code>
   * @return The qrSaleMode.
   */
  public boolean getQrSaleMode() {
    return qrSaleMode_;
  }

  public static final int SPECIAL_MODE_FIELD_NUMBER = 4;
  private int specialMode_;
  /**
   * <code>.org.monitoring.proto.SpecialMode special_mode = 4;</code>
   * @return The enum numeric value on the wire for specialMode.
   */
  public int getSpecialModeValue() {
    return specialMode_;
  }
  /**
   * <code>.org.monitoring.proto.SpecialMode special_mode = 4;</code>
   * @return The specialMode.
   */
  public org.network.monitorandcontrol.SpecialMode getSpecialMode() {
    @SuppressWarnings("deprecation")
    org.network.monitorandcontrol.SpecialMode result = org.network.monitorandcontrol.SpecialMode.valueOf(specialMode_);
    return result == null ? org.network.monitorandcontrol.SpecialMode.UNRECOGNIZED : result;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (operationMode_ != org.network.monitorandcontrol.OperationMode.IN_SERVICE.getNumber()) {
      output.writeEnum(1, operationMode_);
    }
    if (cardProcessMode_ != false) {
      output.writeBool(2, cardProcessMode_);
    }
    if (qrSaleMode_ != false) {
      output.writeBool(3, qrSaleMode_);
    }
    if (specialMode_ != org.network.monitorandcontrol.SpecialMode.TIME_OVERRIDE.getNumber()) {
      output.writeEnum(4, specialMode_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (operationMode_ != org.network.monitorandcontrol.OperationMode.IN_SERVICE.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, operationMode_);
    }
    if (cardProcessMode_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(2, cardProcessMode_);
    }
    if (qrSaleMode_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(3, qrSaleMode_);
    }
    if (specialMode_ != org.network.monitorandcontrol.SpecialMode.TIME_OVERRIDE.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(4, specialMode_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.network.monitorandcontrol.tom.TOMModeControl)) {
      return super.equals(obj);
    }
    org.network.monitorandcontrol.tom.TOMModeControl other = (org.network.monitorandcontrol.tom.TOMModeControl) obj;

    if (operationMode_ != other.operationMode_) return false;
    if (getCardProcessMode()
        != other.getCardProcessMode()) return false;
    if (getQrSaleMode()
        != other.getQrSaleMode()) return false;
    if (specialMode_ != other.specialMode_) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + OPERATION_MODE_FIELD_NUMBER;
    hash = (53 * hash) + operationMode_;
    hash = (37 * hash) + CARD_PROCESS_MODE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getCardProcessMode());
    hash = (37 * hash) + QR_SALE_MODE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getQrSaleMode());
    hash = (37 * hash) + SPECIAL_MODE_FIELD_NUMBER;
    hash = (53 * hash) + specialMode_;
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.network.monitorandcontrol.tom.TOMModeControl parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.network.monitorandcontrol.tom.TOMModeControl parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.network.monitorandcontrol.tom.TOMModeControl parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.network.monitorandcontrol.tom.TOMModeControl parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.network.monitorandcontrol.tom.TOMModeControl parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.network.monitorandcontrol.tom.TOMModeControl parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.network.monitorandcontrol.tom.TOMModeControl parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.network.monitorandcontrol.tom.TOMModeControl parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.network.monitorandcontrol.tom.TOMModeControl parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.network.monitorandcontrol.tom.TOMModeControl parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.network.monitorandcontrol.tom.TOMModeControl parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.network.monitorandcontrol.tom.TOMModeControl parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.network.monitorandcontrol.tom.TOMModeControl prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   *TOMModeControl is to control gate from SCU / CCU
   * </pre>
   *
   * Protobuf type {@code org.monitoring.proto.TOMModeControl}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:org.monitoring.proto.TOMModeControl)
      org.network.monitorandcontrol.tom.TOMModeControlOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.network.monitorandcontrol.tom.TOM.internal_static_org_monitoring_proto_TOMModeControl_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.network.monitorandcontrol.tom.TOM.internal_static_org_monitoring_proto_TOMModeControl_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.network.monitorandcontrol.tom.TOMModeControl.class, org.network.monitorandcontrol.tom.TOMModeControl.Builder.class);
    }

    // Construct using org.network.monitorandcontrol.tom.TOMModeControl.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      operationMode_ = 0;

      cardProcessMode_ = false;

      qrSaleMode_ = false;

      specialMode_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.network.monitorandcontrol.tom.TOM.internal_static_org_monitoring_proto_TOMModeControl_descriptor;
    }

    @java.lang.Override
    public org.network.monitorandcontrol.tom.TOMModeControl getDefaultInstanceForType() {
      return org.network.monitorandcontrol.tom.TOMModeControl.getDefaultInstance();
    }

    @java.lang.Override
    public org.network.monitorandcontrol.tom.TOMModeControl build() {
      org.network.monitorandcontrol.tom.TOMModeControl result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.network.monitorandcontrol.tom.TOMModeControl buildPartial() {
      org.network.monitorandcontrol.tom.TOMModeControl result = new org.network.monitorandcontrol.tom.TOMModeControl(this);
      result.operationMode_ = operationMode_;
      result.cardProcessMode_ = cardProcessMode_;
      result.qrSaleMode_ = qrSaleMode_;
      result.specialMode_ = specialMode_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.network.monitorandcontrol.tom.TOMModeControl) {
        return mergeFrom((org.network.monitorandcontrol.tom.TOMModeControl)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.network.monitorandcontrol.tom.TOMModeControl other) {
      if (other == org.network.monitorandcontrol.tom.TOMModeControl.getDefaultInstance()) return this;
      if (other.operationMode_ != 0) {
        setOperationModeValue(other.getOperationModeValue());
      }
      if (other.getCardProcessMode() != false) {
        setCardProcessMode(other.getCardProcessMode());
      }
      if (other.getQrSaleMode() != false) {
        setQrSaleMode(other.getQrSaleMode());
      }
      if (other.specialMode_ != 0) {
        setSpecialModeValue(other.getSpecialModeValue());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.network.monitorandcontrol.tom.TOMModeControl parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.network.monitorandcontrol.tom.TOMModeControl) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int operationMode_ = 0;
    /**
     * <code>.org.monitoring.proto.OperationMode operation_mode = 1;</code>
     * @return The enum numeric value on the wire for operationMode.
     */
    public int getOperationModeValue() {
      return operationMode_;
    }
    /**
     * <code>.org.monitoring.proto.OperationMode operation_mode = 1;</code>
     * @param value The enum numeric value on the wire for operationMode to set.
     * @return This builder for chaining.
     */
    public Builder setOperationModeValue(int value) {
      operationMode_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.org.monitoring.proto.OperationMode operation_mode = 1;</code>
     * @return The operationMode.
     */
    public org.network.monitorandcontrol.OperationMode getOperationMode() {
      @SuppressWarnings("deprecation")
      org.network.monitorandcontrol.OperationMode result = org.network.monitorandcontrol.OperationMode.valueOf(operationMode_);
      return result == null ? org.network.monitorandcontrol.OperationMode.UNRECOGNIZED : result;
    }
    /**
     * <code>.org.monitoring.proto.OperationMode operation_mode = 1;</code>
     * @param value The operationMode to set.
     * @return This builder for chaining.
     */
    public Builder setOperationMode(org.network.monitorandcontrol.OperationMode value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      operationMode_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.org.monitoring.proto.OperationMode operation_mode = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearOperationMode() {
      
      operationMode_ = 0;
      onChanged();
      return this;
    }

    private boolean cardProcessMode_ ;
    /**
     * <code>bool card_process_mode = 2;</code>
     * @return The cardProcessMode.
     */
    public boolean getCardProcessMode() {
      return cardProcessMode_;
    }
    /**
     * <code>bool card_process_mode = 2;</code>
     * @param value The cardProcessMode to set.
     * @return This builder for chaining.
     */
    public Builder setCardProcessMode(boolean value) {
      
      cardProcessMode_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool card_process_mode = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearCardProcessMode() {
      
      cardProcessMode_ = false;
      onChanged();
      return this;
    }

    private boolean qrSaleMode_ ;
    /**
     * <code>bool qr_sale_mode = 3;</code>
     * @return The qrSaleMode.
     */
    public boolean getQrSaleMode() {
      return qrSaleMode_;
    }
    /**
     * <code>bool qr_sale_mode = 3;</code>
     * @param value The qrSaleMode to set.
     * @return This builder for chaining.
     */
    public Builder setQrSaleMode(boolean value) {
      
      qrSaleMode_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool qr_sale_mode = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearQrSaleMode() {
      
      qrSaleMode_ = false;
      onChanged();
      return this;
    }

    private int specialMode_ = 0;
    /**
     * <code>.org.monitoring.proto.SpecialMode special_mode = 4;</code>
     * @return The enum numeric value on the wire for specialMode.
     */
    public int getSpecialModeValue() {
      return specialMode_;
    }
    /**
     * <code>.org.monitoring.proto.SpecialMode special_mode = 4;</code>
     * @param value The enum numeric value on the wire for specialMode to set.
     * @return This builder for chaining.
     */
    public Builder setSpecialModeValue(int value) {
      specialMode_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.org.monitoring.proto.SpecialMode special_mode = 4;</code>
     * @return The specialMode.
     */
    public org.network.monitorandcontrol.SpecialMode getSpecialMode() {
      @SuppressWarnings("deprecation")
      org.network.monitorandcontrol.SpecialMode result = org.network.monitorandcontrol.SpecialMode.valueOf(specialMode_);
      return result == null ? org.network.monitorandcontrol.SpecialMode.UNRECOGNIZED : result;
    }
    /**
     * <code>.org.monitoring.proto.SpecialMode special_mode = 4;</code>
     * @param value The specialMode to set.
     * @return This builder for chaining.
     */
    public Builder setSpecialMode(org.network.monitorandcontrol.SpecialMode value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      specialMode_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.org.monitoring.proto.SpecialMode special_mode = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearSpecialMode() {
      
      specialMode_ = 0;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:org.monitoring.proto.TOMModeControl)
  }

  // @@protoc_insertion_point(class_scope:org.monitoring.proto.TOMModeControl)
  private static final org.network.monitorandcontrol.tom.TOMModeControl DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.network.monitorandcontrol.tom.TOMModeControl();
  }

  public static org.network.monitorandcontrol.tom.TOMModeControl getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<TOMModeControl>
      PARSER = new com.google.protobuf.AbstractParser<TOMModeControl>() {
    @java.lang.Override
    public TOMModeControl parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new TOMModeControl(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<TOMModeControl> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<TOMModeControl> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.network.monitorandcontrol.tom.TOMModeControl getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}


package org.network.monitorandcontrol;

import jakarta.annotation.Generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: MonitoringAndControl.proto")
public final class MonitorAndControlGrpc {

  private MonitorAndControlGrpc() {}

  public static final String SERVICE_NAME = "org.monitoring.proto.MonitorAndControl";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.network.monitorandcontrol.ag.AGRequest,
      org.network.monitorandcontrol.ag.AGResponse> getAGStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AGStream",
      requestType = org.network.monitorandcontrol.ag.AGRequest.class,
      responseType = org.network.monitorandcontrol.ag.AGResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<org.network.monitorandcontrol.ag.AGRequest,
      org.network.monitorandcontrol.ag.AGResponse> getAGStreamMethod() {
    io.grpc.MethodDescriptor<org.network.monitorandcontrol.ag.AGRequest, org.network.monitorandcontrol.ag.AGResponse> getAGStreamMethod;
    if ((getAGStreamMethod = MonitorAndControlGrpc.getAGStreamMethod) == null) {
      synchronized (MonitorAndControlGrpc.class) {
        if ((getAGStreamMethod = MonitorAndControlGrpc.getAGStreamMethod) == null) {
          MonitorAndControlGrpc.getAGStreamMethod = getAGStreamMethod = 
              io.grpc.MethodDescriptor.<org.network.monitorandcontrol.ag.AGRequest, org.network.monitorandcontrol.ag.AGResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "org.monitoring.proto.MonitorAndControl", "AGStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.network.monitorandcontrol.ag.AGRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.network.monitorandcontrol.ag.AGResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new MonitorAndControlMethodDescriptorSupplier("AGStream"))
                  .build();
          }
        }
     }
     return getAGStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.network.monitorandcontrol.tom.TOMRequest,
      org.network.monitorandcontrol.tom.TOMResponse> getTOMStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TOMStream",
      requestType = org.network.monitorandcontrol.tom.TOMRequest.class,
      responseType = org.network.monitorandcontrol.tom.TOMResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<org.network.monitorandcontrol.tom.TOMRequest,
      org.network.monitorandcontrol.tom.TOMResponse> getTOMStreamMethod() {
    io.grpc.MethodDescriptor<org.network.monitorandcontrol.tom.TOMRequest, org.network.monitorandcontrol.tom.TOMResponse> getTOMStreamMethod;
    if ((getTOMStreamMethod = MonitorAndControlGrpc.getTOMStreamMethod) == null) {
      synchronized (MonitorAndControlGrpc.class) {
        if ((getTOMStreamMethod = MonitorAndControlGrpc.getTOMStreamMethod) == null) {
          MonitorAndControlGrpc.getTOMStreamMethod = getTOMStreamMethod = 
              io.grpc.MethodDescriptor.<org.network.monitorandcontrol.tom.TOMRequest, org.network.monitorandcontrol.tom.TOMResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "org.monitoring.proto.MonitorAndControl", "TOMStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.network.monitorandcontrol.tom.TOMRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.network.monitorandcontrol.tom.TOMResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new MonitorAndControlMethodDescriptorSupplier("TOMStream"))
                  .build();
          }
        }
     }
     return getTOMStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MonitorAndControlStub newStub(io.grpc.Channel channel) {
    return new MonitorAndControlStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MonitorAndControlBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MonitorAndControlBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MonitorAndControlFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MonitorAndControlFutureStub(channel);
  }

  /**
   */
  public static abstract class MonitorAndControlImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<org.network.monitorandcontrol.ag.AGRequest> aGStream(
        io.grpc.stub.StreamObserver<org.network.monitorandcontrol.ag.AGResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getAGStreamMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.network.monitorandcontrol.tom.TOMRequest> tOMStream(
        io.grpc.stub.StreamObserver<org.network.monitorandcontrol.tom.TOMResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getTOMStreamMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAGStreamMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                org.network.monitorandcontrol.ag.AGRequest,
                org.network.monitorandcontrol.ag.AGResponse>(
                  this, METHODID_AGSTREAM)))
          .addMethod(
            getTOMStreamMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                org.network.monitorandcontrol.tom.TOMRequest,
                org.network.monitorandcontrol.tom.TOMResponse>(
                  this, METHODID_TOMSTREAM)))
          .build();
    }
  }

  /**
   */
  public static final class MonitorAndControlStub extends io.grpc.stub.AbstractStub<MonitorAndControlStub> {
    private MonitorAndControlStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MonitorAndControlStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MonitorAndControlStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MonitorAndControlStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.network.monitorandcontrol.ag.AGRequest> aGStream(
        io.grpc.stub.StreamObserver<org.network.monitorandcontrol.ag.AGResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getAGStreamMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.network.monitorandcontrol.tom.TOMRequest> tOMStream(
        io.grpc.stub.StreamObserver<org.network.monitorandcontrol.tom.TOMResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getTOMStreamMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class MonitorAndControlBlockingStub extends io.grpc.stub.AbstractStub<MonitorAndControlBlockingStub> {
    private MonitorAndControlBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MonitorAndControlBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MonitorAndControlBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MonitorAndControlBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class MonitorAndControlFutureStub extends io.grpc.stub.AbstractStub<MonitorAndControlFutureStub> {
    private MonitorAndControlFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MonitorAndControlFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MonitorAndControlFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MonitorAndControlFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_AGSTREAM = 0;
  private static final int METHODID_TOMSTREAM = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MonitorAndControlImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MonitorAndControlImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_AGSTREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.aGStream(
              (io.grpc.stub.StreamObserver<org.network.monitorandcontrol.ag.AGResponse>) responseObserver);
        case METHODID_TOMSTREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.tOMStream(
              (io.grpc.stub.StreamObserver<org.network.monitorandcontrol.tom.TOMResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class MonitorAndControlBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MonitorAndControlBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.network.monitorandcontrol.MonitoringAndControl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MonitorAndControl");
    }
  }

  private static final class MonitorAndControlFileDescriptorSupplier
      extends MonitorAndControlBaseDescriptorSupplier {
    MonitorAndControlFileDescriptorSupplier() {}
  }

  private static final class MonitorAndControlMethodDescriptorSupplier
      extends MonitorAndControlBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MonitorAndControlMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MonitorAndControlGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MonitorAndControlFileDescriptorSupplier())
              .addMethod(getAGStreamMethod())
              .addMethod(getTOMStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}

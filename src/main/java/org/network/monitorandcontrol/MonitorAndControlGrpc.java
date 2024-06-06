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
  private static volatile io.grpc.MethodDescriptor<org.network.monitorandcontrol.ag.AGProtocol,
      org.network.monitorandcontrol.ag.AGProtocol> getAgStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "agStream",
      requestType = org.network.monitorandcontrol.ag.AGProtocol.class,
      responseType = org.network.monitorandcontrol.ag.AGProtocol.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<org.network.monitorandcontrol.ag.AGProtocol,
      org.network.monitorandcontrol.ag.AGProtocol> getAgStreamMethod() {
    io.grpc.MethodDescriptor<org.network.monitorandcontrol.ag.AGProtocol, org.network.monitorandcontrol.ag.AGProtocol> getAgStreamMethod;
    if ((getAgStreamMethod = MonitorAndControlGrpc.getAgStreamMethod) == null) {
      synchronized (MonitorAndControlGrpc.class) {
        if ((getAgStreamMethod = MonitorAndControlGrpc.getAgStreamMethod) == null) {
          MonitorAndControlGrpc.getAgStreamMethod = getAgStreamMethod = 
              io.grpc.MethodDescriptor.<org.network.monitorandcontrol.ag.AGProtocol, org.network.monitorandcontrol.ag.AGProtocol>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "org.monitoring.proto.MonitorAndControl", "agStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.network.monitorandcontrol.ag.AGProtocol.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.network.monitorandcontrol.ag.AGProtocol.getDefaultInstance()))
                  .setSchemaDescriptor(new MonitorAndControlMethodDescriptorSupplier("agStream"))
                  .build();
          }
        }
     }
     return getAgStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.network.monitorandcontrol.tom.TOMProtocol,
      org.network.monitorandcontrol.tom.TOMProtocol> getTomStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "tomStream",
      requestType = org.network.monitorandcontrol.tom.TOMProtocol.class,
      responseType = org.network.monitorandcontrol.tom.TOMProtocol.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<org.network.monitorandcontrol.tom.TOMProtocol,
      org.network.monitorandcontrol.tom.TOMProtocol> getTomStreamMethod() {
    io.grpc.MethodDescriptor<org.network.monitorandcontrol.tom.TOMProtocol, org.network.monitorandcontrol.tom.TOMProtocol> getTomStreamMethod;
    if ((getTomStreamMethod = MonitorAndControlGrpc.getTomStreamMethod) == null) {
      synchronized (MonitorAndControlGrpc.class) {
        if ((getTomStreamMethod = MonitorAndControlGrpc.getTomStreamMethod) == null) {
          MonitorAndControlGrpc.getTomStreamMethod = getTomStreamMethod = 
              io.grpc.MethodDescriptor.<org.network.monitorandcontrol.tom.TOMProtocol, org.network.monitorandcontrol.tom.TOMProtocol>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "org.monitoring.proto.MonitorAndControl", "tomStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.network.monitorandcontrol.tom.TOMProtocol.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.network.monitorandcontrol.tom.TOMProtocol.getDefaultInstance()))
                  .setSchemaDescriptor(new MonitorAndControlMethodDescriptorSupplier("tomStream"))
                  .build();
          }
        }
     }
     return getTomStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.network.monitorandcontrol.scu_console.ConsoleProtocol,
      org.network.monitorandcontrol.scu_console.ConsoleProtocol> getScStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "scStream",
      requestType = org.network.monitorandcontrol.scu_console.ConsoleProtocol.class,
      responseType = org.network.monitorandcontrol.scu_console.ConsoleProtocol.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<org.network.monitorandcontrol.scu_console.ConsoleProtocol,
      org.network.monitorandcontrol.scu_console.ConsoleProtocol> getScStreamMethod() {
    io.grpc.MethodDescriptor<org.network.monitorandcontrol.scu_console.ConsoleProtocol, org.network.monitorandcontrol.scu_console.ConsoleProtocol> getScStreamMethod;
    if ((getScStreamMethod = MonitorAndControlGrpc.getScStreamMethod) == null) {
      synchronized (MonitorAndControlGrpc.class) {
        if ((getScStreamMethod = MonitorAndControlGrpc.getScStreamMethod) == null) {
          MonitorAndControlGrpc.getScStreamMethod = getScStreamMethod = 
              io.grpc.MethodDescriptor.<org.network.monitorandcontrol.scu_console.ConsoleProtocol, org.network.monitorandcontrol.scu_console.ConsoleProtocol>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "org.monitoring.proto.MonitorAndControl", "scStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.network.monitorandcontrol.scu_console.ConsoleProtocol.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.network.monitorandcontrol.scu_console.ConsoleProtocol.getDefaultInstance()))
                  .setSchemaDescriptor(new MonitorAndControlMethodDescriptorSupplier("scStream"))
                  .build();
          }
        }
     }
     return getScStreamMethod;
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
    public io.grpc.stub.StreamObserver<org.network.monitorandcontrol.ag.AGProtocol> agStream(
        io.grpc.stub.StreamObserver<org.network.monitorandcontrol.ag.AGProtocol> responseObserver) {
      return asyncUnimplementedStreamingCall(getAgStreamMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.network.monitorandcontrol.tom.TOMProtocol> tomStream(
        io.grpc.stub.StreamObserver<org.network.monitorandcontrol.tom.TOMProtocol> responseObserver) {
      return asyncUnimplementedStreamingCall(getTomStreamMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.network.monitorandcontrol.scu_console.ConsoleProtocol> scStream(
        io.grpc.stub.StreamObserver<org.network.monitorandcontrol.scu_console.ConsoleProtocol> responseObserver) {
      return asyncUnimplementedStreamingCall(getScStreamMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAgStreamMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                org.network.monitorandcontrol.ag.AGProtocol,
                org.network.monitorandcontrol.ag.AGProtocol>(
                  this, METHODID_AG_STREAM)))
          .addMethod(
            getTomStreamMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                org.network.monitorandcontrol.tom.TOMProtocol,
                org.network.monitorandcontrol.tom.TOMProtocol>(
                  this, METHODID_TOM_STREAM)))
          .addMethod(
            getScStreamMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                org.network.monitorandcontrol.scu_console.ConsoleProtocol,
                org.network.monitorandcontrol.scu_console.ConsoleProtocol>(
                  this, METHODID_SC_STREAM)))
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
    public io.grpc.stub.StreamObserver<org.network.monitorandcontrol.ag.AGProtocol> agStream(
        io.grpc.stub.StreamObserver<org.network.monitorandcontrol.ag.AGProtocol> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getAgStreamMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.network.monitorandcontrol.tom.TOMProtocol> tomStream(
        io.grpc.stub.StreamObserver<org.network.monitorandcontrol.tom.TOMProtocol> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getTomStreamMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.network.monitorandcontrol.scu_console.ConsoleProtocol> scStream(
        io.grpc.stub.StreamObserver<org.network.monitorandcontrol.scu_console.ConsoleProtocol> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getScStreamMethod(), getCallOptions()), responseObserver);
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

  private static final int METHODID_AG_STREAM = 0;
  private static final int METHODID_TOM_STREAM = 1;
  private static final int METHODID_SC_STREAM = 2;

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
        case METHODID_AG_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.agStream(
              (io.grpc.stub.StreamObserver<org.network.monitorandcontrol.ag.AGProtocol>) responseObserver);
        case METHODID_TOM_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.tomStream(
              (io.grpc.stub.StreamObserver<org.network.monitorandcontrol.tom.TOMProtocol>) responseObserver);
        case METHODID_SC_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.scStream(
              (io.grpc.stub.StreamObserver<org.network.monitorandcontrol.scu_console.ConsoleProtocol>) responseObserver);
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
              .addMethod(getAgStreamMethod())
              .addMethod(getTomStreamMethod())
              .addMethod(getScStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: BidEvaluation.proto

package network.platon.bidevaluation;

public final class BidEvaluation {
  private BidEvaluation() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface BidderOrBuilder extends
      // @@protoc_insertion_point(interface_extends:BidEvaluation.Bidder)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>int32 a = 1;</code>
     */
    int getA();

    /**
     * <code>int32 b = 2;</code>
     */
    int getB();

    /**
     * <code>int32 c = 3;</code>
     */
    int getC();
  }
  /**
   * Protobuf type {@code BidEvaluation.Bidder}
   */
  public  static final class Bidder extends
      com.google.protobuf.GeneratedMessageLite<
          Bidder, Bidder.Builder> implements
      // @@protoc_insertion_point(message_implements:BidEvaluation.Bidder)
      BidderOrBuilder {
    private Bidder() {
    }
    public static final int A_FIELD_NUMBER = 1;
    private int a_;
    /**
     * <code>int32 a = 1;</code>
     */
    public int getA() {
      return a_;
    }
    /**
     * <code>int32 a = 1;</code>
     */
    private void setA(int value) {
      
      a_ = value;
    }
    /**
     * <code>int32 a = 1;</code>
     */
    private void clearA() {
      
      a_ = 0;
    }

    public static final int B_FIELD_NUMBER = 2;
    private int b_;
    /**
     * <code>int32 b = 2;</code>
     */
    public int getB() {
      return b_;
    }
    /**
     * <code>int32 b = 2;</code>
     */
    private void setB(int value) {
      
      b_ = value;
    }
    /**
     * <code>int32 b = 2;</code>
     */
    private void clearB() {
      
      b_ = 0;
    }

    public static final int C_FIELD_NUMBER = 3;
    private int c_;
    /**
     * <code>int32 c = 3;</code>
     */
    public int getC() {
      return c_;
    }
    /**
     * <code>int32 c = 3;</code>
     */
    private void setC(int value) {
      
      c_ = value;
    }
    /**
     * <code>int32 c = 3;</code>
     */
    private void clearC() {
      
      c_ = 0;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (a_ != 0) {
        output.writeInt32(1, a_);
      }
      if (b_ != 0) {
        output.writeInt32(2, b_);
      }
      if (c_ != 0) {
        output.writeInt32(3, c_);
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (a_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, a_);
      }
      if (b_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, b_);
      }
      if (c_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, c_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static network.platon.bidevaluation.BidEvaluation.Bidder parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static network.platon.bidevaluation.BidEvaluation.Bidder parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static network.platon.bidevaluation.BidEvaluation.Bidder parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static network.platon.bidevaluation.BidEvaluation.Bidder parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static network.platon.bidevaluation.BidEvaluation.Bidder parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static network.platon.bidevaluation.BidEvaluation.Bidder parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static network.platon.bidevaluation.BidEvaluation.Bidder parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static network.platon.bidevaluation.BidEvaluation.Bidder parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static network.platon.bidevaluation.BidEvaluation.Bidder parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static network.platon.bidevaluation.BidEvaluation.Bidder parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static network.platon.bidevaluation.BidEvaluation.Bidder parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static network.platon.bidevaluation.BidEvaluation.Bidder parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(network.platon.bidevaluation.BidEvaluation.Bidder prototype) {
      return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code BidEvaluation.Bidder}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          network.platon.bidevaluation.BidEvaluation.Bidder, Builder> implements
        // @@protoc_insertion_point(builder_implements:BidEvaluation.Bidder)
        network.platon.bidevaluation.BidEvaluation.BidderOrBuilder {
      // Construct using network.platon.bidevaluation.BidEvaluation.Bidder.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>int32 a = 1;</code>
       */
      public int getA() {
        return instance.getA();
      }
      /**
       * <code>int32 a = 1;</code>
       */
      public Builder setA(int value) {
        copyOnWrite();
        instance.setA(value);
        return this;
      }
      /**
       * <code>int32 a = 1;</code>
       */
      public Builder clearA() {
        copyOnWrite();
        instance.clearA();
        return this;
      }

      /**
       * <code>int32 b = 2;</code>
       */
      public int getB() {
        return instance.getB();
      }
      /**
       * <code>int32 b = 2;</code>
       */
      public Builder setB(int value) {
        copyOnWrite();
        instance.setB(value);
        return this;
      }
      /**
       * <code>int32 b = 2;</code>
       */
      public Builder clearB() {
        copyOnWrite();
        instance.clearB();
        return this;
      }

      /**
       * <code>int32 c = 3;</code>
       */
      public int getC() {
        return instance.getC();
      }
      /**
       * <code>int32 c = 3;</code>
       */
      public Builder setC(int value) {
        copyOnWrite();
        instance.setC(value);
        return this;
      }
      /**
       * <code>int32 c = 3;</code>
       */
      public Builder clearC() {
        copyOnWrite();
        instance.clearC();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:BidEvaluation.Bidder)
    }
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new network.platon.bidevaluation.BidEvaluation.Bidder();
        }
        case IS_INITIALIZED: {
          return DEFAULT_INSTANCE;
        }
        case MAKE_IMMUTABLE: {
          return null;
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case VISIT: {
          Visitor visitor = (Visitor) arg0;
          network.platon.bidevaluation.BidEvaluation.Bidder other = (network.platon.bidevaluation.BidEvaluation.Bidder) arg1;
          a_ = visitor.visitInt(a_ != 0, a_,
              other.a_ != 0, other.a_);
          b_ = visitor.visitInt(b_ != 0, b_,
              other.b_ != 0, other.b_);
          c_ = visitor.visitInt(c_ != 0, c_,
              other.c_ != 0, other.c_);
          if (visitor == com.google.protobuf.GeneratedMessageLite.MergeFromVisitor
              .INSTANCE) {
          }
          return this;
        }
        case MERGE_FROM_STREAM: {
          com.google.protobuf.CodedInputStream input =
              (com.google.protobuf.CodedInputStream) arg0;
          com.google.protobuf.ExtensionRegistryLite extensionRegistry =
              (com.google.protobuf.ExtensionRegistryLite) arg1;
          if (extensionRegistry == null) {
            throw new java.lang.NullPointerException();
          }
          try {
            boolean done = false;
            while (!done) {
              int tag = input.readTag();
              switch (tag) {
                case 0:
                  done = true;
                  break;
                default: {
                  if (!parseUnknownField(tag, input)) {
                    done = true;
                  }
                  break;
                }
                case 8: {

                  a_ = input.readInt32();
                  break;
                }
                case 16: {

                  b_ = input.readInt32();
                  break;
                }
                case 24: {

                  c_ = input.readInt32();
                  break;
                }
              }
            }
          } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw new RuntimeException(e.setUnfinishedMessage(this));
          } catch (java.io.IOException e) {
            throw new RuntimeException(
                new com.google.protobuf.InvalidProtocolBufferException(
                    e.getMessage()).setUnfinishedMessage(this));
          } finally {
          }
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          if (PARSER == null) {    synchronized (network.platon.bidevaluation.BidEvaluation.Bidder.class) {
              if (PARSER == null) {
                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
              }
            }
          }
          return PARSER;
      }
      case GET_MEMOIZED_IS_INITIALIZED: {
        return (byte) 1;
      }
      case SET_MEMOIZED_IS_INITIALIZED: {
        return null;
      }
      }
      throw new UnsupportedOperationException();
    }


    // @@protoc_insertion_point(class_scope:BidEvaluation.Bidder)
    private static final network.platon.bidevaluation.BidEvaluation.Bidder DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Bidder();
      DEFAULT_INSTANCE.makeImmutable();
    }

    public static network.platon.bidevaluation.BidEvaluation.Bidder getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<Bidder> PARSER;

    public static com.google.protobuf.Parser<Bidder> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}

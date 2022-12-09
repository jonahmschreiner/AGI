

public final class Mysqlx {
  private Mysqlx() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
    registry.add(com.mysql.cj.x.protobuf.Mysqlx.clientMessageId);
    registry.add(com.mysql.cj.x.protobuf.Mysqlx.serverMessageId);
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ClientMessagesOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Mysqlx.ClientMessages)
      com.google.protobuf.MessageOrBuilder {
  }
  /**
   * <pre>
   **
   *IDs of messages that can be sent from client to the server.
   *&#64;note
   *This message is never sent on the wire. It is only used to let ``protoc``:
   *-  generate constants
   *-  check for uniqueness
   * </pre>
   *
   * Protobuf type {@code Mysqlx.ClientMessages}
   */
  public static final class ClientMessages extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Mysqlx.ClientMessages)
      ClientMessagesOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ClientMessages.newBuilder() to construct.
    private ClientMessages(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ClientMessages() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new ClientMessages();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ClientMessages(
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
      return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_ClientMessages_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_ClientMessages_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.mysql.cj.x.protobuf.Mysqlx.ClientMessages.class, com.mysql.cj.x.protobuf.Mysqlx.ClientMessages.Builder.class);
    }

    /**
     * Protobuf enum {@code Mysqlx.ClientMessages.Type}
     */
    public enum Type
        implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>CON_CAPABILITIES_GET = 1;</code>
       */
      CON_CAPABILITIES_GET(1),
      /**
       * <code>CON_CAPABILITIES_SET = 2;</code>
       */
      CON_CAPABILITIES_SET(2),
      /**
       * <code>CON_CLOSE = 3;</code>
       */
      CON_CLOSE(3),
      /**
       * <code>SESS_AUTHENTICATE_START = 4;</code>
       */
      SESS_AUTHENTICATE_START(4),
      /**
       * <code>SESS_AUTHENTICATE_CONTINUE = 5;</code>
       */
      SESS_AUTHENTICATE_CONTINUE(5),
      /**
       * <code>SESS_RESET = 6;</code>
       */
      SESS_RESET(6),
      /**
       * <code>SESS_CLOSE = 7;</code>
       */
      SESS_CLOSE(7),
      /**
       * <code>SQL_STMT_EXECUTE = 12;</code>
       */
      SQL_STMT_EXECUTE(12),
      /**
       * <code>CRUD_FIND = 17;</code>
       */
      CRUD_FIND(17),
      /**
       * <code>CRUD_INSERT = 18;</code>
       */
      CRUD_INSERT(18),
      /**
       * <code>CRUD_UPDATE = 19;</code>
       */
      CRUD_UPDATE(19),
      /**
       * <code>CRUD_DELETE = 20;</code>
       */
      CRUD_DELETE(20),
      /**
       * <code>EXPECT_OPEN = 24;</code>
       */
      EXPECT_OPEN(24),
      /**
       * <code>EXPECT_CLOSE = 25;</code>
       */
      EXPECT_CLOSE(25),
      /**
       * <code>CRUD_CREATE_VIEW = 30;</code>
       */
      CRUD_CREATE_VIEW(30),
      /**
       * <code>CRUD_MODIFY_VIEW = 31;</code>
       */
      CRUD_MODIFY_VIEW(31),
      /**
       * <code>CRUD_DROP_VIEW = 32;</code>
       */
      CRUD_DROP_VIEW(32),
      /**
       * <code>PREPARE_PREPARE = 40;</code>
       */
      PREPARE_PREPARE(40),
      /**
       * <code>PREPARE_EXECUTE = 41;</code>
       */
      PREPARE_EXECUTE(41),
      /**
       * <code>PREPARE_DEALLOCATE = 42;</code>
       */
      PREPARE_DEALLOCATE(42),
      /**
       * <code>CURSOR_OPEN = 43;</code>
       */
      CURSOR_OPEN(43),
      /**
       * <code>CURSOR_CLOSE = 44;</code>
       */
      CURSOR_CLOSE(44),
      /**
       * <code>CURSOR_FETCH = 45;</code>
       */
      CURSOR_FETCH(45),
      /**
       * <code>COMPRESSION = 46;</code>
       */
      COMPRESSION(46),
      ;

      /**
       * <code>CON_CAPABILITIES_GET = 1;</code>
       */
      public static final int CON_CAPABILITIES_GET_VALUE = 1;
      /**
       * <code>CON_CAPABILITIES_SET = 2;</code>
       */
      public static final int CON_CAPABILITIES_SET_VALUE = 2;
      /**
       * <code>CON_CLOSE = 3;</code>
       */
      public static final int CON_CLOSE_VALUE = 3;
      /**
       * <code>SESS_AUTHENTICATE_START = 4;</code>
       */
      public static final int SESS_AUTHENTICATE_START_VALUE = 4;
      /**
       * <code>SESS_AUTHENTICATE_CONTINUE = 5;</code>
       */
      public static final int SESS_AUTHENTICATE_CONTINUE_VALUE = 5;
      /**
       * <code>SESS_RESET = 6;</code>
       */
      public static final int SESS_RESET_VALUE = 6;
      /**
       * <code>SESS_CLOSE = 7;</code>
       */
      public static final int SESS_CLOSE_VALUE = 7;
      /**
       * <code>SQL_STMT_EXECUTE = 12;</code>
       */
      public static final int SQL_STMT_EXECUTE_VALUE = 12;
      /**
       * <code>CRUD_FIND = 17;</code>
       */
      public static final int CRUD_FIND_VALUE = 17;
      /**
       * <code>CRUD_INSERT = 18;</code>
       */
      public static final int CRUD_INSERT_VALUE = 18;
      /**
       * <code>CRUD_UPDATE = 19;</code>
       */
      public static final int CRUD_UPDATE_VALUE = 19;
      /**
       * <code>CRUD_DELETE = 20;</code>
       */
      public static final int CRUD_DELETE_VALUE = 20;
      /**
       * <code>EXPECT_OPEN = 24;</code>
       */
      public static final int EXPECT_OPEN_VALUE = 24;
      /**
       * <code>EXPECT_CLOSE = 25;</code>
       */
      public static final int EXPECT_CLOSE_VALUE = 25;
      /**
       * <code>CRUD_CREATE_VIEW = 30;</code>
       */
      public static final int CRUD_CREATE_VIEW_VALUE = 30;
      /**
       * <code>CRUD_MODIFY_VIEW = 31;</code>
       */
      public static final int CRUD_MODIFY_VIEW_VALUE = 31;
      /**
       * <code>CRUD_DROP_VIEW = 32;</code>
       */
      public static final int CRUD_DROP_VIEW_VALUE = 32;
      /**
       * <code>PREPARE_PREPARE = 40;</code>
       */
      public static final int PREPARE_PREPARE_VALUE = 40;
      /**
       * <code>PREPARE_EXECUTE = 41;</code>
       */
      public static final int PREPARE_EXECUTE_VALUE = 41;
      /**
       * <code>PREPARE_DEALLOCATE = 42;</code>
       */
      public static final int PREPARE_DEALLOCATE_VALUE = 42;
      /**
       * <code>CURSOR_OPEN = 43;</code>
       */
      public static final int CURSOR_OPEN_VALUE = 43;
      /**
       * <code>CURSOR_CLOSE = 44;</code>
       */
      public static final int CURSOR_CLOSE_VALUE = 44;
      /**
       * <code>CURSOR_FETCH = 45;</code>
       */
      public static final int CURSOR_FETCH_VALUE = 45;
      /**
       * <code>COMPRESSION = 46;</code>
       */
      public static final int COMPRESSION_VALUE = 46;


      public final int getNumber() {
        return value;
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @java.lang.Deprecated
      public static Type valueOf(int value) {
        return forNumber(value);
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       */
      public static Type forNumber(int value) {
        switch (value) {
          case 1: return CON_CAPABILITIES_GET;
          case 2: return CON_CAPABILITIES_SET;
          case 3: return CON_CLOSE;
          case 4: return SESS_AUTHENTICATE_START;
          case 5: return SESS_AUTHENTICATE_CONTINUE;
          case 6: return SESS_RESET;
          case 7: return SESS_CLOSE;
          case 12: return SQL_STMT_EXECUTE;
          case 17: return CRUD_FIND;
          case 18: return CRUD_INSERT;
          case 19: return CRUD_UPDATE;
          case 20: return CRUD_DELETE;
          case 24: return EXPECT_OPEN;
          case 25: return EXPECT_CLOSE;
          case 30: return CRUD_CREATE_VIEW;
          case 31: return CRUD_MODIFY_VIEW;
          case 32: return CRUD_DROP_VIEW;
          case 40: return PREPARE_PREPARE;
          case 41: return PREPARE_EXECUTE;
          case 42: return PREPARE_DEALLOCATE;
          case 43: return CURSOR_OPEN;
          case 44: return CURSOR_CLOSE;
          case 45: return CURSOR_FETCH;
          case 46: return COMPRESSION;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<Type>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
          Type> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<Type>() {
              public Type findValueByNumber(int number) {
                return Type.forNumber(number);
              }
            };

      public final com.google.protobuf.Descriptors.EnumValueDescriptor
          getValueDescriptor() {
        return getDescriptor().getValues().get(ordinal());
      }
      public final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptor() {
        return com.mysql.cj.x.protobuf.Mysqlx.ClientMessages.getDescriptor().getEnumTypes().get(0);
      }

      private static final Type[] VALUES = values();

      public static Type valueOf(
          com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
            "EnumValueDescriptor is not for this type.");
        }
        return VALUES[desc.getIndex()];
      }

      private final int value;

      private Type(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:Mysqlx.ClientMessages.Type)
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
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.mysql.cj.x.protobuf.Mysqlx.ClientMessages)) {
        return super.equals(obj);
      }
      com.mysql.cj.x.protobuf.Mysqlx.ClientMessages other = (com.mysql.cj.x.protobuf.Mysqlx.ClientMessages) obj;

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
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.mysql.cj.x.protobuf.Mysqlx.ClientMessages parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ClientMessages parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ClientMessages parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ClientMessages parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ClientMessages parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ClientMessages parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ClientMessages parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ClientMessages parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ClientMessages parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ClientMessages parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ClientMessages parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ClientMessages parseFrom(
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
    public static Builder newBuilder(com.mysql.cj.x.protobuf.Mysqlx.ClientMessages prototype) {
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
     **
     *IDs of messages that can be sent from client to the server.
     *&#64;note
     *This message is never sent on the wire. It is only used to let ``protoc``:
     *-  generate constants
     *-  check for uniqueness
     * </pre>
     *
     * Protobuf type {@code Mysqlx.ClientMessages}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Mysqlx.ClientMessages)
        com.mysql.cj.x.protobuf.Mysqlx.ClientMessagesOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_ClientMessages_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_ClientMessages_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.mysql.cj.x.protobuf.Mysqlx.ClientMessages.class, com.mysql.cj.x.protobuf.Mysqlx.ClientMessages.Builder.class);
      }

      // Construct using com.mysql.cj.x.protobuf.Mysqlx.ClientMessages.newBuilder()
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
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_ClientMessages_descriptor;
      }

      @java.lang.Override
      public com.mysql.cj.x.protobuf.Mysqlx.ClientMessages getDefaultInstanceForType() {
        return com.mysql.cj.x.protobuf.Mysqlx.ClientMessages.getDefaultInstance();
      }

      @java.lang.Override
      public com.mysql.cj.x.protobuf.Mysqlx.ClientMessages build() {
        com.mysql.cj.x.protobuf.Mysqlx.ClientMessages result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.mysql.cj.x.protobuf.Mysqlx.ClientMessages buildPartial() {
        com.mysql.cj.x.protobuf.Mysqlx.ClientMessages result = new com.mysql.cj.x.protobuf.Mysqlx.ClientMessages(this);
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
        if (other instanceof com.mysql.cj.x.protobuf.Mysqlx.ClientMessages) {
          return mergeFrom((com.mysql.cj.x.protobuf.Mysqlx.ClientMessages)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.mysql.cj.x.protobuf.Mysqlx.ClientMessages other) {
        if (other == com.mysql.cj.x.protobuf.Mysqlx.ClientMessages.getDefaultInstance()) return this;
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
        com.mysql.cj.x.protobuf.Mysqlx.ClientMessages parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.mysql.cj.x.protobuf.Mysqlx.ClientMessages) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
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


      // @@protoc_insertion_point(builder_scope:Mysqlx.ClientMessages)
    }

    // @@protoc_insertion_point(class_scope:Mysqlx.ClientMessages)
    private static final com.mysql.cj.x.protobuf.Mysqlx.ClientMessages DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.mysql.cj.x.protobuf.Mysqlx.ClientMessages();
    }

    public static com.mysql.cj.x.protobuf.Mysqlx.ClientMessages getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<ClientMessages>
        PARSER = new com.google.protobuf.AbstractParser<ClientMessages>() {
      @java.lang.Override
      public ClientMessages parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ClientMessages(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ClientMessages> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ClientMessages> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.mysql.cj.x.protobuf.Mysqlx.ClientMessages getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  public interface ServerMessagesOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Mysqlx.ServerMessages)
      com.google.protobuf.MessageOrBuilder {
  }
  /**
   * <pre>
   **
   *IDs of messages that can be sent from server to client.
   *&#64;note
   *This message is never sent on the wire. It is only used to let ``protoc``:
   *-  generate constants
   *-  check for uniqueness
   * </pre>
   *
   * Protobuf type {@code Mysqlx.ServerMessages}
   */
  public static final class ServerMessages extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Mysqlx.ServerMessages)
      ServerMessagesOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ServerMessages.newBuilder() to construct.
    private ServerMessages(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ServerMessages() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new ServerMessages();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ServerMessages(
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
      return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_ServerMessages_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_ServerMessages_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.mysql.cj.x.protobuf.Mysqlx.ServerMessages.class, com.mysql.cj.x.protobuf.Mysqlx.ServerMessages.Builder.class);
    }

    /**
     * Protobuf enum {@code Mysqlx.ServerMessages.Type}
     */
    public enum Type
        implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>OK = 0;</code>
       */
      OK(0),
      /**
       * <code>ERROR = 1;</code>
       */
      ERROR(1),
      /**
       * <code>CONN_CAPABILITIES = 2;</code>
       */
      CONN_CAPABILITIES(2),
      /**
       * <code>SESS_AUTHENTICATE_CONTINUE = 3;</code>
       */
      SESS_AUTHENTICATE_CONTINUE(3),
      /**
       * <code>SESS_AUTHENTICATE_OK = 4;</code>
       */
      SESS_AUTHENTICATE_OK(4),
      /**
       * <pre>
       * NOTICE has to stay at 11 forever
       * </pre>
       *
       * <code>NOTICE = 11;</code>
       */
      NOTICE(11),
      /**
       * <code>RESULTSET_COLUMN_META_DATA = 12;</code>
       */
      RESULTSET_COLUMN_META_DATA(12),
      /**
       * <code>RESULTSET_ROW = 13;</code>
       */
      RESULTSET_ROW(13),
      /**
       * <code>RESULTSET_FETCH_DONE = 14;</code>
       */
      RESULTSET_FETCH_DONE(14),
      /**
       * <code>RESULTSET_FETCH_SUSPENDED = 15;</code>
       */
      RESULTSET_FETCH_SUSPENDED(15),
      /**
       * <code>RESULTSET_FETCH_DONE_MORE_RESULTSETS = 16;</code>
       */
      RESULTSET_FETCH_DONE_MORE_RESULTSETS(16),
      /**
       * <code>SQL_STMT_EXECUTE_OK = 17;</code>
       */
      SQL_STMT_EXECUTE_OK(17),
      /**
       * <code>RESULTSET_FETCH_DONE_MORE_OUT_PARAMS = 18;</code>
       */
      RESULTSET_FETCH_DONE_MORE_OUT_PARAMS(18),
      /**
       * <code>COMPRESSION = 19;</code>
       */
      COMPRESSION(19),
      ;

      /**
       * <code>OK = 0;</code>
       */
      public static final int OK_VALUE = 0;
      /**
       * <code>ERROR = 1;</code>
       */
      public static final int ERROR_VALUE = 1;
      /**
       * <code>CONN_CAPABILITIES = 2;</code>
       */
      public static final int CONN_CAPABILITIES_VALUE = 2;
      /**
       * <code>SESS_AUTHENTICATE_CONTINUE = 3;</code>
       */
      public static final int SESS_AUTHENTICATE_CONTINUE_VALUE = 3;
      /**
       * <code>SESS_AUTHENTICATE_OK = 4;</code>
       */
      public static final int SESS_AUTHENTICATE_OK_VALUE = 4;
      /**
       * <pre>
       * NOTICE has to stay at 11 forever
       * </pre>
       *
       * <code>NOTICE = 11;</code>
       */
      public static final int NOTICE_VALUE = 11;
      /**
       * <code>RESULTSET_COLUMN_META_DATA = 12;</code>
       */
      public static final int RESULTSET_COLUMN_META_DATA_VALUE = 12;
      /**
       * <code>RESULTSET_ROW = 13;</code>
       */
      public static final int RESULTSET_ROW_VALUE = 13;
      /**
       * <code>RESULTSET_FETCH_DONE = 14;</code>
       */
      public static final int RESULTSET_FETCH_DONE_VALUE = 14;
      /**
       * <code>RESULTSET_FETCH_SUSPENDED = 15;</code>
       */
      public static final int RESULTSET_FETCH_SUSPENDED_VALUE = 15;
      /**
       * <code>RESULTSET_FETCH_DONE_MORE_RESULTSETS = 16;</code>
       */
      public static final int RESULTSET_FETCH_DONE_MORE_RESULTSETS_VALUE = 16;
      /**
       * <code>SQL_STMT_EXECUTE_OK = 17;</code>
       */
      public static final int SQL_STMT_EXECUTE_OK_VALUE = 17;
      /**
       * <code>RESULTSET_FETCH_DONE_MORE_OUT_PARAMS = 18;</code>
       */
      public static final int RESULTSET_FETCH_DONE_MORE_OUT_PARAMS_VALUE = 18;
      /**
       * <code>COMPRESSION = 19;</code>
       */
      public static final int COMPRESSION_VALUE = 19;


      public final int getNumber() {
        return value;
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @java.lang.Deprecated
      public static Type valueOf(int value) {
        return forNumber(value);
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       */
      public static Type forNumber(int value) {
        switch (value) {
          case 0: return OK;
          case 1: return ERROR;
          case 2: return CONN_CAPABILITIES;
          case 3: return SESS_AUTHENTICATE_CONTINUE;
          case 4: return SESS_AUTHENTICATE_OK;
          case 11: return NOTICE;
          case 12: return RESULTSET_COLUMN_META_DATA;
          case 13: return RESULTSET_ROW;
          case 14: return RESULTSET_FETCH_DONE;
          case 15: return RESULTSET_FETCH_SUSPENDED;
          case 16: return RESULTSET_FETCH_DONE_MORE_RESULTSETS;
          case 17: return SQL_STMT_EXECUTE_OK;
          case 18: return RESULTSET_FETCH_DONE_MORE_OUT_PARAMS;
          case 19: return COMPRESSION;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<Type>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
          Type> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<Type>() {
              public Type findValueByNumber(int number) {
                return Type.forNumber(number);
              }
            };

      public final com.google.protobuf.Descriptors.EnumValueDescriptor
          getValueDescriptor() {
        return getDescriptor().getValues().get(ordinal());
      }
      public final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptor() {
        return com.mysql.cj.x.protobuf.Mysqlx.ServerMessages.getDescriptor().getEnumTypes().get(0);
      }

      private static final Type[] VALUES = values();

      public static Type valueOf(
          com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
            "EnumValueDescriptor is not for this type.");
        }
        return VALUES[desc.getIndex()];
      }

      private final int value;

      private Type(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:Mysqlx.ServerMessages.Type)
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
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.mysql.cj.x.protobuf.Mysqlx.ServerMessages)) {
        return super.equals(obj);
      }
      com.mysql.cj.x.protobuf.Mysqlx.ServerMessages other = (com.mysql.cj.x.protobuf.Mysqlx.ServerMessages) obj;

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
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.mysql.cj.x.protobuf.Mysqlx.ServerMessages parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ServerMessages parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ServerMessages parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ServerMessages parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ServerMessages parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ServerMessages parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ServerMessages parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ServerMessages parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ServerMessages parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ServerMessages parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ServerMessages parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.ServerMessages parseFrom(
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
    public static Builder newBuilder(com.mysql.cj.x.protobuf.Mysqlx.ServerMessages prototype) {
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
     **
     *IDs of messages that can be sent from server to client.
     *&#64;note
     *This message is never sent on the wire. It is only used to let ``protoc``:
     *-  generate constants
     *-  check for uniqueness
     * </pre>
     *
     * Protobuf type {@code Mysqlx.ServerMessages}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Mysqlx.ServerMessages)
        com.mysql.cj.x.protobuf.Mysqlx.ServerMessagesOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_ServerMessages_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_ServerMessages_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.mysql.cj.x.protobuf.Mysqlx.ServerMessages.class, com.mysql.cj.x.protobuf.Mysqlx.ServerMessages.Builder.class);
      }

      // Construct using com.mysql.cj.x.protobuf.Mysqlx.ServerMessages.newBuilder()
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
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_ServerMessages_descriptor;
      }

      @java.lang.Override
      public com.mysql.cj.x.protobuf.Mysqlx.ServerMessages getDefaultInstanceForType() {
        return com.mysql.cj.x.protobuf.Mysqlx.ServerMessages.getDefaultInstance();
      }

      @java.lang.Override
      public com.mysql.cj.x.protobuf.Mysqlx.ServerMessages build() {
        com.mysql.cj.x.protobuf.Mysqlx.ServerMessages result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.mysql.cj.x.protobuf.Mysqlx.ServerMessages buildPartial() {
        com.mysql.cj.x.protobuf.Mysqlx.ServerMessages result = new com.mysql.cj.x.protobuf.Mysqlx.ServerMessages(this);
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
        if (other instanceof com.mysql.cj.x.protobuf.Mysqlx.ServerMessages) {
          return mergeFrom((com.mysql.cj.x.protobuf.Mysqlx.ServerMessages)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.mysql.cj.x.protobuf.Mysqlx.ServerMessages other) {
        if (other == com.mysql.cj.x.protobuf.Mysqlx.ServerMessages.getDefaultInstance()) return this;
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
        com.mysql.cj.x.protobuf.Mysqlx.ServerMessages parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.mysql.cj.x.protobuf.Mysqlx.ServerMessages) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
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


      // @@protoc_insertion_point(builder_scope:Mysqlx.ServerMessages)
    }

    // @@protoc_insertion_point(class_scope:Mysqlx.ServerMessages)
    private static final com.mysql.cj.x.protobuf.Mysqlx.ServerMessages DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.mysql.cj.x.protobuf.Mysqlx.ServerMessages();
    }

    public static com.mysql.cj.x.protobuf.Mysqlx.ServerMessages getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<ServerMessages>
        PARSER = new com.google.protobuf.AbstractParser<ServerMessages>() {
      @java.lang.Override
      public ServerMessages parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ServerMessages(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ServerMessages> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ServerMessages> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.mysql.cj.x.protobuf.Mysqlx.ServerMessages getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  public interface OkOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Mysqlx.Ok)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional string msg = 1;</code>
     * @return Whether the msg field is set.
     */
    boolean hasMsg();
    /**
     * <code>optional string msg = 1;</code>
     * @return The msg.
     */
    java.lang.String getMsg();
    /**
     * <code>optional string msg = 1;</code>
     * @return The bytes for msg.
     */
    com.google.protobuf.ByteString
        getMsgBytes();
  }
  /**
   * Protobuf type {@code Mysqlx.Ok}
   */
  public static final class Ok extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Mysqlx.Ok)
      OkOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Ok.newBuilder() to construct.
    private Ok(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Ok() {
      msg_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Ok();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Ok(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
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
            case 10: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000001;
              msg_ = bs;
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
      return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_Ok_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_Ok_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.mysql.cj.x.protobuf.Mysqlx.Ok.class, com.mysql.cj.x.protobuf.Mysqlx.Ok.Builder.class);
    }

    private int bitField0_;
    public static final int MSG_FIELD_NUMBER = 1;
    private volatile java.lang.Object msg_;
    /**
     * <code>optional string msg = 1;</code>
     * @return Whether the msg field is set.
     */
    @java.lang.Override
    public boolean hasMsg() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>optional string msg = 1;</code>
     * @return The msg.
     */
    @java.lang.Override
    public java.lang.String getMsg() {
      java.lang.Object ref = msg_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          msg_ = s;
        }
        return s;
      }
    }
    /**
     * <code>optional string msg = 1;</code>
     * @return The bytes for msg.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getMsgBytes() {
      java.lang.Object ref = msg_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        msg_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
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
      if (((bitField0_ & 0x00000001) != 0)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, msg_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) != 0)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, msg_);
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
      if (!(obj instanceof com.mysql.cj.x.protobuf.Mysqlx.Ok)) {
        return super.equals(obj);
      }
      com.mysql.cj.x.protobuf.Mysqlx.Ok other = (com.mysql.cj.x.protobuf.Mysqlx.Ok) obj;

      if (hasMsg() != other.hasMsg()) return false;
      if (hasMsg()) {
        if (!getMsg()
            .equals(other.getMsg())) return false;
      }
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
      if (hasMsg()) {
        hash = (37 * hash) + MSG_FIELD_NUMBER;
        hash = (53 * hash) + getMsg().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.mysql.cj.x.protobuf.Mysqlx.Ok parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Ok parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Ok parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Ok parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Ok parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Ok parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Ok parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Ok parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Ok parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Ok parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Ok parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Ok parseFrom(
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
    public static Builder newBuilder(com.mysql.cj.x.protobuf.Mysqlx.Ok prototype) {
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
     * Protobuf type {@code Mysqlx.Ok}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Mysqlx.Ok)
        com.mysql.cj.x.protobuf.Mysqlx.OkOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_Ok_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_Ok_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.mysql.cj.x.protobuf.Mysqlx.Ok.class, com.mysql.cj.x.protobuf.Mysqlx.Ok.Builder.class);
      }

      // Construct using com.mysql.cj.x.protobuf.Mysqlx.Ok.newBuilder()
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
        msg_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_Ok_descriptor;
      }

      @java.lang.Override
      public com.mysql.cj.x.protobuf.Mysqlx.Ok getDefaultInstanceForType() {
        return com.mysql.cj.x.protobuf.Mysqlx.Ok.getDefaultInstance();
      }

      @java.lang.Override
      public com.mysql.cj.x.protobuf.Mysqlx.Ok build() {
        com.mysql.cj.x.protobuf.Mysqlx.Ok result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.mysql.cj.x.protobuf.Mysqlx.Ok buildPartial() {
        com.mysql.cj.x.protobuf.Mysqlx.Ok result = new com.mysql.cj.x.protobuf.Mysqlx.Ok(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          to_bitField0_ |= 0x00000001;
        }
        result.msg_ = msg_;
        result.bitField0_ = to_bitField0_;
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
        if (other instanceof com.mysql.cj.x.protobuf.Mysqlx.Ok) {
          return mergeFrom((com.mysql.cj.x.protobuf.Mysqlx.Ok)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.mysql.cj.x.protobuf.Mysqlx.Ok other) {
        if (other == com.mysql.cj.x.protobuf.Mysqlx.Ok.getDefaultInstance()) return this;
        if (other.hasMsg()) {
          bitField0_ |= 0x00000001;
          msg_ = other.msg_;
          onChanged();
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
        com.mysql.cj.x.protobuf.Mysqlx.Ok parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.mysql.cj.x.protobuf.Mysqlx.Ok) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object msg_ = "";
      /**
       * <code>optional string msg = 1;</code>
       * @return Whether the msg field is set.
       */
      public boolean hasMsg() {
        return ((bitField0_ & 0x00000001) != 0);
      }
      /**
       * <code>optional string msg = 1;</code>
       * @return The msg.
       */
      public java.lang.String getMsg() {
        java.lang.Object ref = msg_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            msg_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string msg = 1;</code>
       * @return The bytes for msg.
       */
      public com.google.protobuf.ByteString
          getMsgBytes() {
        java.lang.Object ref = msg_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          msg_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string msg = 1;</code>
       * @param value The msg to set.
       * @return This builder for chaining.
       */
      public Builder setMsg(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        msg_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string msg = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearMsg() {
        bitField0_ = (bitField0_ & ~0x00000001);
        msg_ = getDefaultInstance().getMsg();
        onChanged();
        return this;
      }
      /**
       * <code>optional string msg = 1;</code>
       * @param value The bytes for msg to set.
       * @return This builder for chaining.
       */
      public Builder setMsgBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        msg_ = value;
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


      // @@protoc_insertion_point(builder_scope:Mysqlx.Ok)
    }

    // @@protoc_insertion_point(class_scope:Mysqlx.Ok)
    private static final com.mysql.cj.x.protobuf.Mysqlx.Ok DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.mysql.cj.x.protobuf.Mysqlx.Ok();
    }

    public static com.mysql.cj.x.protobuf.Mysqlx.Ok getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<Ok>
        PARSER = new com.google.protobuf.AbstractParser<Ok>() {
      @java.lang.Override
      public Ok parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Ok(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Ok> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Ok> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.mysql.cj.x.protobuf.Mysqlx.Ok getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  public interface ErrorOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Mysqlx.Error)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     ** severity of the error message 
     * </pre>
     *
     * <code>optional .Mysqlx.Error.Severity severity = 1 [default = ERROR];</code>
     * @return Whether the severity field is set.
     */
    boolean hasSeverity();
    /**
     * <pre>
     ** severity of the error message 
     * </pre>
     *
     * <code>optional .Mysqlx.Error.Severity severity = 1 [default = ERROR];</code>
     * @return The severity.
     */
    com.mysql.cj.x.protobuf.Mysqlx.Error.Severity getSeverity();

    /**
     * <pre>
     ** error code 
     * </pre>
     *
     * <code>required uint32 code = 2;</code>
     * @return Whether the code field is set.
     */
    boolean hasCode();
    /**
     * <pre>
     ** error code 
     * </pre>
     *
     * <code>required uint32 code = 2;</code>
     * @return The code.
     */
    int getCode();

    /**
     * <pre>
     ** SQL state 
     * </pre>
     *
     * <code>required string sql_state = 4;</code>
     * @return Whether the sqlState field is set.
     */
    boolean hasSqlState();
    /**
     * <pre>
     ** SQL state 
     * </pre>
     *
     * <code>required string sql_state = 4;</code>
     * @return The sqlState.
     */
    java.lang.String getSqlState();
    /**
     * <pre>
     ** SQL state 
     * </pre>
     *
     * <code>required string sql_state = 4;</code>
     * @return The bytes for sqlState.
     */
    com.google.protobuf.ByteString
        getSqlStateBytes();

    /**
     * <pre>
     ** human-readable error message 
     * </pre>
     *
     * <code>required string msg = 3;</code>
     * @return Whether the msg field is set.
     */
    boolean hasMsg();
    /**
     * <pre>
     ** human-readable error message 
     * </pre>
     *
     * <code>required string msg = 3;</code>
     * @return The msg.
     */
    java.lang.String getMsg();
    /**
     * <pre>
     ** human-readable error message 
     * </pre>
     *
     * <code>required string msg = 3;</code>
     * @return The bytes for msg.
     */
    com.google.protobuf.ByteString
        getMsgBytes();
  }
  /**
   * Protobuf type {@code Mysqlx.Error}
   */
  public static final class Error extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Mysqlx.Error)
      ErrorOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Error.newBuilder() to construct.
    private Error(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Error() {
      severity_ = 0;
      sqlState_ = "";
      msg_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Error();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Error(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
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
                @SuppressWarnings("deprecation")
              com.mysql.cj.x.protobuf.Mysqlx.Error.Severity value = com.mysql.cj.x.protobuf.Mysqlx.Error.Severity.valueOf(rawValue);
              if (value == null) {
                unknownFields.mergeVarintField(1, rawValue);
              } else {
                bitField0_ |= 0x00000001;
                severity_ = rawValue;
              }
              break;
            }
            case 16: {
              bitField0_ |= 0x00000002;
              code_ = input.readUInt32();
              break;
            }
            case 26: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000008;
              msg_ = bs;
              break;
            }
            case 34: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000004;
              sqlState_ = bs;
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
      return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_Error_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_Error_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.mysql.cj.x.protobuf.Mysqlx.Error.class, com.mysql.cj.x.protobuf.Mysqlx.Error.Builder.class);
    }

    /**
     * Protobuf enum {@code Mysqlx.Error.Severity}
     */
    public enum Severity
        implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>ERROR = 0;</code>
       */
      ERROR(0),
      /**
       * <code>FATAL = 1;</code>
       */
      FATAL(1),
      ;

      /**
       * <code>ERROR = 0;</code>
       */
      public static final int ERROR_VALUE = 0;
      /**
       * <code>FATAL = 1;</code>
       */
      public static final int FATAL_VALUE = 1;


      public final int getNumber() {
        return value;
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @java.lang.Deprecated
      public static Severity valueOf(int value) {
        return forNumber(value);
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       */
      public static Severity forNumber(int value) {
        switch (value) {
          case 0: return ERROR;
          case 1: return FATAL;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<Severity>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
          Severity> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<Severity>() {
              public Severity findValueByNumber(int number) {
                return Severity.forNumber(number);
              }
            };

      public final com.google.protobuf.Descriptors.EnumValueDescriptor
          getValueDescriptor() {
        return getDescriptor().getValues().get(ordinal());
      }
      public final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptor() {
        return com.mysql.cj.x.protobuf.Mysqlx.Error.getDescriptor().getEnumTypes().get(0);
      }

      private static final Severity[] VALUES = values();

      public static Severity valueOf(
          com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
            "EnumValueDescriptor is not for this type.");
        }
        return VALUES[desc.getIndex()];
      }

      private final int value;

      private Severity(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:Mysqlx.Error.Severity)
    }

    private int bitField0_;
    public static final int SEVERITY_FIELD_NUMBER = 1;
    private int severity_;
    /**
     * <pre>
     ** severity of the error message 
     * </pre>
     *
     * <code>optional .Mysqlx.Error.Severity severity = 1 [default = ERROR];</code>
     * @return Whether the severity field is set.
     */
    @java.lang.Override public boolean hasSeverity() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <pre>
     ** severity of the error message 
     * </pre>
     *
     * <code>optional .Mysqlx.Error.Severity severity = 1 [default = ERROR];</code>
     * @return The severity.
     */
    @java.lang.Override public com.mysql.cj.x.protobuf.Mysqlx.Error.Severity getSeverity() {
      @SuppressWarnings("deprecation")
      com.mysql.cj.x.protobuf.Mysqlx.Error.Severity result = com.mysql.cj.x.protobuf.Mysqlx.Error.Severity.valueOf(severity_);
      return result == null ? com.mysql.cj.x.protobuf.Mysqlx.Error.Severity.ERROR : result;
    }

    public static final int CODE_FIELD_NUMBER = 2;
    private int code_;
    /**
     * <pre>
     ** error code 
     * </pre>
     *
     * <code>required uint32 code = 2;</code>
     * @return Whether the code field is set.
     */
    @java.lang.Override
    public boolean hasCode() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <pre>
     ** error code 
     * </pre>
     *
     * <code>required uint32 code = 2;</code>
     * @return The code.
     */
    @java.lang.Override
    public int getCode() {
      return code_;
    }

    public static final int SQL_STATE_FIELD_NUMBER = 4;
    private volatile java.lang.Object sqlState_;
    /**
     * <pre>
     ** SQL state 
     * </pre>
     *
     * <code>required string sql_state = 4;</code>
     * @return Whether the sqlState field is set.
     */
    @java.lang.Override
    public boolean hasSqlState() {
      return ((bitField0_ & 0x00000004) != 0);
    }
    /**
     * <pre>
     ** SQL state 
     * </pre>
     *
     * <code>required string sql_state = 4;</code>
     * @return The sqlState.
     */
    @java.lang.Override
    public java.lang.String getSqlState() {
      java.lang.Object ref = sqlState_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          sqlState_ = s;
        }
        return s;
      }
    }
    /**
     * <pre>
     ** SQL state 
     * </pre>
     *
     * <code>required string sql_state = 4;</code>
     * @return The bytes for sqlState.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getSqlStateBytes() {
      java.lang.Object ref = sqlState_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        sqlState_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int MSG_FIELD_NUMBER = 3;
    private volatile java.lang.Object msg_;
    /**
     * <pre>
     ** human-readable error message 
     * </pre>
     *
     * <code>required string msg = 3;</code>
     * @return Whether the msg field is set.
     */
    @java.lang.Override
    public boolean hasMsg() {
      return ((bitField0_ & 0x00000008) != 0);
    }
    /**
     * <pre>
     ** human-readable error message 
     * </pre>
     *
     * <code>required string msg = 3;</code>
     * @return The msg.
     */
    @java.lang.Override
    public java.lang.String getMsg() {
      java.lang.Object ref = msg_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          msg_ = s;
        }
        return s;
      }
    }
    /**
     * <pre>
     ** human-readable error message 
     * </pre>
     *
     * <code>required string msg = 3;</code>
     * @return The bytes for msg.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getMsgBytes() {
      java.lang.Object ref = msg_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        msg_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasCode()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasSqlState()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasMsg()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) != 0)) {
        output.writeEnum(1, severity_);
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        output.writeUInt32(2, code_);
      }
      if (((bitField0_ & 0x00000008) != 0)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, msg_);
      }
      if (((bitField0_ & 0x00000004) != 0)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, sqlState_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) != 0)) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(1, severity_);
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(2, code_);
      }
      if (((bitField0_ & 0x00000008) != 0)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, msg_);
      }
      if (((bitField0_ & 0x00000004) != 0)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, sqlState_);
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
      if (!(obj instanceof com.mysql.cj.x.protobuf.Mysqlx.Error)) {
        return super.equals(obj);
      }
      com.mysql.cj.x.protobuf.Mysqlx.Error other = (com.mysql.cj.x.protobuf.Mysqlx.Error) obj;

      if (hasSeverity() != other.hasSeverity()) return false;
      if (hasSeverity()) {
        if (severity_ != other.severity_) return false;
      }
      if (hasCode() != other.hasCode()) return false;
      if (hasCode()) {
        if (getCode()
            != other.getCode()) return false;
      }
      if (hasSqlState() != other.hasSqlState()) return false;
      if (hasSqlState()) {
        if (!getSqlState()
            .equals(other.getSqlState())) return false;
      }
      if (hasMsg() != other.hasMsg()) return false;
      if (hasMsg()) {
        if (!getMsg()
            .equals(other.getMsg())) return false;
      }
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
      if (hasSeverity()) {
        hash = (37 * hash) + SEVERITY_FIELD_NUMBER;
        hash = (53 * hash) + severity_;
      }
      if (hasCode()) {
        hash = (37 * hash) + CODE_FIELD_NUMBER;
        hash = (53 * hash) + getCode();
      }
      if (hasSqlState()) {
        hash = (37 * hash) + SQL_STATE_FIELD_NUMBER;
        hash = (53 * hash) + getSqlState().hashCode();
      }
      if (hasMsg()) {
        hash = (37 * hash) + MSG_FIELD_NUMBER;
        hash = (53 * hash) + getMsg().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.mysql.cj.x.protobuf.Mysqlx.Error parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Error parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Error parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Error parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Error parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Error parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Error parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Error parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Error parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Error parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Error parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.mysql.cj.x.protobuf.Mysqlx.Error parseFrom(
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
    public static Builder newBuilder(com.mysql.cj.x.protobuf.Mysqlx.Error prototype) {
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
     * Protobuf type {@code Mysqlx.Error}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Mysqlx.Error)
        com.mysql.cj.x.protobuf.Mysqlx.ErrorOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_Error_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_Error_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.mysql.cj.x.protobuf.Mysqlx.Error.class, com.mysql.cj.x.protobuf.Mysqlx.Error.Builder.class);
      }

      // Construct using com.mysql.cj.x.protobuf.Mysqlx.Error.newBuilder()
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
        severity_ = 0;
        bitField0_ = (bitField0_ & ~0x00000001);
        code_ = 0;
        bitField0_ = (bitField0_ & ~0x00000002);
        sqlState_ = "";
        bitField0_ = (bitField0_ & ~0x00000004);
        msg_ = "";
        bitField0_ = (bitField0_ & ~0x00000008);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.mysql.cj.x.protobuf.Mysqlx.internal_static_Mysqlx_Error_descriptor;
      }

      @java.lang.Override
      public com.mysql.cj.x.protobuf.Mysqlx.Error getDefaultInstanceForType() {
        return com.mysql.cj.x.protobuf.Mysqlx.Error.getDefaultInstance();
      }

      @java.lang.Override
      public com.mysql.cj.x.protobuf.Mysqlx.Error build() {
        com.mysql.cj.x.protobuf.Mysqlx.Error result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.mysql.cj.x.protobuf.Mysqlx.Error buildPartial() {
        com.mysql.cj.x.protobuf.Mysqlx.Error result = new com.mysql.cj.x.protobuf.Mysqlx.Error(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          to_bitField0_ |= 0x00000001;
        }
        result.severity_ = severity_;
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.code_ = code_;
          to_bitField0_ |= 0x00000002;
        }
        if (((from_bitField0_ & 0x00000004) != 0)) {
          to_bitField0_ |= 0x00000004;
        }
        result.sqlState_ = sqlState_;
        if (((from_bitField0_ & 0x00000008) != 0)) {
          to_bitField0_ |= 0x00000008;
        }
        result.msg_ = msg_;
        result.bitField0_ = to_bitField0_;
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
        if (other instanceof com.mysql.cj.x.protobuf.Mysqlx.Error) {
          return mergeFrom((com.mysql.cj.x.protobuf.Mysqlx.Error)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.mysql.cj.x.protobuf.Mysqlx.Error other) {
        if (other == com.mysql.cj.x.protobuf.Mysqlx.Error.getDefaultInstance()) return this;
        if (other.hasSeverity()) {
          setSeverity(other.getSeverity());
        }
        if (other.hasCode()) {
          setCode(other.getCode());
        }
        if (other.hasSqlState()) {
          bitField0_ |= 0x00000004;
          sqlState_ = other.sqlState_;
          onChanged();
        }
        if (other.hasMsg()) {
          bitField0_ |= 0x00000008;
          msg_ = other.msg_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        if (!hasCode()) {
          return false;
        }
        if (!hasSqlState()) {
          return false;
        }
        if (!hasMsg()) {
          return false;
        }
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.mysql.cj.x.protobuf.Mysqlx.Error parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.mysql.cj.x.protobuf.Mysqlx.Error) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private int severity_ = 0;
      /**
       * <pre>
       ** severity of the error message 
       * </pre>
       *
       * <code>optional .Mysqlx.Error.Severity severity = 1 [default = ERROR];</code>
       * @return Whether the severity field is set.
       */
      @java.lang.Override public boolean hasSeverity() {
        return ((bitField0_ & 0x00000001) != 0);
      }
      /**
       * <pre>
       ** severity of the error message 
       * </pre>
       *
       * <code>optional .Mysqlx.Error.Severity severity = 1 [default = ERROR];</code>
       * @return The severity.
       */
      @java.lang.Override
      public com.mysql.cj.x.protobuf.Mysqlx.Error.Severity getSeverity() {
        @SuppressWarnings("deprecation")
        com.mysql.cj.x.protobuf.Mysqlx.Error.Severity result = com.mysql.cj.x.protobuf.Mysqlx.Error.Severity.valueOf(severity_);
        return result == null ? com.mysql.cj.x.protobuf.Mysqlx.Error.Severity.ERROR : result;
      }
      /**
       * <pre>
       ** severity of the error message 
       * </pre>
       *
       * <code>optional .Mysqlx.Error.Severity severity = 1 [default = ERROR];</code>
       * @param value The severity to set.
       * @return This builder for chaining.
       */
      public Builder setSeverity(com.mysql.cj.x.protobuf.Mysqlx.Error.Severity value) {
        if (value == null) {
          throw new NullPointerException();
        }
        bitField0_ |= 0x00000001;
        severity_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <pre>
       ** severity of the error message 
       * </pre>
       *
       * <code>optional .Mysqlx.Error.Severity severity = 1 [default = ERROR];</code>
       * @return This builder for chaining.
       */
      public Builder clearSeverity() {
        bitField0_ = (bitField0_ & ~0x00000001);
        severity_ = 0;
        onChanged();
        return this;
      }

      private int code_ ;
      /**
       * <pre>
       ** error code 
       * </pre>
       *
       * <code>required uint32 code = 2;</code>
       * @return Whether the code field is set.
       */
      @java.lang.Override
      public boolean hasCode() {
        return ((bitField0_ & 0x00000002) != 0);
      }
      /**
       * <pre>
       ** error code 
       * </pre>
       *
       * <code>required uint32 code = 2;</code>
       * @return The code.
       */
      @java.lang.Override
      public int getCode() {
        return code_;
      }
      /**
       * <pre>
       ** error code 
       * </pre>
       *
       * <code>required uint32 code = 2;</code>
       * @param value The code to set.
       * @return This builder for chaining.
       */
      public Builder setCode(int value) {
        bitField0_ |= 0x00000002;
        code_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       ** error code 
       * </pre>
       *
       * <code>required uint32 code = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearCode() {
        bitField0_ = (bitField0_ & ~0x00000002);
        code_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object sqlState_ = "";
      /**
       * <pre>
       ** SQL state 
       * </pre>
       *
       * <code>required string sql_state = 4;</code>
       * @return Whether the sqlState field is set.
       */
      public boolean hasSqlState() {
        return ((bitField0_ & 0x00000004) != 0);
      }
      /**
       * <pre>
       ** SQL state 
       * </pre>
       *
       * <code>required string sql_state = 4;</code>
       * @return The sqlState.
       */
      public java.lang.String getSqlState() {
        java.lang.Object ref = sqlState_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            sqlState_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       ** SQL state 
       * </pre>
       *
       * <code>required string sql_state = 4;</code>
       * @return The bytes for sqlState.
       */
      public com.google.protobuf.ByteString
          getSqlStateBytes() {
        java.lang.Object ref = sqlState_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          sqlState_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       ** SQL state 
       * </pre>
       *
       * <code>required string sql_state = 4;</code>
       * @param value The sqlState to set.
       * @return This builder for chaining.
       */
      public Builder setSqlState(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        sqlState_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       ** SQL state 
       * </pre>
       *
       * <code>required string sql_state = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearSqlState() {
        bitField0_ = (bitField0_ & ~0x00000004);
        sqlState_ = getDefaultInstance().getSqlState();
        onChanged();
        return this;
      }
      /**
       * <pre>
       ** SQL state 
       * </pre>
       *
       * <code>required string sql_state = 4;</code>
       * @param value The bytes for sqlState to set.
       * @return This builder for chaining.
       */
      public Builder setSqlStateBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        sqlState_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object msg_ = "";
      /**
       * <pre>
       ** human-readable error message 
       * </pre>
       *
       * <code>required string msg = 3;</code>
       * @return Whether the msg field is set.
       */
      public boolean hasMsg() {
        return ((bitField0_ & 0x00000008) != 0);
      }
      /**
       * <pre>
       ** human-readable error message 
       * </pre>
       *
       * <code>required string msg = 3;</code>
       * @return The msg.
       */
      public java.lang.String getMsg() {
        java.lang.Object ref = msg_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            msg_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       ** human-readable error message 
       * </pre>
       *
       * <code>required string msg = 3;</code>
       * @return The bytes for msg.
       */
      public com.google.protobuf.ByteString
          getMsgBytes() {
        java.lang.Object ref = msg_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          msg_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       ** human-readable error message 
       * </pre>
       *
       * <code>required string msg = 3;</code>
       * @param value The msg to set.
       * @return This builder for chaining.
       */
      public Builder setMsg(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
        msg_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       ** human-readable error message 
       * </pre>
       *
       * <code>required string msg = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearMsg() {
        bitField0_ = (bitField0_ & ~0x00000008);
        msg_ = getDefaultInstance().getMsg();
        onChanged();
        return this;
      }
      /**
       * <pre>
       ** human-readable error message 
       * </pre>
       *
       * <code>required string msg = 3;</code>
       * @param value The bytes for msg to set.
       * @return This builder for chaining.
       */
      public Builder setMsgBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
        msg_ = value;
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


      // @@protoc_insertion_point(builder_scope:Mysqlx.Error)
    }

    // @@protoc_insertion_point(class_scope:Mysqlx.Error)
    private static final com.mysql.cj.x.protobuf.Mysqlx.Error DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.mysql.cj.x.protobuf.Mysqlx.Error();
    }

    public static com.mysql.cj.x.protobuf.Mysqlx.Error getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<Error>
        PARSER = new com.google.protobuf.AbstractParser<Error>() {
      @java.lang.Override
      public Error parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Error(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Error> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Error> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.mysql.cj.x.protobuf.Mysqlx.Error getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  public static final int CLIENT_MESSAGE_ID_FIELD_NUMBER = 100001;
  /**
   * <code>extend .google.protobuf.MessageOptions { ... }</code>
   */
  public static final
    com.google.protobuf.GeneratedMessage.GeneratedExtension<
      com.google.protobuf.DescriptorProtos.MessageOptions,
      com.mysql.cj.x.protobuf.Mysqlx.ClientMessages.Type> clientMessageId = com.google.protobuf.GeneratedMessage
          .newFileScopedGeneratedExtension(
        com.mysql.cj.x.protobuf.Mysqlx.ClientMessages.Type.class,
        null);
  public static final int SERVER_MESSAGE_ID_FIELD_NUMBER = 100002;
  /**
   * <code>extend .google.protobuf.MessageOptions { ... }</code>
   */
  public static final
    com.google.protobuf.GeneratedMessage.GeneratedExtension<
      com.google.protobuf.DescriptorProtos.MessageOptions,
      com.mysql.cj.x.protobuf.Mysqlx.ServerMessages.Type> serverMessageId = com.google.protobuf.GeneratedMessage
          .newFileScopedGeneratedExtension(
        com.mysql.cj.x.protobuf.Mysqlx.ServerMessages.Type.class,
        null);
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Mysqlx_ClientMessages_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Mysqlx_ClientMessages_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Mysqlx_ServerMessages_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Mysqlx_ServerMessages_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Mysqlx_Ok_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Mysqlx_Ok_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Mysqlx_Error_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Mysqlx_Error_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014mysqlx.proto\022\006Mysqlx\032 google/protobuf/" +
      "descriptor.proto\"\374\003\n\016ClientMessages\"\351\003\n\004" +
      "Type\022\030\n\024CON_CAPABILITIES_GET\020\001\022\030\n\024CON_CA" +
      "PABILITIES_SET\020\002\022\r\n\tCON_CLOSE\020\003\022\033\n\027SESS_" +
      "AUTHENTICATE_START\020\004\022\036\n\032SESS_AUTHENTICAT" +
      "E_CONTINUE\020\005\022\016\n\nSESS_RESET\020\006\022\016\n\nSESS_CLO" +
      "SE\020\007\022\024\n\020SQL_STMT_EXECUTE\020\014\022\r\n\tCRUD_FIND\020" +
      "\021\022\017\n\013CRUD_INSERT\020\022\022\017\n\013CRUD_UPDATE\020\023\022\017\n\013C" +
      "RUD_DELETE\020\024\022\017\n\013EXPECT_OPEN\020\030\022\020\n\014EXPECT_" +
      "CLOSE\020\031\022\024\n\020CRUD_CREATE_VIEW\020\036\022\024\n\020CRUD_MO" +
      "DIFY_VIEW\020\037\022\022\n\016CRUD_DROP_VIEW\020 \022\023\n\017PREPA" +
      "RE_PREPARE\020(\022\023\n\017PREPARE_EXECUTE\020)\022\026\n\022PRE" +
      "PARE_DEALLOCATE\020*\022\017\n\013CURSOR_OPEN\020+\022\020\n\014CU" +
      "RSOR_CLOSE\020,\022\020\n\014CURSOR_FETCH\020-\022\017\n\013COMPRE" +
      "SSION\020.\"\363\002\n\016ServerMessages\"\340\002\n\004Type\022\006\n\002O" +
      "K\020\000\022\t\n\005ERROR\020\001\022\025\n\021CONN_CAPABILITIES\020\002\022\036\n" +
      "\032SESS_AUTHENTICATE_CONTINUE\020\003\022\030\n\024SESS_AU" +
      "THENTICATE_OK\020\004\022\n\n\006NOTICE\020\013\022\036\n\032RESULTSET" +
      "_COLUMN_META_DATA\020\014\022\021\n\rRESULTSET_ROW\020\r\022\030" +
      "\n\024RESULTSET_FETCH_DONE\020\016\022\035\n\031RESULTSET_FE" +
      "TCH_SUSPENDED\020\017\022(\n$RESULTSET_FETCH_DONE_" +
      "MORE_RESULTSETS\020\020\022\027\n\023SQL_STMT_EXECUTE_OK" +
      "\020\021\022(\n$RESULTSET_FETCH_DONE_MORE_OUT_PARA" +
      "MS\020\022\022\017\n\013COMPRESSION\020\023\"\027\n\002Ok\022\013\n\003msg\030\001 \001(\t" +
      ":\004\220\3520\000\"\216\001\n\005Error\022/\n\010severity\030\001 \001(\0162\026.Mys" +
      "qlx.Error.Severity:\005ERROR\022\014\n\004code\030\002 \002(\r\022" +
      "\021\n\tsql_state\030\004 \002(\t\022\013\n\003msg\030\003 \002(\t\" \n\010Sever" +
      "ity\022\t\n\005ERROR\020\000\022\t\n\005FATAL\020\001:\004\220\3520\001:Y\n\021clien" +
      "t_message_id\022\037.google.protobuf.MessageOp" +
      "tions\030\241\215\006 \001(\0162\033.Mysqlx.ClientMessages.Ty" +
      "pe:Y\n\021server_message_id\022\037.google.protobu" +
      "f.MessageOptions\030\242\215\006 \001(\0162\033.Mysqlx.Server" +
      "Messages.TypeB\031\n\027com.mysql.cj.x.protobuf"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.DescriptorProtos.getDescriptor(),
        });
    internal_static_Mysqlx_ClientMessages_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Mysqlx_ClientMessages_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Mysqlx_ClientMessages_descriptor,
        new java.lang.String[] { });
    internal_static_Mysqlx_ServerMessages_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_Mysqlx_ServerMessages_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Mysqlx_ServerMessages_descriptor,
        new java.lang.String[] { });
    internal_static_Mysqlx_Ok_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_Mysqlx_Ok_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Mysqlx_Ok_descriptor,
        new java.lang.String[] { "Msg", });
    internal_static_Mysqlx_Error_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_Mysqlx_Error_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Mysqlx_Error_descriptor,
        new java.lang.String[] { "Severity", "Code", "SqlState", "Msg", });
    clientMessageId.internalInit(descriptor.getExtensions().get(0));
    serverMessageId.internalInit(descriptor.getExtensions().get(1));
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.mysql.cj.x.protobuf.Mysqlx.serverMessageId);
    com.google.protobuf.Descriptors.FileDescriptor
        .internalUpdateFileDescriptor(descriptor, registry);
    com.google.protobuf.DescriptorProtos.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}

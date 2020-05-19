# Serde Sandbox for Java

This project serves as a playground for demonstrating different data serialization formats using Java. 

A simple [Serde](./src/main/java/sandbox/serde/Serde.java) interface defines two simple functions:

- serialize a Java object to a byte array
- deserialize a byte array to a Java object

There are several implementations of this interface:

| Implementation | Description |
|----------------|-------------|
| [GenericAvroSerde](./src/main/java/sandbox/serde/avro/GenericAvroSerde.java)       | Avro serialization using generic API    |
| [SpecificAvroSerde](./src/main/java/sandbox/serde/avro/SpecificAvroSerde.java)     | Avro serialization using specific API   |
| [ReflectionAvroSerde](./src/main/java/sandbox/serde/avro/ReflectionAvroSerde.java) | Avro serialization using reflection API |
| [JsonSerde](./src/main/java/sandbox/serde/json/JsonSerde.java)                     | JSON serialization using [Jackson](https://github.com/FasterXML/jackson-databind) |
| [ProtoSerde](./src/main/java/sandbox/serde/proto/ProtoSerde.java)                  | Protocol Buffers serialization          |

Demonstration of each can be seen in [unit tests](./src/test/java/sandbox/serde).

package sandbox.serde;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.junit.jupiter.api.Test;
import sandbox.serde.avro.GenericAvroSerde;
import sandbox.serde.avro.ReflectionAvroSerde;
import sandbox.serde.avro.SpecificAvroSerde;
import sandbox.serde.json.JsonSerde;
import sandbox.serde.proto.ProtoSerde;

/** Tests to compare different serialization methods. */
public class SerdeTests {

  private static final Schema schema = sandbox.serde.avro.User.getClassSchema();

  private final Serde<sandbox.serde.User> jsonSerde = new JsonSerde<>(sandbox.serde.User.class);
  private final Serde<sandbox.serde.User> reflectionAvroSerde =
      new ReflectionAvroSerde<>(sandbox.serde.User.class);
  private final Serde<GenericRecord> genericAvroSerde = new GenericAvroSerde(schema);
  private final Serde<sandbox.serde.avro.User> specificAvroSerde =
      new SpecificAvroSerde<>(sandbox.serde.avro.User.class);
  private final Serde<sandbox.serde.proto.UserProto.User> protoSerde =
      new ProtoSerde<>(sandbox.serde.proto.UserProto.User.class);

  @Test
  public void compare() {
    // create representation of a user with a POJO
    sandbox.serde.User pojo = new sandbox.serde.User();
    pojo.setId(1);
    pojo.setEmail("john.doe@gmail.com");
    pojo.setFirstName("John");
    pojo.setLastName("Doe");

    // create representation of a user with an Avro SpecificRecord generated from a schema
    sandbox.serde.avro.User specific = new sandbox.serde.avro.User();
    specific.setId(pojo.getId());
    specific.setEmail(pojo.getEmail());
    specific.setFirstName(pojo.getFirstName());
    specific.setLastName(pojo.getLastName());

    // create representation of a user with an Avro GenericRecord
    GenericRecord generic = new GenericData.Record(schema);
    generic.put("id", pojo.getId());
    generic.put("email", pojo.getEmail());
    generic.put("firstName", pojo.getFirstName());
    generic.put("lastName", pojo.getLastName());

    // create representation of a user with Protocol buffers
    sandbox.serde.proto.UserProto.User protoMessage =
        sandbox.serde.proto.UserProto.User.newBuilder()
            .setId(pojo.getId())
            .setEmail(pojo.getEmail())
            .setFirstName(pojo.getFirstName())
            .setLastName(pojo.getLastName())
            .build();

    // serialize
    byte[] json = jsonSerde.serialize(pojo);
    byte[] avro = reflectionAvroSerde.serialize(pojo);
    byte[] proto = protoSerde.serialize(protoMessage);

    // Avro-encoded data should be identical whether Reflection, Generic, or Specific serialization
    // is used
    assertArrayEquals(avro, genericAvroSerde.serialize(generic));
    assertArrayEquals(avro, specificAvroSerde.serialize(specific));

    // verify size of byte arrays (avro < proto < json)
    assertEquals(31, avro.length);
    assertEquals(33, proto.length);
    assertEquals(73, json.length);
  }
}

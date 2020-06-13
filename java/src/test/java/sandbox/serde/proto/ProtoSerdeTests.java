package sandbox.serde.proto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import sandbox.serde.Serde;

public class ProtoSerdeTests {

  private final Serde<UserProto.User> serde = new ProtoSerde<>(UserProto.User.class);

  @Test
  public void serde() {
    // given
    UserProto.User input =
        UserProto.User.newBuilder()
            .setId(1)
            .setEmail("john.doe@gmail.com")
            .setFirstName("John")
            .setLastName("Doe")
            .build();

    // serialize to bytes
    byte[] bytes = serde.serialize(input);

    // deserialize bytes
    UserProto.User deserialized = serde.deserialize(bytes);
    assertEquals(input, deserialized);
  }
}

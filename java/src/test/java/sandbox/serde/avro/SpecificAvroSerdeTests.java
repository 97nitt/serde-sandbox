package sandbox.serde.avro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import sandbox.serde.Serde;

public class SpecificAvroSerdeTests {

  private final Serde<User> serde = new SpecificAvroSerde<>(User.class);

  @Test
  public void serde() {
    // given
    User input = new User();
    input.setId(1);
    input.setEmail("john.doe@gmail.com");
    input.setFirstName("John");
    input.setLastName("Doe");

    // serialize to bytes
    byte[] bytes = serde.serialize(input);

    // deserialize bytes
    User deserialized = serde.deserialize(bytes);
    assertEquals(input, deserialized);
  }
}

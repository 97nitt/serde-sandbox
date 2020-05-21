package sandbox.serde.avro;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import sandbox.serde.Serde;
import sandbox.serde.User;

public class ReflectionAvroSerdeTests {

  private final Serde<User> serde = new ReflectionAvroSerde<>(User.class);

  @Test
  public void serde() {
    // given
    User input = new User(1, "john.doe@gmail.com", "John", "Doe");

    // serialize to bytes
    byte[] bytes = serde.serialize(input);

    // deserialize bytes
    User deserialized = serde.deserialize(bytes);
    assertEquals(input, deserialized);
  }
}

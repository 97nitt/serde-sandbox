package sandbox.serde.json;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import sandbox.serde.Serde;
import sandbox.serde.User;

public class JsonSerdeTests {

  private final Serde<User> serde = new JsonSerde<>(User.class);

  @Test
  public void serde() {
    // given
    User input = new User(1, "john.doe@gmail.com", "John", "Doe");

    // serialize to bytes
    byte[] bytes = serde.serialize(input);
    String json = new String(bytes);
    assertEquals(
        "{\"id\":1,\"email\":\"john.doe@gmail.com\",\"firstName\":\"John\",\"lastName\":\"Doe\"}",
        json);

    // deserialize bytes
    User deserialized = serde.deserialize(bytes);
    assertEquals(input, deserialized);
  }
}

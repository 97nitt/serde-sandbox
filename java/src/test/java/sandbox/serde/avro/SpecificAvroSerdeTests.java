package sandbox.serde.avro;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SpecificAvroSerdeTests {

	private final SpecificAvroSerde<User> serde = new SpecificAvroSerde<>(User.class);

	@Test
	public void serde() {
		// given
		User user = new User();
		user.setName("Charlie");
		user.setFavoriteColor("brown");
		user.setFavoriteNumber(7);

		// serialize to bytes
		byte[] bytes = serde.serialize(user);

		// deserialize bytes
		User deserialized = serde.deserialize(bytes);
		assertEquals(user, deserialized);
	}
}

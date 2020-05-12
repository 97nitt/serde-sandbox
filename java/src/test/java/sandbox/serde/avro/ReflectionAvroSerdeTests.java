package sandbox.serde.avro;

import org.junit.Test;
import sandbox.serde.Serde;
import sandbox.serde.TestData;

import static org.junit.Assert.assertEquals;

public class ReflectionAvroSerdeTests {

	private final Serde<TestData> serde = new ReflectionAvroSerde<>(TestData.class);

	@Test
	public void serde() {
		// given
		TestData data = new TestData("one", "two");

		// serialize to bytes
		byte[] bytes = serde.serialize(data);

		// deserialize bytes
		TestData deserialized = serde.deserialize(bytes);
		assertEquals(data, deserialized);
	}
}

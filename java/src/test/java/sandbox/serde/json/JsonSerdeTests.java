package sandbox.serde.json;

import org.junit.Test;
import sandbox.serde.Serde;
import sandbox.serde.TestData;

import static org.junit.Assert.assertEquals;

public class JsonSerdeTests {

	private final Serde<TestData> serde = new JsonSerde<>(TestData.class);

	@Test
	public void serde() {
		// given
		TestData data = new TestData("one", "two");

		// serialize to bytes
		byte[] bytes = serde.serialize(data);
		String json = new String(bytes);
		assertEquals("{\"foo\":\"one\",\"bar\":\"two\"}", json);

		// deserialize bytes
		TestData deserialized = serde.deserialize(bytes);
		assertEquals(data, deserialized);
	}
}

package sandbox.serde.avro;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.util.Utf8;
import org.junit.BeforeClass;
import org.junit.Test;
import sandbox.serde.Serde;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class GenericAvroSerdeTests {

	private static Schema schema;
	private static Serde<GenericRecord> serde;

	@BeforeClass
	public static void setUp() throws IOException {
		InputStream is = GenericAvroSerdeTests.class.getResourceAsStream("/User.avsc");
		schema = new Schema.Parser().parse(is);
		serde = new GenericAvroSerde(schema);
	}

	@Test
	public void serde() {
		// given
		GenericRecord user = new GenericData.Record(schema);
		user.put("name", "Charlie");
		user.put("favoriteColor", "brown");
		user.put("favoriteNumber", 7);

		// serialize to bytes
		byte[] bytes = serde.serialize(user);

		// deserialize bytes
		GenericRecord deserialized = serde.deserialize(bytes);
		assertEquals(new Utf8("Charlie"), deserialized.get("name"));
		assertEquals(new Utf8("brown"), deserialized.get("favoriteColor"));
		assertEquals(7, deserialized.get("favoriteNumber"));
	}
}

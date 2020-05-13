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
		GenericRecord input = new GenericData.Record(schema);
		input.put("id", 1);
		input.put("email", "john.doe@gmail.com");
		input.put("firstName", "John");
		input.put("lastName", "Doe");

		// serialize to bytes
		byte[] bytes = serde.serialize(input);

		// deserialize bytes
		GenericRecord deserialized = serde.deserialize(bytes);
		assertEquals(1, deserialized.get("id"));
		assertEquals(new Utf8("john.doe@gmail.com"), deserialized.get("email"));
		assertEquals(new Utf8("John"), deserialized.get("firstName"));
		assertEquals(new Utf8("Doe"), deserialized.get("lastName"));
	}
}

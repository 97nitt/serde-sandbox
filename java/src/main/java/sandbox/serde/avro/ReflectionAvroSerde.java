package sandbox.serde.avro;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;
import sandbox.serde.Serde;
import sandbox.serde.SerdeException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Avro serialization using {@link org.apache.avro.reflect.ReflectData}
 * (derive schemas from POJOs using reflection).
 *
 */
public class ReflectionAvroSerde<T> implements Serde<T>  {

	private final Class<T> type;

	public ReflectionAvroSerde(Class<T> type) {
		this.type = type;
	}

	@Override
	public T deserialize(byte[] bytes) {
		try {
			DatumReader<T> reader = new ReflectDatumReader<>(type);
			Decoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
			return reader.read(null, decoder);

		} catch (IOException e) {
			throw new SerdeException("Failed to deserialize byte array to type: " + type.getName(), e);
		}
	}

	@Override
	public byte[] serialize(T t) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
			DatumWriter<T> writer = new ReflectDatumWriter<>(type);
			writer.write(t, encoder);
			encoder.flush();
			out.close();
			return out.toByteArray();

		} catch (IOException e) {
			throw new SerdeException("Failed to serialize: " + t, e);
		}
	}
}

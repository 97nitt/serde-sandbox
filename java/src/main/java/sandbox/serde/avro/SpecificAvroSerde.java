package sandbox.serde.avro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import sandbox.serde.Serde;
import sandbox.serde.SerdeException;

/**
 * Avro serialization using {@link org.apache.avro.specific.SpecificData} (Java classes generated
 * from Avro schema).
 */
public class SpecificAvroSerde<T> implements Serde<T> {

  private final Class<T> type;

  public SpecificAvroSerde(Class<T> type) {
    this.type = type;
  }

  @Override
  public T deserialize(byte[] bytes) {
    try {
      DatumReader<T> reader = new SpecificDatumReader<>(type);
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
      DatumWriter<T> writer = new SpecificDatumWriter<>(type);
      writer.write(t, encoder);
      encoder.flush();
      out.close();
      return out.toByteArray();

    } catch (IOException e) {
      throw new SerdeException("Failed to serialize: " + t, e);
    }
  }
}

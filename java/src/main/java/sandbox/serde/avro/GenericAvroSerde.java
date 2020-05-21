package sandbox.serde.avro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import sandbox.serde.Serde;
import sandbox.serde.SerdeException;

/** Avro serialization using {@link org.apache.avro.generic.GenericData}. */
public class GenericAvroSerde implements Serde<GenericRecord> {

  private final Schema writerSchema;
  private final Schema readerSchema;

  public GenericAvroSerde(Schema schema) {
    this(schema, schema);
  }

  public GenericAvroSerde(Schema writerSchema, Schema readerSchema) {
    this.writerSchema = writerSchema;
    this.readerSchema = readerSchema;
  }

  @Override
  public GenericRecord deserialize(byte[] bytes) {
    try {
      DatumReader<GenericRecord> reader = new GenericDatumReader<>(writerSchema, readerSchema);
      Decoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
      return reader.read(null, decoder);

    } catch (IOException e) {
      throw new SerdeException("Failed to deserialize byte array to a GenericRecord: ", e);
    }
  }

  @Override
  public byte[] serialize(GenericRecord record) {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
      DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(writerSchema);
      writer.write(record, encoder);
      encoder.flush();
      out.close();
      return out.toByteArray();

    } catch (IOException e) {
      throw new SerdeException("Failed to serialize: " + record, e);
    }
  }
}

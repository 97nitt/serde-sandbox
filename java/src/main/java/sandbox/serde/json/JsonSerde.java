package sandbox.serde.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import sandbox.serde.Serde;
import sandbox.serde.SerdeException;

import java.io.IOException;

/**
 * JSON serialization using <a href="https://github.com/FasterXML/jackson-databind">Jackson</a>.
 *
 */
public class JsonSerde<T> implements Serde<T> {

	private final Class<T> type;
	private final ObjectMapper objectMapper;

	public JsonSerde(Class<T> type) {
		this(type, new ObjectMapper());
	}

	public JsonSerde(Class<T> type, ObjectMapper objectMapper) {
		this.type = type;
		this.objectMapper = objectMapper;
	}

	@Override
	public T deserialize(byte[] bytes) {
		try {
			return objectMapper.readValue(bytes, type);
		} catch (IOException e) {
			throw new SerdeException("Failed to deserialize byte array to type: " + type.getName(), e);
		}
	}

	@Override
	public byte[] serialize(T t) {
		try {
			return objectMapper.writeValueAsBytes(t);
		} catch (JsonProcessingException e) {
			throw new SerdeException("Failed to serialize: " + t, e);
		}
	}
}

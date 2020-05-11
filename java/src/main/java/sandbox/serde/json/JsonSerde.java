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
public class JsonSerde implements Serde {

	private final ObjectMapper objectMapper;

	public JsonSerde() {
		this(new ObjectMapper());
	}

	public JsonSerde(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> type) {
		try {
			return objectMapper.readValue(bytes, type);
		} catch (IOException e) {
			throw new SerdeException("Unable to deserialize byte array to type: " + type.getName(), e);
		}
	}

	@Override
	public <T> byte[] serialize(T t) {
		try {
			return objectMapper.writeValueAsBytes(t);
		} catch (JsonProcessingException e) {
			throw new SerdeException("Unable to serialize: " + t, e);
		}
	}
}

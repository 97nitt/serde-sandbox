package sandbox.serde.proto;

import com.google.protobuf.Message;
import sandbox.serde.Serde;
import sandbox.serde.SerdeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Serialization via <a href="https://developers.google.com/protocol-buffers">Protocol buffers</a>.
 *
 */
public class ProtoSerde<T extends Message> implements Serde<T> {

	private final Method parseFromBytes;

	public ProtoSerde(Class<T> type) {
		try {
			parseFromBytes = type.getMethod("parseFrom", byte[].class);
		} catch (NoSuchMethodException e) {
			throw new SerdeException("Proto message type does not have a parseFrom(bytes) method: " +
					type.getCanonicalName());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public T deserialize(byte[] bytes) {
		try {
			return (T) parseFromBytes.invoke(null, new Object[]{ bytes });
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new SerdeException("Unable to deserialize message", e);
		}
	}

	@Override
	public byte[] serialize(T t) {
		return t.toByteArray();
	}
}

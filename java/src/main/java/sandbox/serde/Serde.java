package sandbox.serde;

public interface Serde {

	/**
	 * Deserialize a byte array to a T.
	 *
	 * @param bytes byte array
	 * @param type deserialized type
	 * @return a T
	 */
	<T> T deserialize(byte[] bytes, Class<T> type);

	/**
	 * Serialize a T to a byte array.
	 *
	 * @param t data to serialize
	 * @return byte array
	 */
	<T> byte[] serialize(T t);
}

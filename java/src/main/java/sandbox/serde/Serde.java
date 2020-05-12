package sandbox.serde;

public interface Serde<T> {

	/**
	 * Deserialize a byte array to a T.
	 *
	 * @param bytes byte array
	 * @return a T
	 */
	T deserialize(byte[] bytes);

	/**
	 * Serialize a T to a byte array.
	 *
	 * @param t data to serialize
	 * @return byte array
	 */
	byte[] serialize(T t);
}

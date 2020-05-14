package sandbox.serde;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.avro.reflect.Nullable;

/**
 * Simple POJO modeling a user.
 *
 * {@link org.apache.avro.reflect} annotations are used to influence Avro schema generation
 * via reflection.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private int id;

	private String email;

	@Nullable
	private String firstName;

	@Nullable
	private String lastName;
}

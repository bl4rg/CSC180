package labs.five;

/**
 * An exception that represents the situation when a user tries to update
 * an item with a different id.
 * 
 * @author jcummings
 *
 */
public class IdMismatchException extends RuntimeException {
	public IdMismatchException(String message) {
		super(message);
	}
}

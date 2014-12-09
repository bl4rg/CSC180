package labs.six;
/**
 * An exception that represents the situation when a user looks up an item
 * that does not exist.
 * 
 * @author jzheaux
 *
 */
public class ObjectNotFoundException extends RuntimeException {
	public ObjectNotFoundException(String message) {
		super(message);
	}
}

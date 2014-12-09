package labs.six;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class CollectionUtils {
	public static <T> Set<T> filter(Collection<T> s, Predicate<T> p) {
		Set<T> filtered = new HashSet<T>();
		for ( T t : s ) {
			if ( p.evaluate(t) ) {
				filtered.add(t);
			}
		}
		return filtered;
	}
}

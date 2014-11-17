package labs.two;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CollectionUtils {
	public static <T> Set<T> filter(Collection<T> coll, Predicate<T> predicate) {
		Set<T> results = new HashSet<T>();
		for(T item : coll) {
			if(predicate.evaluate(item)) {
				results.add(item);
			}
		}
		return results;
	}
}

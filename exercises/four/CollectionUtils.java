import java.util.ArrayList;
import java.util.Collection;

public class CollectionUtils<T> {
	public static int cardinality(Object obj, Collection<?> coll) {
		int count = 0;
		for(Object match : coll) {
			if(match.equals(obj)) {
				count++;
			}
		}
		return count;
	}
	
	public Collection<T> filter(Collection<T> coll, Predicate<T> predicate) {
		Collection<T> results = new ArrayList<T>();
		for(T item : coll) {
			if(predicate.evaluate(item)){
				results.add(item);
			}
		}
		return results;
	}
}

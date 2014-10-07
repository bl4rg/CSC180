import java.util.Collection;

public class CollectionUtils {
	public static int cardinality(Object obj, Collection coll) {
		int count = 0;
		for(Object match : coll) {
			if(match.equals(obj)) {
				count++;
			}
		}
		return count;
	}
}

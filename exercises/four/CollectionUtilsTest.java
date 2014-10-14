import static org.junit.Assert.*;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Test;


public class CollectionUtilsTest {
	CollectionUtils<Auction> utils = new CollectionUtils<Auction>();
	Collection<Auction> testItems = new LinkedList<Auction>();
	
	
	@Test
	public void test() {
		testItems.add(new Auction(1, "item1", 0));
		testItems.add(new Auction(2, "item2", 0));
		testItems.add(new Auction(3, "item3", 0));
		testItems.add(new Auction(4, "item4", 0));
		for(Auction item : utils.filter(testItems, new ContainsPredicate(new Auction(1, "item1", 0)))) {
			System.out.println(item.toString());
		}
	}

}

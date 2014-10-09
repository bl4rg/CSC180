package exercisesThree;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class AuctionTest {

	@Test
	public void test() {
		Auction item1 = new Auction(1, "cat", 0);
		System.out.println(item1.getId() + "\n" +
				item1.getName() + "\n" +
				item1.getCurrentBid() + "\n");
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("Animal", "Dog");
		Auction item2 = new Auction(1, "dog", 0, properties);
		System.out.println(item2.getId() +
				"\n" + item2.getName() + "\n" +
				item2.getCurrentBid() + "\n" +
				item2.getProperties() + "\n" +
				item2.getProperty("Animal") + "\n" +
				item2.getProperty("Animal", String.class));
		
	}

}

package exercisesThree;

import java.util.ArrayList;
import org.junit.Test;

public class AuctionServiceTest implements AuctionService{
	private ArrayList<Auction> items = new ArrayList<Auction>();
	
	public AuctionServiceTest() {
		items.add(new Auction(1, "bowl", 0));
		items.add(new Auction(2, "plate", 0));
		items.add(new Auction(3, "spoon", 0));
		items.add(new Auction(4, "plate", 0));
	}

	@Test
	public void test() {
		new AuctionServiceTest();
		
		System.out.println(items.get(1).toString());
		bid("Sean", 1);
		System.out.println(items.get(1).toString());
		
		create(new Auction(5, "fork", 0));
		System.out.println(items.get(4).toString());
		
		System.out.println(items.get(1).toString());
		delete(1);
		System.out.println(items.get(1).toString());
		
		System.out.println(retreive(0).toString());
		
		System.out.println("Results for bowl");
		for(Auction item : search("bowl")) {
			System.out.println(item.toString());
		}
		System.out.println("Results for cat");
		for(Auction item : search("cat")) {
			System.out.println(item.toString());
		}
		System.out.println("Results for fork");
		for(Auction item : search("fork")) {
			System.out.println(item.toString());
		}
		
		System.out.println(items.get(0).toString());
		System.out.println(update(new Auction(1, "Shark Bowl", 0), 0).toString());
	}

	@Override
	public Auction bid(String username, Integer number) {
		items.get(number).setOwner(username);
		items.get(number).setCurrentBid(items.get(number).getCurrentBid() + 1);
		return null;
	}

	@Override
	public Auction create(Auction auction) {
		items.add(auction);
		return auction;
	}

	@Override
	public void delete(Integer id) {
		items.remove(id);
	}

	@Override
	public Auction retreive(Integer id) {
		return items.get(id);
	}

	@Override
	public Auction[] search(String criteria) {
		ArrayList<Auction> results = new ArrayList<Auction>();
		for(Auction item : items) {
			if(item.getName().equals(criteria)){
				results.add(item);
			}
		}
		return (Auction[]) results.toArray(new Auction[results.size()]);
	}

	@Override
	public Auction update(Auction auction, Integer id) {
		return items.set(id, auction);
	}

}

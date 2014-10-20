package labs.two;
import java.util.ArrayList;

public class InMemoryAuctionService implements AuctionService{
private Auction[] items;
	
	public InMemoryAuctionService() {
		items = new Auction[4];
		items[0] = new Auction(1, "bowl", "plane", 0);
		items[1] = new Auction(2, "plate", "fancy",  0);
		items[2] = new Auction(3, "spoon", "dull", 0);
		items[3] = new Auction(4, "fork", "shiny", 0);
	}

	@Override
	public Auction[] search(String criteria) {
		ArrayList<Auction> results = new ArrayList<Auction>();
		for(int index = 0; index < items.length; index++) {
			if(items[index].getName().matches(criteria)) {
				results.add(items[index]);
			}
		}
		return results.toArray(new Auction[results.size()]);
	}

	@Override
	public void bid(String username, Integer itemId) {
		items[itemId].setOwner(username);
		items[itemId].addToCurrentBid(1);
	}

	public Auction[] getItems() {
		return this.items;
	}
	
	public Auction getItem(int index) {
		return this.items[index];
	}

	@Override
	public Auction create(Auction auction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Auction retreive(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Auction update(Auction auction, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}

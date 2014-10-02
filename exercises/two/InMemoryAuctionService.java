import java.util.ArrayList;

public class InMemoryAuctionService implements AuctionService{
	private Auction[] items;
	
	public InMemoryAuctionService() {
		items = new Auction[4];
		items[0] = new Auction(1, "bowl", 0);
		items[1] = new Auction(2, "plate", 0);
		items[2] = new Auction(3, "spoon", 0);
		items[3] = new Auction(4, "plate", 0);
	}

	@Override
	public Auction[] search(String criteria) {
		int size = 1;
		Auction[] results = new Auction[size];
		for(int index = 0; index < items.length; index++) {
			if(items[index].getName() == criteria) {
				results[index] = items[index];
				size++;
			}
		}
		return results;
	}

	@Override
	public void bid(String username, int itemId) {
		items[itemId].setOwner(username);
		items[itemId].addToCurrentBid(1);
	}

	public Auction[] getItems() {
		return this.items;
	}
	
	public Auction getItem(int index) {
		return this.items[index];
	}
}

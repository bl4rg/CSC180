
public class UserHomeState {
	private InMemoryAuctionService service = new InMemoryAuctionService();
	public UserHomeState(String item, Auction[] results) {	
		results = service.search(item);
	}
}

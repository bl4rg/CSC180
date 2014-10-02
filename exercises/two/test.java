
public class test {
	public static void main(String[] args) {
			InMemoryAuctionService auction = new InMemoryAuctionService();
			for(Auction item : auction.search("bowl")) {
				System.out.println("Results: \n" + item);
			}
			for(Auction item : auction.search("cat")) {
				System.out.println("Results: \n" + item + "\n");
			}
			auction.bid("Sean", 1);
			auction.bid("bid 2", 1);
			
			for(Auction items : auction.getItems()) {
				System.out.println(items);
			}
		}
}

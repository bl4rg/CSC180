
public class test {
	public static void main(String[] args) {
			InMemoryAuctionService auction = new InMemoryAuctionService();
			System.out.println(auction.search("bowl").toString());
			System.out.println(auction.search("cat").toString());
			auction.bid("Sean", 1);
			auction.bid("bid 2", 1);
			
			for(int index = 0; index < auction.getItems().length; index++) {
				System.out.println(auction.getItem(index));
			}
		}
}

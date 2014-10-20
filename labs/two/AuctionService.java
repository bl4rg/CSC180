package labs.two;

public interface AuctionService {
	public void bid(String username, Integer number);
	public Auction create(Auction auction);
	public void delete(Integer id);
	public Auction retreive(Integer id);
	public Auction[] search(String criteria);
	public Auction update(Auction auction, Integer id);
}

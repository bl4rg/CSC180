package labs.four;

public interface AuctionService {
	public void bid(String username, Long number);
	public Auction create(Auction auction);
	public void delete(Long id, String username);
	public Auction retreive(Long id);
	public Auction[] search(String criteria);
	public Auction update(Auction auction, Long id);
}

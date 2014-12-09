package labs.six;

public interface AuctionService {
	Auction[] search(String criteria);
	Auction bid(String username, Long number);
	Auction create(Auction a);
	Auction retreive(Long id);
	Auction update(Long id, Auction a);
	void delete(Long id);
}

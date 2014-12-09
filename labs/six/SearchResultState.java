package labs.six;

import java.util.Scanner;

public class SearchResultState implements State {

	private String username;
	private String search;
	private Auction[] results;
	private Scanner scanner;
	private AuctionService as;
	private TableRowAuctionConverter trac = new TableRowAuctionConverter();
	
	public SearchResultState(String username, String search, Auction[] results, Scanner scanner, AuctionService as) {
		this.username = username;
		this.search = search;
		this.results = results;
		this.scanner = scanner;
		this.as = as;
	}
	
	@Override
	public void show() {
		System.out.println("==================================================================================================================================================");
		System.out.println("======================================================           Search Results              =====================================================");
		System.out.println("==================================================================================================================================================");
		System.out.println(trac.formatHeader());
		for ( Auction result : results ) {
			System.out.println(trac.format(result));
		}
		System.out.println("==================================================================================================================================================");
		System.out.println(username + ", type an item number to increase the bid by $1.  Otherwise, perform a new search by typing in your search.");
	}

	@Override
	public State next() {
		String next = scanner.nextLine();
		Scanner parser = new Scanner(next);
		if ( InputUtils.isEmpty(next) ) {
			return new UserHomeState(username, scanner, as);
		} else if ( parser.hasNextLong() ) {
			long number = parser.nextLong(); 
			as.bid(username, number);
			return new SearchResultState(username, search, as.search(search), scanner, as);
		} else {
			Auction[] results = as.search(next);
			return new SearchResultState(username, search, results, scanner, as);
		}

	} 
}

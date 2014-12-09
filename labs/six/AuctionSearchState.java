package labs.six;

import java.util.Scanner;


public class AuctionSearchState implements State {
	private String username;
	private Scanner scanner;
	private AuctionService as;
	
	public AuctionSearchState(String username, Scanner scanner,
			AuctionService as) {
		this.username = username;
		this.scanner = scanner;
		this.as = as;
	}

	@Override
	public void show() {
		System.out.println("========================================");
		System.out.println("=======     Auction Search     =========");
		System.out.println("========================================");
		System.out.println("\nWhat would you like to search for (blank to go back to home page)?");
	}

	@Override
	public State next() {
		String input = scanner.nextLine();
		
		if ( InputUtils.isEmpty(input) ) {
			return new UserHomeState(username, scanner, as);
		} else {
			Auction[] results = as.search(input);
			return new SearchResultState(username, input, results, scanner, as);
		}
	}

}

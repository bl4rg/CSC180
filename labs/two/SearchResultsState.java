package labs.two;
import java.util.Scanner;

public class SearchResultsState implements Event{
	private int numb = 0;
	private String input;
	private String username;
	private Scanner scanner;
	private Auction[] results = new Auction[4];
	private AuctionService as;
	
	public SearchResultsState(String username, Auction[] results, Scanner scanner, AuctionService as) {
		this.username = username;
		this.results = results;
		this.scanner = scanner;
		this.as = as;
	}

	@Override
	public void show() {
		System.out.println("\n" + username + " here are your results");
		System.out.println("==============================");
		System.out.println("===     Search Results     ===");
		for(Auction item : results) {
			System.out.println("   Id:\t" + item.getId() + 
					"\n   Name:\t" + item.getName() +
					"\n   Owner:\t" + item.getOwner() +
					"\n   Current Bid: $" + item.getCurrentBid());
		}
		System.out.println("==============================\n");		
		System.out.println("Enter the item id to increase the bid by $1. Otherwise, enter another\nsearch: (Hit enter to go back to home page)");
		input = scanner.nextLine();
	}

	@Override
	public Event next() {
		Event temp = null;
		if(input == null || input.length() == 0) {
			temp = new UserHomeState(username, scanner, as);
		}else {
			if(input.matches("\\d+")) {
				numb = Integer.parseInt(input);
				as.bid(username, numb);
				show();
			}else if(input.matches("[A-ZA-Z]+")) {
				temp = new SearchResultsState(username, results, scanner, as);
			}
		}
		return temp;
	}	
}

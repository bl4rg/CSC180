package labs.three;
import java.util.Scanner;

public class SearchResultsState implements Event{
	private String username;
	private Scanner scanner;
	private Auction[] results;
	private AuctionService as;
	
	public SearchResultsState(String username, Auction[] results, Scanner scanner, AuctionService as) {
		this.username = username;
		this.results = results;
		this.scanner = scanner;
		this.as = as;
	}

	@Override
	public void show() {
		String print = "\n====================================" +
				"=======	Search Results    =======" +
				"====================================";
		System.out.println(print);
		for(Auction item : results) {
			System.out.println("   Id:\t" + item.getId() + 
					"\n   Name:\t" + item.getName() +
					"\n   Owner:\t" + item.getOwner() +
					"\n   Current Bid: $" + item.getCurrentBid());
		}
		System.out.println("====================================\n");
		System.out.println("Enter the item id to increase the bid by $1. Otherwise, enter another\nsearch: (Hit enter to go back to home page)");

	}

	@Override
	public Event next() {
		String input = scanner.nextLine();
		Scanner parse = new Scanner(input);

		Event temp = null;
		if(input == null || input.isEmpty()) {
			temp = new UserHomeState(username, scanner, as);
		}else if(parse.hasNextInt()) {
			Integer numb = parse.nextInt();
			as.bid(username, numb);
			temp = this;
		}else {
			Auction[] results = as.search(input);
			temp = new SearchResultsState(username, results, scanner, as);
		}
		return temp;
	}	
}

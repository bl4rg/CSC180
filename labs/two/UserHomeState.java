package labs.two;
import java.util.Scanner;

public class UserHomeState implements Event{
	private Scanner scanner;
	private String username;
	private Auction[] results = new Auction[4];
	private AuctionService as;

	public UserHomeState(String user, Scanner scanner, AuctionService as){
		this.username = user;
		this.scanner = scanner;
		this.as = as;		
	}
	
	@Override
	public void show() {
		System.out.println(username + ", what would you like to search for?");
		String input = scanner.nextLine();
		if(!input.isEmpty()){
			results = as.search(input);
		}
	}
	
	@Override
	public Event next() {
		return new SearchResultsState(username, results, scanner, as);
	}
}

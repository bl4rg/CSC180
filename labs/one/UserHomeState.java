import java.util.Scanner;

public class UserHomeState implements Event{
	private Scanner scan = new Scanner(System.in);
	public static InMemoryAuctionService imas = new InMemoryAuctionService();
	private String username;
	private Auction[] results;
	
	public UserHomeState(String user){
		username = user;
	}
	@Override
	public void show() {
		System.out.println(username + ", what would you like to search for?");
		String input = scan.nextLine();
		if(!input.isEmpty()){
			results = imas.search(input);
		}
	}
	@Override
	public Event next() {
		return new SearchResultsState(username, results);
	}
}
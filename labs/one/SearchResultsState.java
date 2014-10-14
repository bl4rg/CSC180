import java.util.Scanner;

public class SearchResultsState implements Event{
	private int numb = 0;
	private String id;
	private String input;
	private String username;
	private Auction[] results;
	private Scanner scan = new Scanner(System.in);
	
	public SearchResultsState(String username, Auction[] results) {
		this.username = username;
		this.results = results;
	}

	@Override
	public void show() {
		boolean complete = false;
		for(Auction item : results) {
			System.out.println("Search results: " + item);
		}
		
		System.out.println("Enter the item id to increase the bid by $1. Otherwise, enter another\nsearch: (Hit enter to go back to home page)");
		input = scan.nextLine();
	}

	@Override
	public Event next() {
		Event temp = null;
		if(input.isEmpty()) {
			temp = new SearchResultsState(username, results);
		}else {
			if(input.matches("[a-zA-Z]+")) {
				temp = new UserHomeState(username);
			}
			if(input.matches("\\d+")) {
				UserHomeState.imas.bid(username, numb);
			}
		}
		return temp;
	}	
}

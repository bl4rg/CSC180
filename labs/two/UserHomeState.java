package labs.two;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserHomeState implements Event{
	private Scanner scanner;
	private String username;
	private AuctionService as;
	private Pattern Commands = Pattern.compile("([CcSs]?)(reate|earch)?");

	public UserHomeState(String user, Scanner scanner, AuctionService as){
		this.username = user;
		this.scanner = scanner;
		this.as = as;		
	}
	
	@Override
	public void show() {
		String print = "====================================" +
				"\n======      Auction Home      ======" +
				"\n====================================";

	}
	
	@Override
	public Event next() {
		String input;
		Event temp;
		String print  = username + ", what would you like to do?"
				+ "\nSearch for an item (type \"search\")"
				+ "\nAdd a new item to the auction (type \"new\")";
		System.out.println(print);
		input = scanner.nextLine();
		Matcher matcher = Commands.matcher(input);

		if(matcher.group(1) == null || matcher.group(1).isEmpty()) {
			temp = null;
		}else if("C".equalsIgnoreCase(matcher.group(1))) {
			temp = new AuctionCreateState(username, scanner, as);
		}else {
			temp = new AuctionSearchState(username, scanner, as);
		}
		return temp;
	}
}

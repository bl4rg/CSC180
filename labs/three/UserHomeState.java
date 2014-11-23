package labs.three;
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
        System.out.println(print);
	}
	
	@Override
	public Event next() {
		String input;
		Event temp;
		String print  = username + ", what would you like to do?"
				+ "\n\tSearch for an item (type \"search\")"
				+ "\n\tAdd a new item to the auction (type \"create\")";
		System.out.println(print);
		input = scanner.nextLine();
		Matcher match = Commands.matcher(input);
        match.find();

		if(match.group(1) == null || match.group(1).isEmpty()) {
			temp = null;
		}else if("C".equalsIgnoreCase(match.group(1))) {
			temp = new AuctionCreateState(username, scanner, as);
		}else {
			temp = new AuctionSearchState(username, scanner, as);
		}
		return temp;
	}
}

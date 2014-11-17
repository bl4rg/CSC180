package labs.three;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuctionCreateState implements Event {
	private String username;
	private Scanner scanner;
	private AuctionService as;
	private Pattern Yes_No = Pattern.compile("([YyNn])(es|o)?");
	
	public AuctionCreateState(String username, Scanner scanner, AuctionService as) {
		this.username = username;
		this.scanner = scanner;
		this.as = as;
	}

	public void show() {
		String print = "==================================" +
				"=======	Auction Create	=======" +
				"==================================";
		System.out.println(print);
	}

	public Event next() {
		Event temp = null;
		System.out.println("Please enter the name of the new item:");
		String name = scanner.nextLine();
		System.out.println("Please enter a description of the item. (Hit enter to leave blank");
		String description = scanner.nextLine();
		System.out.println("Please enter a starting price. (hit enter to leave it at $1)");
		int startingBid = scanner.nextInt();

		Auction auction = new Auction(0, startingBid, name, description);
		auction = as.create(auction);
		System.out.println(String.format("Item #%d (%s) created, would you like to create another. (Hit enter to decline)", auction.getId(), auction.getName()));
		Matcher matcher = Yes_No.matcher(scanner.nextLine());

		if ("Y".equalsIgnoreCase(matcher.group(1))) {
			temp = this;
		}else {
			temp = new UserHomeState(username, scanner, as);
		}
		return temp;
	}

}

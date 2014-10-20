package labs.two;
import java.util.Scanner;

public class AuctionCreateState implements Event {
	private String username;
	private Scanner scanner;
	private AuctionService as;
	
	public AuctionCreateState(String username, Scanner scanner, AuctionService as) {
		this.username = username;
		this.scanner = scanner;
		this.as = as;
	}

	public void show() {
		System.out.println("Please enter the name of the new item:");
		scanner.nextLine();
		System.out.println("Please enter a description of the item.");
		scanner.nextLine();
		System.out.println("Please enter a starting price. (hit enter to leave it at $1)");
	}

	public Event next() {
		return null;
	}

}

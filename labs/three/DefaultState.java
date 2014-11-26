package labs.three;
import java.util.Scanner;

public class DefaultState implements Event{
	private Scanner scanner;
	private AuctionService as;

	public DefaultState(Scanner scanner, AuctionService as) {
		this.scanner = scanner;
		this.as = as;
	}
	
	@Override
	public void show() {
		System.out.println("New user, would you like to log in? (Hit enter to decline)");
	}

	@Override
	public Event next() {
		Event temp;
		String username = scanner.nextLine();
		if(username == null || username.isEmpty()) {
			temp = null;
		}else {
			temp = new UserHomeState(username, scanner, as);
		}
		return temp;
	}
}

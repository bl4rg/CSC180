package labs.six;
import java.util.Scanner;


public class DefaultState implements State {
	private Scanner scanner;
	private AuctionService as;
	private GUI gui;
	
	public DefaultState(Scanner scanner, AuctionService as, GUI gui) {
		this.scanner = scanner;
		this.as = as;
		this.gui = gui;
	}

	@Override
	public void show() {
		System.out.println("New user, would you like to log in? (hit enter to decline)");
	}

	@Override
	public State next() {
		String username = scanner.nextLine();
		if ( username == null || username.length() == 0 ) {
			return null;
		} else {
			return new UserHomeState(username, scanner, as);
		}
	}
}

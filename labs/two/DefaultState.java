package labs.two;
import java.util.Scanner;

public class DefaultState implements Event{
	boolean quit = false;
	private String userName = "";
	private Scanner scanner;
	private AuctionService as;
	public DefaultState(Scanner scanner, AuctionService as) {
		this.scanner = scanner;
		this.as = as;
	}
	
	@Override
	public void show() {
		System.out.println("New user, would you like to log in? (Hit enter to decline)");
		userName = scanner.nextLine();
		if(userName.isEmpty()) {
			quit = true;
		}
	}

	@Override
	public Event next() {
		Event temp = null;
		if(!quit){
			temp = new UserHomeState(userName, scanner, as);
		}
		return temp;
	}
}

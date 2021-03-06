package labs.six;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserHomeState implements State {
	private static final Pattern COMMAND_PATTERN = Pattern.compile("([CcSs]?)(reate|earch)?");
	
	private String username;
	private Scanner scanner;
	private AuctionService as;
	
	public UserHomeState(String username, Scanner scanner, AuctionService as) {
		this.username = username;
		this.scanner = scanner;
		this.as = as;
	}

	@Override
	public void show() {
		System.out.println("========================================");
		System.out.println("=======       Auction Home       =======");
		System.out.println("========================================");
	}

	@Override
	public State next() {
		Matcher m = InputUtils.promptForPattern(username + ", Would you like to [C]reate or [S]earch? (hit enter to logout)", scanner, COMMAND_PATTERN, "");
		
		// perform search
		String search = m.group(1);
		if ( InputUtils.isEmpty(search) ) {
			return null;
		} else if ( "C".equalsIgnoreCase(search) ) {
			return new AuctionCreateState(username, scanner, as);
		} else {
			return new AuctionSearchState(username, scanner, as);
		}
	}
	
	public static void main(String[] args) throws IOException {
		UserHomeState u = new UserHomeState("bob", new Scanner(System.in), new DatasourceAuctionService());
		u.next();
	}

}

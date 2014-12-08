package labs.five;

import java.util.Scanner;


public class AuctionSearchState implements State {
	private String username;
	private Scanner scanner;
	private AuctionService as;
	private Client client;
	private Server server;
	
	public AuctionSearchState(String username, Scanner scanner, AuctionService as, Client client, Server server) {
		this.username = username;
		this.scanner = scanner;
		this.as = as;
		this.client = client;
		this.server = server;
	}

	@Override
	public void show() {
		System.out.println("========================================");
		System.out.println("=======     Auction Search     =========");
		System.out.println("========================================");
		System.out.println("\nWhat would you like to search for (blank to go back to home page)?");
	}

	@Override
	public State next() {
		String input = scanner.nextLine();
		
		if ( InputUtils.isEmpty(input) ) {
			client.sendRequest(new Request("userhome", username, ""));
		} else {
			client.sendRequest(new Request("search", "" + input,""));
		}

		return server.extractRequest(server.readRequest().toString().split(","));
	}
}

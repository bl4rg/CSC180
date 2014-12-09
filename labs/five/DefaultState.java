package labs.five;

import java.util.Scanner;


public class DefaultState implements State {
	private Scanner scanner;
	private AuctionService as;
	private Client client;
	private Server server;
	
	public DefaultState(Scanner scanner, AuctionService as, Server server, Client passedClient) {
		this.scanner = scanner;
		this.as = as;
		this.server = server;
		this.client = passedClient;
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
		}else {
			client.sendRequest(new Request("userhome",username, ""));
		}

		return server.extractRequest(server.readRequest().toString().split(","));
	}
}

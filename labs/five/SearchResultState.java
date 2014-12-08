package labs.five;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchResultState implements State {

	private String username;
	private String search;
	private Auction[] results;
	private Scanner scanner;
	private AuctionService as;
	private TableRowAuctionConverter trac = new TableRowAuctionConverter();
	private Client client;
	private Server server;
	
	public SearchResultState(String username, String search, Auction[] results, Scanner scanner, AuctionService as, Client client, Server server) {
		this.username = username;
		this.search = search;
		this.results = results;
		this.scanner = scanner;
		this.as = as;
		this.client = client;
		this.server = server;
	}
	
	@Override
	public void show() {
		System.out.println("==================================================================================================================================================");
		System.out.println("======================================================           Search Results              =====================================================");
		System.out.println("==================================================================================================================================================");
		System.out.println(trac.formatHeader());
		for ( Auction result : results ) {
			System.out.println(trac.format(result));
		}
		System.out.println("==================================================================================================================================================");
		System.out.println(username + ", type an item number to increase the bid by $1.  Otherwise, perform a new search/edit###/delete### by typing search/edit###/delete###.");
	}

	@Override
	public State next() {
		Pattern requests = Pattern.compile("([SsEeDd])(earch|dit|elete)?\\.*\\s*([1-9]+)?");
		String next = scanner.nextLine();
		Matcher match = requests.matcher(next);
		match.find();

		Scanner parser = new Scanner(next);
		if ( InputUtils.isEmpty(next) ) {
			client.sendRequest(new Request("userhome", username,""));
		} else if ( parser.hasNextLong() ) {
			client.sendRequest(new Request("bid","" + parser.nextLong() + "," + username + "," + search,""));
		} else if(next.toLowerCase().contains("s")) {
			client.sendRequest(new Request("search","" + next,""));
		}else if(next.toLowerCase().contains("e")){
			client.sendRequest(new Request("edit",match.group(3),""));
		}else if(next.toLowerCase().contains("d")) {
			client.sendRequest(new Request("delete", match.group(3),""));
		}

		return server.extractRequest(server.readRequest().toString().split(","));
	} 
}

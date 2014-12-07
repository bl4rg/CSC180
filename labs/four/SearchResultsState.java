package labs.four;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchResultsState implements Event{
	private String username;
	private Scanner scanner;
	private Auction[] results;
	private AuctionService as;
	TableRowAuctionConverter tableRow = new TableRowAuctionConverter();
	private Pattern searchEdit = Pattern.compile("([SsEeDd])(earch|dit|elete)?[.\\s]*(\\d+)?");
	
	public SearchResultsState(String username, Auction[] results, Scanner scanner, AuctionService as) {
		this.username = username;
		this.results = results;
		this.scanner = scanner;
		this.as = as;
	}

	@Override
	public void show() {
		String print = "====================================" +
				"\n=======	Search Results    =======" +
				"\n====================================" +
                "\n" + username + " here are your results\n";
		System.out.println(print);
		System.out.println(tableRow.header());
		for(Auction item : results) {
			System.out.println(tableRow.format(item));
		}
		System.out.println("\n====================================\n");
		System.out.println("Enter the item id to increase the bid by $1. Otherwise, type \"search\" for another search or \nedit/delete with the item id, to edit/delete an item: (Hit enter to go back to home page)");

	}

	@Override
	public Event next() {
		String input = scanner.nextLine();
		Scanner parse = new Scanner(input);
		Matcher match = searchEdit.matcher(input);
		match.find();

		Event temp = null;
		if(input == null || input.isEmpty()) {
			temp = new UserHomeState(username, scanner, as);
		}else if (parse.hasNextInt()) {
			Long numb = new Long(parse.nextInt());
			as.bid(username, numb);
			temp = this;
		} else if (match.group(1).toLowerCase().contains("s")) {
			String search = scanner.nextLine();
			Auction[] results = as.search(search);
			temp = new SearchResultsState(username, results, scanner, as);
		} else if (match.group(1).toLowerCase().contains("e")) {
			temp = new EditAuctionState(username, scanner, as, as.retreive(Long.parseLong(match.group(3))));
		}else if(match.group(1).toLowerCase().contains("d")) {
			as.delete(Long.parseLong(match.group(3)), username);
			temp =  new UserHomeState(username, scanner, as);
		}
		return temp;
	}
}

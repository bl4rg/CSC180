package labs.five;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AuctionCreateState implements State {
	private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{2})[\\.\\-/]?(\\d{2})[\\.\\-/]?(\\d{2,4})");
	private static final Pattern YES_NO_PATTERN = Pattern.compile("([YyNn])(es|o)?");
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	private String username;
	private Scanner scanner;
	private AuctionService auctionService;
	private Client client;
	private Server server;
	
	public AuctionCreateState(String username, Scanner scanner, AuctionService auctionService, Client client, Server server) {
		this.username = username;
		this.scanner = scanner;
		this.auctionService = auctionService;
		this.client = client;
		this.server = server;
	}
	
	@Override
	public void show() {
		System.out.println("========================================");
		System.out.println("=======     Auction Creation     =======");
		System.out.println("========================================");
	}
	
	@Override
	public State next() {
		Date date = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
		
		System.out.print("Name:                   ");
		String name = scanner.nextLine();
		
		System.out.print("\nDescription [<blank>]:  ");
		String description = scanner.nextLine();

		Double startingBid = InputUtils.promptForDouble("Starting bid [1]:       ", scanner, 1d);
		
		String def = DATE_FORMAT.format(date);
		Matcher m = InputUtils.promptForPattern(String.format("End Date [%s]:    ", def), scanner, DATE_PATTERN, def);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(m.group(3).length() == 2 ? "20" + m.group(3) : m.group(3)));
		cal.set(Calendar.MONTH, Integer.parseInt(m.group(1)) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(m.group(2)));
		
		Auction a = new Auction(0L, name, description, startingBid, cal.getTime());

		client.sendRequest(new Request("create","", a.toString()));
		server.readRequest();
		client.readReply();
		
		m = InputUtils.promptForPattern(String.format("Item #%d (%s) created, would you like to create another [N]?", a.getId(), a.getName()), scanner, YES_NO_PATTERN, "N");
		if ( "Y".equalsIgnoreCase(m.group(1)) ) {
			client.sendRequest(new Request("create","",""));
		} else {
			client.sendRequest(new Request("userhome", username,""));
		}

		return server.extractRequest(server.readRequest().toString().split(","));
	}
}

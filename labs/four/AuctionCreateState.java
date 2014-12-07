package labs.four;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuctionCreateState implements Event {
	private String username;
	private Scanner scanner;
	private AuctionService as;
	private Pattern Yes_No = Pattern.compile("([YyNn])(es|o)?");
	private Pattern Date_Pattern = Pattern.compile("(\\d{2})[\\.\\-/]?(\\d{2})[\\.\\-/]?(\\d{2,4})?");
	private SimpleDateFormat Date_Format = new SimpleDateFormat("MM/dd/yyyy");
	
	public AuctionCreateState(String username, Scanner scanner, AuctionService as) {
		this.username = username;
		this.scanner = scanner;
		this.as = as;
	}

	public void show() {
		String print = "==================================" +
				"\n=======	Auction Create	=========" +
				"\n==================================";
		System.out.println(print);
	}

	public Event next() {
		Event temp = null;

		String name = promptForName(scanner);

		String description = promptForDescription(scanner);

		double startingBid = promptForStartingPrice(scanner);

		Matcher matcher = matchDate();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(matcher.group(3).length() == 2 ? "20" + matcher.group(3) : matcher.group(3)));
		cal.set(Calendar.MONTH, Integer.parseInt(matcher.group(1)) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(matcher.group(2)));

		Auction auction = new Auction(0L, startingBid, name, description, cal.getTime(), username);
		auction = as.create(auction);

        Matcher match = matchResponse(auction);

        if ("Y".equalsIgnoreCase(match.group(1))) {
            temp = new AuctionCreateState(username, scanner, as);
        }else {
            temp = new UserHomeState(username, scanner, as);
		}
		return temp;
	}

	public Matcher matchDate() {
		String inputDate = promptForDate(scanner);
		Matcher match = Date_Pattern.matcher(inputDate);
		match.matches();
		return match;
	}

	public Matcher matchResponse(Auction auction) {
		String input =  promptForNext(scanner, auction);
		Matcher match = Yes_No.matcher(input);
		match.matches();
		return match;
	}

	private String promptForName(Scanner scanner) {
		System.out.println(username + ", please enter the name of the new item:");
		String inputName = scanner.nextLine();
		String results = (inputName == null || inputName.isEmpty()) ? promptForName(scanner) : inputName;
		return results;
	}

	private String promptForDescription(Scanner scanner) {
		System.out.println("Please enter a description of the item. (Hit enter to leave blank)");
		return scanner.nextLine();
	}

	private double promptForStartingPrice(Scanner scanner) {
		double defPrice = 1;
		System.out.println("Please enter a starting price. (hit enter to leave it at $1)");
		double inputPrice = scanner.nextInt();
		double price = (inputPrice != 0 || inputPrice != 1) ? inputPrice : defPrice;
		return price;
	}

	private String promptForDate(Scanner scanner) {
		String buffer = "-" +  scanner.nextLine();
		Date date = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
		String defDate = Date_Format.format(date);
		System.out.println("Please enter a ending date for the item's bidding. (01/27/2014, 01/27, 01.27.2014, 01-27-2014)\n\tLeave empty for a default end date of a week from now.");
		String inputDate = scanner.nextLine();
		String results = (inputDate == null || inputDate.isEmpty()) ? defDate : inputDate;
		return results;
	}

	private String promptForNext(Scanner scanner, Auction auction) {
		String ifEmpty = "no";
		System.out.println(String.format("Item #%d [%s] created, would you like to create another. (Y/N, Hit enter to decline)", auction.getId(), auction.getName()));
		String input = scanner.nextLine();
		String results = (input == null || input.isEmpty()) ? ifEmpty : input;
		return results;
	}

}

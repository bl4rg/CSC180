package labs.three;
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
	private Pattern Date_Pattern = Pattern.compile("(\\d{2})[\\.\\-/]?(\\d{2})[\\.\\-/]?(\\d{2,4})");
	private SimpleDateFormat Date_Format = new SimpleDateFormat("MM/dd/yyyy");
	
	public AuctionCreateState(String username, Scanner scanner, AuctionService as) {
		this.username = username;
		this.scanner = scanner;
		this.as = as;
	}

	public void show() {
		String print = "==================================" +
				"=======	Auction Create	=======" +
				"==================================";
		System.out.println(print);
	}

	public Event next() {
		Date date = new Date(System.currentTimeMillis());
		Event temp = null;
		Matcher matcher;

		System.out.println("Please enter the name of the new item:");
		String name = scanner.nextLine();

		System.out.println("Please enter a description of the item. (Hit enter to leave blank");
		String description = scanner.nextLine();

		System.out.println("Please enter a starting price. (hit enter to leave it at $1)");
		int startingBid = scanner.nextInt();

		String defDate = Date_Format.format(date);
		String inputDate = scanner.nextLine();
		inputDate = (inputDate == null || inputDate.isEmpty()) ? defDate: inputDate;
		matcher = Date_Pattern.matcher(inputDate);
        matcher.find();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(matcher.group(3).length() == 2 ? "20" + matcher.group(3) : matcher.group(3)));
		cal.set(Calendar.MONTH, Integer.parseInt(matcher.group(1)) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(matcher.group(2)));

		Auction auction = new Auction(0, startingBid, name, description, date);
		auction = as.create(auction);

//		System.out.println(String.format("Item #%d (%s) created, would you like to create another. (Hit enter to decline)", auction.getId(), auction.getName()));
//        matcher = Yes_No.matcher(scanner.nextLine());
//
//        if ("Y".equalsIgnoreCase(matcher.group(1))) {
//            temp = this;
//        }else {
//            temp = new UserHomeState(username, scanner, as);
//		}
        temp = new UserHomeState(username, scanner, as);
		return temp;
	}

}

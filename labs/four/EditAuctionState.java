package labs.four;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sean on 12/4/2014.
 */
public class EditAuctionState implements Event{
    private String username;
    private Scanner scanner;
    private AuctionService as;
    Auction item = null;
    private Pattern Yes_No = Pattern.compile("([YyNn])(es|o)?");
    private Pattern Date_Pattern = Pattern.compile("(\\d{2})[\\.\\-/]?(\\d{2})[\\.\\-/]?(\\d{2,4})?");
    private SimpleDateFormat Date_Format = new SimpleDateFormat("MM/dd/yyyy");

    public EditAuctionState(String username, Scanner scanner, AuctionService as, Auction item) {
        this.username = username;
        this.scanner = scanner;
        this.as = as;
        this.item = item;
    }

    @Override
    public void show() {
        String print = "==================================" +
                "\n=======	Edit Auction	=========" +
                "\n==================================";
        System.out.println(print);
    }

    @Override
    public Event next() {
        Event temp = null;
        String name = item.getName();
        double startingBid = item.getCurrentBid();
        Matcher matcher = null;
        Calendar cal = null;
        Auction auction;

        String description = promptForDescription(scanner);


        if(item.getNumberOfBids() != 0) {
            name = promptForName(scanner);
            startingBid = promptForStartingPrice(scanner);
            matcher = matchDate();
            cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.parseInt(matcher.group(3).length() == 2 ? "20" + matcher.group(3) : matcher.group(3)));
            cal.set(Calendar.MONTH, Integer.parseInt(matcher.group(1)) - 1);
            cal.set(Calendar.DATE, Integer.parseInt(matcher.group(2)));
            auction = new Auction(item.getId(), startingBid, name, description, cal.getTime(), username);
        }else {
            auction = new Auction(item.getId(), startingBid, name, description, item.getEndDate(), username);
        }

        as.update(auction, auction.getId());

        System.out.printf("\nItem: %s, has been edited.\n", item.getName());
        return new UserHomeState(username, scanner, as);
    }

    public Matcher matchDate() {
        String inputDate = promptForDate(scanner);
        Matcher match = Date_Pattern.matcher(inputDate);
        match.matches();
        return match;
    }

    private String promptForName(Scanner scanner) {
        String oldName = item.getName();
        System.out.println(username + ", please enter the updated name: (hit enter to leave the same)");
        String inputName = scanner.nextLine();
        String results = (inputName == null || inputName.isEmpty()) ? oldName : inputName;
        return results;
    }

    private String promptForDescription(Scanner scanner) {
        System.out.println("Please enter tne updated description of the item. (Hit enter to leave blank)");
        return scanner.nextLine();
    }

    private double promptForStartingPrice(Scanner scanner) {
        double defPrice = item.getCurrentBid();
        System.out.println("Please enter the updated starting price. (hit enter to leave the same)");
        double inputPrice = scanner.nextInt();
        double price = (inputPrice != 0 || inputPrice != 1) ? inputPrice : defPrice;
        return price;
    }

    private String promptForDate(Scanner scanner) {
        String buffer = "-" +  scanner.nextLine();
        Date date = item.getEndDate();
        String defDate = Date_Format.format(date);
        System.out.println("Please enter the updated ending date for the item's bidding. (01/27/2014, 01/27, 01.27.2014, 01-27-2014)\n\tLeave empty for a default end date of a week from now.");
        String inputDate = scanner.nextLine();
        String results = (inputDate == null || inputDate.isEmpty()) ? defDate : inputDate;
        System.out.println(results);
        return results;
    }
}

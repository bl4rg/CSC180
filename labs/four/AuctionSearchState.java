package labs.four;

import java.util.Scanner;

/**
 * Created by sean on 11/16/2014.
 */
public class AuctionSearchState implements Event{
    private String username;
    private Scanner scanner;
    private AuctionService as;

    public AuctionSearchState(String username, Scanner scanner, AuctionService as) {
        this.username = username;
        this.scanner = scanner;
        this.as = as;
    }



    @Override
    public void show() {
        String print = "====================================" +
                "\n======     Auction Search     ======" +
                "\n====================================\n" +
                username + ", what would you like to search for? (Hit enter to go back to the homepage)";
        System.out.println(print);
    }

    @Override
    public Event next() {
        String input = scanner.nextLine();
        Event temp = null;
        if(input == null || input.isEmpty()) {
            temp = new UserHomeState(username, scanner, as);
        }else {
            Auction[] results = as.search(input);
            temp = new SearchResultsState(username, results, scanner, as);
        }
        return temp;
    }
}

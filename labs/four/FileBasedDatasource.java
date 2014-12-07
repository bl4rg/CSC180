package labs.four;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sean on 12/4/2014.
 */
public class FileBasedDatasource implements AuctionService{
    private Map<Long, Auction> items = new HashMap<>();
    private Long counter = 1L;
    private Matcher matcher;
    private Pattern Auction_Item = Pattern.compile("<h3 class=\"lvtitle\"><a .*?>(?:<span class=\"newly\">New listing</span>.*?)?(.*?)</a>.*?\\$([0-9,]+\\.\\d{2}).*?((\\d+) bid.*?)?(timeMs=\"(\\d+)\".*?)?Item: (\\d+)", Pattern.DOTALL);

    public FileBasedDatasource() {
        Load();
    }

    public FileBasedDatasource(String file) {
        try {
            Path path = Paths.get(file);
            byte[] bytes = Files.readAllBytes(path);
            String line = new String(bytes);
            matcher = Auction_Item.matcher(line);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        find(matcher);
    }

    public void find(Matcher matcher) {
        Auction temp;
        while(matcher.find()) {
            String title = matcher.group(1).trim();
            Double price = Double.parseDouble(matcher.group(2).replace(",", ""));
            int bids = (matcher.group(4) == null || matcher.group(4).isEmpty()) ? 0 : Integer.parseInt(matcher.group(4));
            int numberOfBids = bids;
            long date = (matcher.group(6) == null || matcher.group(6).isEmpty()) ? System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000 : Long.parseLong(matcher.group(6));
            long endTimeMillis = date;
            Long id = Long.parseLong(matcher.group(7));

            temp = new Auction(id, price, "", title, "", numberOfBids, endTimeMillis);
            items.put(temp.getId(), temp);
            if(temp.getId() > counter) {
                counter = temp.getId() + 1;
            }
        }
    }

    public void Load() {
        try (BufferedReader reader = new BufferedReader(new FileReader("auction.dat"))) {
            while (reader.ready()) {
                String line = reader.readLine();
                Auction item = new Auction(line.split(","));
                if(item.getId() > counter) {
                    counter = item.getId();
                }
                items.put(counter, item);
                counter++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("auction.dat"))){
            for(long index = 1L; index < items.size(); index++) {
                writer.write(items.get(index).toString() + "\n");
                writer.flush();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Auction[] search(String criteria) {
        Auction[] results;
        Set<Auction> temp = null;

        if(criteria == null || criteria.isEmpty()) {
            results =  new Auction[0];
        }else {
            Stack<Predicate<Auction>> predicates = new Stack<>();
            Stack<String> operators = new Stack<>();
            Scanner scanner = new Scanner(criteria);
            while(scanner.hasNext()) {
                String nextCriterion = scanner.next();
                String nextOperator = scanner.hasNext() ? scanner.next() : null;

                predicates.push(new Contains(nextCriterion));
                while(nextOperator != null && !operators.isEmpty() && "OR".equals(nextOperator) && "AND".equals(operators.peek())) {
                    Predicate<Auction> right = predicates.pop();
                    Predicate<Auction> left = predicates.pop();
                    String and = operators.pop();
                    predicates.push(new AndPredicate<>(left, right));
                }
                if(nextOperator != null) {
                    operators.push(nextOperator);
                }
            }

            while(predicates.size() > 1) {
                Predicate<Auction> right =  predicates.pop();
                Predicate<Auction> left = predicates.pop();
                String operator = operators.pop();
                predicates.push("AND".equals(operator) ? new AndPredicate<> (left, right) : new OrPredicate<>(left, right));
            }

            Predicate<Auction> finalPredicate = predicates.pop();
            temp = CollectionUtils.filter(items.values(), finalPredicate);
            results = temp.toArray(new Auction[temp.size()]);
        }

        return results;
    }

    @Override
    public void bid(String username, Long itemId) {
        Auction temp = items.get(itemId);
        if(username == null) {
            throw new IllegalArgumentException("Must have an owner.");
        }
        if(temp != null && temp.getEndTimeMillis() < System.currentTimeMillis()) {
            temp.setOwner(username);
            temp.setCurrentBid(temp.getCurrentBid() + 1);
            temp.setNumberOfBids(temp.getNumberOfBids() + 1);
        }
        Save();
    }

    @Override
    public Auction create(Auction auction) {
        Auction a = new Auction(counter++, auction.getCurrentBid(), auction.getName(), auction.getDescription(), auction.getEndDate(), auction.getCreator());
        items.put(a.getId(), a);
        Save();
        return a;
    }

    @Override
    public void delete(Long id, String username) {
        Auction temp = items.get(id);
        if(temp.getCreator().equals(username)) {
            if(temp.getNumberOfBids() == 0) {
                items.remove(id);
                System.out.println("Item has been deleted.");
            }
        }else {
            String error = "";
            if(!temp.getCreator().equals(username)) {
                error = "not the original creator.";
            }else if(temp.getNumberOfBids() != 0) {
                error = "item has a bid on it.";
            }
            System.out.printf("\nItem cannot be deleted due to %s.", error);
        }
        Save();
    }

    @Override
    public Auction retreive(Long id) {
        Auction temp = items.get(id);
        if(temp == null) {
            throw new ObjectNotFoundException("Auction ID:" + id + " was not found");
        }
        return temp;
    }

    @Override
    public Auction update(Auction auction, Long id) {
        if(!auction.getId().equals(id)) {
            throw new IdMismatchException("Auction ID:" + auction.getId() + " does not match.");
        }
        items.put(id, auction);
        Save();
        return auction;
    }
}

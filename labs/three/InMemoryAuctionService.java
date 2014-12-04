package labs.three;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InMemoryAuctionService implements AuctionService{
	private Map<Integer, Auction> items = new HashMap<>();
	private int counter = 1;
	private Matcher matcher;
	private Pattern Auction_Item = Pattern.compile("<h3 class=\"lvtitle\"><a .*?(?:<span class=\"newly\">New listing</span>.*?)?(.*?)</a>.*?\\$([0-9,]+\\.\\d{2}).*?((\\d+) bid.*?)?(timeMs=\"(\\d+)\".*?)?Item: (\\d+)");
	
	public InMemoryAuctionService() {}

	public InMemoryAuctionService(String file) {
		Auction temp;
		try {
			Path path = Paths.get(file);
			byte[] bytes = Files.readAllBytes(path);
			String line = new String(bytes);
			matcher = Auction_Item.matcher(line);
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
		while(matcher.find()) {
			String title = matcher.group(1).trim();
			Integer price = Integer.parseInt(matcher.group(2).replace(",", " "));
			int bids = (matcher.group(4) == null || matcher.group(4).isEmpty()) ? 0 : Integer.parseInt(matcher.group(4));
			int numberOfBids = bids;
			long date = (matcher.group(6) == null || matcher.group(6).isEmpty()) ? System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000 : Long.parseLong(matcher.group(6));
			long endTimeMillis = date;
			Integer id = Integer.parseInt(matcher.group(7));

			temp = new Auction(id, price, "", title, "", numberOfBids, endTimeMillis);
			items.put(temp.getId(), temp);
			if(temp.getId() > counter) {
				counter = temp.getId() + 1;
			}
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
					predicates.push(new AndPredicate<Auction>(left, right));
				}
				if(nextOperator != null) {
					operators.push(nextOperator);
				}
			}

			while(predicates.size() > 1) {
				Predicate<Auction> right =  predicates.pop();
				Predicate<Auction> left = predicates.pop();
				String operator = operators.pop();
				predicates.push("AND".equals(operator) ? new AndPredicate<Auction> (left, right) : new OrPredicate<Auction>(left, right));
			}

			Predicate<Auction> finalPredicate = predicates.pop();
			temp = CollectionUtils.filter(items.values(), finalPredicate);
			results = temp.toArray(new Auction[temp.size()]);
		}

		return results;
	}

	@Override
	public void bid(String username, Integer itemId) {
		Auction temp = items.get(itemId);
		if(username == null) {
			throw new IllegalArgumentException("Must have an owner.");
		}
		if(temp != null && temp.getEndTimeMillis() < System.currentTimeMillis()) {
			temp.setOwner(username);
			temp.setCurrentBid(temp.getCurrentBid() + 1);
			temp.setNumberOfBids(temp.getNumberOfBids() + 1);
		}
	}

	@Override
	public Auction create(Auction auction) {
		Auction a = new Auction(counter++, auction.getCurrentBid(), auction.getName(), auction.getDescription(), auction.getEndDate());
		items.put(a.getId(), a);
		return a;
	}

	@Override
	public void delete(Integer id) {
		items.remove(id);
	}

	@Override
	public Auction retreive(Integer id) {
		Auction temp = items.get(id);
		if(temp == null) {
			throw new ObjectNotFoundException("Auction ID:" + id + " was not found");
		}
		return temp;
	}

	@Override
	public Auction update(Auction auction, Integer id, String username) {
		if(!auction.getId().equals(id)) {
			throw new IdMismatchException("Auction ID:" + auction.getId() + " does not match.");
		}
		if(!username.equals(auction.getCreator()))
		items.put(id, auction);
		return auction;
	}
}

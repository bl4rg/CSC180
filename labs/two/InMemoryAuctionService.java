package labs.two;

import java.util.*;

public class InMemoryAuctionService implements AuctionService{
	private Map<Integer, Auction> items = new HashMap<>();
	private int counter = 0;
	
	public InMemoryAuctionService() {
		create(new Auction(1, 0, "bowl", "plane"));
		create(new Auction(2, 0, "plate", "fancy"));
		create(new Auction(3, 0, "spoon", "dull"));
		create(new Auction(4, 0, "fork", "shiny"));
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
		temp.setOwner(username);
		temp.setCurrentBid(temp.getCurrentBid() + 1);
	}

	@Override
	public Auction create(Auction auction) {
		Auction a = new Auction(counter++, auction.getCurrentBid(), auction.getName(), auction.getDescription());
		items.put(a.getId(), a);
		return a;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Auction retreive(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Auction update(Auction auction, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}

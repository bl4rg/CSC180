package labs.six;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 * Created by sean on 12/7/2014.
 */
public class RemoteClientAuctionService implements AuctionService {
    private Datasource items;

    public RemoteClientAuctionService() throws IOException {
        items = new FileBasedDatasource("auctions.dat");
    }

    public RemoteClientAuctionService(Datasource items) {
        this.items = items;
    }

    public RemoteClientAuctionService(RecordReader rr) {
        while ( rr.hasNext() ) {
            Auction a = rr.next();
            try {
                items.update(a);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public Auction create(Auction auction) {
        Auction a = new Auction(null, auction.getName(), auction.getDescription(), auction.getCurrentBid(), auction.getOwner(), auction.getNumberOfBids(), auction.getEndTimeMillis());
        try {
            return items.update(a);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Auction retreive(Long id) {
        Auction a;
        try {
            a = items.findById(id);
            if ( a == null ) {
                throw new ObjectNotFoundException("labs.five.Auction #" + id + " does not exist");
            }
            return a;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Auction update(Long id, Auction auction) {
        if ( !auction.getId().equals(id) ) throw new IdMismatchException("labs.five.Auction #" + auction.getId() + " does not match passed id " + id);

        try {
            return items.update(auction);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            items.delete(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Auction[] search(String criteria) {
        if ( criteria == null ) return new Auction[0];

        criteria = criteria.toLowerCase();

        Set<Auction> filtered = filter(criteria);

        return filtered.toArray(new Auction[filtered.size()]);
    }

    private Set<Auction> filter(String criteria) {
        Stack<Predicate<Auction>> predicates = new Stack<>();
        Stack<String> operators = new Stack<>();

        Scanner scanner = new Scanner(criteria);
        while ( scanner.hasNext() ) {
            String nextCriterion = scanner.next();
            String nextOperator = scanner.hasNext() ? scanner.next() : null;

            predicates.push(new ContainsPredicate(nextCriterion));
            while ( nextOperator != null && !operators.isEmpty() && "OR".equals(nextOperator) && "AND".equals(operators.peek()) ) {
                Predicate<Auction> right = predicates.pop();
                Predicate<Auction> left = predicates.pop();
                String and = operators.pop();
                predicates.push(new AndPredicate<Auction>(left, right));
            }
            if ( nextOperator != null ) { operators.push(nextOperator); }
        }

        while ( predicates.size() > 1 ) {
            Predicate<Auction> right = predicates.pop();
            Predicate<Auction> left = predicates.pop();
            String operator = operators.pop();
            predicates.push("AND".equals(operator) ? new AndPredicate<Auction>(left, right) : new OrPredicate<Auction>(left, right));
        }

        Predicate<Auction> finalPredicate = predicates.pop();

        try {
            return CollectionUtils.filter(items.findAll(), finalPredicate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Auction bid(String owner, Long itemId) {
        if ( owner == null ) throw new IllegalArgumentException("Owner must not be null.");

        Auction a = retreive(itemId);
        if ( a != null ) {
            a.setCurrentBid(a.getCurrentBid() + 1);
            a.setNumberOfBids(a.getNumberOfBids() + 1);
            a.setOwner(owner);
        }

        try {
            return items.update(a);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

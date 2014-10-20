package labs.two;

public class ContainsPredicate implements Predicate<Auction>{
	Auction item;
	
	public ContainsPredicate(Auction item) {
		this.item = item;
	}
	
	@Override
	public boolean evaluate(Auction t) {
		return item.getName().contains(t.getName());
	}

}

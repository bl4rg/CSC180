package labs.six;

public class ContainsPredicate implements Predicate<Auction> {
	private String toSearch;
	
	public ContainsPredicate(String toSearch) {
		this.toSearch = toSearch;
	}
	
	@Override
	public boolean evaluate(Auction entity) {
		return entity.getName().toLowerCase().contains(toSearch) ||
				( entity.getDescription() != null && entity.getDescription().toLowerCase().contains(toSearch) );
	}

}

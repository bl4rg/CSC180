package labs.two;

public class Contains implements Predicate<Auction>{
	private String search;
	
	public Contains(String toSearch) {
		this.search = toSearch;
	}
	
	@Override
	public boolean evaluate(Auction t) {
		return t.getName().toLowerCase().contains(search) || (t.getDescription() != null && t.getDescription().toLowerCase().contains(search));
	}

}

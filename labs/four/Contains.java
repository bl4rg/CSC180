package labs.four;

public class Contains implements Predicate<Auction>{
	private String search;
	
	public Contains(String toSearch) {
		this.search = toSearch;
	}
	
	@Override
	public boolean evaluate(Auction t) {
		boolean results = false;
		if(t.getEndTimeMillis() > System.currentTimeMillis()) {
			results =  t.getName().toLowerCase().contains(search.toLowerCase()) || (t.getDescription() != null && t.getDescription().toLowerCase().contains(search.toLowerCase()));
		}
		return results;
	}
}

package exercisesFive;

public class TableRowAuctionConverter implements Converter<Auction>{
	Auction item;

	@Override
	public Auction pasre(String fromString) {
		
		return null;
	}

	@Override
	public String format(Auction fromObject) {
		String tableFormat = "Id\tName\tDescription\tOwner\tCurrentBid"
						+ "\n" + fromObject.getId() + "\t" 
						+ fromObject.getName() + "\t"
						+ fromObject.getDescription() + "\t\t"
						+ fromObject.getOwner() + "\t"
						+ fromObject.getCurrentBid();
		
		return tableFormat;
	}
}

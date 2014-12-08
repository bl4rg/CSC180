package labs.five;

import static labs.five.InputUtils.pad;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class TableRowAuctionConverter {
	private SimpleDateFormat sdf = new SimpleDateFormat("E @ ha");
	
	public String formatHeader() {
		String s = pad("Id", 15) + pad("Name", 90) + pad("Bid", 10) + pad("Owner", 20) + pad("Bids", 5) + pad("Ends By", 15);
		return s;
	}
	
	public String format(Auction a) {
		String s = pad(a.getId(), 15) + pad(a.getName(), 90) + 
				pad(NumberFormat.getCurrencyInstance().format(a.getCurrentBid()), 10) + 
				pad(a.getOwner(), 20) + pad(a.getNumberOfBids(), 5) + 
				pad(sdf.format(a.getEndsBy()), 15);
		return s;
	}
}

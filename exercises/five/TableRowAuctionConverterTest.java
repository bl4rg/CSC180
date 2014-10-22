package exercisesFive;

import org.junit.Assert;
import org.junit.Test;

public class TableRowAuctionConverterTest {

	@Test
	public void test() {
		Auction item = new Auction(1, "cup", "shiny", 1);
		Converter<Auction> trac = new TableRowAuctionConverter();
		
		System.out.println(trac.format(item));
		String tableFormat = "Id\tName\tDescription\tOwner\tCurrentBid"
				+ "\n" + item.getId() + "\t" 
				+ item.getName() + "\t"
				+ item.getDescription() + "\t\t"
				+ item.getOwner() + "\t"
				+ item.getCurrentBid();
		
		Assert.assertEquals(tableFormat, trac.format(item));
	}

}

package labs.three;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * Created by sean on 11/19/2014.
 */
public class TableRowAuctionConverter {
    private SimpleDateFormat sdf = new SimpleDateFormat();
    private Padding padder = new Padding();
    private int idPad = 15;
    private int namePad = 90;
    private int bidPad = 10;
    private int ownerPad = 20;
    private int bidsPad = 5;
    private int endsPad = 15;

    public String header() {
        String output = padder.pad("Id", idPad) +
                padder.pad("Name", namePad) +
                padder.pad("Bid", bidPad) +
                padder.pad("Owner", ownerPad) +
                padder.pad("Bids", bidsPad) +
                padder.pad("Bidding ends", endsPad);
        return output;
    }

    public String format(Auction item) {
        String output = padder.pad(item.getId(), idPad) +
                padder.pad(item.getName(), namePad) +
                padder.pad(NumberFormat.getCurrencyInstance().format(item.getCurrentBid()), bidPad) +
                padder.pad(item.getOwner(), ownerPad) +
                padder.pad(item.getNumberOfBids(), bidsPad) +
                padder.pad(sdf.format(item.getEndDate()), endsPad);
        return output;
    }
}

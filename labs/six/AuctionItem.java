package labs.six;

import javax.swing.*;

/**
 * Created by sean on 12/9/2014.
 */
public class AuctionItem extends JPanel {
    private JButton bid = new JButton("Bid");
    private JButton edit = new JButton("Edit");
    private JButton delete = new JButton("Delete");

    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel priceLabel;
    private JLabel ownerLabel;
    private JLabel bidsLabel;
    private JLabel endDateLabel;



    public AuctionItem(Auction item) {
        init(item.getId(), item.getName(), item.getDescription(), item.getCurrentBid(), item.getOwner(), item.getNumberOfBids(), item.getEndTimeMillis());
    }

    public void init(Long id, String name, String description, Double price, String owner, Integer bids, Long endDate) {
        this.idLabel = new JLabel("" + id);
        this.nameLabel = new JLabel(name);
        this.descriptionLabel = new JLabel(description);
        this.priceLabel = new JLabel("" + price);
        this.ownerLabel = new JLabel(owner);
        this.bidsLabel = new JLabel("" + bids);
        this.endDateLabel = new JLabel("" + endDate);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(idLabel);
        this.add(nameLabel);
        this.add(descriptionLabel);
        this.add(priceLabel);
        this.add(ownerLabel);
        this.add(bidsLabel);
        this.add(endDateLabel);
    }
}

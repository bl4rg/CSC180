package labs.four;

import java.util.Date;

public class Auction {
	private Long id;
	private Integer externalId;
	private Double currentBid;
	private String creator;
	private String owner;
	private String name;
	private String description;
	private Integer numberOfBids;
	private long endTimeMillis;

	public Auction(Long id, Double bid, String name, String username) {
		this(new Long(id), new Double(bid), null, name, new Date(), username);
	}

	public Auction(Long id, Double bid, String name, String description, Date endsBy, String username) {
		this(id, bid, "", name, description, 0, endsBy.getTime());
		this.creator = username;
	}

	public Auction(Long id, Double currentBid, String owner, String name, String description, Integer numberOfBids, long endTimeMillis) {
		this.id = id;
		this.currentBid = currentBid;
		this.owner = owner;
		this.name = name;
		this.description = description;
		this.numberOfBids = numberOfBids;
		this.endTimeMillis = endTimeMillis;
	}

	public Auction(String...info) {
		this.id = Long.parseLong(info[0]);
		this.currentBid = Double.parseDouble(info[1]);
		this.creator = info[2];
		this.owner = info[3];
		this.name = info[4];
		this.description = info[5];
		this.numberOfBids = Integer.parseInt(info[6]);
		this.endTimeMillis = Long.parseLong(info[7]);
	}

	public Long getId() {
		return id;
	}

	public Double getCurrentBid() {
		return currentBid;
	}

	public void setCurrentBid(Double currentBid) {
		this.currentBid = currentBid;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setNumberOfBids(Integer numberOfBids) {
		this.numberOfBids = numberOfBids;
	}

	public Integer getNumberOfBids() {
		return numberOfBids;
	}

	public long getEndTimeMillis() {
		return endTimeMillis;
	}

	public Date getEndDate() {
		return new Date(endTimeMillis);
	}

	public String getCreator() {
		return creator;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Auction auction = (Auction) o;

		if (id != null ? !id.equals(auction.id) : auction.id != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "" + id + "," + currentBid + "," + creator + "," + owner + "," + name + "," + description + "," + numberOfBids + "," + endTimeMillis;
	}
}

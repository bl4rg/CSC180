package labs.three;

import java.util.Date;

public class Auction {
	private Integer id;
	private Integer currentBid;
	private String creator;
	private String owner;
	private String name;
	private String description;
	private Integer numberOfBids;
	private long endTimeMillis;

	public Auction(Integer id, Integer bid, String name, String username) {
		this(new Integer(id), new Integer(bid), null, name, new Date(), username);
	}

	public Auction(Integer id, Integer bid, String name, String description, Date endsBy, String username) {
		this(id, bid, "", name, description, 0, endsBy.getTime(), username);
	}

	public Auction(Integer id, Integer currentBid, String owner, String name, String description, Integer numberOfBids, long endTimeMillis, String creator) {
		this.id = id;
		this.currentBid = currentBid;
		this.owner = owner;
		this.name = name;
		this.description = description;
		this.numberOfBids = numberOfBids;
		this.endTimeMillis = endTimeMillis;
		this.creator = creator;
	}

	public Integer getId() {
		return id;
	}

	public Integer getCurrentBid() {
		return currentBid;
	}

	public void setCurrentBid(Integer currentBid) {
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
		return new Date(endTimeMillis + 7 * 24 * 60 * 60 * 1000);
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
		return "Auction{" +
				"id=" + id +
				", currentBid=" + currentBid +
				", owner='" + owner + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", numberOfBids=" + numberOfBids +
				", endDate=" + getEndDate() +
				'}';
	}
}

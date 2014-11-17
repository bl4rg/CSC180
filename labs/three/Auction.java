package labs.three;

public class Auction {
	private Integer id;
	private Integer currentBid;
	private String owner;
	private String name;
	private String description;

	public Auction(Integer id, Integer currentBid, String name, String description) {
		this.id = id;
		this.currentBid = currentBid;
		this.name = name;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
				'}';
	}
}

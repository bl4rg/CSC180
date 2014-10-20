package labs.two;

public class Auction {
	private final Integer id;
	private Integer currentBid;
	private String owner;
	private final String name;
	private String description;
	
	public Auction(int id, String name, String description, Integer currentBid) {
		this.id = id;
		this.name = name;
		this.setDescription(description);
		this.currentBid = currentBid;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public void addToCurrentBid(int bid) {
		this.currentBid =+ bid;
	}
	
	public Integer getCurrentBid() {
		return this.currentBid;
	}
	
	public boolean equals(Auction auctionObject) {
		boolean equal = false;
		if(this.id == auctionObject.id) {
			equal = true;
		}else{
			equal = false;
		}
		return equal;
	}
	
	public int hashCode() {
		return this.id;
	}
	
	public String toString() {
		return "Id: " + this.id + 
				"\nCurrent Bid: " + this.currentBid + 
				"\nOwner: " + this.owner +
				"\nName: " + this.name + 
				"\n";
		
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

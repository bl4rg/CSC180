
public class Auction {
	private final int id;
	private int currentBid;
	private String owner;
	private final String name;
	
	public Auction(int id, String name, int currentBid) {
		this.id = id;
		this.name = name;
		this.currentBid = currentBid;
	}
	
	public int getId() {
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
	
	public int getCurrentBid() {
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
				"\nName: " + this.name;
		
	}
}

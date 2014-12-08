package labs.five;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Auction implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final Long id;
	private String name;
	private String description;
	private Double currentBid;
	private String owner = "";
	private Integer numberOfBids;
	private Long endTimeMillis;
	
	public Auction(Integer id, String name, Double currentBid) {
		this(new Long(id), name, null, new Double(currentBid), new Date());
	}
	public Auction(Long id, String name, String description, Double currentBid, Date endsBy) {
		this(id, name, description, currentBid, "", 0, endsBy.getTime());
	}
	
	public Auction(Long id, String name, String description, Double currentBid, String currentOwner, Integer numberOfBids, Long endTimeMillis) {
		this.id = id;
		this.name = name;
		this.currentBid = currentBid;
		this.description = description;
		this.owner = currentOwner;
		this.numberOfBids = numberOfBids;
		this.endTimeMillis = endTimeMillis;
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if ( owner == null ) {
			this.name = name;
		}
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public Integer getNumberOfBids() {
		return numberOfBids;
	}
	
	public Long getEndTimeMillis() {
		return endTimeMillis;
	}
	
	public boolean equals(Object obj) {
		return obj instanceof Auction ? this.id.equals(((Auction)obj).getId()) : false;
	}
	
	public int hashCode() {
		return this.id.hashCode();
	}
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("E @ ha");
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		String currentBidAsString = nf.format(currentBid);
		return this.id + "               ".substring(0, 15 - String.valueOf(this.id).length()) + 
				this.name + "                                                                                                                        ".substring(0, 90 - name.length()) + 
			    currentBidAsString + "          ".substring(0, 10 - String.valueOf(currentBidAsString).length()) + 
				this.owner + "       ".substring(0, 7 - this.owner.length()) + 
				sdf.format(new Date(endTimeMillis));
	}

	public String toString(String parseFrom) {
		return "" + id + "," + name + "," + description + "," + currentBid + "," + owner + "," + numberOfBids + "," + endTimeMillis;
	}

	public Date getEndsBy() {
		return new Date(endTimeMillis);
	}
	
	public void setNumberOfBids(int numberOfBids) {
		this.numberOfBids = numberOfBids;
	}
}

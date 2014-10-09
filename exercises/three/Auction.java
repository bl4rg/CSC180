package exercisesThree;

import java.util.Map;

public class Auction extends Object{
	private Integer currentBid;
	private Integer Id;
	private String Name;
	private String Owner;
	private Map<String, Object> Properties;
	private Object Property;
	
	
	public Auction(Integer id, String name, Integer currentBid){
		this.Id =  id;
		this.Name = name;
		this.currentBid = currentBid;
	}
	
	public Auction(Integer id, String name, Integer currentBid, Map<String, Object> properties) {
		this.Id =  id;
		this.Name = name;
		this.currentBid = currentBid;
		this.Properties = properties;
	}
	
	public String toString() {
		return "ID: " + Id + "\nNam: " + Name + "\nOwner: " + Owner + "\n";
	}
	
	public boolean equals(Object obj) {
		return false;
	}
	
	public Integer getCurrentBid() {
		return this.currentBid;
	}
	
	public Integer getId() {
		return this.Id;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public String getOwner() {
		return this.Owner;
	}
	
	public Map<String, Object> getProperties() {
		return this.Properties;
	}
	
	public Object getProperty(String propertyName) {
		return this.Properties.get(propertyName);
	}
	
	public <T> T getProperty(String propertyName, Class<T> propertyType) {
		return (T) this.Properties.get(propertyName);
	}
	
	public int hashCode() {
		return this.Id;
	}
	
	public void setCurrentBid(int currentBid) {
		this.currentBid = currentBid;
	}
	
	public void setName(String name) {
		this.Name = name;
	}
	
	public void setOwner(String owner) {
		this.Owner = owner;
	}
	
	public void setProperty(String propertyName, Object propertyValue) {
		this.Properties.put(propertyName, propertyValue);
	}
}

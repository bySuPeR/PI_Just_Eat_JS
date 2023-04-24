package es.unex.pi.model;

public class Restaurant {

	private long id;
	private String name;
	private String address;
	private String telephone;
	private String city;
	private int minPrice;
	private int maxPrice;
	private String contactEmail;
	private Double gradesAverage;
	private int bikeFriendly;
	private int available;
	private long idu;
	private String restaurantPic;
	
	public String getName () {
		return name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public String getAddress () {
		return address;
	}
	
	public void setAddress (String address) {
		this.address = address;
	}
	
	public String getCity () {
		return city;
	}
	
	public void setCity (String city) {
		this.city = city;
	}
	
	public int getMinPrice () {
		return minPrice;
	}
	
	public void setMinPrice (int minPrice) {
		this.minPrice = minPrice;
	}
	
	public int getMaxPrice () {
		return maxPrice;
	}
	
	public void setMaxPrice (int maxPrice) {
		this.maxPrice = maxPrice;
	}
	
	public Double getGradesAverage () {
		return gradesAverage;
	}
	public void setGradesAverage (Double gradesAverage) {
		this.gradesAverage = gradesAverage;
	}
	
	public int getBikeFriendly () {
		return bikeFriendly;
	}
	
	public void setBikeFriendly (int bikeFriendly) {
		this.bikeFriendly = bikeFriendly;
	}
	
	public long getId () {
		return id;
	}
	
	public void setId (long id) {
		this.id = id;
	}
	
	public String getTelephone () {
		return telephone;
	}
	
	public void setTelephone (String telephone) {
		this.telephone = telephone;
	}
	
	public String getContactEmail () {
		return contactEmail;
	}
	
	public void setContactEmail (String contactEmail) {
		this.contactEmail = contactEmail;
	}
	
	public int getAvailable () {
		return available;
	}
	
	public void setAvailable (int available) {
		this.available = available;
	}
	
	public long getIdu () {
		return idu;
	}
	
	public void setIdu (long idu) {
		this.idu = idu;
	}
	
	public String getRestaurantPic () {
		return this.restaurantPic;
	}
	
	public void setRestaurantPic (String restaurantPic) {
		this.restaurantPic = restaurantPic;
	}
}

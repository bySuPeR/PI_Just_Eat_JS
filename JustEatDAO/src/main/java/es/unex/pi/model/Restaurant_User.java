package es.unex.pi.model;

public class Restaurant_User {

	private long idr;
	private long idu;
	private String review;
	private Double grade;
	
	private User user;
	
	public long getIdr () {
		return idr;
	}
	
	public void setIdr (long idr) {
		this.idr = idr;
	}
	
	public long getIdu () {
		return idu;
	}
	
	public void setIdu (long idu) {
		this.idu = idu;
	}
	
	public String getReview () {
		return review;
	}
	
	public void setReview (String review) {
		this.review = review;
	}
	
	public Double getGrade () {
		return grade;
	}
	public void setGrade (Double grade) {
		this.grade = grade;
	}
}

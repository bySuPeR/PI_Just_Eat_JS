package es.unex.pi.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Favorito {
	private long idRestaurant;
	private long idUser;
	
	public long getidRestaurant() {
		return idRestaurant;
	}
	
	public void setidRestaurant(long idRestaurant) {
		this.idRestaurant = idRestaurant;
	}
	
	public long getidUser() {
		return idUser;
	}
	
	public void setidUser(long idUser) {
		this.idUser = idUser;
	}
}

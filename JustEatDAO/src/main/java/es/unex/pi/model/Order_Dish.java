package es.unex.pi.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order_Dish {

	private long ido;
	private long iddi;
	
	public long getIdo () {
		return ido;
	}
	
	public void setIdo (long ido) {
		this.ido = ido;
	}
	
	public long getIddi () {
		return iddi;
	}
	
	public void setIddi (long iddi) {
		this.iddi = iddi;
	}
}

package es.unex.pi.dao;

import java.sql.Connection;
import java.util.ArrayList;
import es.unex.pi.model.Favorito;

public interface FavoritoDAO {
	
	public void setConnection(Connection conn);

	
	
	public ArrayList<Favorito> getAll();

	
	public ArrayList<Favorito> getAllByUser(long idUser);
	
	
	public ArrayList<Favorito> getAllByRestaurant(long idRestaurant);

	
	
	public Favorito get(long idRestaurant,long idUser);

	
	
	public boolean add(Favorito Favoritos);

	
	
	public boolean save(Favorito Favoritos);

	
	
	public boolean delete(long idRestaurant, long idUser);
}


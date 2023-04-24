package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;
import es.unex.pi.model.Favorito;

public interface FavoritoDAO {
	
	public void setConnection(Connection conn);

	
	
	public List<Favorito> getAll();

	
	public List<Favorito> getAllByUser(long idUser);
	
	
	public List<Favorito> getAllByRestaurant(long idRestaurant);

	
	
	public Favorito get(long idRestaurant,long idUser);

	
	
	public boolean add(Favorito Favoritos);

	
	
	public boolean save(Favorito Favoritos);

	
	
	public boolean delete(long idRestaurant, long idUser);
}


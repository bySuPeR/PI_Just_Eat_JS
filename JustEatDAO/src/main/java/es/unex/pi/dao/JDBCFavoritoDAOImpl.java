package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.Favorito;

public class JDBCFavoritoDAOImpl implements FavoritoDAO{
	
	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCFavoritoDAOImpl.class.getName());
	
	@Override
	public List<Favorito> getAll() {

		if (conn == null) return null;
						
		ArrayList<Favorito> FavoritoList = new ArrayList<Favorito>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Favorito");
						
			while ( rs.next() ) {
				Favorito favorito = new Favorito();
				favorito.setidRestaurant(rs.getInt("idRestaurant"));
				favorito.setidUser(rs.getInt("idUser"));
						
				FavoritoList.add(favorito);
					
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return FavoritoList;
	}
	
	
	@Override
	public List<Favorito> getAllByUser(long idUser) {
		
		if (conn == null) return null;
						
		ArrayList<Favorito> FavoritoList = new ArrayList<Favorito>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Favorito WHERE idUser="+idUser);

			while ( rs.next() ) {
				Favorito favorito = new Favorito();
				favorito.setidRestaurant(rs.getInt("idRestaurant"));
				favorito.setidUser(rs.getInt("idUser"));

				FavoritoList.add(favorito);
				logger.info("fetching all Favorito by idR: "+favorito.getidRestaurant()+"->"+favorito.getidUser());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return FavoritoList;
	}
	
	@Override
	public List<Favorito> getAllByRestaurant(long idRestaurant) {
		
		if (conn == null) return null;
						
		ArrayList<Favorito> FavoritoList = new ArrayList<Favorito>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Favorito WHERE idRestaurant="+idRestaurant);

			while ( rs.next() ) {
				Favorito favorito = new Favorito();
				favorito.setidRestaurant(rs.getInt("idRestaurant"));
				favorito.setidUser(rs.getInt("idUser"));
							
				FavoritoList.add(favorito);
				logger.info("fetching all Favorito by idct: "+favorito.getidUser()+"-> "+favorito.getidRestaurant());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return FavoritoList;
	}
	
	@Override
	public Favorito get(long idRestaurante,long idusuario) {
		if (conn == null) return null;
		
		Favorito favorito = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Favorito WHERE idRestaurant="+idRestaurante+" AND idUser="+idusuario);			 
			if (!rs.next()) return null;
			favorito= new Favorito();
			favorito.setidRestaurant(rs.getInt("idRestaurant"));
			favorito.setidUser(rs.getInt("idUser"));

			logger.info("fetching Favorito by idRestaurant: "+favorito.getidRestaurant()+"  and idUser: "+favorito.getidUser());
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return favorito;
	}
	
	@Override
	public boolean add(Favorito favorito) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Favorito (idRestaurant,idUser) VALUES('"+
						favorito.getidRestaurant()+"','"+
						favorito.getidUser()+"')");
						
				logger.info("creating Favorito:("+favorito.getidRestaurant()+" "+favorito.getidUser());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}
	
	@Override
	public boolean save(Favorito favorito) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM Favorito WHERE idRestaurant="+favorito.getidRestaurant());			 
				if (!rs.next()) 
					return false;
				long idusuario = rs.getInt("idUser");

				stmt.executeUpdate("UPDATE Favorito SET idUser="+favorito.getidUser()
				+" WHERE idRestaurant = "+favorito.getidRestaurant() + " AND idUser = " + idusuario);
				
				logger.info("updating Favorito:("+favorito.getidRestaurant()+" "+favorito.getidUser());
				
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}
	
	@Override
	public boolean delete(long idRestaurante, long idusuario) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Favorito WHERE idRestaurant ="+idRestaurante+" AND idUser="+idusuario);
				logger.info("deleting Favorito: "+idRestaurante+" , idUser="+idusuario);
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}
	
	

}

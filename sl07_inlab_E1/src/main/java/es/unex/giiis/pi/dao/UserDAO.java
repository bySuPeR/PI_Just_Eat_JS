package es.unex.giiis.pi.dao;

import java.sql.Connection;

import es.unex.giiis.pi.model.User;



public interface UserDAO {
	
	public User get(long id);
	public User get(String username);
	public long add(User User);
	public boolean save(User User);
	public boolean delete(long id);
	
	public void setConnection(Connection conn);
}

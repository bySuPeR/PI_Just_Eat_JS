package es.unex.pi.dao;

import java.sql.Connection;
import java.util.ArrayList;

import es.unex.pi.model.Restaurant;


public interface RestaurantDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	
	public void setConnection (Connection conn);
	
	
	/**
	 * Gets a restaurant from the DB using id.
	 * 
	 * @param id
	 *            Restaurant Identifier.
	 * 
	 * @return Restaurant object with that id.
	 */
	
	public Restaurant get (long id);

	
	/**
	 * Gets all the notes from the database.
	 * 
	 * @return List of all the restaurants from the database.
	 */
	
	public ArrayList <Restaurant> getAll ();
	
	
	/**
	 * Gets all the restaurants whose city, name or address contains the specified string.
	 * 
	 * @param search
	 *            Search string .
	 * 
	 * @return List of all the restaurants from the database that contain a text either in the city, name or address.
	 */
	
	public ArrayList <Restaurant> searchAmongRestaurants (String search);


	/**
	 * Gets all the restaurants from the database that belong to a user.
	 * 
	 * @param idu
	 *            User identifier.
	 * 
	 * @return List of all the restaurants from the database that belong to a user
	 */
	
	public ArrayList <Restaurant> getAllByUser (long idu);
	
	
	/**
	 * Gets the restaurant by searching with its name.
	 * In case there are more than one restaurant with the same name,
	 * it will return the first one that it finds.
	 * @param name: The restaurant name that needs to be looked for.
	 * @return Restaurant: The Restaurant with the specified name.
	 */
	
	public Restaurant getByName (String name);
	
	
	/**
	 * Adds a restaurant to the database.
	 * 
	 * @param restaurant
	 *            Restaurant object with the restaurant details.
	 * 
	 * @return Restaurant identifier or -1 in case the operation failed.
	 */
	
	public long add (Restaurant restaurant);

	
	/**
	 * Updates an existing restaurant in the database.
	 * 
	 * @param restaurant
	 *            Restaurant object with the new details of the existing restaurant.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean update (Restaurant restaurant);

	
	/**
	 * Deletes a restaurant from the database.
	 * 
	 * @param id
	 *            Restaurant identifier.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete (long id);
}

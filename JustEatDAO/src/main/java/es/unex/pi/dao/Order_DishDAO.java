package es.unex.pi.dao;

import java.sql.Connection;
import java.util.ArrayList;

import es.unex.pi.model.Order_Dish;


public interface Order_DishDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	
	public void setConnection (Connection conn);

	
	/**
	 * Gets all the order and the dishes related to them from the database.
	 * 
	 * @return List of all the order and the dishes related to them from the database.
	 */
	
	public ArrayList <Order_Dish> getAll ();

	
	/**
	 *Gets all the OrderDish that are related to a dish.
	 * 
	 * @param iddi
	 *            Dish identifier
	 * 
	 * @return List of all the OrderDish related to a dish.
	 */
	
	public ArrayList <Order_Dish> getAllByDish (long iddi);
	
	
	/**
	 * Gets all the OrderDish that contains an specific order.
	 * 
	 * @param ido
	 *            Order Identifier
	 * 
	 * @return List of all the OrderDish that contains an specific order
	 */
	
	public ArrayList <Order_Dish> getAllByOrder (long ido);

	
	/**
	 * Gets a OrderDish from the DB using ido and iddi.
	 * 
	 * @param ido 
	 *            order identifier.
	 *            
	 * @param iddi
	 *            Dish Identifier
	 * 
	 * @return OrderDish with that ido and iddi.
	 */
	
	public Order_Dish get (long ido, long iddi);

	
	/**
	 * Adds an OrderDish to the database.
	 * 
	 * @param OrderDish
	 *            OrderDish object with the details of the relation between the order and the dish.
	 * 
	 * @return order identifier or -1 in case the operation failed.
	 */
	
	public boolean add (Order_Dish OrderDish);

	
	/**
	 * Updates an existing OrderDish in the database.
	 * 
	 * @param dbOrderDish
	 *            OrderDish object existing in the database 
	 *
	 * @param newOrderDish
	 *            OrderDish object with the new details of the existing relationship between the order and the dish. 
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean update (Order_Dish previousOrderDish, Order_Dish newOrderDish);

	
	/**
	 * Deletes an OrderDish from the database.
	 * 
	 * @param ido
	 *            Order identifier.
	 *            
	 * @param iddi
	 *            Dish Identifier
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete (long ido, long iddi);
}

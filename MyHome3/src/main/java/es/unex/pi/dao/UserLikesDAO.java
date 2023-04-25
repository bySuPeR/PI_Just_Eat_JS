package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.UserLikes;


public interface UserLikesDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection (Connection conn);

	/**
	 * Gets all the hosting and the categories related to them from the database.
	 * 
	 * @return List of all the hosting and the categories related to them from the database.
	 */
	
	public List<UserLikes> getAll();

	/**
	 *Gets all the HostingCategory that are related to a category.
	 * 
	 * @param idct
	 *            Category identifier
	 * 
	 * @return List of all the HostingCategory related to a category.
	 */
	public List<UserLikes> getAllByLike (long idLike);
	
	/**
	 * Gets all the HostingCategory that contains an specific hosting.
	 * 
	 * @param idh
	 *            Hosting Identifier
	 * 
	 * @return List of all the HostingCategory that contains an specific hosting
	 */
	public List<UserLikes> getAllByHosting (long idhost);

	/**
	 * Gets a HostingCategory from the DB using idh and idct.
	 * 
	 * @param idr 
	 *            hosting identifier.
	 *            
	 * @param idct
	 *            Category Identifier
	 * 
	 * @return HostingCategory with that idh and idct.
	 */
	
	public UserLikes get (long idhost, long idLike);

	/**
	 * Adds an HostingCategory to the database.
	 * 
	 * @param HostingCategory
	 *            HostingCategory object with the details of the relation between the hosting and the category.
	 * 
	 * @return hosting identifier or -1 in case the operation failed.
	 */
	
	public boolean add (UserLikes userLikes);

	/**
	 * Updates an existing HostingCategory in the database.
	 * 
	 * @param HostingCategory
	 *            HostingCategory object with the new details of the existing relation between the hosting and the category. 
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean save (UserLikes userLikes);

	/**
	 * Deletes an HostingCategory from the database.
	 * 
	 * @param idh
	 *            Hosting identifier.
	 *            
	 * @param idct
	 *            Category Identifier
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete (long idhost, long idLike);
}
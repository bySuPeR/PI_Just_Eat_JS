package es.unex.pi.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.HostingCategoriesDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCHostingCategoriesDAOImpl;
import es.unex.pi.model.Category;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingCategories;
import es.unex.pi.util.LikesComparator;

public class Operations {
	
	/**
	 * Indicates if a list of hosts contains certain host.
	 * @param hostList contains many hosts.
	 * @param host has to be searched in the list
	 * @return boolean indicating whether the list contains the specified host.
	 */
	
	public boolean listContains (List <Hosting> hostList, Hosting host) {
		boolean found = false;
		for (int index = 0; index < hostList.size (); index++) {
			if (hostList.get (index).getId () == host.getId ()) {found = true;}
		}
		return found;
	}
	
	
	/**
	 * Indicates if a host is classified with certain category
	 * @param host that will be classified-
	 * @param cat that will be compared with the hosts.
	 * @param conn to connect to the database
	 * @return boolean value indicating whether the host is classified with certain category.
	 */
	
	public boolean hostHasCategory (Hosting host, Category cat, Connection conn) {
		boolean hasCat = false;
		
		HostingCategoriesDAO hostCatDAO = new JDBCHostingCategoriesDAOImpl ();
		hostCatDAO.setConnection (conn);
		
		List <HostingCategories> hostCatList = hostCatDAO.getAllByHosting (host.getId ());
		for (int index = 0; index < hostCatList.size (); index++) {
			if (hostCatList.get (index).getIdct () == cat.getId ()) {
				hasCat = true;
			}
		}
		return hasCat;
	}
	
	
	/**
	 * Indicates if a host offers certain service.
	 * @param host
	 * @param service
	 * @param conn
	 * @return
	 */
	
	public boolean hostHasService (Hosting host, String service, Connection conn) {
		boolean hasServ = false;
		String [] hostServList = host.getServicios ().split ("/");
		for (String s: hostServList) {
			if (s.equals (service)) {
				hasServ = true;
			}
		}
		return hasServ;
	}
	
	
	/**
	 * Returns a list of all the categories that the host is classified as.
	 * @param host
	 * @param conn
	 * @return
	 */
	
	public List <Category> getHostCats (Hosting host, Connection conn) {
		HostingCategoriesDAO hostCatDAO = new JDBCHostingCategoriesDAOImpl ();
		CategoryDAO catDAO = new JDBCCategoryDAOImpl ();
		hostCatDAO.setConnection (conn);
		catDAO.setConnection (conn);
		
		List <Category> catList = new ArrayList <Category> ();
		List <HostingCategories> hostCatList = hostCatDAO.getAllByHosting (host.getId ());
		
		for (HostingCategories aux: hostCatList) {
			catList.add (catDAO.get (aux.getIdct()));
		}
		return catList;
	}
	
	
	/**
	 * Indicates whether certain price is in a certain range.
	 * @param price
	 * @param higher
	 * @param lower
	 * @return
	 */
	
	public boolean isNear (double price, double higher, double lower) {
		boolean result = false;
		
		if (price >= lower && price <= higher) {
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Permite ordenar la lista de entrada en función del número de likes en orden creciente o decreciente.
	 */
	
	public void compareOrder (List <Hosting> hostList, boolean def) {
		if (def) {
			hostList.sort (new LikesComparator ());
		}
		else {
			hostList.sort (new LikesComparator ().reversed ());
		}
	}
}

package es.unex.pi.controller;

import java.util.List;

import es.unex.pi.model.Hosting;

public class SingleHostList {
	private List <Hosting> hostList;
	private static SingleHostList instance;
	
	private SingleHostList () {
		hostList = null;
	}
	
	public static SingleHostList getInstance () {
		if (instance == null) {
			instance = new SingleHostList ();
		}
		return instance;
	}
	
	public List <Hosting> getList () {
		return this.hostList;
	}
	
	public void setList (List <Hosting> list) {
		this.hostList = list;
	}
}

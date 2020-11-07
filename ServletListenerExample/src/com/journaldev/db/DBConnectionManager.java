package com.journaldev.db;

import java.sql.Connection;

public class DBConnectionManager {

	@Override
	public String toString() {
		return "DBConnectionManager [dbURL=" + dbURL + ", user=" + user + ", password=" + password + ", con=" + con
				+ "]";
	}

	private String dbURL;
	private String user;
	private String password;
	private Connection con;
	
	public DBConnectionManager(String url, String u, String p){
		this.dbURL=url;
		this.user=u;
		this.password=p;
		//create db connection now
		
	}
	
	public Connection getConnection(){
		return this.con;
	}
	
	public void closeConnection(){
		//close DB connection here
	}
}

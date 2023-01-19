package com.example.develop.controllers;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
	private Connection con;
	private static DbConnection dbc;
	private DbConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			FileInputStream fis=new FileInputStream("src\\main\\java\\com\\example\\develop\\Connection.prop");
			Properties p = new Properties ();
			p.load(fis);
			con = DriverManager.getConnection((String)p.get("url"),(String)p.get("username"),(String)p.get("password"));
		} catch (Exception ex) {
			Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static DbConnection getDatabaseConnection() {
		if (dbc == null) {
			dbc = new DbConnection();
		}
		return dbc;
	}

	public Connection getConnection() {
		return con;
	}

	public static void main(String[] args) {
		new DbConnection();
	}

}

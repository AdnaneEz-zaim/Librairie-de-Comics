package com.example.develop.controllers;

import com.example.develop.ComicApplication;
import com.example.develop.helper.AlertHelper;
import com.example.develop.model.UserModel;
import javafx.scene.control.Alert;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
	private static Connection con;
	private static DbConnection dbc;
	private DbConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Properties p = new Properties ();
			InputStream input = ComicApplication.class.getResourceAsStream("prop/Connection.prop");
			p.load(input);
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

	// open/close Connection
	public Connection getConnection() {
		return con;
	}
	public static void closeConnection() throws SQLException {
		con.close();
		dbc = null;
	}

	//for profile configuration
	public static Boolean changeUserInfo() throws SQLException {
		Statement stmt;
		PreparedStatement ps;
		stmt = con.createStatement();
		String query = "UPDATE users set firstname = ?,lastname = ?,email = ?,username = ?,password = ? where userid = ?";
		ps = con.prepareStatement(query);
		ps.setString(1, UserModel.getUserModel().getFirstname());
		ps.setString(2, UserModel.getUserModel().getLastname());
		ps.setString(3, UserModel.getUserModel().getEmail());
		ps.setString(4, UserModel.getUserModel().getUsername());
		ps.setString(5, UserModel.getUserModel().getPassword());
		ps.setInt(6,UserModel.getUserModel().getUserid());
		if (ps.executeUpdate() > 0) {
			ps.close();
			return true;
		} else {
			ps.close();
			return false;
		}
	}

	//for library
	public static void removeComicFromLibraryById(String idComic) throws SQLException {
			PreparedStatement statement = con.prepareStatement("DELETE FROM library WHERE idComic = ? and idUser = ?");
			statement.setString(1, idComic);
			statement.setInt(2, UserModel.getUserModel().getUserid());
			statement.executeUpdate();
			statement.close();
	}
	public static ResultSet getUserLibrary() throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps;

		String query = "select * from library where idUser =  ?";

		ps = con.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, UserModel.getUserModel().getUserid());
		rs = ps.executeQuery();

		return rs;
	}
	public static void changeComicState(String idComic, int idUser, String state) throws SQLException {
		PreparedStatement statement = con.prepareStatement("UPDATE library SET comicState = ? WHERE idComic = ? and idUser = ?");
		statement.setString(1, state);
		statement.setString(2, idComic);
		statement.setInt(3,idUser);
		statement.executeUpdate();
		statement.close();

	}

	//for configuring preferences
	public static void addAuthorsAsPref(ArrayList<String> authors,String comicId) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("INSERT INTO authorPref (userid,comicid,authorName) VALUES (?,?,?)");
		for (String author : authors) {
			stmt.setInt(1,UserModel.getUserModel().getUserid());
			stmt.setString(2,comicId);
			stmt.setString(3, author);
			stmt.executeUpdate();
		}
		stmt.close();
	}
	public static void addConceptsAsPref(ArrayList<String> concepts,String comicId) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("INSERT INTO conceptPref (userid,comicid,concept) VALUES (?,?,?)");
		for (String concept : concepts) {
			stmt.setInt(1,UserModel.getUserModel().getUserid());
			stmt.setString(2,comicId);
			stmt.setString(3, concept);
			stmt.executeUpdate();
		}
		stmt.close();
	}
	public static void removeAuthorsAsPref(String comicId) throws SQLException {
		PreparedStatement statement = con.prepareStatement("DELETE FROM authorPref WHERE comicid = ? and userid = ?");
		statement.setString(1, comicId);
		statement.setInt(2, UserModel.getUserModel().getUserid());
		statement.executeUpdate();
		statement.close();
	}
	public static void removeConceptsAsPref(String comicId) throws SQLException {
		PreparedStatement statement = con.prepareStatement("DELETE FROM conceptPref WHERE comicid = ? and userid = ?");
		statement.setString(1, comicId);
		statement.setInt(2, UserModel.getUserModel().getUserid());
		statement.executeUpdate();
		statement.close();
	}

	public static void main(String[] args) {
		new DbConnection();
	}

}

package com.example.develop.service;

import com.example.develop.ComicApplication;
import com.example.develop.model.UserModel;
import com.fasterxml.jackson.databind.JsonNode;

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
		PreparedStatement ps;
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
	public static ResultSet getUserById(int userId){
		ResultSet rs = null;
		try {
			PreparedStatement ps;
			String query = "select username from users where userid =  ?";
			ps = con.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return rs;
	}
	public static int SignUp(String firstName,String lastName,String email,String username,String password) throws SQLException {
		PreparedStatement ps;
		String query = "insert into users (firstname,lastname,email,username,password)values (?,?,?,?,?)";
		ps = con.prepareStatement(query);
		ps.setString(1, firstName);
		ps.setString(2, lastName);
		ps.setString(3, email);
		ps.setString(4, username);
		ps.setString(5, password);
		int r = ps.executeUpdate();
		return r;
	}

	public static ResultSet login(String username, String password) throws SQLException {
		PreparedStatement ps;
		String query = "select * from users WHERE username = ? and password = ?";
		ps = con.prepareStatement(query);
		ps.setString(1, username);
		ps.setString(2, password);
		return ps.executeQuery();
	}

	public static boolean isAlreadyRegistered(String username) {
		PreparedStatement ps;
		ResultSet rs;
		boolean usernameExist = false;

		String query = "select * from users WHERE username = ?";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				usernameExist = true;
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return usernameExist;
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
	public static int addComicToLibrary(JsonNode comic, String comicId) throws SQLException {
		PreparedStatement ps;
		String query = "insert into library (idUser,idComic,imageComic,nameComic)values (?,?,?,?)";
		ps = con.prepareStatement(query);
		ps.setInt(1, UserModel.getUserModel().getUserid());
		ps.setString(2, comicId);
		ps.setString(3, comic.get("image").get("thumb_url").textValue());
		ps.setString(4,comic.get("name").textValue());
		int r = ps.executeUpdate();
		ps.close();
		return r;
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
	public static ArrayList<String> getPrefAuthors() throws SQLException {
		PreparedStatement statement = con.prepareStatement("SELECT authorName from authorPref where userid = ?");
		statement.setInt(1,UserModel.getUserModel().getUserid());
		ResultSet rs = statement.executeQuery();
		ArrayList<String> authors = new ArrayList<>();
		while (rs.next()) {
			String value = rs.getString("authorName");
			authors.add(value);
		}
		return authors;
	}
	public static ArrayList<String> getPrefConcepts() throws SQLException {
		PreparedStatement statement = con.prepareStatement("SELECT concept from conceptPref where userid = ?");
		statement.setInt(1,UserModel.getUserModel().getUserid());
		ResultSet rs = statement.executeQuery();
		ArrayList<String> concepts = new ArrayList<>();
		while (rs.next()) {
			String value = rs.getString("concept");
			concepts.add(value);
		}
		return concepts;
	}

	//comments
	public static ResultSet getComments(String comicId){
		ResultSet rs = null;
		try {
			PreparedStatement ps;
			String query = "select * from comments where idComic =  ?";

			ps = con.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, comicId);
			rs = ps.executeQuery();
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return rs;
	}
	public static int addComment(String comicId, String comment) throws SQLException {
		PreparedStatement ps;
		String query = "insert into comments (userid,idComic,commentContent)values (?,?,?)";
		ps = con.prepareStatement(query);
		ps.setInt(1, UserModel.getUserModel().getUserid());
		ps.setString(2, comicId);
		ps.setString(3, comment);
		int r = ps.executeUpdate();
		return r;
	}


	//Rating
	public static ResultSet getRating(String comicId) throws SQLException {
		PreparedStatement ps;
		String query = "select * from rating where idUser =  ? and idComic = ?";
		ps = con.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, UserModel.getUserModel().getUserid());
		ps.setString(2,comicId);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	public static ResultSet getAverageRating(String comicId) throws SQLException {
		PreparedStatement ps;
		String query = "SELECT AVG(rating) FROM Rating where idComic = ?";
		ps = con.prepareStatement(query);
		ps.setString(1,comicId);
		return ps.executeQuery();
	}
	public static void addRating(String comicId, Double note) throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps;
		String query = "select * from rating where idUser =  ? and idComic = ?";
		ps = con.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, UserModel.getUserModel().getUserid());
		ps.setString(2,comicId);
		rs = ps.executeQuery();
		if(rs.next()){
			String query1 = "UPDATE Rating SET rating = ? WHERE idUser = ? and idComic = ?";
			ps = con.prepareStatement(query1);
			ps.setDouble(1,note);
			ps.setInt(2,UserModel.getUserModel().getUserid());
			ps.setString(3,comicId);
			ps.executeUpdate();
			ps.close();
		}
		else{
			String query2 = "insert into Rating (rating,idUser,idComic)values (?,?,?)";
			ps = con.prepareStatement(query2);
			ps.setDouble(1,note);
			ps.setInt(2,UserModel.getUserModel().getUserid());
			ps.setString(3,comicId);
			ps.executeUpdate();
			ps.close();
		}
		ps.close();
	}



}

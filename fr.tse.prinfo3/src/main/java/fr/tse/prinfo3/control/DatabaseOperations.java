package fr.tse.prinfo3.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * FIXME Describe the content of this class
 *
 */
public class DatabaseOperations {

	private Connection myConnection;
	private String hostname;
	private String db;

	private String port;
	private String username;
	private String password;
	
    public DatabaseOperations(String hostname, String db, String port,String username, String password) {
        this.hostname = hostname;
    	this.db = db;
        this.port = port;
        this.username = username;
        this.password = password;
        try {
			 myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/comicunivers", "root", "");
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

   
	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}


	public Connection getMyConnection() {
		return myConnection;
	}


	public void setMyConnection(Connection myConnection) {
		this.myConnection = myConnection;
	}


	public String getHostname() {
		return hostname;
	}


	public void setHostname(String hostname) {
		this.hostname = hostname;
	}


	public String getDb() {
		return db;
	}


	public void setDb(String db) {
		this.db = db;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public int hashCode() {
		return Objects.hash(db, hostname, myConnection, password, username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatabaseOperations other = (DatabaseOperations) obj;
		return Objects.equals(db, other.db) && Objects.equals(hostname, other.hostname)
				&& Objects.equals(myConnection, other.myConnection) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}


	
	public void insertUser(String email, String password, String username) {
		try {
			this.myConnection.setAutoCommit(false);
			
			PreparedStatement laRequete = this.myConnection.prepareStatement("INSERT INTO user VALUE (0, ?, ?, ?)");
			
			 laRequete.setString(1, email);
			 laRequete.setString(2, password);
			 laRequete.setString(3, username);
			 laRequete.execute();
			 
			myConnection.commit();
			laRequete.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
    public void selectAllUser() {
    	try {
    		Statement query = this.myConnection.createStatement();
    		ResultSet result = query.executeQuery("SELECT * from user");
    		while (result.next()) {
		    	int key = result.getInt(1);
		    	String email = result.getString(2);
		    	String password = result.getString(3);
		    	String username = result.getString(4);
		    	System.out.println(key);
		    	System.out.println(email);
		    	System.out.println(password);
		    	System.out.println(username);
		    }
    		
    		query.close();
	    	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public String getUsernameId(String id_user) {
    	String username = "";
    	try {
    		Statement query = this.myConnection.createStatement();
    		ResultSet result = query.executeQuery("SELECT username from user where id="+id_user);
    		
    		while (result.next()) {
    			username = result.getString(1);
		    }

    		query.close();
	    	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return username; 
    }
    
    
    public void insertComment(String comment, String id_commics, String username) {
		try {
			this.myConnection.setAutoCommit(false);
			PreparedStatement laRequete = this.myConnection.prepareStatement("INSERT INTO commentaire (id_comics, username, comment) VALUES (?, ?, ?)");
			
			 laRequete.setString(1, id_commics);
			 laRequete.setString(2, username);
			 laRequete.setString(3, comment);
			 laRequete.execute();
			 
			myConnection.commit();
			laRequete.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    
    public ArrayList<List<String>> selectAllComments(String id_commics) {
    	ArrayList<List<String>> comicsComments = new ArrayList<List<String>>();
    	try {
    		System.out.println(id_commics);
    		Statement query = this.myConnection.createStatement();
    		ResultSet result = query.executeQuery("SELECT username, comment from commentaire WHERE id_comics=\""+id_commics+"\"");
    		
    		while (result.next()) {
    			List<String> comment = new ArrayList<String>();
    			comment.add(result.getString(1));
    			comment.add(result.getString(2));

    			comicsComments.add(comment);
    			
		    }
    		
    		
    		query.close();
    		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		

		return comicsComments;
    }
    
    
    public void insertNotation(double note,String id_commics,String username) {
    	try {
    		Statement query = this.myConnection.createStatement();
    		ResultSet result = query.executeQuery("SELECT note from notation WHERE id_comics=\"" + id_commics+"\" AND username=\"" + username+"\"");
    		if (!result.next()) { // on verifie qu'une note n'a pas déjà été donnée par un user
    			this.myConnection.setAutoCommit(false);
    			PreparedStatement laRequete = this.myConnection.prepareStatement("INSERT INTO notation (id_comics, username, note) VALUES (?, ?, ?)");
		
    			laRequete.setString(1, id_commics);
    			laRequete.setString(2, username);
    			laRequete.setDouble(3, note);
    			laRequete.execute();
		 
    			myConnection.commit();
    			laRequete.close();
    		}
    		else {
    			this.myConnection.setAutoCommit(false);
    			PreparedStatement laRequete = this.myConnection.prepareStatement("UPDATE notation SET note=? WHERE username=\""+username+"\" AND id_comics=\"" +id_commics+"\"");
    			
    			laRequete.setDouble(1, note);
    			
    			laRequete.execute();
    			myConnection.commit();
    			laRequete.close();
    		}
    	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
    	}
    }
    
    public double getNotation(String id_commics) {
    	try {
    		Statement query = this.myConnection.createStatement();
    		ResultSet result = query.executeQuery("SELECT note from notation WHERE id_comics=\"" + id_commics+"\"");
    		System.out.println(id_commics);
    		double global_notation = 0;
    		int i=0;
    		while (result.next()) {
    			global_notation= result.getDouble(1) + global_notation;

    			i++;
    		}
    		if(i>0) {
        		global_notation=global_notation/i;
    		}
    		
    		
    		query.close();
    		return global_notation;
    		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
    }

    
    
    /**
     * Terminate the connection, if any
     */
    public void close() {
    	try {
			this.myConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
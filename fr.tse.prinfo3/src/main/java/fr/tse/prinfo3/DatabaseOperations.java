package fr.tse.prinfo3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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


	/**
     * FIXME Describe what this query does
     *
     * @return describe the returned value
     */
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
	    	close();
	    	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
package fr.tse.prinfo3.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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


	
	public void insertUser(String email, String password, String username) {
		try {
			this.myConnection.setAutoCommit(false);
			PreparedStatement laRequete = this.myConnection.prepareStatement("INSERT INTO user VALUE (0, ?, ?, ?, ?)");
			
			 laRequete.setString(1, email);
			 laRequete.setString(2, password);
			 laRequete.setString(3, username);
			 laRequete.setString(4, "");
			 laRequete.execute();
			 
			myConnection.commit();
			laRequete.close();
			
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertComicsUser(int id, String idComics) {
		boolean alreadyInDb = false;
		String biblio = selectBibliotheque(id);

		String[] Comics = biblio.split(",");
		for (String oneComics : Comics) {
			
			if(oneComics.compareTo(idComics)==0) {
				alreadyInDb = true;
			}
        }
		
		if(alreadyInDb) {
			System.out.println("Deja en bdd");
		}else {
			try {
			
				String newBiblio ="";
				if(biblio.compareTo("")==0){
					newBiblio = idComics;
				}else {
					newBiblio = biblio+","+idComics;
				}
				
				
				this.myConnection.setAutoCommit(false);
				PreparedStatement laRequete = this.myConnection.prepareStatement("UPDATE user SET bibliotheque=? WHERE id=?");
				
				 laRequete.setString(1, newBiblio);
				 laRequete.setInt(2, id);
				 laRequete.execute();
				 
				myConnection.commit();
				laRequete.close();
				
				
				
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void deleteComicsUser(int id, String idComics) {
		
		String biblio = selectBibliotheque(id);

		String[] Comics = biblio.split(",");
		System.out.println(Comics);
		System.out.println(idComics);
			try {
				String newBiblio ="";
				for (String oneComics : Comics) {
					
					if(oneComics.compareTo(idComics)!=0) {
						if(biblio.compareTo("")==0){
							newBiblio = oneComics;
						}else {
							newBiblio = newBiblio+","+oneComics;
						}
					}
		        }
			
				this.myConnection.setAutoCommit(false);
				PreparedStatement laRequete = this.myConnection.prepareStatement("UPDATE user SET bibliotheque=? WHERE id=?");
				
				 laRequete.setString(1, newBiblio);
				 laRequete.setInt(2, id);
				 laRequete.execute();
				 
				myConnection.commit();
				laRequete.close();
				
				
				
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public String selectBibliotheque(int id) {

		String bibliothequeComics = null;
		
    	try {
    		Statement query = this.myConnection.createStatement();
    		ResultSet result = query.executeQuery("SELECT bibliotheque from user where id="+id);
    		
    		while (result.next()) {
		    	bibliothequeComics = result.getString(1);
		    }
    		
    		query.close();
    		
    		
	    	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return bibliothequeComics;
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
		    	String bilbio = result.getString(5);
		    	System.out.println(key);
		    	System.out.println(email);
		    	System.out.println(password);
		    	System.out.println(username);
		    	System.out.println(bilbio);
		    }
    		
    		query.close();
	    	
			
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
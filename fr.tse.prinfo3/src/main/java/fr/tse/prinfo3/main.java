package fr.tse.prinfo3;






import fr.tse.prinfo3.view.openMenuFXML;
import javafx.application.Application;

public class main {


	public static void main(String[] args) {
		/*String hostname = "localhost";
		String db = "comicunivers";
		String port = "3306";
		String username = "root";
		String password = "";
		DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);
		dbComic.selectAllUser();
		dbComic.insertUser("lardon.adrian@gmail.com", "1234", "Adrian");
		dbComic.selectAllUser();
		dbComic.close();
*/
		
		
		

		Application.launch(openMenuFXML.class, args);
	}

}

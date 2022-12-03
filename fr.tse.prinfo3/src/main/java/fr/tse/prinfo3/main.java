package fr.tse.prinfo3;

import fr.tse.prinfo3.control.Controller;
import fr.tse.prinfo3.model.Model;
import fr.tse.prinfo3.view.ViewMenu;
// import fr.tse.prinfo3.view.TemperatureVueCelsius;
public class main {

	public main() {
		Model tempmod = new Model();
		Controller tempcontrolMenu = new Controller(tempmod);
		//Controller tempcontrolF = new Controller(tempmod);
		ViewMenu pvc = new ViewMenu(tempmod, tempcontrolMenu, 100, 100);
		//TemperatureVueFarenheit tvf = new TemperatureVueFarenheit(tempmod, tempcontrolF, 100, 350);
		tempcontrolMenu.setView(pvc);
		//tempcontrolF.setView(tvf);
	    }

	public static void main(String[] args) {
		System.out.println("hello project info3");
		String hostname = "localhost";
		String db = "comicunivers";
		String port = "3306";
		String username = "root";
		String password = "";
		DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);
		dbComic.selectAllUser();
		dbComic.insertUser("lardon.adrian@gmail.com", "1234", "Adrian");
		dbComic.selectAllUser();
		dbComic.close();
		new main();
	}

}

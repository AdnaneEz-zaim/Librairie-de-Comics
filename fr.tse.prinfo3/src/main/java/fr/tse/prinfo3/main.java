package fr.tse.prinfo3;

import fr.tse.prinfo3.control.ComicVineService;
import fr.tse.prinfo3.model.Issue;
import fr.tse.prinfo3.model.SearchResultDto;



import fr.tse.prinfo3.control.DatabaseOperations;

import fr.tse.prinfo3.view.openMenuFXML;

public class main {


	public static void main(String[] args) {
		String hostname = "localhost";
		String db = "comicunivers";
		String port = "3306";
		String username = "root";
		String password = "";
		DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);
		//dbComic.selectAllUser();
		//dbComic.insertUser("lardon.adrian@gmail.com", "1234", "Adrian");
		//dbComic.selectAllUser();
		dbComic.close();

		
		ComicVineService comicVineService = new ComicVineService();
		//SearchResultDto result = comicVineService.search("batman", 10, 0);
		SearchResultDto result2 = comicVineService.searchLastesComics(30, 0);

		/*for (Issue res : result.getResults()) {
			System.out.println(res);
		}
		*/
		for (Issue res2 : result2.getResults()) {
			System.out.println(res2);
			
		}
		
		
		openMenuFXML.main(args);
	}

}

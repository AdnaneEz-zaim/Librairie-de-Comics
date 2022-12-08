package fr.tse.prinfo3;
import fr.tse.prinfo3.control.ComicVineService;
import fr.tse.prinfo3.model.Issue;
import fr.tse.prinfo3.model.SearchResultDto;

// import fr.tse.prinfo3.view.TemperatureVueCelsius;
public class main {
	public static void main(String[] args) {
		System.out.println("hello project info3");
		String hostname = "localhost";
		String db = "comicunivers";
		String port = "3306";
		String username = "root";
		String password = "";
		DatabaseOperations dbComic = new DatabaseOperations(hostname, db, port, username, password);
		dbComic.selectAllUser();
		//dbComic.insertUser("lardon.adrian@gmail.com", "1234", "Adrian");
		//dbComic.selectAllUser();
		dbComic.close();
		
		ComicVineService comicVineService = new ComicVineService();
		SearchResultDto result = comicVineService.search("batman", 10, 0);
		
//		result.getResults().stream().forEach(System.out::println);
		for (Issue res : result.getResults()) {
			System.out.println(res);
		}
		
	}

}

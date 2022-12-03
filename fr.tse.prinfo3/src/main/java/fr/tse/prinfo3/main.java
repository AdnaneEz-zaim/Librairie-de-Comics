package fr.tse.prinfo3;


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
		
	}

}

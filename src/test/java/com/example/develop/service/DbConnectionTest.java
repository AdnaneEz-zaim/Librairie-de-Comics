package com.example.develop.service;

import org.junit.After;

import java.sql.*;

import static org.junit.Assert.*;

public class DbConnectionTest {


    DbConnection dbTest = new DbConnection();


    @After
    public void close() {
        if (dbTest != null) {
            dbTest.close();
        }
    }

    @org.junit.Test
    public void getUserById() throws SQLException {
        dbTest.setMyConnection_test();

        ResultSet usernameRs = dbTest.getUserById(1);
        usernameRs.beforeFirst();
        String username = null;
        while(usernameRs.next())
            username = usernameRs.getString("username");
        assertEquals("Adrian", username);
    }

    @org.junit.Test
    public void signUp() {
        try {
            Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/comicunivers_test", "root", "");
            myConnection.setAutoCommit(false);
            // Insérer un utilisateur de test
            dbTest.setMyConnection_test();
            int i = dbTest.SignUp("Test","Test","test@test.com","Test","test123");

            Statement laRequete = myConnection.createStatement();
            ResultSet result = laRequete.executeQuery("SELECT email, password, username from users WHERE username='Test'");
            if(result.next()){
                assertEquals("test@test.com", result.getString("email"));
                assertEquals("test123", result.getString("password"));
                assertEquals("Test", result.getString("username"));
            }
            else {
                System.out.println("Aucun résultat");
            }

            // Supprimer l'utilisateur de test
            PreparedStatement deleteQuery = myConnection.prepareStatement("DELETE FROM users WHERE username = 'Test'");
            deleteQuery.execute();
            myConnection.commit();
            deleteQuery.close();
            laRequete.close();


        }catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


/* #Need USERID
    @org.junit.Test
    public void getRating() {
        dbTest.setMyConnection_test();
        ResultSet rs = null;
        ResultSet rs2 = null;
        double note = 0;
        double note2 = 0;
        double noteEx = 3.49;
        double note2Ex = 2.78;
        try {
            rs = dbTest.getRating("963819");
            if(rs.next()){
                note = rs.getDouble("rating");
            }
            assertEquals(noteEx, note, 0.00);

            rs2 = dbTest.getRating("963614");
            if(rs2.next()){
                note2 = rs2.getDouble("rating");
            }
            assertEquals(note2Ex, note2, 0.00);
        }catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }*/


    /*
    *
    * OLDER TestUnit:
    *
    */


    /*
@Test
	void test_InsertUser() {
		try {
			// Créer une connexion de test
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/comicunivers_test", "root", "");

			// Insérer un utilisateur de test
			dbTest.setMyConnection_test();
			dbTest.insertUser("test@test.com", "test123", "Test");

			// Vérifier que l'utilisateur a été inséré avec les bonnes informations
			Statement laRequete = myConnection.createStatement();
			ResultSet result = laRequete.executeQuery("SELECT email, password, username from user WHERE username='Test'");
			if(result.next()){
		        assertEquals("test@test.com", result.getString("email"));
		        assertEquals("test123", result.getString("password"));
		        assertEquals("Test", result.getString("username"));
		    }
		    else {
		        System.out.println("Aucun résultat");
		    }
			// Supprimer l'utilisateur de test
			PreparedStatement deleteQuery = myConnection.prepareStatement("DELETE FROM user WHERE username = 'Test'");
			deleteQuery.execute();
			myConnection.commit();
			deleteQuery.close();
			laRequete.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void test_insertComicsUser() {
		try {
			// Créer une connexion de test
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/comicunivers_test", "root", "");

			// Insérer un utilisateur de test
			dbTest.setMyConnection_test();
			dbTest.insertComicsUser(3,"963942");

			// Vérifier que l'utilisateur a été inséré avec les bonnes informations
			Statement laRequete = myConnection.createStatement();
			ResultSet result = laRequete.executeQuery("SELECT bibliotheque from user WHERE id= 3");
			if(result.next()){
		        assertEquals("963942,", result.getString("bibliotheque"));
		    }
		    else {
		        System.out.println("Aucun résultat");
		    }
			// Supprimer l'utilisateur de test
			PreparedStatement deleteQuery = myConnection.prepareStatement("UPDATE user SET bibliotheque='' WHERE id=3");
			deleteQuery.execute();
			myConnection.commit();
			deleteQuery.close();
			laRequete.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void test_insertComicsAlire(){
		try {
			// Créer une connexion de test
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/comicunivers_test", "root", "");

			// Insérer un utilisateur de test
			dbTest.setMyConnection_test();
			dbTest.insertComicsAlire(3,"963942");

			// Vérifier que l'utilisateur a été inséré avec les bonnes informations
			Statement laRequete = myConnection.createStatement();
			ResultSet result = laRequete.executeQuery("SELECT comicsAlire from user WHERE id= 3");
			if(result.next()){
		        assertEquals("963942,", result.getString("comicsAlire"));
		    }
		    else {
		        System.out.println("Aucun résultat");
		    }
			// Supprimer l'utilisateur de test
			PreparedStatement deleteQuery = myConnection.prepareStatement("UPDATE user SET comicsAlire='' WHERE id=3");
			deleteQuery.execute();
			myConnection.commit();
			deleteQuery.close();
			laRequete.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void test_insertComicsEnCours(){
		try {
			// Créer une connexion de test
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/comicunivers_test", "root", "");

			// Insérer un utilisateur de test
			dbTest.setMyConnection_test();
			dbTest.insertComicsEnCours(3,"963942");

			// Vérifier que l'utilisateur a été inséré avec les bonnes informations
			Statement laRequete = myConnection.createStatement();
			ResultSet result = laRequete.executeQuery("SELECT comicsEnCours from user WHERE id= 3");
			if(result.next()){
		        assertEquals("963942,", result.getString("comicsEnCours"));
		    }
		    else {
		        System.out.println("Aucun résultat");
		    }
			// Supprimer l'utilisateur de test
			PreparedStatement deleteQuery = myConnection.prepareStatement("UPDATE user SET comicsEnCours='' WHERE id=3");
			deleteQuery.execute();
			myConnection.commit();
			deleteQuery.close();
			laRequete.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void test_insertComicsLu(){
		try {
			// Créer une connexion de test
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/comicunivers_test", "root", "");

			// Insérer un utilisateur de test
			dbTest.setMyConnection_test();
			dbTest.insertComicsLu(3,"963942");

			// Vérifier que l'utilisateur a été inséré avec les bonnes informations
			Statement laRequete = myConnection.createStatement();
			ResultSet result = laRequete.executeQuery("SELECT comicsLu from user WHERE id= 3");
			if(result.next()){
		        assertEquals("963942,", result.getString("comicsLu"));
		    }
		    else {
		        System.out.println("Aucun résultat");
		    }
			// Supprimer l'utilisateur de test
			PreparedStatement deleteQuery = myConnection.prepareStatement("UPDATE user SET comicsLu='' WHERE id=3");
			deleteQuery.execute();
			myConnection.commit();
			deleteQuery.close();
			laRequete.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void test_deleteComicsUser(){
		try {
			// Créer une connexion de test
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/comicunivers_test", "root", "");

			// Insérer un utilisateur de test
			dbTest.setMyConnection_test();
			dbTest.insertComicsUser(3,"963942");
			dbTest.deleteComicsUser(3, "963942");
			// Vérifier que l'utilisateur a été inséré avec les bonnes informations
			Statement laRequete = myConnection.createStatement();
			ResultSet result = laRequete.executeQuery("SELECT bibliotheque from user WHERE id= 3");
			if(result.next()){
		        assertEquals("", result.getString("bibliotheque"));
		    }
		    else {
		        System.out.println("Aucun résultat");
		    }
			// Supprimer l'utilisateur de test
			laRequete.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void test_deleteComicsLu() {
		try {
			// Créer une connexion de test
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/comicunivers_test", "root", "");

			// Insérer un utilisateur de test
			dbTest.setMyConnection_test();
			dbTest.insertComicsLu(3,"963942");
			dbTest.deleteComicsLu(3, "963942");
			// Vérifier que l'utilisateur a été inséré avec les bonnes informations
			Statement laRequete = myConnection.createStatement();
			ResultSet result = laRequete.executeQuery("SELECT comicsLu from user WHERE id= 3");
			if(result.next()){
		        assertEquals("", result.getString("comicsLu"));
		    }
		    else {
		        System.out.println("Aucun résultat");
		    }
			// Supprimer l'utilisateur de test
			laRequete.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void test_deleteComicsAlire() {
		try {
			// Créer une connexion de test
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/comicunivers_test", "root", "");

			// Insérer un utilisateur de test
			dbTest.setMyConnection_test();
			dbTest.insertComicsAlire(3,"963942");
			dbTest.deleteComicsAlire(3, "963942");
			// Vérifier que l'utilisateur a été inséré avec les bonnes informations
			Statement laRequete = myConnection.createStatement();
			ResultSet result = laRequete.executeQuery("SELECT comicsAlire from user WHERE id= 3");
			if(result.next()){
		        assertEquals("", result.getString("comicsAlire"));
		    }
		    else {
		        System.out.println("Aucun résultat");
		    }
			// Supprimer l'utilisateur de test
			laRequete.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void test_deleteComicsEnCours() {
		try {
			// Créer une connexion de test
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/comicunivers_test", "root", "");

			// Insérer un utilisateur de test
			dbTest.setMyConnection_test();
			dbTest.insertComicsEnCours(3,"963942");
			dbTest.deleteComicsEnCours(3, "963942");
			// Vérifier que l'utilisateur a été inséré avec les bonnes informations
			Statement laRequete = myConnection.createStatement();
			ResultSet result = laRequete.executeQuery("SELECT comicsEnCours from user WHERE id= 3");
			if(result.next()){
		        assertEquals("", result.getString("comicsEnCours"));
		    }
		    else {
		        System.out.println("Aucun résultat");
		    }
			// Supprimer l'utilisateur de test
			laRequete.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void test_selectComicsLu() {
		dbTest.setMyConnection_test();
		assertEquals("964211,", dbTest.selectComicsLu(1));
	}

	@Test
	void test_selectComicsAlire() {
		dbTest.setMyConnection_test();
		assertEquals("963940,", dbTest.selectComicsAlire(1));
	}

	@Test
	void test_selectComicsEnCours() {
		dbTest.setMyConnection_test();
		assertEquals("", dbTest.selectComicsEnCours(1));
	}


	@Test
	void test_getUsernameId() {
		dbTest.setMyConnection_test();
		assertEquals("Adrian", dbTest.getUsernameId("1"));
	}

	@Test
	void test_selectAllComments() {
		try {
			// Créer une connexion de test
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/comicunivers_test", "root", "");

			// Insérer un utilisateur de test
			dbTest.setMyConnection_test();
			dbTest.selectAllComments("964211");

			// Vérifier que l'utilisateur a été inséré avec les bonnes informations
			Statement laRequete = myConnection.createStatement();
			ResultSet result = laRequete.executeQuery("SELECT comment from commentaire WHERE id_comics = '964211'");
			List<String> comment = new ArrayList<String>();
			while (result.next()) {
	    		comment.add(result.getString("comment"));
	    		System.out.println(result.getString("comment"));
			}
		    assertEquals("Test",comment.get(0));
		    assertEquals("salut",comment.get(1));

			// Supprimer l'utilisateur de test
			laRequete.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void test_insertComment() {
		try {
			// Créer une connexion de test
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/comicunivers_test", "root", "");

			// Insérer un utilisateur de test
			dbTest.setMyConnection_test();
			dbTest.insertComment("Ceci est un Test","963942","Test");

			// Vérifier que l'utilisateur a été inséré avec les bonnes informations
			Statement laRequete = myConnection.createStatement();
			ResultSet result = laRequete.executeQuery("SELECT comment from commentaire WHERE username = 'Test'");
			if(result.next()){
		        assertEquals("Ceci est un Test", result.getString("comment"));
		    }
		    else {
		        System.out.println("Aucun résultat");
		    }
			// Supprimer l'utilisateur de test
			PreparedStatement deleteQuery = myConnection.prepareStatement("DELETE FROM commentaire WHERE username = 'Test'");
			deleteQuery.execute();
			myConnection.commit();
			deleteQuery.close();
			laRequete.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void test_insertNotation() {
		try {
			// Créer une connexion de test
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/comicunivers_test", "root", "");

			// Insérer un utilisateur de test
			dbTest.setMyConnection_test();
			dbTest.insertNotation(3,"963942","Test");

			// Vérifier que l'utilisateur a été inséré avec les bonnes informations
			Statement laRequete = myConnection.createStatement();
			ResultSet result = laRequete.executeQuery("SELECT note from notation WHERE username = 'Test'");
			if(result.next()){
		        assertEquals(3.0, result.getDouble("note"));
		    }
		    else {
		        System.out.println("Aucun résultat");
		    }
			// Supprimer l'utilisateur de test
			PreparedStatement deleteQuery = myConnection.prepareStatement("DELETE FROM notation WHERE username = 'Test'");
			deleteQuery.execute();
			myConnection.commit();
			deleteQuery.close();
			laRequete.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void test_getNotation() {
		dbTest.setMyConnection_test();
		assertEquals(3.56249982660467, dbTest.getNotation("4000-963819"));
		assertEquals(2, dbTest.getNotation("123456"));
	}


}


     */

}
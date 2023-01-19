package com.example.develop.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import com.example.develop.ComicApplication;
import com.example.develop.helper.AlertHelper;
import com.example.develop.service.ComicVineService;
import com.example.develop.model.*;
import com.example.develop.model.Character;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.Rating;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;
public class ComicsController implements Initializable {
	private static CompletableFuture<JsonNode> future = null;
	private final Connection con;
	private final int userId= UserModel.getUserModel().getUserid();
	private String comicId = ObjectClicked.getObjectClicked().getId();
	private Window window;
	@FXML
	private Label descComic;
	@FXML
	private Text nameComic;
	@FXML
	private ImageView imgComic;
	 @FXML
	 private ListView<Author> listOfAuthors = new ListView<Author>();
	 @FXML
	 private ListView<Character> listOfCharacters = new ListView<Character>();
	 @FXML
	 private TextArea commentArea;
	 @FXML
	 private ListView<Comment> listOfComments = new ListView<Comment>();
	 @FXML
	 private Rating rateComics;
	 @FXML
	 private Text averageRating;

	public ComicsController() throws IOException {
		DbConnection dbc = DbConnection.getDatabaseConnection();
		 con = dbc.getConnection();
	 }

	public void initCharacters(){
		future.thenAccept(comic -> Platform.runLater(()-> {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode characters = null;
			try {
				characters = objectMapper.readTree(comic.get("character_credits").toString());
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}

			ObservableList<Character> items = FXCollections.observableArrayList ();
			for (JsonNode item : characters) {
				if(item.get("name").textValue() != null){
					Character character = new Character();
					character.setName(item.get("name").textValue() );
					character.setId(String.valueOf(item.get("id")));
					character.setImage("");
					items.add(character);
				}
			}

			listOfCharacters.setItems(items);
			listOfCharacters.setCellFactory(param -> new ListCell<Character>() {
				@Override
				protected void updateItem(Character character, boolean empty) {
					super.updateItem(character, empty);
					if (empty) {
						setText(null);
						setGraphic(null);
					} else {
						setText(getItem().getName());
					}
				}
			});
		}));
	}
	public void initCreators(){
		future.thenAccept(comic ->Platform.runLater(()->{
			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode authors = null;
			try {
				authors = objectMapper.readTree(comic.get("person_credits").toString());
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}

			ObservableList<Author> items = FXCollections.observableArrayList ();
			for (JsonNode item : authors) {
				if(item.get("name").textValue() != null){
					Author author = new Author();
					author.setName(item.get("name").textValue() );
					author.setId(String.valueOf(item.get("id")));
					author.setImage("");
					items.add(author);
				}
			}

			listOfAuthors.setItems(items);
			listOfAuthors.setCellFactory(param -> new ListCell<Author>() {
				@Override
				protected void updateItem(Author author, boolean empty) {
					super.updateItem(author, empty);
					if (empty) {
						setText(null);
						setGraphic(null);
					} else {
						setText(getItem().getName());
					}
				}
			});
		}));

	}
	public void initComicInfo() throws IOException {
		ComicVineService comicVineService = new ComicVineService();
		future = comicVineService.GetComicById(comicId);
		future.thenAccept(comic -> Platform.runLater(() -> {
			imgComic.setImage(new Image(comic.get("image").get("thumb_url").textValue()));
			nameComic.setText(comic.get("name").textValue());
			if (comic.get("description").textValue() == null)
				descComic.setText("this is issue #" + comic.get("issue_number").textValue() + " of" + comic.get("volume").get("name").textValue() + " volume");
			else
				descComic.setText(comic.get("description").textValue().replaceAll("<[^>]*>", ""));
		}));
		initCreators();
		initCharacters();
	}
	public ResultSet getComments(){
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
	public ResultSet getUserById(int userId){
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
	public void initComments() throws SQLException {
		ResultSet rs = getComments();
		rs.beforeFirst();
		ObservableList<Comment> items =  FXCollections.observableArrayList ();
		while (rs.next()) {

			ResultSet usernameRs = getUserById(rs.getInt("userid"));
			usernameRs.beforeFirst();
			String username = null;
			while(usernameRs.next())
				username = usernameRs.getString("username");

			Comment comment = new Comment();
			comment.setUsername(username);
			comment.setCommentContent(rs.getString("commentContent"));
			comment.setPublishDate(rs.getDate("publish_date"));
			items.add(comment);
		}

		listOfComments.setItems(items);
		listOfComments.setCellFactory(param-> new ListCell<Comment>(){

			@Override
			protected void updateItem(Comment comment, boolean empty) {
				super.updateItem(comment, empty);

				if(empty || comment == null) {
					setText(null);
					setGraphic(null);
				} else {
					try {
						FXMLLoader loader = new FXMLLoader(ComicApplication.class.getResource("Views/CommentLayout.fxml"));
						Node node = loader.load();
						Label usernameLabel = (Label) loader.getNamespace().get("username");
						Label commentLabel = (Label) loader.getNamespace().get("comment");
						Label dateLabel = (Label) loader.getNamespace().get("publishDate");

						dateLabel.setText(String.valueOf((comment.getPublishDate())));
						usernameLabel.setText(String.valueOf(comment.getUsername()));
						commentLabel.setText(comment.getCommentContent());

						setGraphic(node);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});
	}
	public void initRating(){
		ResultSet rs = null;
		try {
			PreparedStatement ps;
			String query = "select * from rating where idUser =  ? and idComic = ?";
			ps = con.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, userId);
			ps.setString(2,comicId);
			rs = ps.executeQuery();
			if(rs.next()){
				rateComics.setRating(rs.getDouble("rating"));
			}
			else{
				rateComics.setRating(0);
			}
			ps.close();
		} catch (SQLException ex) {
			System.out.println(ex);
		}
	}
	public void initAverageRating(){
		ResultSet rs = null;
		try {
			PreparedStatement ps;
			String query = "SELECT AVG(rating) FROM Rating where idComic = ?";
			ps = con.prepareStatement(query);
			ps.setString(1,comicId);
			rs = ps.executeQuery();
			if(rs.next()){
				DecimalFormat df = new DecimalFormat("#.##");
				averageRating.setText("Rating : "+ df.format(rs.getDouble(1)));
			}
			else{
				averageRating.setText("No rating available");
			}
			ps.close();
		} catch (SQLException ex) {
			System.out.println(ex);
		}


	}

	@FXML
	void addComicsToLibrary(MouseEvent event) {
		future.thenAccept(comic -> {
			try {
				Statement stmt;
				PreparedStatement ps;
				stmt = con.createStatement();
				String query = "insert into library (idUser,idComic,imageComic,nameComic)values (?,?,?,?)";
				ps = con.prepareStatement(query);
				ps.setInt(1, userId);
				ps.setString(2, comicId);

				 ps.setString(3, comic.get("image").get("thumb_url").textValue());
				 ps.setString(4,comic.get("name").textValue());

				if (ps.executeUpdate() > 0) {
				 AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information",
						 "Comic added successfuly to your library");
				} else {
				 AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
						 "Something went wrong.");
		 	}
			}catch (SQLException ex) {
			 AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
					 "Something went wrong.");
			}
		});
	}
	@FXML
	void returnHandler(MouseEvent event) throws IOException {
	 Stage stage = (Stage) rateComics.getScene().getWindow();
	 stage.close();

	 FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/MainPage.fxml"));
	 Scene scene = new Scene(fxmlLoader.load());
	 stage.setTitle("TSE ComicVine!");
	 stage.setScene(scene);
	 stage.show();

	}
	@FXML
	void publishComment(MouseEvent event) throws IOException {
		try {
			Statement stmt;
			PreparedStatement ps;
			stmt = con.createStatement();
			String query = "insert into comments (userid,idComic,commentContent)values (?,?,?)";
			ps = con.prepareStatement(query);
			ps.setInt(1, userId);
			ps.setString(2, comicId);
			ps.setString(3, commentArea.getText());
			if (ps.executeUpdate() > 0) {
				AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information",
						"Comment Added successfully");
				initComments();
			} else {
				AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
						"Something went wrong.");
			}
		}catch (SQLException ex) {
			/*AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
					"Something went wrong.");*/
			System.out.println(ex);
		}
	}
	@FXML
	void doRating(MouseEvent event) throws IOException {
	 Double note =rateComics.getRating();
	 ResultSet rs = null;
		try {
			PreparedStatement ps;
			String query = "select * from rating where idUser =  ? and idComic = ?";
			ps = con.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, userId);
			ps.setString(2,comicId);
			rs = ps.executeQuery();
			if(rs.next()){
				String query1 = "UPDATE Rating SET rating = ? WHERE idUser = ? and idComic = ?";
				ps = con.prepareStatement(query1);
				ps.setDouble(1,note);
				ps.setInt(2,userId);
				ps.setString(3,comicId);
				ps.executeUpdate();
				ps.close();
			}
			else{
				String query2 = "insert into Rating (rating,idUser,idComic)values (?,?,?)";
				ps = con.prepareStatement(query2);
				ps.setDouble(1,note);
				ps.setInt(2,userId);
				ps.setString(3,comicId);
				ps.executeUpdate();
				ps.close();
			}
			ps.close();
			initAverageRating();
		} catch (SQLException ex) {
			AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
					"Something went wrong.");
		}
	}

	@FXML
	void CharacterClicked(MouseEvent event) throws IOException {
		Boolean empty = false;
		if(	listOfCharacters.getSelectionModel().getSelectedItem() != null){
			ObjectClicked objectClicked = ObjectClicked.getObjectClicked();
			objectClicked.setId(listOfCharacters.getSelectionModel().getSelectedItem().getId());
		}
		else empty = true;

		if(!empty){
			Stage stage = (Stage) listOfCharacters.getScene().getWindow();
			stage.close();
			FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/CharacterView.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setTitle("TSE ComicVine!");
			stage.setScene(scene);
			stage.show();
		}
	}
	@FXML
	void AuthorClicked(MouseEvent event) throws IOException {
		Boolean empty = false;
		if(	listOfAuthors.getSelectionModel().getSelectedItem() != null){
			ObjectClicked objectClicked = ObjectClicked.getObjectClicked();
			objectClicked.setId(listOfAuthors.getSelectionModel().getSelectedItem().getId());
		}
		else empty = true;

		if(!empty){
			Stage stage = (Stage) listOfAuthors.getScene().getWindow();
			stage.close();

			FXMLLoader fxmlLoader = new FXMLLoader(ComicApplication.class.getResource("Views/AuthorView.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setTitle("TSE ComicVine!");
			stage.setScene(scene);
			stage.show();
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		try {
			initComicInfo();
			initComments();
			initRating();
			initAverageRating();
		} catch (IOException | SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@FXML
	void handleClickProfileImage(MouseEvent event) throws IOException {
		//TODO
	}

	
}

package fr.tse.prinfo3.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {
	
	private int id;
	private String name;
	@JsonProperty(value = "image")
	private ImagePOJO image;
	private String deck;
	private String description;
	private List<OtherCredits> issue_credits;
	
	
	
	
	public List<OtherCredits> getIssue_credits() {
		return issue_credits;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void setIssue_credits(List<OtherCredits> issue_credits) {
		this.issue_credits = issue_credits;
	}

	public ImagePOJO getImage() {
		return image;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void setImage(ImagePOJO image) {
		this.image = image;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDeck() {
		return deck;
	}
	public void setDeck(String deck) {
		this.deck = deck;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Character [id=" + id + ", name=" + name + ", image=" + image + ", deck=" + deck + ", description="
				+ description + ", issue_credits=" + issue_credits + "]";
	}
	
	
}

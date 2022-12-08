package fr.tse.prinfo3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {
	private int id;
	private String name;
	private String origin;
	private String gender;
	private String powers;
	private String deck;
	private String description;
	private String creators;
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPowers() {
		return powers;
	}
	public void setPowers(String powers) {
		this.powers = powers;
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
	public String getCreators() {
		return creators;
	}
	public void setCreators(String creators) {
		this.creators = creators;
	}
	@Override
	public String toString() {
		return "Issue [id=" + id +  ", name=" + name + ", origin="+ origin +", gender=" + gender +", powers=" + powers + ", deck=" + deck + ", description=" + description + ", creators=" + creators +"]";
	}
}

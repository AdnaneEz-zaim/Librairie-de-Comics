package fr.tse.prinfo3.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {
	
	private String aliases;
	private String api_detail_url;
	private List<String> associated_images =null;
	private List<OtherCredits> character_credits =null;
	private List<OtherCredits> character_died_in=null;
	private List<OtherCredits> concept_credits=null;
	private String cover_date;
	private String date_added;
	private String date_last_updated;
	private String deck;
	private String description;
	private String first_appearance_characters = null;
	private String first_appearance_concepts = null;
	private String first_appearance_locations = null;
	private String first_appearance_objects = null;
	private String first_appearance_storyarcs = null;
	private String first_appearance_teams = null;
	private String has_staff_review;
	@JsonProperty(value = "id")
	private int id;
	@JsonProperty(value = "image")
	private ImagePOJO image;
	private String issue_number;
	//private List<String> location_credits;
	private String name;
	//private List<String> object_credits;
	@JsonProperty(value = "person_credits")
	private List<PersonCredits> person_credits;
	private String site_detail_url;
	private String store_date;
	/*private List<String> story_arc_credits;
	private List<String> team_credits;
	private List<String> team_disbanded_in;
	*/
	private OtherCredits volume;

	
   public List<String> getAssociated_images() {
		return associated_images;
	}

	public void setAssociated_images(List<String> associated_images) {
		this.associated_images = associated_images;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public List<PersonCredits> getPerson_credits() {
		return person_credits;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void setPerson_credits(List<PersonCredits> person_credits) {
		this.person_credits = person_credits;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public ImagePOJO getImage()
    {
        return image;
    }

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    public void setImage(ImagePOJO image)
    {
        this.image = image;
    }

   /* @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public List<String> getAssociated_images() {
		return associated_images;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void setAssociated_images(List<String> associated_images) {
		this.associated_images = associated_images;
	}
*/
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public List<OtherCredits> getCharacter_credits() {
		return character_credits;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void setCharacter_credits(List<OtherCredits> character_credits) {
		this.character_credits = character_credits;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public List<OtherCredits> getCharacter_died_in() {
		return character_died_in;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void setCharacter_died_in(List<OtherCredits> character_died_in) {
		this.character_died_in = character_died_in;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public List<OtherCredits> getConcept_credits() {
		return concept_credits;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void setConcept_credits(List<OtherCredits> concept_credits) {
		this.concept_credits = concept_credits;
	}

	public String getCover_date() {
		return cover_date;
	}

	public void setCover_date(String cover_date) {
		this.cover_date = cover_date;
	}

	public String getDate_added() {
		return date_added;
	}

	public void setDate_added(String date_added) {
		this.date_added = date_added;
	}

	public String getDate_last_updated() {
		return date_last_updated;
	}

	public void setDate_last_updated(String date_last_updated) {
		this.date_last_updated = date_last_updated;
	}

	public String getDeck() {
		return deck;
	}

	public void setDeck(String deck) {
		this.deck = deck;
	}

	public String getFirst_appearance_characters() {
		return first_appearance_characters;
	}

	public void setFirst_appearance_characters(String first_appearance_characters) {
		this.first_appearance_characters = first_appearance_characters;
	}

	public String getFirst_appearance_concepts() {
		return first_appearance_concepts;
	}

	public void setFirst_appearance_concepts(String first_appearance_concepts) {
		this.first_appearance_concepts = first_appearance_concepts;
	}

	public String getFirst_appearance_locations() {
		return first_appearance_locations;
	}

	public void setFirst_appearance_locations(String first_appearance_locations) {
		this.first_appearance_locations = first_appearance_locations;
	}

	public String getFirst_appearance_objects() {
		return first_appearance_objects;
	}

	public void setFirst_appearance_objects(String first_appearance_objects) {
		this.first_appearance_objects = first_appearance_objects;
	}

	public String getFirst_appearance_storyarcs() {
		return first_appearance_storyarcs;
	}

	public void setFirst_appearance_storyarcs(String first_appearance_storyarcs) {
		this.first_appearance_storyarcs = first_appearance_storyarcs;
	}

	public String getFirst_appearance_teams() {
		return first_appearance_teams;
	}

	public void setFirst_appearance_teams(String first_appearance_teams) {
		this.first_appearance_teams = first_appearance_teams;
	}

	public String getHas_staff_review() {
		return has_staff_review;
	}

	public void setHas_staff_review(String has_staff_review) {
		this.has_staff_review = has_staff_review;
	}
	/*@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public List<String> getLocation_credits() {
		return location_credits;
	}
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void setLocation_credits(List<String> location_credits) {
		this.location_credits = location_credits;
	}
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public List<String> getObject_credits() {
		return object_credits;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void setObject_credits(List<String> object_credits) {
		this.object_credits = object_credits;
	}*/

	public String getSite_detail_url() {
		return site_detail_url;
	}

	public void setSite_detail_url(String site_detail_url) {
		this.site_detail_url = site_detail_url;
	}

	public String getStore_date() {
		return store_date;
	}

	public void setStore_date(String store_date) {
		this.store_date = store_date;
	}

   /* @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public List<String> getStory_arc_credits() {
		return story_arc_credits;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void setStory_arc_credits(List<String> story_arc_credits) {
		this.story_arc_credits = story_arc_credits;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public List<String> getTeam_credits() {
		return team_credits;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void setTeam_credits(List<String> team_credits) {
		this.team_credits = team_credits;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public List<String> getTeam_disbanded_in() {
		return team_disbanded_in;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void setTeam_disbanded_in(List<String> team_disbanded_in) {
		this.team_disbanded_in = team_disbanded_in;
	}
*/
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public OtherCredits getVolume() {
		return volume;
	}

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void setVolume(OtherCredits volume) {
		this.volume = volume;
	}

	public String getAliases() {
		return aliases;
	}
	public void setAliases(String aliases) {
		this.aliases = aliases;
	}
	public String getApi_detail_url() {
		return api_detail_url;
	}
	public void setApi_detail_url(String api_detail_url) {
		this.api_detail_url = api_detail_url;
	}
	
	public String getIssue_number() {
		return issue_number;
	}
	public void setIssue_number(String issue_number) {
		this.issue_number = issue_number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Issue [aliases=" + aliases + ", api_detail_url=" + api_detail_url + ", associated_images="
				+ associated_images + ", character_credits=" + character_credits + ", character_died_in="
				+ character_died_in + ", concept_credits=" + concept_credits + ", cover_date=" + cover_date
				+ ", date_added=" + date_added + ", date_last_updated=" + date_last_updated + ", deck=" + deck
				+ ", description=" + description + ", first_appearance_characters=" + first_appearance_characters
				+ ", first_appearance_concepts=" + first_appearance_concepts + ", first_appearance_locations="
				+ first_appearance_locations + ", first_appearance_objects=" + first_appearance_objects
				+ ", first_appearance_storyarcs=" + first_appearance_storyarcs + ", first_appearance_teams="
				+ first_appearance_teams + ", has_staff_review=" + has_staff_review + ", id=" + id + ", image=" + image
				+ ", issue_number=" + issue_number + ", name=" + name + ", person_credits=" + person_credits
				+ ", site_detail_url=" + site_detail_url + ", store_date=" + store_date + ", volume=" + volume + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(aliases, api_detail_url, associated_images, character_credits, character_died_in,
				concept_credits, cover_date, date_added, date_last_updated, deck, description,
				first_appearance_characters, first_appearance_concepts, first_appearance_locations,
				first_appearance_objects, first_appearance_storyarcs, first_appearance_teams, has_staff_review, id,
				image, issue_number, name, person_credits, site_detail_url, store_date, volume);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Issue other = (Issue) obj;
		return Objects.equals(aliases, other.aliases) && Objects.equals(api_detail_url, other.api_detail_url)
				&& Objects.equals(associated_images, other.associated_images)
				&& Objects.equals(character_credits, other.character_credits)
				&& Objects.equals(character_died_in, other.character_died_in)
				&& Objects.equals(concept_credits, other.concept_credits)
				&& Objects.equals(cover_date, other.cover_date) && Objects.equals(date_added, other.date_added)
				&& Objects.equals(date_last_updated, other.date_last_updated) && Objects.equals(deck, other.deck)
				&& Objects.equals(description, other.description)
				&& Objects.equals(first_appearance_characters, other.first_appearance_characters)
				&& Objects.equals(first_appearance_concepts, other.first_appearance_concepts)
				&& Objects.equals(first_appearance_locations, other.first_appearance_locations)
				&& Objects.equals(first_appearance_objects, other.first_appearance_objects)
				&& Objects.equals(first_appearance_storyarcs, other.first_appearance_storyarcs)
				&& Objects.equals(first_appearance_teams, other.first_appearance_teams)
				&& Objects.equals(has_staff_review, other.has_staff_review) && id == other.id
				&& Objects.equals(image, other.image) && Objects.equals(issue_number, other.issue_number)
				&& Objects.equals(name, other.name) && Objects.equals(person_credits, other.person_credits)
				&& Objects.equals(site_detail_url, other.site_detail_url)
				&& Objects.equals(store_date, other.store_date) && Objects.equals(volume, other.volume);
	}
	
	

	
	
}

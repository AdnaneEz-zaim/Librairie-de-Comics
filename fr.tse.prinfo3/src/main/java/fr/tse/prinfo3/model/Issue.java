package fr.tse.prinfo3.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {
	
	private String aliases;
	private String api_detail_url;
	private int id;
	@JsonProperty(value = "image")
	private ImagePOJO image;
	private String issue_number;
	private String name;
	
	
	public ImagePOJO getImage()
    {
        return image;
    }

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    public void setImage(ImagePOJO image)
    {
        this.image = image;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		return "Issue [aliases=" + aliases + ", api_detail_url=" + api_detail_url + ", id=" + id + ", issue_number="
				+ issue_number + ", name=" + name + ", image=" + image + "]";
	}
}

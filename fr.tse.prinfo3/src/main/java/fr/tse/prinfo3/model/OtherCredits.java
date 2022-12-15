package fr.tse.prinfo3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherCredits {
	private String api_detail_url;
	private String id;
	private String name;
	private String site_detail_url;
	
	
	public String getApi_detail_url() {
		return api_detail_url;
	}
	public void setApi_detail_url(String api_detail_url) {
		this.api_detail_url = api_detail_url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSite_detail_url() {
		return site_detail_url;
	}
	public void setSite_detail_url(String site_detail_url) {
		this.site_detail_url = site_detail_url;
	}
	
	@Override
	public String toString() {
		return "PersonCredits [api_detail_url=" + api_detail_url + ", id=" + id + ", name=" + name
				+ ", site_detail_url=" + site_detail_url + "]";
	}

}

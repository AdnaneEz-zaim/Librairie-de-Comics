package fr.tse.prinfo3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultIssue {

	private int limit;
	private int offset;
	private int number_of_page_results;
	private int number_of_total_results;
	
	private Issue results;
	public Issue getResults() {
		
		// TODO Auto-generated method stub
		return this.results;
	}
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getNumber_of_page_results() {
		return number_of_page_results;
	}
	public void setNumber_of_page_results(int number_of_page_results) {
		this.number_of_page_results = number_of_page_results;
	}
	public int getNumber_of_total_results() {
		return number_of_total_results;
	}
	public void setNumber_of_total_results(int number_of_total_results) {
		this.number_of_total_results = number_of_total_results;
	}
	public void setResults(Issue results) {
		this.results = results;
	}
	
	@Override
	public String toString() {
		return "SearchResultDto [limit=" + limit + ", offset=" + offset + ", number_of_page_results="
				+ number_of_page_results + ", number_of_total_results=" + number_of_total_results + ", results="
				+ results + "]";
	}
	
}

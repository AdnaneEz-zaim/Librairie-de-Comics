package fr.tse.prinfo3.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultDto {

	
	private int limit;
	private int offset;
	private int number_of_page_results;
	private int number_of_total_results;
	
	private List<Issue> results;
	
	
	
	
	public List<Issue> getResults() {
		
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
	public void setResults(List<Issue> results) {
		this.results = results;
	}
	
	@Override
	public String toString() {
		return "SearchResultDto [limit=" + limit + ", offset=" + offset + ", number_of_page_results="
				+ number_of_page_results + ", number_of_total_results=" + number_of_total_results + ", results="
				+ results + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(limit, number_of_page_results, number_of_total_results, offset, results);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchResultDto other = (SearchResultDto) obj;
		return limit == other.limit && number_of_page_results == other.number_of_page_results
				&& number_of_total_results == other.number_of_total_results && offset == other.offset
				&& Objects.equals(results, other.results);
	}
	
}

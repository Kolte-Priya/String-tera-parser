package com.example.demo;

import java.util.List;

public class resultObject {

	private String keyword;
	private List<String> relatedWords;
	public resultObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public List<String> getRelatedWords() {
		return relatedWords;
	}
	public void setRelatedWords(List<String> relatedWords) {
		this.relatedWords = relatedWords;
	}
	public resultObject(String keyword, List<String> relatedWords) {
		super();
		this.keyword = keyword;
		this.relatedWords = relatedWords;
	}
	
	
	
	
}

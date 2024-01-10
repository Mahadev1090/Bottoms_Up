package com.ins.web.vo;

public class SearchRequest {
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	private String name;
	private String company;
	private String title;
	private String status;
	
	private String sortField;
    private String sortOrder;
    private int limit;
    
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public SearchRequest(Long id, String name, String company, String title, String status) {
		super();
		this.id = id;
		this.name = name;
		this.company = company;
		this.title = title;
		this.status = status;
	}
	public SearchRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

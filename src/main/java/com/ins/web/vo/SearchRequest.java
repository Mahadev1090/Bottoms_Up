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
	private String type;
	private String location;
	
	private String projectName;
	private String accountType;
	private String projectApprovedCapex;
	private String projectApprovedOpex;
	private String projectAFENum;
	private Long projectKey;
	private Long projectId;
	
	private String sortField;
    private String sortOrder;
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	private int startIndex;
    private int endIndex;
    
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getProjectApprovedCapex() {
		return projectApprovedCapex;
	}
	public void setProjectApprovedCapex(String projectApprovedCapex) {
		this.projectApprovedCapex = projectApprovedCapex;
	}
	public String getProjectApprovedOpex() {
		return projectApprovedOpex;
	}
	public void setProjectApprovedOpex(String projectApprovedOpex) {
		this.projectApprovedOpex = projectApprovedOpex;
	}
	public String getProjectAFENum() {
		return projectAFENum;
	}
	public void setProjectAFENum(String projectAFENum) {
		this.projectAFENum = projectAFENum;
	}
	public Long getProjectKey() {
		return projectKey;
	}
	public void setProjectKey(Long projectKey) {
		this.projectKey = projectKey;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
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
	
	public SearchRequest(Long id, String name, String company, String title, String status, String type,
			String location, String projectName, String accountType, String projectApprovedCapex,
			String projectApprovedOpex, String projectAFENum, Long projectKey, Long projectId, String sortField,
			String sortOrder, int startIndex, int endIndex) {
		super();
		this.id = id;
		this.name = name;
		this.company = company;
		this.title = title;
		this.status = status;
		this.type = type;
		this.location = location;
		this.projectName = projectName;
		this.accountType = accountType;
		this.projectApprovedCapex = projectApprovedCapex;
		this.projectApprovedOpex = projectApprovedOpex;
		this.projectAFENum = projectAFENum;
		this.projectKey = projectKey;
		this.projectId = projectId;
		this.sortField = sortField;
		this.sortOrder = sortOrder;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	public SearchRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

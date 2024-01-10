package com.ins.web.vo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="projects")
public class MasterProjectVo {public MasterProjectVo() {
}
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String status;

	private Long projectKey;

	private String projectName;

	private String projectDescription;

	private String accountType;

	private String projectAFENum;

	private String projectApprovedCapex;

	private String projectApprovedOpex;

	private String startDate;

	private String endDate;

	private String projectManger;

	private String createdBy;

	private String createdOn;

	private String updatedOn;

	private String updatedBy;
	
	@OneToMany(mappedBy = "masterProject", cascade = CascadeType.ALL)
	private List<MasterUserVo> masterUsers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(Long projectKey) {
		this.projectKey = projectKey;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getProjectAFENum() {
		return projectAFENum;
	}

	public void setProjectAFENum(String projectAFENum) {
		this.projectAFENum = projectAFENum;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getProjectManger() {
		return projectManger;
	}

	public void setProjectManger(String projectManger) {
		this.projectManger = projectManger;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<MasterUserVo> getMasterUsers() {
		return masterUsers;
	}

	public void setMasterUsers(List<MasterUserVo> masterUsers) {
		this.masterUsers = masterUsers;
	}

	public MasterProjectVo(Long id, String status, Long projectKey, String projectName, String projectDescription,
			String accountType, String projectAFENum, String projectApprovedCapex, String projectApprovedOpex,
			String startDate, String endDate, String projectManger, String createdBy, String createdOn,
			String updatedOn, String updatedBy, List<MasterUserVo> masterUsers) {
		super();
		this.id = id;
		this.status = status;
		this.projectKey = projectKey;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.accountType = accountType;
		this.projectAFENum = projectAFENum;
		this.projectApprovedCapex = projectApprovedCapex;
		this.projectApprovedOpex = projectApprovedOpex;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectManger = projectManger;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
		this.masterUsers = masterUsers;
	}
	
	
}

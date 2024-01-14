package com.ins.web.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class MasterUserVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String company;

	private String status;

	private String type;

	private String manager;

	private double rates;

	private String startDate;

	private String endDate;

	private String title;

	private String location;

	private String createdOn;

	private String createdBy;

	private String updatedOn;

	private String updatedBy;
	
	
	@ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private MasterProjectVo masterProject;
	public MasterUserVo() {
		super();
	}

	public long getId() {
		return id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public double getRates() {
		return rates;
	}

	public void setRates(double rates) {
		this.rates = rates;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public MasterUserVo(long id, String name, String company, String status, String type, String manager, double rates,
			String startDate, String endDate, String title, String location, String createdOn, String createdBy,
			String updatedOn, String updatedBy, MasterProjectVo masterProject) {
		super();
		this.id = id;
		this.name = name;
		this.company = company;
		this.status = status;
		this.type = type;
		this.manager = manager;
		this.rates = rates;
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		this.location = location;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
		this.masterProject = masterProject;
	}



	public MasterProjectVo getMasterProject() {
		return masterProject;
	}



	public void setMasterProject(MasterProjectVo masterProject) {
		this.masterProject = masterProject;
	}
	
	

	
}

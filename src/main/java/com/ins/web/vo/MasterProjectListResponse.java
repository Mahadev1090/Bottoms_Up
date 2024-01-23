package com.ins.web.vo;

import java.util.List;

public class MasterProjectListResponse {
	private int count;
    private List<MasterProjectVo> projects;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<MasterProjectVo> getProjects() {
		return projects;
	}
	public void setProjects(List<MasterProjectVo> projects) {
		this.projects = projects;
	}
	public MasterProjectListResponse(int count, List<MasterProjectVo> projects) {
		super();
		this.count = count;
		this.projects = projects;
	}
	public MasterProjectListResponse() {
		// TODO Auto-generated constructor stub
	}
    
    
}

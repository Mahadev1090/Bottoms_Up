package com.ins.web.vo;

import java.util.List;

public class MasterUserListResponse {
	private int count;
    private List<MasterUserVo> users;
	public List<MasterUserVo> getUsers() {
		return users;
	}
	public void setUsers(List<MasterUserVo> users) {
		this.users = users;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

    // Constructors, getters, and setters
    
}

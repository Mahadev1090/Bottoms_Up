package com.ins.web.dto;

import java.util.List;

public class SearchResultDTO {

    private int count;
    private List<MasterUserWithMasterProjectDTO> result;

    public SearchResultDTO(int count, List<MasterUserWithMasterProjectDTO> result) {
        this.count = count;
        this.result = result;
    }

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<MasterUserWithMasterProjectDTO> getResult() {
		return result;
	}

	public void setResult(List<MasterUserWithMasterProjectDTO> result) {
		this.result = result;
	}

    // Add getters and setters
    
}

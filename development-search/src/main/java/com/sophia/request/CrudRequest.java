package com.sophia.request;

import javax.validation.constraints.NotNull;

public class CrudRequest{
	
	private String id;
	
	@NotNull
	private long version;

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}

package com.sophia.vo;

import javax.validation.constraints.NotNull;

public class CrudRequest{
	
	@NotNull
	private long version;

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
}

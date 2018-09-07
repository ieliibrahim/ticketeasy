package com.ieli.tieasy.model.api;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Upload implements Serializable {

	private static final long serialVersionUID = 1L;

	@SerializedName("result")
	private UploadResult uploadResult;

	public Upload() {

	}

	public UploadResult getUploadResult() {
		return uploadResult;
	}

	public void setUploadResult(UploadResult uploadResult) {
		this.uploadResult = uploadResult;
	}

}

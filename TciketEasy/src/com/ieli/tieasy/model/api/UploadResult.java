package com.ieli.tieasy.model.api;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class UploadResult implements Serializable {

	private static final long serialVersionUID = 1L;

	@SerializedName("file_name")
	private String fileName;

	@SerializedName("download_link")
	private String downloadLink;

	public UploadResult() {

	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDownloadLink() {
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}

}

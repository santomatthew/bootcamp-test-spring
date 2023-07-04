package com.lawencon.myapp.model;

public class CandidateInformation extends BaseModel {

	private User candidate;
	private FileType fileType;
	private File File;


	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public File getFile() {
		return File;
	}

	public void setFile(File file) {
		File = file;
	}

	public User getCandidate() {
		return candidate;
	}

	public void setCandidate(User candidate) {
		this.candidate = candidate;
	}

}

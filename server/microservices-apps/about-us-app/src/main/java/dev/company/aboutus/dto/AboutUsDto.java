package dev.company.aboutus.dto;

import java.util.Date;

import jakarta.persistence.Column;

public class AboutUsDto {
	private int id;

	private Date dateCreated;

	private byte[] photo;

	private String organizationTitle;

	private String organizationDescription;

	private String organizationMission;

	private String organizationVision;

	@Column(name = "org_vision")
	private String organizationValues;

	public AboutUsDto() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getOrganizationTitle() {
		return organizationTitle;
	}

	public void setOrganizationTitle(String organizationTitle) {
		this.organizationTitle = organizationTitle;
	}

	public String getOrganizationDescription() {
		return organizationDescription;
	}

	public void setOrganizationDescription(String organizationDescription) {
		this.organizationDescription = organizationDescription;
	}

	public String getOrganizationMission() {
		return organizationMission;
	}

	public void setOrganizationMission(String organizationMission) {
		this.organizationMission = organizationMission;
	}

	public String getOrganizationVision() {
		return organizationVision;
	}

	public void setOrganizationVision(String organizationVision) {
		this.organizationVision = organizationVision;
	}

	public String getOrganizationValues() {
		return organizationValues;
	}

	public void setOrganizationValues(String organizationValues) {
		this.organizationValues = organizationValues;
	}
}

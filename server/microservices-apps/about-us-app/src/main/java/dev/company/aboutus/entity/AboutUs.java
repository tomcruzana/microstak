package dev.company.aboutus.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "about_us")
public class AboutUs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	@Column(name = "date_created")
	private Date dateCreated;

	@Lob
	private byte[] photo;

	@Column(name = "org_title")
	private String organizationTitle;

	@Column(name = "org_description")
	private String organizationDescription;

	@Column(name = "org_mission")
	private String organizationMission;

	@Column(name = "org_vision")
	private String organizationVision;

	@Column(name = "org_values")
	private String organizationValues;

	public AboutUs() {
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

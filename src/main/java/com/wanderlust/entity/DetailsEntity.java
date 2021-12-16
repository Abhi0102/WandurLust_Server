package com.wanderlust.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DETAILS")
public class DetailsEntity {
	
	@Id
	private String detailsId;
	private String about;
	private String packageInclusion;
	private String highlights;
	private String pace;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ITINERARY_ID")
	private ItineraryEntity itineraryEntity;
	public String getDetailsId() {
		return detailsId;
	}
	public void setDetailsId(String detailsId) {
		this.detailsId = detailsId;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getPackageInclusion() {
		return packageInclusion;
	}
	public void setPackageInclusion(String packageInclusion) {
		this.packageInclusion = packageInclusion;
	}
	public String getHighlights() {
		return highlights;
	}
	public void setHighlights(String highlights) {
		this.highlights = highlights;
	}
	public String getPace() {
		return pace;
	}
	public void setPace(String pace) {
		this.pace = pace;
	}
	public ItineraryEntity getItineraryEntity() {
		return itineraryEntity;
	}
	public void setItineraryEntity(ItineraryEntity itineraryEntity) {
		this.itineraryEntity = itineraryEntity;
	}
	
	

}

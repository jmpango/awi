package org.awi.ui.model;

public class Usage {
	int id;
	int pageHits;
	int callHits;
	int urlHits;
	int emailHits;
	int buddyId;

	public Usage() {

	}

	public Usage(int id, int pageHits, int callHits, int urlHits,
			int emailHits, int buddyId) {
		this.id = id;
		this.pageHits = pageHits;
		this.callHits = callHits;
		this.urlHits = urlHits;
		this.emailHits = emailHits;
		this.buddyId = buddyId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPageHits() {
		return pageHits;
	}

	public void setPageHits(int pageHits) {
		this.pageHits = pageHits;
	}

	public int getCallHits() {
		return callHits;
	}

	public void setCallHits(int callHits) {
		this.callHits = callHits;
	}

	public int getUrlHits() {
		return urlHits;
	}

	public void setUrlHits(int urlHits) {
		this.urlHits = urlHits;
	}

	public int getEmailHits() {
		return emailHits;
	}

	public void setEmailHits(int emailHits) {
		this.emailHits = emailHits;
	}

	public int getBuddyId() {
		return buddyId;
	}

	public void setBuddyId(int buddyId) {
		this.buddyId = buddyId;
	}

}

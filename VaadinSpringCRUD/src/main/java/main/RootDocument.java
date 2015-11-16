package main;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RootDocument {

	@Id
	@GeneratedValue
	private Long id;

	private String description;

	// TODO created, modified, user
	
	protected RootDocument() {
	}

	public RootDocument(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return description;
	}

}

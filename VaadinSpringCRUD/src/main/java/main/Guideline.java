package main;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Guideline {

	@Id
	@GeneratedValue
	private Long id;

	private String description;

    @ManyToOne(optional = false)
    private RootDocument rootDocument;
    
	// TODO created, modified, user
	
	protected Guideline() {
	}

	public Guideline(String description, RootDocument rootDocument) {
		this.description = description;
		this.rootDocument = rootDocument;
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
	
	public RootDocument getRootDocument() {
		return rootDocument;
	}

	public void setRootDocument(RootDocument rootDocument) {
		this.rootDocument = rootDocument;
	}

	@Override
	public String toString() {
		return String.format("Guideline[id=%d, description='%s']", id, description);
	}

}

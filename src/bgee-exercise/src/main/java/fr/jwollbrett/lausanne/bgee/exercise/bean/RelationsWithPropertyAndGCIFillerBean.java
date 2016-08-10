package fr.jwollbrett.lausanne.bgee.exercise.bean;

import org.semanticweb.owlapi.model.OWLClass;

import owltools.graph.OWLQuantifiedProperty;

public class RelationsWithPropertyAndGCIFillerBean {
	
	private OWLQuantifiedProperty property;
	private OWLClass homologousStructure;
	private OWLClass targetStructure;
	private OWLClass gciFillerTaxon;
	
	public OWLQuantifiedProperty getProperty() {
		return property;
	}
	public void setProperty(OWLQuantifiedProperty property) {
		this.property = property;
	}
	public OWLClass getHomologousStructure() {
		return homologousStructure;
	}
	public void setHomologousStructure(OWLClass homologousStructure) {
		this.homologousStructure = homologousStructure;
	}
	public OWLClass getTargetStructure() {
		return targetStructure;
	}
	public void setTargetStructure(OWLClass targetStructure) {
		this.targetStructure = targetStructure;
	}
	public OWLClass getGciFillerTaxon() {
		return gciFillerTaxon;
	}
	public void setGciFillerTaxon(OWLClass gciFillerTaxon) {
		this.gciFillerTaxon = gciFillerTaxon;
	}

}

package fr.jwollbrett.lausanne.bgee.exercise.bean;

import java.util.HashSet;
import java.util.Set;

public class HomoStructWithAncestorsBean {
	
	private Set<RelationsWithPropertyAndGCIFillerBean> relations= new HashSet<RelationsWithPropertyAndGCIFillerBean>();
	private int numberOfDifferentSpecies;
	private int numberOfDifferentAncestors;
	
	public Set<RelationsWithPropertyAndGCIFillerBean> getRelations() {
		return relations;
	}
	public void setRelations(Set<RelationsWithPropertyAndGCIFillerBean> relations) {
		this.relations = relations;
	}
	public int getNumberOfDifferentSpecies() {
		return numberOfDifferentSpecies;
	}
	public void setNumberOfDifferentSpecies(int numberOfDifferentSpecies) {
		this.numberOfDifferentSpecies = numberOfDifferentSpecies;
	}
	public int getNumberOfDifferentAncestors() {
		return numberOfDifferentAncestors;
	}
	public void setNumberOfDifferentAncestors(int numberOfDifferentAncestors) {
		this.numberOfDifferentAncestors = numberOfDifferentAncestors;
	}
	
	

}

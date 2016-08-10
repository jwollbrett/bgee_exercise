package fr.jwollbrett.lausanne.bgee.exercise.annotations.manager;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;

import owltools.graph.OWLGraphWrapper;
import owltools.graph.OWLGraphWrapperExtended;

public class ManageAnnotations {

	//Map similarities annotations and ontology
	public static Set<OWLClass> mapAnnotationsAndOntologies(Set<String> entityIdSet, OWLOntology ontology){
		@SuppressWarnings("resource")
		OWLGraphWrapperExtended graph = new OWLGraphWrapper(ontology);
		 Set<OWLClass> owlClassesWithAnnotations = new HashSet<OWLClass>();
		 for(String entityId : entityIdSet){
			 OWLClass mappedClass=graph.getOWLClassByIdentifier(entityId);
			 if(mappedClass!=null){
				 owlClassesWithAnnotations.add(mappedClass);
			 }
		 }
		 return owlClassesWithAnnotations;
	}
}

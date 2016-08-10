package fr.jwollbrett.lausanne.bgee.exercise.ontology.extractor;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

public class RetrieveLabel {

	public static String retrieveLabelFromIRI(IRI iri,OWLOntology ontology){
		String label=null;
		for(OWLAnnotationAssertionAxiom a : ontology.getAnnotationAssertionAxioms(iri)) {
		    if(a.getProperty().isLabel()) {
		        if(a.getValue() instanceof OWLLiteral) {
		            OWLLiteral literal = (OWLLiteral) a.getValue();
		            label =literal.getLiteral();
		        }
		    }
		}
		return label;
	}
}

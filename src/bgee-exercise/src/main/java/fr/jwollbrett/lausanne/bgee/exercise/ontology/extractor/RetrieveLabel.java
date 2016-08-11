package fr.jwollbrett.lausanne.bgee.exercise.ontology.extractor;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * 
 * @author Kobe70
 *
 */
public class RetrieveLabel {
	/**
	 * Retrieve the label of an entity
	 * @param iri	{@code IRI} of the entity you want the label
	 * @param ontology {@code OWLOntology} containing the entity for which you want to retrieve the label
	 * @return	a {@code String} corresponding to the rdfs:label value of the entity
	 */
	public static String retrieveLabelFromIRI(IRI iri, OWLOntology ontology) {
		String label = null;
		for (OWLAnnotationAssertionAxiom a : ontology.getAnnotationAssertionAxioms(iri)) {
			if (a.getProperty().isLabel()) {
				if (a.getValue() instanceof OWLLiteral) {
					OWLLiteral literal = (OWLLiteral) a.getValue();
					label = literal.getLiteral();
				}
			}
		}
		return label;
	}
}

package fr.jwollbrett.lausanne.bgee.exercise.ontology.extractor;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;

/**
 * Extract object properties of an ontology
 * @author Kobe70
 *
 */
public class ExtractProperties {
	
	/**
	 * Extract all subproperties of a selected object property used in the ontology
	 * @param property	property you want to find subproperties
	 * @param ontology	ontology you want to use to find subproperties
	 * @return	The selected property and all its subproperties
	 */
	public static Set<OWLPropertyExpression> extractAllSubProp(OWLObjectProperty property,OWLOntology ontology){
		Set<OWLPropertyExpression> allSubProperties = new HashSet<OWLPropertyExpression>();
		allSubProperties.add(property);
		allSubProperties.addAll(returnSubProperties(property, ontology));
		return allSubProperties;
	}
	
	private static Set<OWLObjectPropertyExpression> extractSubPropertiesRecursive(OWLObjectPropertyExpression property,OWLOntology ontology){
		Set<OWLObjectPropertyExpression> subProperties = new HashSet<OWLObjectPropertyExpression>();
		subProperties.addAll(returnSubProperties(property, ontology));
		return subProperties;
	}
	
	private static Set<OWLObjectPropertyExpression> returnSubProperties(OWLObjectPropertyExpression property,OWLOntology ontology){
		Set<OWLObjectPropertyExpression> subProperties = new HashSet<OWLObjectPropertyExpression>();
		Set<OWLSubObjectPropertyOfAxiom>subPropertyAxiomSet=ontology.getObjectSubPropertyAxiomsForSuperProperty(property);
		if(subPropertyAxiomSet!=null){
			for(OWLSubObjectPropertyOfAxiom subPropertyAxiom:subPropertyAxiomSet){
				OWLObjectPropertyExpression subProperty=subPropertyAxiom.getSubProperty();
				subProperties.add(subProperty);
				subProperties.addAll(extractSubPropertiesRecursive(subProperty, ontology));
			}
		}
		return subProperties;
	}
}

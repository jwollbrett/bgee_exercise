package fr.jwollbrett.lausanne.bgee.exercise.variation.detector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

import fr.jwollbrett.lausanne.bgee.exercise.bean.HomoStructWithAncestorsBean;
import fr.jwollbrett.lausanne.bgee.exercise.bean.RelationsWithPropertyAndGCIFillerBean;
import owltools.graph.OWLGraphEdge;
import owltools.graph.OWLGraphWrapperEdgesExtended;

public class DetectHomologyEntities {

	/**
	 * Extract relations with selected properties, outgoing from anatomical
	 * entities present in the similarity annotation file
	 * 
	 * @param ontology
	 *            {@code OWLOntology} ontology
	 * @param owlClassesWithAnnotations
	 *            all entities both present in the ontology and in the bgee
	 *            annotation file
	 * @param properties
	 *            Set of properties used to detect relations
	 * @return relations with selected properties, outgoing from anatomical
	 *         entities present in the similarity annotation file
	 */
	public static Map<String, HomoStructWithAncestorsBean> detectHomologieswithAncestorsThroughProperty(
			OWLOntology ontology, Set<OWLClass> owlClassesWithAnnotations, Set<OWLPropertyExpression> properties) {
		OWLGraphWrapperEdgesExtended graphWrapper = new OWLGraphWrapperEdgesExtended(ontology);
		Map<String, HomoStructWithAncestorsBean> homologiesWithAncestors = new HashMap<String, HomoStructWithAncestorsBean>();
		Iterator<OWLClass> it = owlClassesWithAnnotations.iterator();
		while (it.hasNext()) {
			OWLClass homologousClass = it.next();
			Set<OWLGraphEdge> subGraphEdgeSet = graphWrapper.getOutgoingEdgesWithGCI(homologousClass, properties);
			// Set used to count both number of distinct species and number of
			// distinct ancestors
			Set<String> numberOfDistinctAncestors = new HashSet<String>();
			Set<String> numberOfDistinctSpecies = new HashSet<String>();
			HomoStructWithAncestorsBean homoStructures = new HomoStructWithAncestorsBean();
			for (OWLGraphEdge edge : subGraphEdgeSet) {
				if (edge.getGCIFiller() != null
						&& properties.contains(edge.getSingleQuantifiedProperty().getProperty())) {
					RelationsWithPropertyAndGCIFillerBean result = new RelationsWithPropertyAndGCIFillerBean();
					result.setGciFillerTaxon(edge.getGCIFiller());
					result.setHomologousStructure(homologousClass);
					result.setProperty(edge.getSingleQuantifiedProperty());
					OWLClass targetStructure = fromTargetOWLObjectToOWLClass(edge.getTarget());
					result.setTargetStructure(targetStructure);
					numberOfDistinctAncestors.add(result.getTargetStructure().toString());
					numberOfDistinctSpecies.add(result.getGciFillerTaxon().toString());
					homoStructures.getRelations().add(result);
				}
			}
			if (homoStructures.getRelations().size() != 0) {
				homoStructures.setNumberOfDifferentAncestors(numberOfDistinctAncestors.size());
				homoStructures.setNumberOfDifferentSpecies(numberOfDistinctSpecies.size());
				homologiesWithAncestors.put(homologousClass.toString(), homoStructures);
			}
		}
		return homologiesWithAncestors;
	}

	private static OWLClass fromTargetOWLObjectToOWLClass(OWLObject targetObject) {
		OWLClass targetClass = null;
		Set<OWLClass> targetClassSet = targetObject.getClassesInSignature();
		if (targetClassSet.size() == 1) {
			targetClass = targetClassSet.iterator().next();
		} else {
			Logger.getLogger(DetectHomologyEntities.class)
					.error("Can't find target Class because the target OWLObject contains more than one OWLClass");
		}
		return targetClass;
	}
}

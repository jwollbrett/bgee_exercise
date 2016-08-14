package fr.jwollbrett.lausanne.bgee.exercise.variation.detector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLOntology;

import fr.jwollbrett.lausanne.bgee.exercise.bean.HomoStructWithAncestorsBean;
import fr.jwollbrett.lausanne.bgee.exercise.bean.OutputFileBean;
import fr.jwollbrett.lausanne.bgee.exercise.bean.OutputLineBean;
import fr.jwollbrett.lausanne.bgee.exercise.bean.RelationsWithPropertyAndGCIFillerBean;
import fr.jwollbrett.lausanne.bgee.exercise.ontology.extractor.RetrieveLabel;

/**
 * @author jwollbrett
 *
 */
public class DetectDistinctEntitiesAndTaxon {

	/**
	 * Detect homologous structures having ancestors through one object
	 * properties (or any of its subproperties) that vary between species
	 * 
	 * @param ontology
	 *            {@code OWLOntology} ontology
	 * @param structuresWithAncestorsThroughProperties
	 *            relations with an object property or any of its
	 *            sub-properties, outgoing from anatomical entities present in
	 *            similarity annotation file coming from bgee
	 * @return a {@code List} of {@code OutputFileBean} corresponding to
	 *         homologous structures having ancestors through one object
	 *         properties (or any of its subproperties) that vary between
	 *         species
	 */
	public static List<OutputFileBean> distinctEntitiesAndTaxon(OWLOntology ontology,
			List<DetectHomologyEntitiesBean> structuresWithAncestorsThroughProperties) {
		List<OutputFileBean> outputFilesBeanList = new ArrayList<OutputFileBean>();
		for (DetectHomologyEntitiesBean structuresWithAncestorsThroughProperty : structuresWithAncestorsThroughProperties) {
			String propertyId = structuresWithAncestorsThroughProperty.getPropertyId();
			Set<HomoStructWithAncestorsBean> distinctResults = new HashSet<HomoStructWithAncestorsBean>(
					structuresWithAncestorsThroughProperty.getStructuresWithAncestorsThroughProperty().values());
			List<OutputLineBean> outputLinesBean = new ArrayList<OutputLineBean>();
			for (HomoStructWithAncestorsBean homoStruct : distinctResults) {
				Set<RelationsWithPropertyAndGCIFillerBean> distinctPropertiesForSameAnnotation = homoStruct
						.getRelations();
				// test presence of more than one species and more than one
				// different ancestor
				if (homoStruct.getNumberOfDifferentAncestors() > 1 && homoStruct.getNumberOfDifferentSpecies() > 1) {
					// test that both ancestors are not the same for different
					// species and different species don't annotate same
					// ancestors
					if (homoStruct.getNumberOfDifferentAncestors()
							* homoStruct.getNumberOfDifferentSpecies() == distinctPropertiesForSameAnnotation.size()) {
						for (RelationsWithPropertyAndGCIFillerBean bean : distinctPropertiesForSameAnnotation) {
							OutputLineBean outputLine = new OutputLineBean();
							outputLine.setHomologousEntityId(bean.getHomologousStructure().getIRI().getShortForm());
							outputLine.setHomologousentityName(RetrieveLabel
									.retrieveLabelFromIRI(bean.getHomologousStructure().getIRI(), ontology));
							outputLine.setPropertyId(bean.getProperty().getProperty().getIRI().getShortForm());
							outputLine.setPropertyName(RetrieveLabel
									.retrieveLabelFromIRI(bean.getProperty().getProperty().getIRI(), ontology));
							outputLine.setTargetId(bean.getTargetStructure().getIRI().getShortForm());
							outputLine.setTargetName(
									RetrieveLabel.retrieveLabelFromIRI(bean.getTargetStructure().getIRI(), ontology));
							outputLine.setTaxonId(bean.getGciFillerTaxon().getIRI().getShortForm());
							outputLine.setTaxonName(
									RetrieveLabel.retrieveLabelFromIRI(bean.getGciFillerTaxon().getIRI(), ontology));
							outputLinesBean.add(outputLine);
						}
					}
				}
			}
			outputFilesBeanList.add(new OutputFileBean(propertyId, outputLinesBean));
		}
		return outputFilesBeanList;
	}

}

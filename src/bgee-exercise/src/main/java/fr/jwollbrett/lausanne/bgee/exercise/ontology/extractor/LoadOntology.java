package fr.jwollbrett.lausanne.bgee.exercise.ontology.extractor;

import java.io.File;

import org.apache.log4j.Logger;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * Class used to load an ontology
 * @author Kobe70
 *
 */
public class LoadOntology {
	
	private static final Logger LOGGER = Logger.getLogger(LoadOntology.class);
	
	/**
	 * Load an ontology as an {@code OWLOntology} from a file
	 * @param file : {@code File} corresponding to an ontology
	 * @return {@code OWLOntology} object of the ontology
	 */
	public static OWLOntology loadOntologyFromFile(File file){
		OWLOntology ontology=null;
		try {
			ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(new FileDocumentSource(file));
		} catch (OWLOntologyCreationException e) {
			LOGGER.error("can't load ontology from file "+file.getPath(),e);
		}
		return ontology;
	}

}

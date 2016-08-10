package fr.jwollbrett.lausanne.bgee.exercise.ontology.extractor;

import java.io.File;

import org.apache.log4j.Logger;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class LoadOntology {
	
	private static final Logger LOGGER = Logger.getLogger(LoadOntology.class);
	
	public static OWLOntology loadOntologyFromFile(File file){

//		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology=null;
		try {
			ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(new FileDocumentSource(file));
		} catch (OWLOntologyCreationException e) {
			LOGGER.error("can't load ontology from file "+file.getPath(),e);
		}
		return ontology;
	}

}

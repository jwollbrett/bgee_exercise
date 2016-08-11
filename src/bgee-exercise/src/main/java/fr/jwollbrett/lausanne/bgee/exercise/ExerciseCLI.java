package fr.jwollbrett.lausanne.bgee.exercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;
import org.bgee.pipeline.annotations.SimilarityAnnotationUtils.SummaryAnnotationBean;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

import fr.jwollbrett.lausanne.bgee.exercise.annotations.importer.ExtractFromBgeePipeline;
import fr.jwollbrett.lausanne.bgee.exercise.annotations.manager.ManageAnnotations;
import fr.jwollbrett.lausanne.bgee.exercise.bean.HomoStructWithAncestorsBean;
import fr.jwollbrett.lausanne.bgee.exercise.bean.OutputFileBean;
import fr.jwollbrett.lausanne.bgee.exercise.ontology.extractor.ExtractProperties;
import fr.jwollbrett.lausanne.bgee.exercise.ontology.extractor.ExtractPropertiesBean;
import fr.jwollbrett.lausanne.bgee.exercise.ontology.extractor.LoadOntology;
import fr.jwollbrett.lausanne.bgee.exercise.output.manager.OutputFileUtils;
import fr.jwollbrett.lausanne.bgee.exercise.variation.detector.DetectDistinctEntitiesAndTaxon;
import fr.jwollbrett.lausanne.bgee.exercise.variation.detector.DetectHomologyEntities;
import fr.jwollbrett.lausanne.bgee.exercise.variation.detector.DetectHomologyEntitiesBean;

/**
 * Class that contains the main method you need to use to persist files with
 * homologous structures having ancestors through select object properties (or
 * any of its subproperties) that vary between species
 * 
 * @author Kobe70
 *
 */
public class ExerciseCLI {

	private static final Logger LOGGER = Logger.getLogger(ExerciseCLI.class);
	private static String OUTPUT_FILE_SUFFIX = "-results.tsv";
	private static Options options;

	static {
		options = new Options();
		options.addOption("f", "file", true,
				"path to file containing structural homology annotations (if -u option is selected this path corresponds to the path where the file will be created)");
		options.addOption("u", "url", true, "url where the structural homology annotation file has to be downloaded");
		options.addOption("h", "help", false, "print usage information");
		options.addOption("o", "ontology", true, "path to the UBERON ontology");
		options.addOption("p", "properties", true,
				"list of properties identifier you want to follow. Properties and there subproperties will be followed. If you want to select more than one property please separate them using semi-colon (ex: http://purl.obolibrary.org/obo/BFO_0000050;http://purl.obolibrary.org/obo/RO_0002202)."
						+ "One output file will be created for each selected property.");
		options.addOption("l", "location", true, "location repository where you want to creat output file(s)");
	}

	/**
	 * Main metthod allowing to to persist files with homologous structures
	 * having ancestors through select object properties (or any of its
	 * subproperties) that vary between species
	 * 
	 * @param args
	 *            arguments of the main methods. Use -h argument to show list of
	 *            available arguments
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException, IOException {
		CommandLineParser parser = new PosixParser();
		String annotationFilePath = null;
		String annotationFileURL = null;
		String ontologyFile = null;
		String[] properties = null;
		String outputRepositoryPath = null;
		boolean argumentsOK = true;
		boolean fromURL = true;
		Map<String, SummaryAnnotationBean> annotationsMapById = null;

		try {
			CommandLine cmdLine = parser.parse(options, args);

			if (cmdLine.hasOption("help")) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp(
						"homologous structures having ancestors through selected object properties (or any of its subproperties) that vary between species",
						options);
				argumentsOK = false;
			} else {
				if (cmdLine.hasOption("url")) {
					annotationFileURL = cmdLine.getOptionValue("url");
					if (cmdLine.hasOption("file")) {
						annotationFilePath = cmdLine.getOptionValue("file");
					} else {
						LOGGER.error("Missing homologous structure annotation file location");
						argumentsOK = false;
					}
				} else {
					if (cmdLine.hasOption("file")) {
						fromURL = false;
						annotationFilePath = cmdLine.getOptionValue("file");
					} else {
						argumentsOK = false;
						LOGGER.error("Missing homologous structure annotation file location");
					}
				}
				if (cmdLine.hasOption("ontology")) {
					ontologyFile = cmdLine.getOptionValue("ontology");
				} else {
					argumentsOK = false;
					LOGGER.error("Missing UBERON ontology file location");
				}
				if (cmdLine.hasOption("properties")) {
					properties = cmdLine.getOptionValue("properties").split(";");
				} else {
					argumentsOK = false;
					LOGGER.error("Missing list of properties identifier you want to follow");
				}
				if (cmdLine.hasOption("location")) {
					outputRepositoryPath = cmdLine.getOptionValue("location");
				} else {
					argumentsOK = false;
					LOGGER.error("Missing path to result repository location");
				}
			}
		} catch (ParseException e) {
			LOGGER.error("Command line parsing error :" + e.getMessage());
		}

		if (argumentsOK) {
			// LOAD ANNOTATION FILE
			if (fromURL) {
				annotationsMapById = ExtractFromBgeePipeline.extractSimilaritiesFromURL(annotationFileURL,
						annotationFilePath);
			} else {
				annotationsMapById = ExtractFromBgeePipeline.extractSimilaritiesFromFile(annotationFilePath);
			}
			// LOAD ONTOLOGY
			OWLOntology ontology = LoadOntology.loadOntologyFromFile(new File(ontologyFile));

			// Select classes present in the similarities annotations file
			Set<String> entityIdSet = annotationsMapById.keySet();
			Set<OWLClass> owlClassesWithAnnotations = ManageAnnotations.mapAnnotationsAndOntologies(entityIdSet,
					ontology);
			LOGGER.debug("number of classes with homologous annotations : " + owlClassesWithAnnotations.size());

			// find subproperties for selected properties
			List<ExtractPropertiesBean> propertiesAndSubProp = new ArrayList<ExtractPropertiesBean>();
			for (String propertyId : properties) {
				OWLObjectProperty property = OWLManager.createOWLOntologyManager().getOWLDataFactory()
						.getOWLObjectProperty(IRI.create(propertyId));
				Set<OWLPropertyExpression> propertyAndSubProp = ExtractProperties.extractAllSubProp(property, ontology);
				propertiesAndSubProp
						.add(new ExtractPropertiesBean(property.getIRI().getShortForm(), propertyAndSubProp));
				LOGGER.debug("number of property and subproperties for " + property.getIRI().getShortForm() + " : "
						+ propertyAndSubProp.size());
			}

			// Keep only graph edges for owlclasses with homology annotations
			// AND selected properties (and these subproperties)
			List<DetectHomologyEntitiesBean> structuresWithAncestorsThroughProperties = new ArrayList<DetectHomologyEntitiesBean>();
			for (ExtractPropertiesBean propertyAndSubProp : propertiesAndSubProp) {
				String propertyId = propertyAndSubProp.getPropertyId();
				Set<OWLPropertyExpression> propertiesExpression = propertyAndSubProp.getPropertyAndSubproperties();
				Map<String, HomoStructWithAncestorsBean> structuresWithAncestorsThroughProperty = DetectHomologyEntities
						.detectHomologieswithAncestorsThroughProperty(ontology, owlClassesWithAnnotations,
								propertiesExpression);
				structuresWithAncestorsThroughProperties
						.add(new DetectHomologyEntitiesBean(propertyId, structuresWithAncestorsThroughProperty));
				LOGGER.debug("number of results for detection of homology structure with ancestors through "
						+ propertyId + " and subproperties : " + structuresWithAncestorsThroughProperty.size());
			}

			// Identify homologous structures having ancestors through selected
			// object properties (or any of its subproperties) that vary between
			// species,
			List<OutputFileBean> outputFilesBean = DetectDistinctEntitiesAndTaxon.distinctEntitiesAndTaxon(ontology,
					structuresWithAncestorsThroughProperties);

			// write result files
			for (OutputFileBean outputFileBean : outputFilesBean) {
				String filePath = outputRepositoryPath + File.separator + outputFileBean.getPropertyId()
						+ OUTPUT_FILE_SUFFIX;
				OutputFileUtils.persistOutputFile(filePath, outputFileBean.getOutputLinesBean(), annotationsMapById);
				//
				// List<OutputFileBean> outputData =
				// OutputFileUtils.parseOutputFile("src/main/resources/partof-output.txt");
				// for(OutputFileBean bean:outputData){
				// System.out.println(bean.getHomologousEntityId());
			}
		}
	}
}

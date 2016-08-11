package fr.jwollbrett.lausanne.bgee.exercise.output.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.bgee.pipeline.annotations.SimilarityAnnotationUtils.SummaryAnnotationBean;

import fr.jwollbrett.lausanne.bgee.exercise.bean.OutputLineBean;

/**
 * Class used to manage output result file (persist, load, etc.)
 * 
 * @author Kobe70
 *
 */
public class OutputFileUtils {

	private static Logger LOGGER = Logger.getLogger(OutputFileUtils.class);
	private static String COLUMN_SEPARATOR = "\t";
	private static String NEW_LINE_SEPARATOR = "\n";
	private static List<String> mandatoryHeaders;
	static {
		mandatoryHeaders = new ArrayList<String>();
		mandatoryHeaders.add("entity_id");
		mandatoryHeaders.add("entity_name");
		mandatoryHeaders.add("homologous_taxon_name");
		mandatoryHeaders.add("property_id");
		mandatoryHeaders.add("property_name");
		mandatoryHeaders.add("target_entity_id");
		mandatoryHeaders.add("target_entity_name");
		mandatoryHeaders.add("taxon_id");
		mandatoryHeaders.add("taxon_name");
	}

	/**
	 * persist file that will contain all homologous structures having ancestors
	 * through the selected object properties (or any of its subproperties) that
	 * vary between species
	 * 
	 * @param filePath
	 *            path of the file you want to persist
	 * @param outputInfo
	 *            all results you want to persist
	 * @param summaryAnnotationMap
	 *            all similarity annotations from bgee
	 */
	public static void persistOutputFile(String filePath, List<OutputLineBean> outputInfo,
			Map<String, SummaryAnnotationBean> summaryAnnotationMap) {
		BufferedWriter writer = null;
		try {
			File outputFile = new File(filePath);
			writer = new BufferedWriter(new FileWriter(outputFile));
			writer.write(writeHeader());
			for (OutputLineBean outputLineInfo : outputInfo) {
				String summaryAnnotationTaxonName = summaryAnnotationMap
						.get(outputLineInfo.getHomologousEntityId().replace("_", ":")).getTaxonName();
				String line = outputLineInfo.getHomologousEntityId() + COLUMN_SEPARATOR
						+ outputLineInfo.getHomologousentityName() + COLUMN_SEPARATOR + summaryAnnotationTaxonName
						+ COLUMN_SEPARATOR + outputLineInfo.getPropertyId() + COLUMN_SEPARATOR
						+ outputLineInfo.getPropertyName() + COLUMN_SEPARATOR + outputLineInfo.getTargetId()
						+ COLUMN_SEPARATOR + outputLineInfo.getTargetName() + COLUMN_SEPARATOR
						+ outputLineInfo.getTaxonId() + COLUMN_SEPARATOR + outputLineInfo.getTaxonName()
						+ NEW_LINE_SEPARATOR;
				writer.write(line);
			}
		} catch (Exception e) {
			LOGGER.error("can't write output file " + filePath, e);
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				LOGGER.error("can't close output file " + filePath, e);
			}
		}

	}

	private static String writeHeader() {
		String firstLine = "";
		Iterator<String> it = mandatoryHeaders.iterator();
		while (it.hasNext()) {
			firstLine += it.next();
			if (it.hasNext()) {
				firstLine += COLUMN_SEPARATOR;
			}
		}
		firstLine += NEW_LINE_SEPARATOR;
		return firstLine;
	}
	
	/**
	 * Parse result file and load data as a {@code List} of {@code OutputLineBean}
	 * @param filePath	path to the file you want to load
	 * @return	{@code List} of {@code OutputLineBean}
	 * @throws IOException
	 */
	public static List<OutputLineBean> parseOutputFile(String filePath) throws IOException {
		List<OutputLineBean> outputData = new ArrayList<OutputLineBean>();
		TableParser parser = new TableParser();
		parser.setOutputFile(new File(filePath));
		Collection<TableCellBean> lineBeans = parser.parseLine();
		while (lineBeans != null) {
			OutputLineBean outputDataLine = new OutputLineBean();
			Set<String> missingMandatoryHeaders = new HashSet<String>();
			missingMandatoryHeaders.addAll(mandatoryHeaders);
			for (TableCellBean tcb : lineBeans) {
				String header = tcb.getHeader();
				String contents = tcb.getContents();
				switch (header) {

				case "entity_id":
					outputDataLine.setHomologousEntityId(contents);
					missingMandatoryHeaders.remove("entity_id");
					break;

				case "entity_name":
					outputDataLine.setHomologousentityName(contents);
					missingMandatoryHeaders.remove("entity_name");
					break;

				case "homologous_taxon_name":
					outputDataLine.setHomologousTaxonName(contents);
					missingMandatoryHeaders.remove("homologous_taxon_name");
					break;

				case "property_id":
					outputDataLine.setPropertyId(contents);
					missingMandatoryHeaders.remove("property_id");
					break;

				case "property_name":
					outputDataLine.setPropertyName(contents);
					missingMandatoryHeaders.remove("property_name");
					break;

				case "target_entity_id":
					outputDataLine.setTargetId(contents);
					missingMandatoryHeaders.remove("target_entity_id");
					break;

				case "target_entity_name":
					outputDataLine.setTargetName(contents);
					missingMandatoryHeaders.remove("target_entity_name");
					break;

				case "taxon_id":
					outputDataLine.setTaxonId(contents);
					missingMandatoryHeaders.remove("taxon_id");
					break;

				case "taxon_name":
					outputDataLine.setTaxonName(contents);
					missingMandatoryHeaders.remove("taxon_name");
					break;

				default:
					break;
				}
			}
			if (!missingMandatoryHeaders.isEmpty()) {
				String missingHeaders = "";
				for (String missingHeader : missingMandatoryHeaders) {
					missingHeaders = " " + missingHeader;
				}
				LOGGER.warn("Missing headers: " + missingHeaders);
				outputDataLine = null;
			}
			outputData.add(outputDataLine);
			lineBeans = parser.parseLine();
		}
		return outputData;
	}

}

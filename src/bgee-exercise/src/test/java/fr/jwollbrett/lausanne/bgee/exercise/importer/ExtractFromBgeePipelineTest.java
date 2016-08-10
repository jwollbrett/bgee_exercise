package fr.jwollbrett.lausanne.bgee.exercise.importer;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.bgee.pipeline.annotations.SimilarityAnnotationUtils.SummaryAnnotationBean;
import org.junit.Test;

import fr.jwollbrett.lausanne.bgee.exercise.annotations.importer.ExtractFromBgeePipeline;

public class ExtractFromBgeePipelineTest {
	
	@Test
	public void testExtractSimilarities() throws FileNotFoundException, IllegalArgumentException, IOException{
		String fileURL = "https://raw.githubusercontent.com/BgeeDB/anatomical-similarity-annotations/master/release/ancestral_taxa_homology_annotations.tsv";
		String filePath="src/test/resources/testExtractSimilarities.tsv";
		Map<String,SummaryAnnotationBean> annotationList=ExtractFromBgeePipeline.extractSimilaritiesFromURL(fileURL,filePath);
		assertEquals(1819, annotationList.size());
	}
	
	
}

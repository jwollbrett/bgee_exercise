package fr.jwollbrett.lausanne.bgee.exercise.annotations.importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bgee.pipeline.annotations.SimilarityAnnotationUtils;
import org.bgee.pipeline.annotations.SimilarityAnnotationUtils.SummaryAnnotationBean;

/**
 * Class used to extract data from the bgee pipeline
 * @author Kobe70
 *
 */
public class ExtractFromBgeePipeline {
	
	/**
	 * Extract homology data contained in a file available through an URL
	 * @param file2ExtractPath	URL of the file you want to extract
	 * @param extractedFilePath	Path of the local version of the file 
	 * @return	A HashMap with all homology informations. Keys correspond to entity IDs
	 * @throws IOException
	 */
	public static HashMap<String, SummaryAnnotationBean> extractSimilaritiesFromURL(String file2ExtractPath, String extractedFilePath) throws IOException{
		URL file2ExtractURL= new URL(file2ExtractPath);
		File file = new File(extractedFilePath);
		FileUtils.copyURLToFile(file2ExtractURL, file);
		HashMap<String, SummaryAnnotationBean> annotationsMapById = extractSimilaritiesFromFile(file.getPath());
		return annotationsMapById;
	}
	/**
	 * Extract homology data contained in a file available on your computer
	 * @param filePath	Path of the file you want to extract
	 * @return	A HashMap with all homology informations. Keys correspond to entity IDs
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public static HashMap<String, SummaryAnnotationBean> extractSimilaritiesFromFile(String filePath) throws FileNotFoundException, IllegalArgumentException, IOException {
		File file = new File(filePath);
		List<SummaryAnnotationBean> annotationList=SimilarityAnnotationUtils.extractSummaryAnnotations(file.toString());
		HashMap<String, SummaryAnnotationBean> annotationsMapById = fromList2Map(annotationList);
		return annotationsMapById;
	}
	
	//transform list to hashmap in order to both easily manage id values and retrieve related similarity annotations
	private static HashMap<String, SummaryAnnotationBean> fromList2Map(List<SummaryAnnotationBean> annotationsList){
		HashMap<String, SummaryAnnotationBean> annotationsMapById = new HashMap<String, SummaryAnnotationBean>();
		for (SummaryAnnotationBean annotation : annotationsList) {
			List<String> entitiesId=annotation.getEntityIds();
			//take into account same similarity annotations with different Ids
			if(entitiesId.size()>1){
				for(int index=0;index<entitiesId.size();index++){
					annotationsMapById.put(entitiesId.get(index), annotation);
				}
			}else{
				annotationsMapById.put(annotation.getEntityIds().get(0), annotation);
			}
		}
		return annotationsMapById;
	}
	
		
}

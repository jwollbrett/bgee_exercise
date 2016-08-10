package fr.jwollbrett.lausanne.bgee.exercise.output.manager.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import fr.jwollbrett.lausanne.bgee.exercise.bean.OutputLineBean;
import fr.jwollbrett.lausanne.bgee.exercise.output.manager.OutputFileUtils;

public class OutputFileUtilsTest {

	@Test
	public void testLoadFile() throws IOException{
		List<OutputLineBean> outputData = OutputFileUtils.parseOutputFile("src/test/resources/test-results.tsv");
		assertEquals(4, outputData.size());
	}

}

package ro.usv.rf;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Arrays;

import org.junit.Test;

public class TestJUnit {
	@Test
	protected static void testKNNForClass(String[][] unknownPatterns, String[][] learningSet, int kFactor) {
		// calculate distances
		double[][] distances = new double[unknownPatterns.length][learningSet.length];
		for (int i = 0; i < unknownPatterns.length; i++) {
			for (int j = 0; j < learningSet.length; j++) {
				distances[i][j] = DistanceUtils.euclidianDistance(unknownPatterns[i], learningSet[j], 1);
			}
		}

		for (int i = 0; i < unknownPatterns.length; i++) {
			String foundClass = Classificators.performKNNClassification(unknownPatterns[i], learningSet, distances[i],
					kFactor);

			assertEquals(foundClass, unknownPatterns[i][unknownPatterns[i].length - 1]);
		}
	}
}

package ro.usv.rf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class VMainClass {

	public static void main(String[] args) {
		String[][] learningSet;
		String[][] unknownSet;
		String[][] testingSet;
		try {
			learningSet = FileUtils.readLearningSetFromFile("gradesClasses.txt");
			unknownSet = FileUtils.readLearningSetFromFile("evalGrades.txt");
			testingSet = FileUtils.readLearningSetFromFile("testing.txt");

			// calculate distances
			double[][] distances = new double[unknownSet.length][learningSet.length];
			for (int i = 0; i < unknownSet.length; i++) {
				for (int j = 0; j < learningSet.length; j++) {
					distances[i][j] = DistanceUtils.euclidianDistance(unknownSet[i], learningSet[j], 1);
				}
			}

			// add the class pattern to the eval set
			String[][] newEvalSet = new String[unknownSet.length][unknownSet[0].length + 1];
			int[] kValues = new int[] { 1, 3, 5, 7, 9, 13, 17 };

			for (int k : kValues) {
				for (int i = 0; i < unknownSet.length; i++) {
					for (int j = 0; j < unknownSet[0].length; j++) {
						newEvalSet[i][j] = unknownSet[i][j];
					}

					newEvalSet[i][newEvalSet[i].length - 1] = Classificators.performKNNClassification(unknownSet[i],
							learningSet, distances[i], k);
				}
				FileUtils.writeLearningSetToFile(k + "out.txt", newEvalSet);
			}

			TestJUnit.testKNNForClass(testingSet, learningSet, 3);

		} catch (USVInputFileCustomException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Finished learning set operations");
		}
	}

}
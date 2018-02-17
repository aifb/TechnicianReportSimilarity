package edu.kit.aifb.TechnicianReportSimilarity.distance;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

public class SimilarityMetrics {

	public static void main(String[] args) {
		String str1 = "This is a sentence. It is made of words";
		String str2 = "This sentence is similar. It has almost the same words";

		StringMetric metric = StringMetrics.cosineSimilarity();

		float result = metric.compare(str1, str2); 
		System.out.println(result);

	}

}

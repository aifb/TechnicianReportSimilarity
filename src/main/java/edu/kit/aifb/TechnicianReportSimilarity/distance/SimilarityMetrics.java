package edu.kit.aifb.TechnicianReportSimilarity.distance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

public class SimilarityMetrics {

	public static void main(String[] args) {
		String str1 = "Check sensor of printing press. Might be too dirty.";
		String str2 = "Cleaned sensor of printing press.";

		StringMetric metric = StringMetrics.cosineSimilarity();

		float result = metric.compare(str1, str2); 
		System.out.println(result);

	}

}

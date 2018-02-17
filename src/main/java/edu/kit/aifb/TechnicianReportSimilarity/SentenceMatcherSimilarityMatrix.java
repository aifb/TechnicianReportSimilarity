package edu.kit.aifb.TechnicianReportSimilarity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.HirstStOnge;
import edu.cmu.lti.ws4j.impl.JiangConrath;
import edu.cmu.lti.ws4j.impl.LeacockChodorow;
import edu.cmu.lti.ws4j.impl.Lesk;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.Path;
import edu.cmu.lti.ws4j.impl.Resnik;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

//Source: https://stackoverflow.com/questions/36300485/how-to-resolve-the-difference-between-the-values-attained-in-the-web-api-and-the
/**
 * 
 * @author sba
 *
 */
public class SentenceMatcherSimilarityMatrix {
	
	public SentenceMatcherSimilarityMatrix(){
		WS4JConfiguration.getInstance().setMFS(false);
	}
	
	private ILexicalDatabase db = new NictWordNet();
	
	private final Logger logger = LoggerFactory.getLogger(SentenceMatcherSimilarityMatrix.class);
	
	
	public double[][] getSimilarityMatrix( String[] words1, String[] words2, RelatednessCalculator rc )	{
		double[][] result = new double[words1.length][words2.length];
		for ( int i=0; i<words1.length; i++ ){
			for ( int j=0; j<words2.length; j++ ) {
				double score = rc.calcRelatednessOfWords(words1[i], words2[j]);
				result[i][j] = score;
			}
		}
		return result;
	}
	
	
	
	void compute (String[] words1, String[] words2) {
		logger.info("WuPalmer");
		RelatednessCalculator rc1 = new WuPalmer(db);
		{
			double[][] s1 = getSimilarityMatrix(words1, words2,rc1);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s1[i][j] +"\t");
				} 
			}}
		logger.info("");

		logger.info("Resnik");
		RelatednessCalculator rc2 = new Resnik(db);
		{
			double[][] s2 = getSimilarityMatrix(words1, words2,rc2);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s2[i][j] +"\t");
				} 
			}}
		logger.info("");

		logger.info("JiangConrath");
		RelatednessCalculator rc3 = new JiangConrath(db);
		{
			double[][] s2 = getSimilarityMatrix(words1, words2,rc3);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s2[i][j] +"\t");
				} 
			}}
		logger.info("");

		logger.info("Lin");
		RelatednessCalculator rc4 = new Lin(db);
		{
			double[][] s2 = getSimilarityMatrix(words1, words2,rc4);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s2[i][j] +"\t");
				} 
			}}
		logger.info("");

		logger.info("LeacockChodrow");
		RelatednessCalculator rc5 = new LeacockChodorow(db);
		{
			double[][] s2 = getSimilarityMatrix(words1, words2,rc5);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s2[i][j] +"\t");
				} 
			}}
		logger.info("");

		logger.info("Path");
		RelatednessCalculator rc6 = new Path(db);
		{
			double[][] s2 = getSimilarityMatrix(words1, words2,rc6);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s2[i][j] +"\t");
				} 
			}}
		logger.info("");

		logger.info("Lesk");
		RelatednessCalculator rc7 = new Lesk(db);
		{
			double[][] s2 = getSimilarityMatrix(words1, words2,rc7);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s2[i][j] +"\t");
				} 
			}}
		logger.info("");

		logger.info("HirstStOnge");
		RelatednessCalculator rc8 = new HirstStOnge(db);
		{
			double[][] s2 = getSimilarityMatrix(words1, words2,rc8);

			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s2[i][j] +"\t");
				} 
			}}
	}

	
	
	
	public static void main(String[] args) {
		String sent1 = "The boy is playing with a dog.";
		String sent2 = "The kid is playing with his pet.";

		String[] words1 = sent1.split(" ");
		String[] words2 = sent2.split(" ");
		SentenceMatcherSimilarityMatrix sm1 = new SentenceMatcherSimilarityMatrix(); 
		sm1.compute(words1, words2);
	}
	
	double[][] computeWuPalmer(String[] words1, String[] words2) {
		logger.info("WuPalmer");
		
		RelatednessCalculator rc1 = new WuPalmer(db);
		double[][] s1 = getSimilarityMatrix(words1, words2,rc1);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s1[i][j] +"\t");
			}}
	return s1;
	}
	
	double[][] computeResnik(String[] words1, String[] words2) {
		logger.info("Resnik");
		
		RelatednessCalculator rc1 = new Resnik(db);
		double[][] s1 = getSimilarityMatrix(words1, words2,rc1);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s1[i][j] +"\t");
			}}
	return s1;
	}
	
	double[][] computeJiangConrath(String[] words1, String[] words2) {
		logger.info("JiangConrath");
		
		RelatednessCalculator rc1 = new JiangConrath(db);
		double[][] s1 = getSimilarityMatrix(words1, words2,rc1);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s1[i][j] +"\t");
			}}
	return s1;
	}
	
	double[][] computeLin(String[] words1, String[] words2) {
		logger.info("Lin");
		
		RelatednessCalculator rc1 = new Lin(db);
		double[][] s1 = getSimilarityMatrix(words1, words2,rc1);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s1[i][j] +"\t");
			}}
	return s1;
	}
	
	double[][] computeLeacockChodorow(String[] words1, String[] words2) {
		logger.info("LeacockChodorow");
		
		RelatednessCalculator rc1 = new LeacockChodorow(db);
		double[][] s1 = getSimilarityMatrix(words1, words2,rc1);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s1[i][j] +"\t");
			}}
	return s1;
	}
	
	double[][] computePath(String[] words1, String[] words2) {
		logger.info("Path");
		
		RelatednessCalculator rc1 = new Path(db);
		double[][] s1 = getSimilarityMatrix(words1, words2,rc1);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s1[i][j] +"\t");
			}}
	return s1;
	}

	
	double[][] computeLesk(String[] words1, String[] words2) {
		logger.info("Lesk");
		
		RelatednessCalculator rc1 = new Lesk(db);
		double[][] s1 = getSimilarityMatrix(words1, words2,rc1);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s1[i][j] +"\t");
			}}
	return s1;
	}
	

	
	double[][] computeHirstStOnge(String[] words1, String[] words2) {
		logger.info("HirstStOnge");
		
		RelatednessCalculator rc1 = new HirstStOnge(db);
		double[][] s1 = getSimilarityMatrix(words1, words2,rc1);
			for(int i=0; i<words1.length; i++){
				for(int j=0; j< words2.length; j++){
					logger.info(s1[i][j] +"\t");
			}}
	return s1;
	}
	
}

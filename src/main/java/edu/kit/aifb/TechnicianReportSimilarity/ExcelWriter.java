package edu.kit.aifb.TechnicianReportSimilarity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
//	 private static final String FILE_PATH = "./data/sheet25.xlsx";
//
//	    public static void main(String args[]){
//	    	List <String> reportLabels = Arrays.asList("Printing Press","Cable");
//	    	List <String> proposalLabels = Arrays.asList("Drucker","Kabel");
//	    	int [][] distances = new int[2][2];
//	    	distances[0][0] = 1;
//	    	distances[1][0] = -1;
//	    	distances[0][1] = 1;
//	    	distances[1][1] = 2;
//	        Workbook workbook = new XSSFWorkbook();
//	        Sheet sheet = workbook.createSheet("25");
//	        int rowIndex = 1;
//	        Iterator iter = reportLabels.iterator();
//	        	while(iter.hasNext()){
//	        		 Row row = sheet.createRow(rowIndex++);
//	        		String label = (String) iter.next();
//	        		int cellIndex = 0;
//	            row.createCell(cellIndex++).setCellValue(label);
//	        }
//		        rowIndex = 0;
//		        Iterator iter2 = proposalLabels.iterator();
//		        Row row = sheet.createRow(rowIndex);
//		        int cellIndex = 1;
//		        	while(iter2.hasNext()){
//		        		String label = (String) iter2.next();
//		            //first place in row is name
//		            row.createCell(cellIndex++).setCellValue(label);
//		        }
//		        	rowIndex = 1;
//		        	cellIndex = 1;
//		      for (int i = 0; i < distances.length; i++) {
//		    	  row = sheet.getRow(rowIndex+i);
//		    	  for (int j = 0; j<distances[i].length;j++) {
//		    		  row.createCell(cellIndex+j).setCellValue(distances[i][j]);
//		    	  }	  
//		      }
//	        //write this workbook in excel file.
//	        try {
//	            FileOutputStream fos = new FileOutputStream(FILE_PATH);
//	            workbook.write(fos);
//	            fos.close();
//
//	            System.out.println(FILE_PATH + " is successfully written");
//	        } catch (FileNotFoundException e) {
//	            e.printStackTrace();
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	    }
		private Workbook workbook;
		public ExcelWriter() {
	    	workbook = new XSSFWorkbook();
		}


	    public void writeToExcel(int nr, List<Concept>reportConcepts, List<Concept>proposalConcepts, int[][] distances){

	        Sheet sheet = workbook.createSheet(Integer.toString(nr));
	        int rowIndex = 1;
	        Iterator iter = reportConcepts.iterator();
	        	while(iter.hasNext()){
	        		 Row row = sheet.createRow(rowIndex++);
	        		 Concept c = (Concept) iter.next();
	        		String label = c.getLabel();
	        		int cellIndex = 0;
	            row.createCell(cellIndex++).setCellValue(label);
	        }
		        rowIndex = 0;
		        Iterator iter2 = proposalConcepts.iterator();
		        Row row = sheet.createRow(rowIndex);
		        int cellIndex = 1;
		        	while(iter2.hasNext()){
		        		 Concept c = (Concept) iter2.next();
			        		String label = c.getLabel();
		            //first place in row is name
		            row.createCell(cellIndex++).setCellValue(label);
		        }
		        	rowIndex = 1;
		        	cellIndex = 1;
		      for (int i = 0; i < distances.length; i++) {
		    	  row = sheet.getRow(rowIndex+i);
		    	  for (int j = 0; j<distances[i].length;j++) {
		    		  row.createCell(cellIndex+j).setCellValue(distances[i][j]);
		    	  }	  
		      }
	    }
	    
	    public void writeWorkbookToFile() {
	        String filePath = "./data/JenaResults.xlsx";
	        try {
	            FileOutputStream fos = new FileOutputStream(filePath);
	            workbook.write(fos);
	            fos.close();

	            System.out.println(filePath + " is successfully written");
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


}

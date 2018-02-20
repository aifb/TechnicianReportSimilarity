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
		private Workbook workbook;
		
		public ExcelWriter() {
	    	workbook = new XSSFWorkbook();
		}
		
	    public void writeToExcel(String sheetname, List<Concept>reportConcepts, List<Concept>proposalConcepts, double[][] distances){
	        Sheet sheet = workbook.createSheet(sheetname);
	        int rowIndex = 1;
	        Iterator<Concept> iter = reportConcepts.iterator();
	        	while(iter.hasNext()){
	        		 Row row = sheet.createRow(rowIndex++);
	        		 Concept c = iter.next();
	        		String label = c.getLabel();
	        		int cellIndex = 0;
	            row.createCell(cellIndex++).setCellValue(label);
	        }
		        rowIndex = 0;
		        Iterator<Concept> iter2 = proposalConcepts.iterator();
		        Row row = sheet.createRow(rowIndex);
		        int cellIndex = 1;
		        	while(iter2.hasNext()){
		        		 Concept c = iter2.next();
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
	    
	    public void writeWorkbookToFile(String filename) {
	        String filePath = "./data/"+filename+".xlsx";
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

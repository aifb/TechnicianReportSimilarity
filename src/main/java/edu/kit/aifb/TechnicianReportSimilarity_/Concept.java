package edu.kit.aifb.TechnicianReportSimilarity_;

public class Concept {
	private String url;
	private String label;
	
	public Concept (String url, String label)
	{
		this.url = url;
		this.label = label;
		
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String toString() {
		return "Label: " + label + " URL: "+ url;
	}
	
	

}

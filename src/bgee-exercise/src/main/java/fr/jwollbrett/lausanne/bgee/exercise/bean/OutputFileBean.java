package fr.jwollbrett.lausanne.bgee.exercise.bean;

import java.util.ArrayList;
import java.util.List;

public class OutputFileBean {
	
	private String propertyId;
	private List<OutputLineBean> outputLinesBean = new ArrayList<OutputLineBean>();
	
	
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public List<OutputLineBean> getOutputLinesBean() {
		return outputLinesBean;
	}
	public void setOutputLinesBean(List<OutputLineBean> outputLinesBean) {
		this.outputLinesBean = outputLinesBean;
	}
	
	public OutputFileBean() {}
	
	public OutputFileBean(String propertyId, List<OutputLineBean> outputLinesBean) {
		this.propertyId = propertyId;
		this.outputLinesBean = outputLinesBean;
	}
	
	

}

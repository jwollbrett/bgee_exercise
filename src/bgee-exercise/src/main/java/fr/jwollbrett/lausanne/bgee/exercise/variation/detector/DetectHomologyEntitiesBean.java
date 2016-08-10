package fr.jwollbrett.lausanne.bgee.exercise.variation.detector;

import java.util.HashMap;
import java.util.Map;

import fr.jwollbrett.lausanne.bgee.exercise.bean.HomoStructWithAncestorsBean;

public class DetectHomologyEntitiesBean {

	private String propertyId;
	private Map<String, HomoStructWithAncestorsBean> structuresWithAncestorsThroughProperty = new HashMap<String, HomoStructWithAncestorsBean>();
	
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public Map<String, HomoStructWithAncestorsBean> getStructuresWithAncestorsThroughProperty() {
		return structuresWithAncestorsThroughProperty;
	}
	public void setStructuresWithAncestorsThroughProperty(
			Map<String, HomoStructWithAncestorsBean> structuresWithAncestorsThroughProperty) {
		this.structuresWithAncestorsThroughProperty = structuresWithAncestorsThroughProperty;
	}
	
	public DetectHomologyEntitiesBean(String propertyId,Map<String, HomoStructWithAncestorsBean> structuresWithAncestorsThroughProperty) {
		this.propertyId = propertyId;
		this.structuresWithAncestorsThroughProperty = structuresWithAncestorsThroughProperty;
	}
	
	public DetectHomologyEntitiesBean(){}
	
	
}

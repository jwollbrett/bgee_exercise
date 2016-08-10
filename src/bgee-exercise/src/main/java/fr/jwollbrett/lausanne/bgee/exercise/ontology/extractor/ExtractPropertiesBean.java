package fr.jwollbrett.lausanne.bgee.exercise.ontology.extractor;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLPropertyExpression;

public class ExtractPropertiesBean {
	
	private String propertyId;
	private Set<OWLPropertyExpression> propertyAndSubproperties;
	
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public Set<OWLPropertyExpression> getPropertyAndSubproperties() {
		return propertyAndSubproperties;
	}
	public void setPropertyAndSubproperties(Set<OWLPropertyExpression> propertyAndSubproperties) {
		this.propertyAndSubproperties = propertyAndSubproperties;
	}
	
	public ExtractPropertiesBean() {}
	
	public ExtractPropertiesBean(String propertyId, Set<OWLPropertyExpression> propertyAndSubProp) {
		setPropertyAndSubproperties(propertyAndSubProp);
		setPropertyId(propertyId);
	}

}

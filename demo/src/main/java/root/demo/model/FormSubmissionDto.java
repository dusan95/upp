package root.demo.model;

import java.io.Serializable;
import java.util.List;

public class FormSubmissionDto implements Serializable{
	
	String fieldId;
	String fieldValue;
	List<String> fieldListValue;
	
	
	public FormSubmissionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormSubmissionDto(String fieldId, String fieldValue, List<String> fieldListValue) {
		super();
		this.fieldId = fieldId;
		this.fieldValue = fieldValue;
		this.fieldListValue = fieldListValue;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public List<String> getFieldListValue() {
		return fieldListValue;
	}

	public void setFieldListValue(List<String> fieldListValue) {
		this.fieldListValue = fieldListValue;
	}



}

package br.com.caelum.grails.stella.constraints

import org.codehaus.groovy.grails.validation.AbstractConstraint 
import org.springframework.validation.Errors 

import br.com.caelum.stella.validation.InvalidStateException 
import br.com.caelum.stella.validation.CNPJValidator 

/** 
* @author Lucas Teixeira
*/
class CNPJConstraint extends AbstractConstraint { 

	public static final String CNPJ_CONSTRAINT = "cnpj" 
	public static final String CNPJ_MESSAGE_CODE = "default.cnpj.message" 
	public static final String CNPJ_NOT_MESSAGE_CODE = "default.invalid.cnpj.message" 	

	Boolean formatted = false 
	Boolean cnpj = true 

	void setParameter(Object constraintParameter) { 
		super.setParameter(constraintParameter) 
		if(constraintParameter instanceof Boolean) { 
			cnpj = (Boolean)constraintParameter 
		} else if(constraintParameter instanceof Map) { 
			formatted = constraintParameter.get('formatted', false) 
			cnpj = constraintParameter.get('cnpj', true) 
		} 
	} 

	public void processValidate(Object target, Object propertyValue, Errors errors) { 

		if(!cnpj) { 
			return; 
		} 
		if(!validateCnpj(propertyValue)) {
			def args = [constraintPropertyName, constraintOwningClass, propertyValue] 
			super.rejectValue( 
				target, 
				errors, 
				CNPJ_MESSAGE_CODE, 
				CNPJ_NOT_MESSAGE_CODE,
				args as Object[])
		} 

	} 

	public String getName() { CNPJ_CONSTRAINT } 

	boolean supports(Class type) { 
		return type != null && String.class.isAssignableFrom(type); 
	} 

	private validateCnpj(cnpj) { 
		def validator = new CNPJValidator(formatted) 
		return validator.invalidMessagesFor(cnpj).empty 
	} 

}
package br.com.caelum.grails.stella.constraints

import org.codehaus.groovy.grails.validation.AbstractConstraint 
import org.springframework.validation.Errors 

import br.com.caelum.stella.validation.InvalidStateException 
import br.com.caelum.stella.validation.NITValidator 

/** 
* @author Lucas Teixeira
*/
class NITConstraint extends AbstractConstraint { 

	public static final String NIT_CONSTRAINT = "nit" 
	public static final String NIT_MESSAGE_CODE = "default.nit.message" 
	public static final String NIT_NOT_MESSAGE_CODE = "default.invalid.nit.message" 	

	Boolean formatted = false 
	Boolean nit = true 

	void setParameter(Object constraintParameter) { 
		super.setParameter(constraintParameter) 
		if(constraintParameter instanceof Boolean) { 
			nit = (Boolean)constraintParameter 
		} else if(constraintParameter instanceof Map) { 
			formatted = constraintParameter.get('formatted', false) 
			nit = constraintParameter.get('nit', true) 
		} 
	} 

	public void processValidate(Object target, Object propertyValue, Errors errors) { 

		if(!nit) { 
			return; 
		} 
		if(!validateNit(propertyValue)) {
			def args = [constraintPropertyName, constraintOwningClass, propertyValue] 
			super.rejectValue( 
				target, 
				errors, 
				NIT_MESSAGE_CODE, 
				NIT_NOT_MESSAGE_CODE,
				args as Object[])
		} 

	} 

	public String getName() { NIT_CONSTRAINT } 

	boolean supports(Class type) { 
		return type != null && String.class.isAssignableFrom(type); 
	} 

	private validateNit(nit) { 
		def validator = new NITValidator(formatted) 
		return validator.invalidMessagesFor(nit).empty 
	} 

}
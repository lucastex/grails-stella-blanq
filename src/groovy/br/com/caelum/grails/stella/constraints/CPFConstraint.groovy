package br.com.caelum.grails.stella.constraints

import org.codehaus.groovy.grails.validation.AbstractConstraint 
import org.springframework.validation.Errors 

import br.com.caelum.stella.validation.InvalidStateException 
import br.com.caelum.stella.validation.CPFValidator 

/** 
* @author Marcos Pereira
* @author Lucas Teixeira
*/
class CPFConstraint extends AbstractConstraint { 

	public static final String CPF_CONSTRAINT = "cpf" 
	public static final String CPF_MESSAGE_CODE = "default.cpf.message" 
	public static final String CPF_NOT_MESSAGE_CODE = "default.invalid.cpf.message" 	

	Boolean formatted = false 
	Boolean cpf = true 

	void setParameter(Object constraintParameter) { 
		super.setParameter(constraintParameter) 
		if(constraintParameter instanceof Boolean) { 
			cpf = (Boolean)constraintParameter 
		} else if(constraintParameter instanceof Map) { 
			formatted = constraintParameter.get('formatted', false) 
			cpf = constraintParameter.get('cpf', true) 
		} 
	} 

	public void processValidate(Object target, Object propertyValue, Errors errors) { 

		if(!cpf) { 
			return; 
		} 
		println validateCpf(propertyValue)
		if(!validateCpf(propertyValue)) {
			def args = [constraintPropertyName, constraintOwningClass, propertyValue] 
			super.rejectValue( 
				target, 
				errors, 
				CPF_MESSAGE_CODE, 
				CPF_NOT_MESSAGE_CODE,
				args as Object[])
		} 

	} 

	public String getName() { CPF_CONSTRAINT } 

	boolean supports(Class type) { 
		return type != null && String.class.isAssignableFrom(type); 
	} 

	private validateCpf(cpf) { 
		def validator = new CPFValidator(formatted) 
		return validator.invalidMessagesFor(cpf).empty 
	} 

}
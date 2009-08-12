import br.com.caelum.grails.stella.constraints.CPFConstraint
import br.com.caelum.grails.stella.constraints.CNPJConstraint
import org.codehaus.groovy.grails.validation.ConstrainedProperty

ConstrainedProperty.registerNewConstraint(CPFConstraint.CPF_CONSTRAINT, CPFConstraint.class)
ConstrainedProperty.registerNewConstraint(CNPJConstraint.CNPJ_CONSTRAINT, CNPJConstraint.class)
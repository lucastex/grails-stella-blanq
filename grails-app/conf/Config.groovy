import br.com.caelum.grails.stella.CPFConstraint
import org.codehaus.groovy.grails.validation.ConstrainedProperty

ConstrainedProperty.registerNewConstraint(CPFConstraint.CPF_CONSTRAINT, CPFConstraint.class)
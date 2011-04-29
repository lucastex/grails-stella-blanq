import br.com.caelum.grails.stella.constraints.CPFConstraint
import br.com.caelum.grails.stella.constraints.CNPJConstraint
import br.com.caelum.grails.stella.constraints.NITConstraint
import org.codehaus.groovy.grails.validation.ConstrainedProperty

class StellaGrailsPlugin {

    def version =  "1.4"
    def grailsVersion = "1.3 > *"
    def dependsOn = [:]
    def pluginExcludes = ["grails-app/views/error.gsp"]

    def author = "Lucas Teixeira"
    def authorEmail = "lucastex@gmail.com"
    def title = "Grails-Stella integration"
    def description = "Grails plugin for integrating Stella (http://stella.caelum.com.br) framework components."
    def documentation = "http://blanq.github.com/grails-stella"

	def license                  = "APACHE"
	def organization             = [  name:   "Blanq", url: "http://github.com/blanq" ]
	def developers               = [[ name:   "Lucas Teixeira", email: "lucastex@gmail.com" ]]
	def scm                      = [  url:    "https://github.com/blanq/grails-stella" ]
	def issueManagement          = [  system: "GITHUB", url: "https://github.com/blanq/grails-stella/issues" ]

    def doWithSpring = {
		ConstrainedProperty.registerNewConstraint(CPFConstraint.CPF_CONSTRAINT, CPFConstraint.class)
		ConstrainedProperty.registerNewConstraint(CNPJConstraint.CNPJ_CONSTRAINT, CNPJConstraint.class)
		ConstrainedProperty.registerNewConstraint(NITConstraint.NIT_CONSTRAINT, NITConstraint.class)
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def doWithWebDescriptor = { xml ->
    }

    def doWithDynamicMethods = { ctx ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }
}

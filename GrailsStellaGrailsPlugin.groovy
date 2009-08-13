import br.com.caelum.grails.stella.constraints.CPFConstraint
import br.com.caelum.grails.stella.constraints.CNPJConstraint
import br.com.caelum.grails.stella.constraints.NITConstraint
import org.codehaus.groovy.grails.validation.ConstrainedProperty

class GrailsStellaGrailsPlugin {
    // the plugin version
    def version = "1.2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Lucas Teixeira"
    def authorEmail = "lucastex@gmail.com"
    def title = "Grails-Stella integration"
    def description = '''\\
Grails plugin for integrating Stella (http://stella.caelum.com.br) framework components.
'''

    // URL to the plugin's documentation
    def documentation = "http://wiki.github.com/lucastex/grails-stella"

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

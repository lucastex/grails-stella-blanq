package groovy.grails.plugin.stella

import br.com.caelum.stella.format.CNPJFormatter
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException

class CNPJFormatterTagLib {
	
	static namespace = "cnpj"
	
	def format = { attrs ->
		def value = attrs.value
		if (!value)
			throw new GrailsTagException("Você deve enviar o valor a ser formatado no atributo 'value' da taglib.")
		
		out << new CNPJFormatter().format(value)
	}
	
	def unformat = { attrs ->
		def value = attrs.value
		if (!value)
			throw new GrailsTagException("Você deve enviar o valor a ser formatado no atributo 'value' da taglib.")
		
		out << new CNPJFormatter().unformat(value)
	}

}

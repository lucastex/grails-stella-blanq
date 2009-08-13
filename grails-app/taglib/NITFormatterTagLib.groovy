import br.com.caelum.stella.format.NITFormatter
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException

class NITFormatterTagLib {
	
	static namespace = "nit"
	
	def format = { attrs ->
		def value = attrs.value
		if (!value)
			throw new GrailsTagException("Você deve enviar o valor a ser formatado no atributo 'value' da taglib.")
		
		return new NITFormatter().format(value)
	}
	
	def unformat = { attrs ->
		def value = attrs.value
		if (!value)
			throw new GrailsTagException("Você deve enviar o valor a ser formatado no atributo 'value' da taglib.")
		
		return new NITFormatter().unformat(value)
	}

}

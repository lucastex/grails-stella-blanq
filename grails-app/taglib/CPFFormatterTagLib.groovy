import br.com.caelum.stella.format.CPFFormatter
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException

class CPFFormatterTagLib {
	
	static namespace = "cpf"
	
	def format = { attrs ->
		def value = attrs.value
		if (!value)
			throw new GrailsTagException("Você deve enviar o valor a ser formatado no atributo 'value' da taglib.")
		
		out << new CPFFormatter().format(value)
	}
	
	def unformat = { attrs ->
		def value = attrs.value
		if (!value)
			throw new GrailsTagException("Você deve enviar o valor a ser formatado no atributo 'value' da taglib.")
		
		out << new CPFFormatter().unformat(value)
	}

}

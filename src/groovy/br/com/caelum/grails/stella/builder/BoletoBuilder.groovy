package br.com.caelum.grails.stella.builder

import br.com.caelum.grails.stella.exception.BoletoException

class BoletoBuilder {
	
	//datas
	def datasVencimento
	def datasDocumento
	def datasProcessamento
	
	//cedente
	def cedenteNome
	def cedenteNrAgencia
	def cedenteDvAgencia
	def cedenteNrConta
	def cedenteDvConta
	def cedenteConvenio
	def cedenteCarteira
	def cedenteOperacao
	def cedenteNrNossoNumero
	def cedenteDvNossoNumero
	
	//sacado
	def sacadoNome
	def sacadoDocumento
	def sacadoEndereco
	def sacadoBairro
	def sacadoCep
	def sacadoCidade
	def sacadoUF
	
	//boleto
	def boletoValor
	def boletoDocumento
	def boletoBancoClassName
	def boletoInstrucoes = []
	def boletoInformacoes = []
	def boletoLocaisPagamento = []
	
	static def bancos = [:]
	
	public BoletoBuilder(BigDecimal valor, String bancoShortName) {
		
		def bancoClassName = bancos[bancoShortName]
		if (!bancoClassName) {
			throw new BoletoException("Banco '${bancoShortName}' não disponível")
		}
		
		if (!valor) {
			throw new BoletoException("Indique o valor do boleto a ser criado")
		}
		
		boletoValor = valor
		boletoBancoClassName = bancoShortName
	}
	
	//closure principal do boleto
	void build(Closure mainClosure) {
		
		if (!mainClosure) {
			throw new BoletoException("Você não informou os dados do boleto. Consulte o guia do plugin para conhecer a maneira correta de se criar boletos.")
		}
		
		mainClosure()
	}
	
	//closure que define as datas do boleto
	void datas(Closure datasClosure) {
				
		if (!datasClosure) {
			throw new BoletoException("Você não informou as datas do boleto. Consulte o guia do plugin para conhecer a maneira correta de se criar boletos.")
		}
		
		datasClosure()
	}

	//closure que define os dados do cedente
	void cedente(Closure cedenteClosure) {
				
		if (!cedenteClosure) {
			throw new BoletoException("Você não informou os dados do cedente do boleto. Consulte o guia do plugin para conhecer a maneira correta de se criar boletos.")
		}
		
		cedenteClosure()
	}

	//closure que define os dados do sacado
	void sacado(Closure sacadoClosure) {
				
		if (!sacadoClosure) {
			throw new BoletoException("Você não informou os dados do sacado do boleto. Consulte o guia do plugin para conhecer a maneira correta de se criar boletos.")
		}
		
		sacadoClosure()
	}
}
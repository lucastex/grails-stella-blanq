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
		mainClosure.delegate = this
		mainClosure()
	}
	
	//closure que define as datas do boleto
	void datas(Closure datasClosure) {
				
		if (!datasClosure) {
			throw new BoletoException("Você não informou as datas do boleto. Consulte o guia do plugin para conhecer a maneira correta de se criar boletos.")
		}
		datasClosure.delegate = this
		datasClosure()
	}

	//closure que define os dados do cedente
	void cedente(Closure cedenteClosure) {
				
		if (!cedenteClosure) {
			throw new BoletoException("Você não informou os dados do cedente do boleto. Consulte o guia do plugin para conhecer a maneira correta de se criar boletos.")
		}
		cedenteClosure.delegate = this
		cedenteClosure()
	}

	//closure que define os dados do sacado
	void sacado(Closure sacadoClosure) {
				
		if (!sacadoClosure) {
			throw new BoletoException("Você não informou os dados do sacado do boleto. Consulte o guia do plugin para conhecer a maneira correta de se criar boletos.")
		}
		sacadoClosure.delegate = this
		sacadoClosure()
	}
	
	//metodos para setar atributos de datas
	void vencimento(Date date)    { datasVencimento    = date }
	void documento(Date date)     { datasDocumento     = date }
	void processamento(Date date) { datasProcessamento = date }
	
	//metodos para setar atributos do cedente
	void cedente(String cedente)         {  cedenteNome     = cedente }
	void convenio(long convenio)         {  cedenteConvenio = convenio }
	void carteira(int carteira)          {  cedenteCarteira = carteira }
	void operacao(int operacao)          {  cedenteOperacao = operacao }
	void agencia(long nr, String dv)     { (cedenteNrAgencia,     cedenteDvAgencia)       = [nr, (char) (dv[0])] }
	void agencia(long nr, int dv)        { (cedenteNrAgencia,     cedenteDvAgencia)       = [nr, (char) "${dv}"] }
	void conta(long nr, String dv)       { (cedenteNrConta,       cedenteDvConta)         = [nr, (char) (dv[0])] }
	void conta(long nr, int dv)          { (cedenteNrConta,       cedenteDvConta)         = [nr, (char) "${dv}"] }
	void nossoNumero(long nr, String dv) { (cedenteNrNossoNumero, cedenteDvNossoNumero)   = [nr, (char) (dv[0])] }
	void nossoNumero(long nr, int dv)    { (cedenteNrNossoNumero, cedenteDvNossoNumero)   = [nr, (char) "${dv}"] }
    
	//metodos para setar atributos do sacado
	void nome(String nome)                {  sacadoNome      = nome }
	void cpf(String cpf)                  {  sacadoDocumento = cpf }
	void cnpj(String cnpj)                {  sacadoDocumento = cnpj }
	void endereco(String endereco)        {  sacadoEndereco  = endereco }
	void bairro(String bairro)            {  sacadoBairro    = bairro }
	void cep(String cep)                  {  sacadoCep       = cep }
	void cidade(String cidade, String uf) { (sacadoCidade, sacadoUF) = [cidade, uf] }	

}
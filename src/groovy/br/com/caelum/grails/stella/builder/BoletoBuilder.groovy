package br.com.caelum.grails.stella.builder

import br.com.caelum.stella.boleto.Datas
import br.com.caelum.stella.boleto.Sacado
import br.com.caelum.stella.boleto.Emissor
import br.com.caelum.stella.boleto.Boleto
import br.com.caelum.stella.boleto.transformer.BoletoGenerator 

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
	def boletoAceite
	def boletoValorMoeda
	def boletoEspecieDocumento
	def boletoNumeroDocumento
	def boletoQuantidadeMoeda
	
	def boletoValor
	def boletoBancoClassName
	List<String> boletoInstrucoes = new ArrayList<String>()
	List<String> boletoInformacoes = new ArrayList<String>()
	List<String> boletoLocaisPagamento = new ArrayList<String>()
	
	//guarda as classes referentes ao bancos implementados
	static def bancos = [:]
	
	//boleto construido
	def boletoFinal
	
	public BoletoBuilder(BigDecimal valor, String bancoShortName) {
		
		def bancoClassName = bancos[bancoShortName.toLowerCase()]
		if (!bancoClassName) {
			throw new BoletoException("Banco '${bancoShortName}' não disponível".toString())
		}
		
		if (!valor) {
			throw new BoletoException("Indique o valor do boleto a ser criado")
		}
		
		boletoValor = valor
		boletoBancoClassName = bancoClassName
	}
	
	//closure principal do boleto
	void build(Closure mainClosure) {
		
		if (!mainClosure) {
			throw new BoletoException("Você não informou os dados do boleto. Consulte o guia do plugin para conhecer a maneira correta de se criar boletos.")
		}
		mainClosure.delegate = this
		mainClosure()
		
		validaCamposObrigatorios()
		
		def datas   = buildDatas()
		def emissor = buildEmissor()
		def sacado  = buildSacado()
		
		boletoFinal  = buildBoleto(datas, emissor, sacado)		
	}
	
	byte[] png() {
		def gerador = new BoletoGenerator(boleto)
        return gerador.toPNG()
	}
	
	byte[] pdf() {
		def gerador = new BoletoGenerator(boleto)
        return gerador.toPDF()		
	}
	
	//closure que define as datas do boleto
	private void datas(Closure datasClosure) {
				
		if (!datasClosure) {
			throw new BoletoException("Você não informou as datas do boleto. Consulte o guia do plugin para conhecer a maneira correta de se criar boletos.")
		}
		datasClosure.delegate = this
		datasClosure()
	}

	//closure que define os dados do cedente
	private void cedente(Closure cedenteClosure) {
				
		if (!cedenteClosure) {
			throw new BoletoException("Você não informou os dados do cedente do boleto. Consulte o guia do plugin para conhecer a maneira correta de se criar boletos.")
		}
		cedenteClosure.delegate = this
		cedenteClosure()
	}

	//closure que define os dados do sacado
	private void sacado(Closure sacadoClosure) {
				
		if (!sacadoClosure) {
			throw new BoletoException("Você não informou os dados do sacado do boleto. Consulte o guia do plugin para conhecer a maneira correta de se criar boletos.")
		}
		sacadoClosure.delegate = this
		sacadoClosure()
	}
	
	//metodos para setar atributos de datas
	private void vencimento(Date date)    { datasVencimento    = date }
	private void documento(Date date)     { datasDocumento     = date }
	private void processamento(Date date) { datasProcessamento = date }
	
	//metodos para setar atributos do cedente
	private void cedente(String cedente)         {  cedenteNome     = cedente }
	private void convenio(long convenio)         {  cedenteConvenio = convenio }
	private void carteira(int carteira)          {  cedenteCarteira = carteira }
	private void operacao(int operacao)          {  cedenteOperacao = operacao }
	private void agencia(int nr, String dv)      { (cedenteNrAgencia,     cedenteDvAgencia)       = [nr, (char) (dv[0])] }
	private void agencia(int nr, int dv)         { (cedenteNrAgencia,     cedenteDvAgencia)       = [nr, (char) "${dv}"] }
	private void conta(long nr, String dv)       { (cedenteNrConta,       cedenteDvConta)         = [nr, (char) (dv[0])] }
	private void conta(long nr, int dv)          { (cedenteNrConta,       cedenteDvConta)         = [nr, (char) "${dv}"] }
	private void nossoNumero(long nr, String dv) { (cedenteNrNossoNumero, cedenteDvNossoNumero)   = [nr, (char) (dv[0])] }
	private void nossoNumero(long nr, int dv)    { (cedenteNrNossoNumero, cedenteDvNossoNumero)   = [nr, (char) "${dv}"] }
    
	//metodos para setar atributos do sacado
	private void nome(String nome)                {  sacadoNome      = nome }
	private void cpf(String cpf)                  {  sacadoDocumento = cpf }
	private void cnpj(String cnpj)                {  sacadoDocumento = cnpj }
	private void endereco(String endereco)        {  sacadoEndereco  = endereco }
	private void bairro(String bairro)            {  sacadoBairro    = bairro }
	private void cep(String cep)                  {  sacadoCep       = cep }
	private void cidade(String cidade, String uf) { (sacadoCidade, sacadoUF) = [cidade, uf] }	
	
	private void aceite(boolean aceite)        { boletoAceite = aceite }
	private void documento(Map documentoProps) { (boletoNumeroDocumento, boletoEspecieDocumento) = [documentoProps['numero'], documentoProps['especie']] }
	private void moeda(Map moedaProps)         { (boletoValorMoeda, boletoQuantidadeMoeda)       = [moedaProps['valor'], moedaProps['quantidade']] }
	
	private void informacoes(String ... info)       { boletoInformacoes.addAll(info) }
	private void instrucoes(String ... instrucoes)  { boletoInstrucoes.addAll(instrucoes) }
	private void pagamento(String localPagamento)   { boletoLocaisPagamento << localPagamento }
	private void pagamento(String locPag1, locPag2) { boletoLocaisPagamento.addAll([locPag1, locPag2]) }
	
	//validacao de campos obrigatorios e cosntraints do boleto
	private void validaCamposObrigatorios() {
		
	}
	
	private def buildDatas() {
		
		def datas = Datas.newDatas()

		if (datasVencimento) {
			def calVencimento = Calendar.instance
			calVencimento.time = datasVencimento
			datas.withVencimento(calVencimento)
		}
		
		if (datasProcessamento) {
			def calProcessamento = Calendar.instance
			calProcessamento.time = datasProcessamento
			datas.withProcessamento(calProcessamento)
		}
		
		if (datasDocumento) {
			def calDocumento = Calendar.instance
			calDocumento.time = datasDocumento		
			datas.withDocumento(calDocumento)		
		}
		
		return datas
	}
	
	private def buildEmissor() {
		
		def emissor = Emissor.newEmissor()
		
		if (cedenteNrAgencia)     { emissor.withAgencia(cedenteNrAgencia) }
		if (cedenteDvAgencia)     { emissor.withDvAgencia(cedenteDvAgencia) }
		if (cedenteNome)          { emissor.withCedente(cedenteNome) }
		if (cedenteNrConta)       { emissor.withContaCorrente(cedenteNrConta) }
		if (cedenteDvConta)       { emissor.withDvContaCorrente(cedenteDvConta) }
		if (cedenteOperacao)      { emissor.withCodOperacao(cedenteOperacao) }
		if (cedenteCarteira)      { emissor.withCarteira(cedenteCarteira) }
		if (cedenteConvenio)      { emissor.withNumConvenio(cedenteConvenio) }
		if (cedenteNrNossoNumero) { emissor.withNossoNumero(cedenteNrNossoNumero) }
		if (cedenteDvNossoNumero) { emissor.withDvNossoNumero(cedenteDvNossoNumero) }
		
		return emissor		
	}
	
	private def buildSacado() {
		
		def sacado = Sacado.newSacado()
		if (sacadoNome)      { sacado.withNome(sacadoNome) }
		if (sacadoDocumento) { sacado.withCpf(sacadoDocumento) }
		if (sacadoEndereco)  { sacado.withEndereco(sacadoEndereco) }
		if (sacadoBairro)    { sacado.withBairro(sacadoBairro) }
		if (sacadoCep)       { sacado.withCep(sacadoCep) }
		if (sacadoCidade)    { sacado.withCidade(sacadoCidade) }
		if (sacadoUF)        { sacado.withUf(sacadoUF) }
				
		return sacado
	}
	
	private def buildBoleto(datas, emissor, sacado) {
		
		def boleto = Boleto.newBoleto()
		boleto.withDatas(datas)
		boleto.withEmissor(emissor)
		boleto.withSacado(sacado)
		
		if (boletoAceite)           { boleto.withAceite(boletoAceite) }
		if (boletoNumeroDocumento)  { boleto.withNoDocumento(boletoNumeroDocumento) }
		if (boletoEspecieDocumento) { boleto.withEspecieDocumento(boletoEspecieDocumento) }
		if (boletoValor)            { boleto.withValorBoleto(boletoValor) }
		if (boletoValorMoeda)       { boleto.withValorMoeda(boletoValorMoeda) }
		if (boletoQuantidadeMoeda)  { boleto.withQtdMoeda(boletoQuantidadeMoeda) }
		if (boletoInformacoes)      { boleto.withDescricoes(boletoInformacoes.toArray(new String[boletoInformacoes.size()])) }
		if (boletoInstrucoes)       { boleto.withInstrucoes(boletoInstrucoes.toArray(new String[boletoInstrucoes.size()])) }
		if (boletoLocaisPagamento)  { boleto.withLocaisDePagamento(boletoLocaisPagamento.toArray(new String[boletoLocaisPagamento.size()])) }
		
		def banco = boletoBancoClassName.newInstance()
		boleto.withBanco(banco)
		
		return boleto
	}
	
	static void registerImplementation(String name, Class implementingClass) {
		bancos[name.toLowerCase()] = implementingClass
	}
	
}
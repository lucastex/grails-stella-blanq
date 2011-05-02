package groovy.grails.plugin.stella

import grails.test.GrailsUnitTestCase
import br.com.caelum.stella.boleto.bancos.Itau
import br.com.caelum.grails.stella.builder.BoletoBuilder

class BoletoBuilderTests extends GrailsUnitTestCase {

    protected void setUp() {
        super.setUp()
		BoletoBuilder.registerImplementation("Itau",     br.com.caelum.stella.boleto.bancos.Itau)
    }

    protected void tearDown() {
        super.tearDown()
		BoletoBuilder.bancos = [:]
    }

	void testCriaBoletoBuilderComSucessoComValorEBanco() {
		
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		assertNotNull boletoBuilder
		assertEquals  1000,   boletoBuilder.boletoValor
		assertEquals  br.com.caelum.stella.boleto.bancos.Itau, boletoBuilder.boletoBancoClassName
	}

    void testErroEmCasoDeBancoInexistente() {
		shouldFail {
			new BoletoBuilder(1000, "REAL")
		}
    }

    void testErroEmCasoDeNaoInformarValor() {
		shouldFail {
			new BoletoBuilder(null, "ITAU")
		}
    }

	void testErroSeNaoRepassarClosureDoBoleto() {
		
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		shouldFail {
			boletoBuilder.build()
		}
	}
	
	void testErroSeNaoRepassarClosureDeDatas() {
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		shouldFail {
			boletoBuilder.build {
				datas()
				cedente {
					println "hey ho!"
				}
				sacado {
					println "hey ho!"
				}
			}
		}
	}

	void testErroSeNaoRepassarClosureDeCedente() {
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		shouldFail {
			boletoBuilder.build {
				datas {
					println "hey ho!"
				}
				cedente()
				sacado {
					println "hey ho!"
				}
			}
		}
	}

	void testErroSeNaoRepassarClosureDeSacado() {
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		shouldFail {
			boletoBuilder.build {
				datas {
					println "hey ho!"
				}
				cedente {
					println "hey ho!"
				}
				sacado()
			}
		}
	}
	
	void testAtribuicaoDeValoresDasDatas() {

		//datas
		def dataDeVencimento    = new Date() + 5
		def dataDoDocumento     = new Date()
		def dataDoProcessamento = new Date()

		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		boletoBuilder.build {
			datas {
				vencimento    dataDeVencimento
				documento     dataDoDocumento
				processamento dataDoProcessamento
			}
			cedente {
				println "hey ho!"
			}
			sacado {
				println "hey ho!"
			}
		}
		
		assertEquals dataDeVencimento,    boletoBuilder.datasVencimento
		assertEquals dataDoDocumento,     boletoBuilder.datasDocumento
		assertEquals dataDoProcessamento, boletoBuilder.datasProcessamento
	}
	
	void testAtribuicaoDeValoresCedenteComDvTipoString() {
		
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		boletoBuilder.build {
			datas {
				println "hey ho!"
			}
			cedente {
				cedente     "Lucas"
				convenio    100
				carteira    200
				operacao    300
				agencia     400, 'x'
				conta       500, 'y'
				nossoNumero 600, 'z'
			}
			sacado {
				println "hey ho!"
			}
		}
		
		assertEquals "Lucas", boletoBuilder.cedenteNome
		assertEquals 100, boletoBuilder.cedenteConvenio
		assertEquals 200, boletoBuilder.cedenteCarteira
		assertEquals 300, boletoBuilder.cedenteOperacao
		
		assertEquals 400, boletoBuilder.cedenteNrAgencia
		assertEquals 'x', boletoBuilder.cedenteDvAgencia		
		assertEquals java.lang.Character, boletoBuilder.cedenteDvAgencia.getClass()
		
		assertEquals 500, boletoBuilder.cedenteNrConta
		assertEquals 'y', boletoBuilder.cedenteDvConta		
		assertEquals java.lang.Character, boletoBuilder.cedenteDvConta.getClass()
		
		assertEquals 600, boletoBuilder.cedenteNrNossoNumero
		assertEquals 'z', boletoBuilder.cedenteDvNossoNumero
		assertEquals java.lang.Character, boletoBuilder.cedenteDvNossoNumero.getClass()
	}	

	void testAtribuicaoDeValoresCedenteComDvTipoNumero() {
		
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		boletoBuilder.build {
			datas {
				println "hey ho!"
			}
			cedente {
				cedente     "Lucas"
				convenio    100
				carteira    200
				operacao    300
				agencia     400, 1
				conta       500, 2
				nossoNumero 600, 3
			}
			sacado {
				println "hey ho!"
			}
		}
		
		assertEquals "Lucas", boletoBuilder.cedenteNome
		assertEquals 100, boletoBuilder.cedenteConvenio
		assertEquals 200, boletoBuilder.cedenteCarteira
		assertEquals 300, boletoBuilder.cedenteOperacao
		
		assertEquals 400, boletoBuilder.cedenteNrAgencia
		assertEquals '1', boletoBuilder.cedenteDvAgencia		
		assertEquals java.lang.Character, boletoBuilder.cedenteDvAgencia.getClass()
		
		assertEquals 500, boletoBuilder.cedenteNrConta
		assertEquals '2', boletoBuilder.cedenteDvConta		
		assertEquals java.lang.Character, boletoBuilder.cedenteDvConta.getClass()
		
		assertEquals 600, boletoBuilder.cedenteNrNossoNumero
		assertEquals '3', boletoBuilder.cedenteDvNossoNumero
		assertEquals java.lang.Character, boletoBuilder.cedenteDvNossoNumero.getClass()
	}	
	
	void testAtribuicaoDeValoresSacadoComCPF() {
		
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		boletoBuilder.build {
			datas {
				println "hey ho!"
			}
			cedente {
				println "hey ho!"
			}
			sacado {
				nome     "Lucas"
				cpf      "000.000.000-00"
				endereco "Rua xxx, 123"
				bairro   "Centro"
				cep      "00000-000"
				cidade   "Sao Paulo", "SP"
			}
		}
		
		assertEquals "Lucas",          boletoBuilder.sacadoNome
		assertEquals "000.000.000-00", boletoBuilder.sacadoDocumento
		assertEquals "Rua xxx, 123",   boletoBuilder.sacadoEndereco
		assertEquals "Centro",         boletoBuilder.sacadoBairro
		assertEquals "00000-000",      boletoBuilder.sacadoCep
		assertEquals "Sao Paulo",      boletoBuilder.sacadoCidade
		assertEquals "SP",             boletoBuilder.sacadoUF
	}

	void testAtribuicaoDeValoresSacadoComCNPJ() {
		
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		boletoBuilder.build {
			datas {
				println "hey ho!"
			}
			cedente {
				println "hey ho!"
			}
			sacado {
				nome     "Lucas"
				cnpj     "00.000.000/0000-00"
				endereco "Rua xxx, 123"
				bairro   "Centro"
				cep      "00000-000"
				cidade   "Sao Paulo", "SP"
			}
		}
		
		assertEquals "Lucas",              boletoBuilder.sacadoNome
		assertEquals "00.000.000/0000-00", boletoBuilder.sacadoDocumento
		assertEquals "Rua xxx, 123",       boletoBuilder.sacadoEndereco
		assertEquals "Centro",             boletoBuilder.sacadoBairro
		assertEquals "00000-000",          boletoBuilder.sacadoCep
		assertEquals "Sao Paulo",          boletoBuilder.sacadoCidade
		assertEquals "SP",                 boletoBuilder.sacadoUF
	}
	
	void testAceite() {
		
		def random = new Random()
		def valorAceite = random.nextBoolean()
		
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		boletoBuilder.build {
			datas   { println "hey ho!" }
			cedente { println "hey ho!" }
			sacado  { println "hey ho!" }
			aceite valorAceite
		}
		
		assertEquals valorAceite, boletoBuilder.boletoAceite
	}
	
	void testDocumento() {
		
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		boletoBuilder.build {
			datas   { println "hey ho!" }
			cedente { println "hey ho!" }
			sacado  { println "hey ho!" }
			documento numero: "1234", especie: "R\$"
		}
		
		assertEquals "1234", boletoBuilder.boletoNumeroDocumento
		assertEquals "R\$", boletoBuilder.boletoEspecieDocumento
	}
	
	void testMoeda() {
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		boletoBuilder.build {
			datas   { println "hey ho!" }
			cedente { println "hey ho!" }
			sacado  { println "hey ho!" }
			moeda valor: 1234, quantidade: 1000
		}
		
		assertEquals 1234, boletoBuilder.boletoValorMoeda
		assertEquals 1000, boletoBuilder.boletoQuantidadeMoeda
	}
	
	void testInformacoes() {
		
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		boletoBuilder.build {
			datas   { println "hey ho!" }
			cedente { println "hey ho!" }
			sacado  { println "hey ho!" }
			informacoes "info1", "info2", "info3"
		}
		
		assertEquals 3, boletoBuilder.boletoInformacoes.size()
		assertEquals "info1", boletoBuilder.boletoInformacoes[0]
		assertEquals "info2", boletoBuilder.boletoInformacoes[1]
		assertEquals "info3", boletoBuilder.boletoInformacoes[2]
	}

	void testInstrucoes() {
		
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		boletoBuilder.build {
			datas   { println "hey ho!" }
			cedente { println "hey ho!" }
			sacado  { println "hey ho!" }
			instrucoes "instrucao 1", "instrucao 2", "instrucao 3", "instrucao 4"
		}
		
		assertEquals 4, boletoBuilder.boletoInstrucoes.size()
		assertEquals "instrucao 1", boletoBuilder.boletoInstrucoes[0]
		assertEquals "instrucao 2", boletoBuilder.boletoInstrucoes[1]
		assertEquals "instrucao 3", boletoBuilder.boletoInstrucoes[2]
		assertEquals "instrucao 4", boletoBuilder.boletoInstrucoes[3]
	}
	
	void testLocalPagamentoUmLocal() {
		
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		boletoBuilder.build {
			datas   { println "hey ho!" }
			cedente { println "hey ho!" }
			sacado  { println "hey ho!" }
			pagamento "Pagável em qualquer agência bancária até o vencimento"
		}
		
		assertEquals 1, boletoBuilder.boletoLocaisPagamento.size()
		assertEquals "Pagável em qualquer agência bancária até o vencimento", boletoBuilder.boletoLocaisPagamento[0]
	}

	void testLocalPagamentoDoisLocais() {
		
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		boletoBuilder.build {
			datas   { println "hey ho!" }
			cedente { println "hey ho!" }
			sacado  { println "hey ho!" }
			pagamento "Pagável em qualquer agência bancária até o vencimento", "Não receber após vencimento"
		}
		
		assertEquals 2, boletoBuilder.boletoLocaisPagamento.size()
		assertEquals "Pagável em qualquer agência bancária até o vencimento", boletoBuilder.boletoLocaisPagamento[0]
		assertEquals "Não receber após vencimento", boletoBuilder.boletoLocaisPagamento[1]
	}
}
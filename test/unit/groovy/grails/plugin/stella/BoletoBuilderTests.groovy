package groovy.grails.plugin.stella

import grails.test.GrailsUnitTestCase
import br.com.caelum.stella.boleto.bancos.Itau
import br.com.caelum.grails.stella.builder.BoletoBuilder

class BoletoBuilderTests extends GrailsUnitTestCase {

    protected void setUp() {
        super.setUp()
		BoletoBuilder.bancos = ["ITAU": br.com.caelum.grails.stella.builder.BoletoBuilder]
    }

    protected void tearDown() {
        super.tearDown()
		BoletoBuilder.bancos = [:]
    }

	void testCriaBoletoBuilderComSucessoComValorEBanco() {
		
		def boletoBuilder = new BoletoBuilder(1000, "ITAU")
		assertNotNull boletoBuilder
		assertEquals  "ITAU", boletoBuilder.boletoBancoClassName
		assertEquals  1000,   boletoBuilder.boletoValor
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

}

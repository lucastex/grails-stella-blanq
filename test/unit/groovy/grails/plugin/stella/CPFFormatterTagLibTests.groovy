package groovy.grails.plugin.stella

import grails.test.*
import groovy.grails.plugin.stella.CPFFormatterTagLib

class CPFFormatterTagLibTests extends TagLibUnitTestCase {
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testFormat() {
		def taglib = new CPFFormatterTagLib()
		assertEquals taglib.format(value: "04297060906").toString(), "042.970.609-06"
    }

    void testUnformat() {
		def taglib = new CPFFormatterTagLib()
		assertEquals taglib.unformat(value: "042.970.609-06").toString(), "04297060906"
    }
}

package groovy.grails.plugin.stella

import grails.test.*
import groovy.grails.plugin.stella.NITFormatterTagLib

class NITFormatterTagLibTests extends TagLibUnitTestCase {
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testFormat() {
		def taglib = new NITFormatterTagLib()
		assertEquals taglib.format(value: "11660700498").toString(), "116.60700.49-8"
    }

    void testUnformat() {
		def taglib = new NITFormatterTagLib()
		assertEquals taglib.unformat(value: "116.60700.49-8").toString(), "11660700498"
    }
}

import grails.test.*

class CPFFormatterTagLibTests extends TagLibUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testFormat() {
		CPFFormatterTagLib taglib = new CPFFormatterTagLib()
		assertEquals taglib.format(value: "04297060906").toString(), "042.970.609-06"
    }

    void testUnformat() {
		CPFFormatterTagLib taglib = new CPFFormatterTagLib()
		assertEquals taglib.unformat(value: "042.970.609-06").toString(), "04297060906"
    }
}

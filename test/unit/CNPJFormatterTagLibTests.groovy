import grails.test.*

class CNPJFormatterTagLibTests extends TagLibUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testFormat() {
		CNPJFormatterTagLib taglib = new CNPJFormatterTagLib()
		assertEquals taglib.format(value: "78425986003615").toString(), "78.425.986/0036-15"
    }

    void testUnformat() {
		CNPJFormatterTagLib taglib = new CNPJFormatterTagLib()
		assertEquals taglib.unformat(value: "78.425.986/0036-15").toString(), "78425986003615"
    }
}

import grails.test.*

class NITFormatterTagLibTests extends TagLibUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testFormat() {
		NITFormatterTagLib taglib = new NITFormatterTagLib()
		assertEquals taglib.format(value: "11660700498"), "116.60700.49-8"
    }

    void testUnformat() {
		NITFormatterTagLib taglib = new NITFormatterTagLib()
		assertEquals taglib.unformat(value: "116.60700.49-8"), "11660700498"
    }
}

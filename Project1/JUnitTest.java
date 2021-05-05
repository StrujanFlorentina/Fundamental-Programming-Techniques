package PT2020.demo.DemoProject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JUnitTest {

	private static CalcModel m = new CalcModel();
	private static int nrTesteExecutate = 0;
	private static int nrTesteCuSucces = 0;

	public JUnitTest() {
		System.out.println("Constructor inaintea fiecarui test!");
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("O singura data inaintea executiei setului de teste din clasa!");
		m = new CalcModel();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("O singura data dupa terminarea executiei setului de teste din clasa!");
		System.out.println(
				"S-au executat " + nrTesteExecutate + " teste din care " + nrTesteCuSucces + " au avut succes!");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("Incepe un nou test!");
		nrTesteExecutate++;
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("S-a terminat testul curent!");
	}

	@Test
	public void testResetGetValue() {
		m.reset();
		String t = m.getValue();
		assertNotNull(t);
		assertEquals(t, "0");
		nrTesteCuSucces++;
	}

	@Test
	public void testAdunare() {
		m.reset();
		m.adunare("-2x^1+5x^0", "3x^1+2x^0");
		String t = m.getValue();
		assertNotNull(t);
		assertEquals(t, "+x+7.0");
		nrTesteCuSucces++;
	}

	@Test
	public void testScadere() {
		m.reset();
		m.scadere("2x^1+5x^0", "-3x^1+2x^0");
		String t = m.getValue();
		assertEquals(t, "+5.0x+3.0");
		nrTesteCuSucces++;
	}

	@Test
	public void testInmultire() {
		m.reset();
		m.inmultire("2x^1+5x^0", "-1x^0");
		String t = m.getValue();
		assertEquals(t, "-2.0x^1-5.0");
		nrTesteCuSucces++;
	}

	@Test
	public void testDerivare() {
		m.reset();
		m.derivare("5x^2-2x^1");
		String t = m.getValue();
		assertEquals(t, "+10.0x-2.0");
		nrTesteCuSucces++;
	}

	@Test
	public void testIntegrare() {
		m.reset();
		m.integrare("3x^2-2x^1+5x^0");
		String t = m.getValue();
		assertEquals(t, "+x^3-x^2+5.0x");
		nrTesteCuSucces++;
	}

	@Test
	public void testSetValue1() {
		m.setValue("50x^0");
		String t = m.getValue();
		assertNotNull(t); // verifica t sa nu fie null
		assertSame(t, "50x^0"); // verifica referinta lui t sa fie identica cu referinta lui "50"
		nrTesteCuSucces++;
	}

	@Test
	public void testSetValue2() {
		m.setValue("50x^0");
		String t = m.getValue();
		assertNotNull(t); // verifica t sa nu fie null
		assertEquals(t, "50x^0"); // verifica continutul lui t sa fie identic cu "50"
		nrTesteCuSucces++;
	}

}

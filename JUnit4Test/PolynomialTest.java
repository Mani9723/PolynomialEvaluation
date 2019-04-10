import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * PolynomialEvaluation
 *
 * @author Mani Shah
 * Date:  12/30/2018
 * Time:  19:57
 * @since 1.0
 */
public class PolynomialTest
{
	private Polynomial polynomial;
	private Polynomial polynomial1;
	private Polynomial result;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void initObjects()
	{
		polynomial = null;
		polynomial1 = null;
		result = null;
	}

	@Test
	public void testEmptyPoly()
	{
		thrown.expect(PolynomialFormatError.class);
		thrown.expectMessage("Empty Polynomial");
		polynomial = new Polynomial("");
	}

	@Test
	public void testInvalidCharacter()
	{
		String poly = "3x^e";
		thrown.expect(PolynomialFormatError.class);
		thrown.expectMessage("Invalid Polynomial: " + poly);
		polynomial = new Polynomial(poly);
	}

	@Test
	public void testInvalidCharacter1()
	{
		String poly = "3x^4*qwe";
		thrown.expect(PolynomialFormatError.class);
		thrown.expectMessage("Invalid Polynomial: " + poly);
		polynomial = new Polynomial(poly);
	}

	@Ignore
	@Test
	public void testInvalidCharacter2()
	{
		String poly = "3x^-b";
		thrown.expect(PolynomialFormatError.class);
		thrown.expectMessage("Invalid Polynomial: " + poly);
		polynomial = new Polynomial(poly);
	}

	@Test
	public void testInvalidCharacter3()
	{
		String poly = "3x^1 ] qwe";
		thrown.expect(PolynomialFormatError.class);
		thrown.expectMessage("Invalid Polynomial: " + poly);
		polynomial = new Polynomial(poly);
	}

	@Test
	public void testPoly1()
	{
		String poly = "2x^3+1234-4x^98";
		polynomial = new Polynomial(poly);
		assertEquals("-4x^98+2x^3+1234",polynomial.toString());
	}
	@Test
	public void testPoly2()
	{
		String poly = "5x^12-2x^6+x^5-198x+1";
		polynomial = new Polynomial(poly);
		assertEquals(poly,polynomial.toString());

	}


	@Test
	public void testPoly3()
	{
		String poly = "x^3-x^5-x^2-x^6-1";
		polynomial = new Polynomial(poly);
		assertEquals("-x^6-x^5+x^3-x^2-1",polynomial.toString());

	}
	@Test
	public void testPoly4()
	{
		String poly = "12x^3+123-33x^5";
		polynomial = new Polynomial(poly);
		assertEquals("-33x^5+12x^3+123",polynomial.toString());


	}
	@Test
	public void testPoly5()
	{
		String poly = "-4x^98";
		polynomial = new Polynomial(poly);
		assertEquals(poly,polynomial.toString());

	}
	@Test
	public void testPoly6()
	{
		String poly = "2x";
		polynomial = new Polynomial(poly);
		assertEquals(poly,polynomial.toString());

	}

	@Test
	public void testPolyWithSpaces()
	{
		String poly = "2x^3 - 4x+x- 23";
		polynomial = new Polynomial(poly);
		assertEquals("2x^3+x-4x-23",polynomial.toString());

	}
	@Test
	public void testPolyWithSpaces1()
	{
		String poly = "2x^3 - 4x + x - 23";
		polynomial = new Polynomial(poly);
		assertEquals("2x^3+x-4x-23",polynomial.toString());

	}
	@Ignore
	@Test
	public void testGetTerm1()
	{
		String poly = "-x^2+23x+6x^2";
		polynomial = new Polynomial(poly);
		//	assertEquals(6,polynomial.getTerm(0).coeff);

	}
	@Ignore
	@Test
	public void testGetTerm2()
	{
		String poly = "-x^2+23x+6x^4";
		polynomial = new Polynomial(poly);
		//	assertEquals(2,polynomial.getTerm(1));

	}
	@Ignore
	@Test
	public void testGetTerm3()
	{
		String poly = "-x^2+23x+6x^2+33";
		polynomial = new Polynomial(poly);
		//	assertEquals(0,polynomial.getTerm(3).expo);

	}
	@Ignore
	@Test
	public void testGetTerm4()
	{
		String poly = "-x^2+23x+6x^2+33";
		polynomial= new Polynomial(poly);
		//	assertEquals("",polynomial.getTerm(3).var);

	}

	@Test
	public void testInvalidGetTerm()
	{
		polynomial = new Polynomial("3x^2");
		thrown.expect(IndexOutOfBoundsException.class);
		thrown.expectMessage("Invalid Term");
		polynomial.getTerm(34);
	}

	@Test
	public void testInvalidGetTerm1()
	{
		polynomial = new Polynomial("3x^2");
		thrown.expect(IndexOutOfBoundsException.class);
		thrown.expectMessage("Invalid Term");
		polynomial.getTerm(-34);
	}

	@Test
	public void testDegree()
	{
		String poly = "2x-45x^3+x^56";
		polynomial = new Polynomial(poly);
		assertEquals(56,polynomial.getDegree());

	}

	@Test
	public void testDegree1()
	{
		String poly = "2x-45";
		polynomial = new Polynomial(poly);
		assertEquals(1,polynomial.getDegree());

	}

	@Test
	public void testDegree2()
	{
		String poly = "34";
		polynomial = new Polynomial(poly);
		assertEquals(0,polynomial.getDegree());

	}

	@Test
	public void testPolyType()
	{
		assertEquals("Linear Monomial",new Polynomial("3x").getPolynomialType());
	}

	@Test
	public void testPolyType1()
	{
		assertEquals("A 11th degree Polynomial",
				new Polynomial("3x^11+34x-12x^3+9x^4+65").getPolynomialType());
	}

	@Test
	public void testPolyType2()
	{
		assertEquals("Quintic Polynomial",
				new Polynomial("3x^5+34x-12x^3+9x^4+65").getPolynomialType());
	}

	@Test
	public void testPolyType3()
	{
		assertEquals("A 15th degree Trinomial",
				new Polynomial("3x^15+34x-12x^3").getPolynomialType());
	}

	@Test
	public void testPolyType4()
	{
		assertEquals("Octic Binomial",
				new Polynomial("4x^8+4").getPolynomialType());
	}
	@Test
	public void testPolyType5()
	{
		assertEquals("Nonic Monomial",
				new Polynomial("4x^9").getPolynomialType());
	}
	@Test
	public void testPolyType6()
	{
		assertEquals("Cubic Polynomial",
				new Polynomial("4x^3+4x+5x^2+45").getPolynomialType());
	}
	@Test
	public void testPolyType7()
	{
		assertEquals("Sextic Binomial",
				new Polynomial("4x^6+4").getPolynomialType());
	}
	@Test
	public void testPolyType8()
	{
		assertEquals("Constant Monomial",
				new Polynomial("4").getPolynomialType());
	}

	@Test
	public void testAdd1()
	{
		String poly1 = "5x^5+3x^2+4x+8";
		String poly2 = "6x^8+4x^7+9x^3+11x^2+2x+18";
		polynomial = new Polynomial(poly1);
		polynomial1 = new Polynomial(poly2);
		result = polynomial.add(polynomial1);
		assertEquals("6x^8+4x^7+5x^5+9x^3+14x^2+6x+26",result.toString());
	}

	@Test
	public void testAdd2()
	{
		String poly1 = "5x^5+3x^2+4x+8";
		String poly2 = "2x^8+9x^6+3x^5+4x^3+8x^2+7";
		polynomial = new Polynomial(poly1);
		polynomial1 = new Polynomial(poly2);
		result = polynomial.add(polynomial1);
		assertEquals("2x^8+9x^6+8x^5+4x^3+11x^2+4x+15",result.toString());
	}

	@Test
	public void testAdd6()
	{
		String poly2 = "5x^5+3x^2+4x+8";
		String poly1 = "2x^8+9x^6+3x^5+4x^3+8x^2+7";
		polynomial = new Polynomial(poly2);
		polynomial1 = new Polynomial(poly1);
		result = polynomial.add(polynomial1);
		assertEquals("2x^8+9x^6+8x^5+4x^3+11x^2+4x+15",result.toString());
	}

	@Test
	public void testAdd3()
	{
		String poly = "3x^3 - 5x + 9";
		String poly1 = "6x^3 + 8x - 7";
		polynomial = new Polynomial(poly);
		polynomial1 = new Polynomial(poly1);
		result = polynomial.add(polynomial1);
		assertEquals("9x^3+3x+2",result.toString());
	}

	@Test
	public void testAdd4()
	{
		String poly = "3x^3 + 5x + 9";
		String poly1 = "6x^3 - 8x - 7";
		polynomial = new Polynomial(poly);
		polynomial1 = new Polynomial(poly1);
		result = polynomial.add(polynomial1);
		assertEquals("9x^3-3x+2",result.toString());
	}

	@Test
	public void testAdd5()
	{
		String poly = "3x^3 - 5x - 9";
		String poly1 = "-6x^3 + 8x - 7";
		polynomial = new Polynomial(poly);
		polynomial1 = new Polynomial(poly1);
		result = polynomial.add(polynomial1);
		assertEquals("-3x^3+3x-16",result.toString());
	}

	@Test
	public void testAdd7()
	{
		String poly = "3x^3";
		String poly1 = "-6x^3 + 8x - 7";
		polynomial = new Polynomial(poly1);
		polynomial1 = new Polynomial(poly);
		result = polynomial.add(polynomial1);
		assertEquals("-3x^3+8x-7",result.toString());
	}

	@Test
	public void testAdd8()
	{
		String poly = "1";
		String poly1 = "-6x^3 + 8x - 7";
		polynomial = new Polynomial(poly1);
		polynomial1 = new Polynomial(poly);
		result = polynomial.add(polynomial1);
		assertEquals("-6x^3+8x-6",result.toString());
	}

	@Test
	public void testAdd9()
	{
		String poly = "1";
		String poly1 = "-x^3 + x - 7";
		polynomial = new Polynomial(poly1);
		polynomial1 = new Polynomial(poly);
		result = polynomial.add(polynomial1);
		assertEquals("-x^3+x-6",result.toString());
	}


	@Test
	public void testSubtract()
	{
		String poly = "6x^3+7x^2+9";
		String poly1 = "3x^3+4x^2+5";
		polynomial = new Polynomial(poly);
		polynomial1 = new Polynomial(poly1);
		result = polynomial.subtract(polynomial1);
		assertEquals("3x^3+3x^2+4",result.toString());
	}

	@Test
	public void testSubtract1()
	{
		String poly = "-x^3-x^5+34";
		String poly2 = "-x";
		polynomial = new Polynomial(poly);
		polynomial1 = new Polynomial(poly2);
		result = polynomial.subtract(polynomial1);
		assertEquals("-x^5-x^3+x+34", result.toString());
	}

	@Test
	public void testSubtract2()
	{
		String  poly = "-x^3-x^5+34";
		String poly2 = "-x^5+x^2-8x^3-34";
		polynomial = new Polynomial(poly);
		polynomial1 = new Polynomial(poly2);
		result = polynomial.subtract(polynomial1);
		assertEquals("7x^3-x^2+68",result.toString());
	}

	@Test
	public void testSubtract3()
	{
		polynomial = new Polynomial("-21x+34");
		polynomial1 = new Polynomial("21x-34");
		result = polynomial.subtract(polynomial1);
		assertEquals("-42x+68",result.toString());
	}

	@Test
	public void testSubtract4()
	{
		polynomial = new Polynomial("-21x-34");
		polynomial1 = new Polynomial("21x-34");
		result = polynomial.subtract(polynomial1);
		assertEquals("-42x",result.toString());
	}

	@Test
	public void testSubtract5()
	{
		polynomial = new Polynomial("21x-34");
		polynomial1 = new Polynomial("21x-34");
		result = polynomial.subtract(polynomial1);
		assertEquals("0",result.toString());
	}

	@Test
	public void testSubtraction6()
	{
		polynomial = new Polynomial("-1");
		polynomial1 = new Polynomial("1");
		assertEquals("-2",
				polynomial.subtract(polynomial1).toString());
	}

	@Test
	public void testSubtraction7()
	{
		polynomial = new Polynomial("1");
		polynomial1 = new Polynomial("1");
		assertEquals("0",
				polynomial.subtract(polynomial1).toString());
	}

	@Test
	public void testSubtraction8()
	{
		polynomial = new Polynomial("1");
		polynomial1 = new Polynomial("34x^7+2x^9");
		assertEquals("-2x^9-34x^7+1",
				polynomial.subtract(polynomial1).toString());
	}

	@Test
	public void testGetTerms()
	{
		polynomial = new Polynomial("3x^5+9x+23x^3+9");
		assertEquals(4,polynomial.getTerms());
	}

	@Test
	public void testMultiplication()
	{
		polynomial = new Polynomial("3x^3+2x^2+4x+8");
		polynomial1 = new Polynomial("3x^3+2x^2+4x+8");
		assertEquals("9x^6+12x^5+28x^4+64x^3+48x^2+64x+64",polynomial.multiply(polynomial1).toString());
	}

	@Test
	public void testMultipliation1()
	{
		polynomial = new Polynomial("3x^56+23x^2+5");
		polynomial1 = new Polynomial("32x^2");
		assertEquals("96x^58+736x^4+160x^2",polynomial.multiply(polynomial1).toString());
	}

	@Test
	public void testMultipliation2()
	{
		polynomial = new Polynomial("3x^56+23x^2+5");
		polynomial1 = new Polynomial("1");
		assertEquals(polynomial.toString(),polynomial.multiply(polynomial1).toString());
	}

	@Test
	public void testMultipliation3()
	{
		polynomial = new Polynomial("3x^56+23x^2+5");
		polynomial1 = new Polynomial("-1");
		assertEquals("-3x^56-23x^2-5",polynomial.multiply(polynomial1).toString());
	}

	@Test
	public void testMultipliation4()
	{
		polynomial1 = new Polynomial("3x^56+23x^2+5");
		polynomial = new Polynomial("-1");
		assertEquals("-3x^56-23x^2-5",polynomial.multiply(polynomial1).toString());
	}

	@Test
	public void testMultipliation5()
	{
		polynomial1 = new Polynomial("3x^56+23x^2+5");
		polynomial = new Polynomial("1");
		assertEquals("3x^56+23x^2+5",polynomial.multiply(polynomial1).toString());
	}

	@Test
	public void testMultipliation6()
	{
		polynomial1 = new Polynomial("3x^56+23x^2+5");
		polynomial = new Polynomial("0");
		assertEquals("0",polynomial.multiply(polynomial1).toString());
	}

	@Test
	public void testEquality()
	{
		polynomial = new Polynomial("3x^4+9x");
		polynomial1 = new Polynomial("9x+3x^4");
		assertTrue(polynomial.isEqual(polynomial1));
	}

	@Test
	public void testLessThan()
	{
		polynomial = new Polynomial("34x^4+2x^65");
		polynomial1 = new Polynomial("2xil^4+3x^23");
		assertTrue(polynomial1.isLessThan(polynomial));
	}
	@Test
	public void testGreaterThan()
	{
		polynomial = new Polynomial("34x^4+2x^65");
		polynomial1 = new Polynomial("2xil^4+3x^23");
		assertTrue(polynomial.isGreaterThan(polynomial1));
	}

	@After
	public void deleteList()
	{
		if(polynomial!=null) polynomial.deletePolynomial();
		if(polynomial1!=null) polynomial1.deletePolynomial();
		if(result!=null) result.deletePolynomial();
	}
}
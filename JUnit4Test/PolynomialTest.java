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

	@Test
	public void testGetTerm1()
	{
		String poly = "-x^2+23x+6x^2";
		polynomial = new Polynomial(poly);
		assertEquals(6,polynomial.getTerm(0).coeff);

	}
	@Test
	public void testGetTerm2()
	{
		String poly = "-x^2+23x+6x^4";
		polynomial = new Polynomial(poly);
		assertEquals(2,polynomial.getTerm(1).expo);

	}
	@Test
	public void testGetTerm3()
	{
		String poly = "-x^2+23x+6x^2+33";
		polynomial = new Polynomial(poly);
		assertEquals(0,polynomial.getTerm(3).expo);

	}
	@Test
	public void testGetTerm4()
	{
		String poly = "-x^2+23x+6x^2+33";
		polynomial= new Polynomial(poly);
		assertEquals("",polynomial.getTerm(3).var);

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
		assertEquals("-x^5-x^3-x+34", result.toString());
	}

	@Test
	public void testSubtract2()
	{
		String  poly = "-x^3-x^5+34";
		String poly2 = "-x^5+x^2-8x^3-34";
		polynomial = new Polynomial(poly);
		polynomial1 = new Polynomial(poly2);
		result = polynomial.subtract(polynomial1);
		assertEquals("7x^3+x^2+68",result.toString());
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


	@After
	public void deleteList()
	{
		if(polynomial!=null) polynomial.deletePolynomial();
		if(polynomial1!=null) polynomial1.deletePolynomial();
		if(result!=null) result.deletePolynomial();
	}
}
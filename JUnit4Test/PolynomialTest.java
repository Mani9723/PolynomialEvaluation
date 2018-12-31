import org.junit.Test;

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

	@Test
	public void testPoly1()
	{
		String poly = "2x^3+1234+4x^98";
		polynomial = new Polynomial(poly);
		assertEquals(poly,polynomial.toString());
		polynomial.deleteList();
	}
	@Test
	public void testPoly2()
	{
		String poly = "5x^12-2x^6+x^5-198x+1";
		polynomial = new Polynomial(poly);
		assertEquals(poly,polynomial.toString());
		polynomial.deleteList();
	}
	@Test
	public void testPoly3()
	{
		String poly = "x^4-x^3-x^2-x-1";
		polynomial = new Polynomial(poly);
		assertEquals(poly,polynomial.toString());
		polynomial.deleteList();
	}
	@Test
	public void testPoly4()
	{
		String poly = "12x^3+123-33x^5";
		polynomial = new Polynomial(poly);
		assertEquals(poly,polynomial.toString());
		polynomial.deleteList();

	}
	@Test
	public void testPoly5()
	{
		String poly = "-4x^98";
		polynomial = new Polynomial(poly);
		assertEquals(poly,polynomial.toString());
		polynomial.deleteList();
	}
	@Test
	public void testPoly6()
	{
		String poly = "2x";
		polynomial = new Polynomial(poly);
		assertEquals(poly,polynomial.toString());
		polynomial.deleteList();
	}

	@Test
	public void testGetTerm1()
	{
		String poly = "-x^2+23x+6x^2";
		polynomial = new Polynomial(poly);
		assertEquals(-1,polynomial.getTerm(0).coeff);
		polynomial.deleteList();
	}
	@Test
	public void testGetTerm2()
	{
		String poly = "-x^2+23x+6x^2";
		polynomial = new Polynomial(poly);
		assertEquals(1,polynomial.getTerm(1).expo);
		polynomial.deleteList();
	}
	@Test
	public void testGetTerm3()
	{
		String poly = "-x^2+23x+6x^2+33";
		polynomial = new Polynomial(poly);
		assertEquals(1,polynomial.getTerm(3).expo);
		polynomial.deleteList();
	}
	@Test
	public void testGetTerm4()
	{
		String poly = "-x^2+23x+6x^2+33";
		polynomial = new Polynomial(poly);
		assertNull(null,polynomial.getTerm(3).var);
		polynomial.deleteList();
	}
}
/**
 * PolynomialEvaluation
 *
 * This interface defines
 *
 * @author Mani Shah
 * Date:  12/25/2018
 * Time:  19:00
 * @since 1.0
 */
public interface PolynomialInterface
{

	/**
	 * Defines the Add method for the polynomial
	 * @param other Second Polynomial
	 * @return Sum of POlynomials
	 */
	Polynomial add(Polynomial other);

	/**
	 * Defines the subtract method for the polynomial
	 * @param other Second Polynomial
	 * @return Difference of Polynomials
	 */
	Polynomial subtract(Polynomial other);


}

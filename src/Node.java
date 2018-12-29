/**
 * PolynomialEvaluation
 *
 * @author Mani Shah
 * Date:  12/25/2018
 * Time:  19:25
 * @since 1.0
 */

@SuppressWarnings("WeakerAccess")
class Node
{
	protected int coeff;
	protected int expo;
	protected String var;


	protected Node next;

	Node(int coeff, int expo, String var)
	{
		this.coeff = coeff;
		this.expo = expo;
		this.var = var;
	}
}
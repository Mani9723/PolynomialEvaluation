/**
 * PolynomialEvaluation
 *
 * @author Mani Shah
 * Date:  12/25/2018
 * Time:  19:25
 * @since 1.0
 */

class Node
{
	protected int coeff;
	protected int expo;
	protected char var;

	protected Node next;

	Node(int coeff, int expo, char var){
		this.coeff = coeff;
		this.expo = expo;
		this.var = var;
	}
}
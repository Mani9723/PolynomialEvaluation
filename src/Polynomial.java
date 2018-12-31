/**
 * PolynomialEvaluation
 *
 * @author Mani Shah
 * Date:  12/25/2018
 * Time:  19:00
 * @since 1.0
 */

@SuppressWarnings("WeakerAccess")
public class Polynomial implements PolynomialInterface
{
	private Node head;
	private Node tail;
	private int degree;
	private int terms;

	public Polynomial(String poly)
	{
		if(poly.equals("")) {
			throw new PolynomialFormatError("Invalid Polynomial: "+poly);
		}
		degree = 0;
		storePoly(poly);
	}

	@SuppressWarnings("StringConcatenationInLoop")
	private void storePoly(String poly)
	{
		boolean sign = false, expo = false;
		String coeff = "", expon = "", var = "";
		for(int i = 0; i < poly.length(); i++){
			if(Character.isDigit(poly.charAt(i))){
				if(expo) expon += poly.charAt(i);
				else coeff += poly.charAt(i);
				continue;
			}if(Character.isLetter(poly.charAt(i))){
				var = String.valueOf(poly.charAt(i));
				continue;
			}if(poly.charAt(i) == '^'){
				expo = true;
				continue;
			}if(poly.charAt(i) == '-'){
				if(i > 0){
					createTerm(coeff,var,expon,sign);
					coeff = ""; expon = ""; var = "";
				}
				sign = true; expo = false;
				continue;
			}if(poly.charAt(i) == '+'){
				if(i > 0){
					createTerm(coeff,var,expon,sign);
					coeff = ""; expon = ""; var = "";
				}
				expo = false; sign = false;
			}
		}
		createTerm(coeff,var,expon,sign);
	}

	private void createTerm(String coeff, String var, String expo, boolean sign)
	{
		int ex;
		int co;
		if(coeff.equals("")){
			co = 0;
			if(sign) co = -1;
		} else {
			co = Integer.parseInt(coeff);
			if (sign) co *= -1;
		}
		if(expo.equals("")) ex = 1;
		else ex = Integer.parseInt(expo);
		if(var.equals("")) var = null;
		insertNode(new Node(co,ex,var));
		degree = Math.max(degree,ex);
		terms++;
	}

	private void insertNode(Node temp)
	{
		if(temp == null)
			throw new NullPointerException("Null element");

		if(head == null){
			this.head = this.tail = temp;
		}else{
			this.tail.next = temp;
			this.tail = temp;
		}
	}

	public Node getTerm(int index)
	{
		if(index >= terms || index < 0){
			throw new IndexOutOfBoundsException("Invalid Term");
		}
		Node curr = head;
		int i = 0;
		while(curr != null){
			if(i == index) return curr;
			i++; curr = curr.next;
		}
		return null;
	}

	@Override
	public Polynomial addPolynomials(Polynomial other)
	{
		return null;
	}

	@Override
	public Polynomial subPolynomials(Polynomial other)
	{
		return null;
	}

	public void deleteList()
	{
		head = tail = null;
		System.out.println("List deleted");
	}

	@Override
	public String toString()
	{
		StringBuilder poly = new StringBuilder();
		Node curr = head;
		while(curr != null){
			if(curr.coeff != 0) {
				if (curr.coeff == -1 && curr.var != null) poly.append("-");
				else poly.append(curr.coeff);
			}
			if(curr.var != null) poly.append(curr.var);
			if(curr.expo != 0) poly.append("^").append(curr.expo);
			curr = curr.next;
			if(curr != null && (curr.coeff >= 0)) poly.append("+");
		}
		return poly.toString();
	}

	static class Node
	{
		protected final int coeff;
		protected final int expo;
		protected final String var;


		private Node next;

		private Node(int co, int ex, String vr)
		{
			this.coeff = co;
			this.expo = ex;
			this.var = vr;
		}
	}
}

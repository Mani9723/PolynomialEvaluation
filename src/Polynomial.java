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
	private int degree;
	private int terms;
	private String orgPolynomial;

	public Polynomial(String poly)
	{
		if(poly.equals("")) {
			throw new PolynomialFormatError("Invalid Polynomial: "+poly);
		}
		degree = 0;
		readPoly(poly.replaceAll(" ",""));
		this.orgPolynomial = toString();
	}

	@SuppressWarnings("StringConcatenationInLoop")
	private void readPoly(String poly)
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
			}else
				throw new PolynomialFormatError("Invalid Polynomial: " + poly);
		}
		createTerm(coeff,var,expon,sign);
	}

	private void createTerm(String coeff, String var, String expo, boolean sign)
	{
		int ex;
		int co;
		if(coeff.equals("")){
			co = 1;
			if(sign) co = -1;
		} else {
			co = Integer.parseInt(coeff);
			if (sign) co *= -1;
		}
		if(expo.equals("")){
			if(var.equals("")) ex = 0;
			else ex = 1;
		}
		else ex = Integer.parseInt(expo);
		if(var.equals("")) var = "";
		insertNode(new Node(co,ex,var));
		this.degree = Math.max(this.degree,ex);
		this.terms++;
	}

	private void insertNode(Node temp)
	{
		Node curr = this.head;
		Node prev = null;

		if(this.head == null){
			this.head = temp;
		}else{
			if(curr.expo <= temp.expo){
				temp.next = curr;
				this.head = temp;
			}else if(curr.next == null){
				temp.next = null;
				curr.next = temp;
			}else{
				while(curr != null) {
					if(temp.expo >= curr.expo) break;
					prev = curr;
					curr = curr.next;
				}
				prev.next = temp;
				temp.next = curr;
			}
		}
	}

	public Node getTerm(int index)
	{
		if(index >= this.terms || index < 0){
			throw new IndexOutOfBoundsException("Invalid Term");
		}
		Node curr = this.head;
		int i = 0;
		while(curr != null){
			if(i == index) return curr;
			i++; curr = curr.next;
		}
		return null;
	}

	public int getDegree()
	{
		return degree;
	}

	@Override
	public Polynomial addPolynomials(Polynomial other)
	{
		Node first,second;
		int len = Math.min(this.terms,other.terms);
		if(len == this.terms){
			first = this.head;
			second = other.head;
		}else{
			first = other.head;
			second = this.head;
		}
		return getSum(len,other,first,second);
	}

	private Polynomial getSum(int len, Polynomial other, Node...nodes)
	{
		Node first = nodes[0], fCurr = first;
		Node second = nodes[1], sCurr = second;
		StringBuilder answer = new StringBuilder();
		while(fCurr != null && sCurr != null){
			if(sCurr.expo > fCurr.expo){
				answer.append(sCurr);
			}
		}



		return new Polynomial(answer.toString());
	}

	@Override
	public Polynomial subPolynomials(Polynomial other)
	{
		return null;
	}

	public void deletePolynomial()
	{
		head = null;
		System.out.println("List deleted");
	}

	private String termToString(Node term)
	{
		String ans = "";
		if(term.coeff != 0) {
			if (term.coeff == -1 && term.var != ""){
				ans += "-";
			}
			else if(term.var != "" && term.coeff == 1){
				ans += "";
			}
			else{ ans += term.coeff;}
		}
		if(term.var != ""){ans += term.var;}
		if(term.expo != 1 && term.expo != 0){
			ans+= "^"; ans+=term.expo;
		}
		return ans;
	}


	@Override
	public String toString()
	{
		StringBuilder poly = new StringBuilder();
		Node curr = head;
		while(curr != null){
			if(curr.coeff != 0) {
				if (curr.coeff == -1 && curr.var != "") poly.append("-");
				else if(curr.var != "" && curr.coeff == 1) poly.append("");
				else poly.append(curr.coeff);
			}
			if(curr.var != "") poly.append(curr.var);
			if(curr.expo != 1 && curr.expo != 0) poly.append("^").append(curr.expo);
			curr = curr.next;
			if(curr != null && (curr.coeff >= 0)) poly.append("+");
		}
		return poly.toString();
	}

	protected static class Node
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

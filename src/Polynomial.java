import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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

	private String originalPolynomial;

	private String[] multiVars;
	private static String[] polyByTerms;
	private static String[] polyByDegree;

	private LinkedHashMap<Pair, Integer> termPairs;

	private Polynomial()
	{
		polyByTerms = new String[]{"Monomial", "Binomial", "Trinomial", "Polynomial"};
		polyByDegree = new String[]{"Constant", "Linear", "Quadratic", "Cubic",
				"Quartic", "Quintic", "Sextic", "Septic", "Octic", "Nonic", "Decic"};
		this.termPairs = new LinkedHashMap<>();
		this.degree = 0;
	}

	public Polynomial(String poly)
	{
		this();
		if (poly.equals(""))
			throw new PolynomialFormatError("Empty Polynomial");
		this.originalPolynomial = poly;
		parsePolynomial(poly.replaceAll(" ", ""));
	}

	public Polynomial(String poly, String...variables)
	{
		this(poly);
		multiVars = new String[variables.length];
		System.arraycopy(variables,0,multiVars,0,variables.length);
	}

	@SuppressWarnings("StringConcatenationInLoop")
	private void parsePolynomial(String poly)
	{
		boolean sign = false, expo = false;
		String coeff = "", expon = "", var = "";
		for(int i = 0; i < poly.length(); i++){
			if(Character.isDigit(poly.charAt(i))){
				if(expo) expon += poly.charAt(i);
				else coeff += poly.charAt(i);
				continue;
			}if(Character.isLetter(poly.charAt(i)) && !expo){
				var = String.valueOf(poly.charAt(i));
				continue;
			}if(poly.charAt(i) == '^'){
				expo = true; continue;
			}if(poly.charAt(i) == '-'){
				if(i > 0){
					createTerm(coeff,var,expon,sign);
					coeff = ""; expon = ""; var = "";
				}
				sign = true; expo = false; continue;
			}if(poly.charAt(i) == '+'){
				if(i > 0){
					createTerm(coeff,var,expon,sign);
					coeff = ""; expon = ""; var = "";
				}
				expo = false; sign = false;
			}else
				throw new PolynomialFormatError("Invalid Polynomial: " + originalPolynomial);
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
			if(co == 0) return;
			if (sign) co *= -1;
		}if(expo.equals("")){
			if(var.equals("")) ex = 0;
			else ex = 1;
		} else
			ex = Integer.parseInt(expo);
		if(var.equals(""))
			var = "";
		insertSortedNode(new Node(co,ex,var));
		this.degree = Math.max(this.degree,ex);
		this.terms++;
	}

	private void insertSortedNode(Node temp)
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

	@SuppressWarnings({"unused", "UnusedReturnValue"})
	public Node getTerm(int index)
	{
		if(index >= this.terms || index < 0)
			throw new IndexOutOfBoundsException("Invalid Term");
		Node curr = this.head;
		int i = 0;
		while(curr != null){
			if(i == index) break;
			i++; curr = curr.next;
		}
		return curr;
	}

	public int getDegree()
	{
		return this.degree;
	}

	public int getTerms()
	{
		return this.terms;
	}

	@Override
	public Polynomial add(Polynomial other)
	{
		preparePolys(other);
		Polynomial result = calcSum();
		clearLinkedHashMap();
		return result;
	}

	@Override
	public Polynomial subtract(Polynomial other)
	{
		if(isEqual(other)) return new Polynomial("0");
		other.negatePolynomial();
		populateHashMap(this,other);
		Polynomial difference = calcDiff();
		clearLinkedHashMap();
		return difference;
	}

	@Override
	public Polynomial multiply(Polynomial other)
	{
		Polynomial product;

		if(this.originalPolynomial.equals("1")) return other;
		else if(other.originalPolynomial.equals("1")) return this;
		else if(this.originalPolynomial.equals("0") || other.originalPolynomial.equals("0"))
			return new Polynomial("0");
		else {
			product = this.terms <= other.terms ? calcProduct(this, other)
					: calcProduct(other, this);
			return simplify(product);
		}
	}

	private void clearLinkedHashMap()
	{
		termPairs.clear();
	}

	private Polynomial calcProduct(Polynomial multiplier,Polynomial multiplicand)
	{
		Polynomial product = new Polynomial();
		Node shortPoly = multiplier.head;
		Node longPoly = multiplicand.head;

		product.insertSortedNode(calcTermProduct(shortPoly,longPoly));
		longPoly = longPoly.next;

		while(shortPoly != null){
			while(longPoly != null){
				product.insertSortedNode(calcTermProduct(shortPoly,longPoly));
				longPoly = longPoly.next;
			}
			longPoly = multiplicand.head;
			shortPoly = shortPoly.next;
		}
		return product;
	}

	private Node calcTermProduct(Node termOne, Node termTwo)
	{
		int coeff;
		int expo;
		coeff = termOne.coeff*termTwo.coeff;
		expo = termOne.expo+termTwo.expo;
		return new Node(coeff,expo,getVarForMultiply(termOne,termTwo));
	}

	private String getVarForMultiply(Node first, Node second)
	{
		if(first.var.equals("") && !second.var.equals(""))
			return second.var;
		else if(!first.var.equals("") && second.var.equals(""))
			return first.var;
		else if(first.var.equals(second.var))
			return first.var;
		else return "";
	}

	private Polynomial simplify(Polynomial polynomial)
	{
		return groupLikeTerms(polynomial);
	}

	public void simplify()
	{
		this.head = simplify(this).head;
	}

	private Polynomial groupLikeTerms(Polynomial polynomial)
	{
		Polynomial answer = new Polynomial();
		Node mainNode = polynomial.head;
		Node tempNode = null;
		HashMap<Integer,Node> similarTerms = new HashMap<>(polynomial.terms);

		while(mainNode != null){
			tempNode = mainNode;
			mainNode = mainNode.next;
			tempNode.next = null;
			if(similarTerms.containsKey(tempNode.expo)) {
				tempNode.coeff += similarTerms.get(tempNode.expo).coeff;
				similarTerms.put(tempNode.expo,tempNode);
			}
			else
				similarTerms.put(tempNode.expo, tempNode);
			if(mainNode!= null && (tempNode.expo != mainNode.expo))
				answer.insertSortedNode(tempNode);
		}
		answer.insertSortedNode(tempNode);
		return answer;
	}

	private void preparePolys(Polynomial other)
	{
		boolean first = this.terms <= other.terms;
		if(first) populateHashMap(this,other);
		else populateHashMap(other,this);
	}

	private Polynomial calcSum()
	{
		Polynomial sum = new Polynomial();
		int coefSum;
		Node addend, auguend;

		for(Map.Entry<Pair,Integer> pairs : termPairs.entrySet()){
			addend = pairs.getKey().a;
			auguend = pairs.getKey().b;
			if(auguend == null){
				sum.insertSortedNode(new Node(addend.coeff,addend.expo,addend.var));
			}else{
				coefSum = addend.coeff + auguend.coeff;
				if(coefSum != 0)
					sum.insertSortedNode(new Node(coefSum, addend.expo, addend.var));
			}
		}
		return sum;
	}

	@Deprecated
	@SuppressWarnings("unused")
	private void appendSign(Node node, StringBuilder result)
	{
		if(result.length() != 0 && node.coeff >= 0) result.append("+");
	}

	private void populateHashMap(Polynomial one, Polynomial two)
	{
		Node firstCurr, secCurr;
		firstCurr = one.head;
		secCurr = two.head;

		while(firstCurr != null){
			if(secCurr == null)
				break;
			if(firstCurr.expo < secCurr.expo){
				termPairs.put(new Pair(secCurr,null),secCurr.expo);
				secCurr = secCurr.next;
			}else if(firstCurr.expo > secCurr.expo) {
				termPairs.put(new Pair(firstCurr,null),firstCurr.expo);
				firstCurr = firstCurr.next;
			}else{
				if(firstCurr.var.equals(secCurr.var)) {
					termPairs.put(new Pair(firstCurr, secCurr), firstCurr.expo);
				}else{
					termPairs.put(new Pair(firstCurr,null),firstCurr.expo);
					termPairs.put(new Pair(secCurr,null),secCurr.expo);
				}
				firstCurr = firstCurr.next;
				secCurr = secCurr.next;
			}
		}//add remaining terms
		while(firstCurr != null){
			termPairs.put(new Pair(firstCurr,null),firstCurr.expo);
			firstCurr = firstCurr.next;
		}
		while(secCurr != null){
			termPairs.put(new Pair(secCurr,null),secCurr.expo);
			secCurr = secCurr.next;
		}
		//THis method is still not ready for work. FIX THIS YOU RETARD
	}

	private void negatePolynomial()
	{
		Node mainHead = head;
		Node curr;

		curr = mainHead;
		while (curr != null){
			if(curr.coeff != 0)
				curr.coeff = curr.coeff * -1;
			curr = curr.next;
		}
	}

	private Polynomial calcDiff()
	{
		return calcSum();
	}

	public String getPolynomialType()
	{
		String type;
		if(this.degree > 10 && this.terms > 4){
			type = "A " + this.degree +"th degree Polynomial";
		}else if(this.degree < 10 && this.terms > 4 ){
			type = polyByDegree[this.degree] + " " + polyByTerms[3];
		}else if(this.degree > 10){
			type = "A " + this.degree + "th degree " + polyByTerms[this.terms-1];
		} else
			type = polyByDegree[this.degree] + " " + polyByTerms[this.terms-1];
		return type;
	}

	public double evaluate(double value)
	{
		double result = 0;
		Node currNode = this.head;

		while(currNode != null){
			result += currNode.coeff*(Math.pow(value,currNode.expo));
			currNode = currNode.next;
		}
		return result;
	}

	public void deletePolynomial()
	{
		this.head = null;
		System.out.println("List deleted");
	}

	@Deprecated
	@SuppressWarnings("unused")
	private String termToString(Node term)
	{
		if(term == null)
			return null;
		String ans = "";
		if(term.coeff != 0) {
			if (term.coeff == -1 && !term.var.equals(""))
				ans += "-";
			else if(!term.var.equals("") && term.coeff == 1)
				ans += "";
			else{ ans += term.coeff;}
		}
		if(!term.var.equals("")){ans += term.var;}
		if(term.expo != 1 && term.expo != 0)
			ans += "^" + term.expo;
		return ans;
	}

	public boolean isEqual(Polynomial other)
	{
		return this.toString().equals(other.toString());
	}

	public boolean isLessThan(Polynomial other)
	{
		return this.degree < other.degree;
	}

	public boolean isGreaterThan(Polynomial other)
	{
		return this.degree > other.degree;
	}

	@SuppressWarnings("StringOperationCanBeSimplified")
	@Override
	public String toString()
	{
		StringBuilder poly = new StringBuilder();
		Node curr = head;
		while(curr != null){
			if(curr.coeff != 0) {
				if (curr.coeff == -1 && !curr.var.equals("")) poly.append("-");
				else if(!curr.var.equals("") && curr.coeff == 1) poly.append("");
				else poly.append(curr.coeff);
			}
			if(!curr.var.equals("")) poly.append(curr.var);
			if(curr.expo != 1 && curr.expo != 0) poly.append("^").append(curr.expo);
			curr = curr.next;
			if(curr != null && (curr.coeff >= 0)) poly.append("+");
		}
		return head == null ? "0" : poly.toString();
	}

	private static class Node
	{
		private int coeff;
		private int expo;
		private String var;

		private Node next;

		private Node(int co, int ex, String vr)
		{
			this.coeff = co;
			this.expo = ex;
			this.var = vr;
		}
	}

	private static class Pair
	{
		private Node a;
		private Node b;
		private Pair(Node first, Node second){
			a = first;
			b = second;
		}
	}
}
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

	private LinkedHashMap<Pair,Integer> termPairs;

	public Polynomial(String poly)
	{
		if(poly.equals("")) {
			throw new PolynomialFormatError("Invalid Polynomial: "+poly);
		}
		degree = 0;
		readPoly(poly.replaceAll(" ",""));
		termPairs = new LinkedHashMap<>();
	}

	@SuppressWarnings("unused")
	private Polynomial(Node newHead)
	{
		this.head = newHead;
		this.degree = newHead.expo;
		//this.terms = termPairs.size();
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
				var = String.valueOf(poly.charAt(i)); continue;
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
				throw new PolynomialFormatError("Invalid Polynomial: " + poly.charAt(i));
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
		}if(expo.equals("")){
			if(var.equals("")) ex = 0;
			else ex = 1;
		} else ex = Integer.parseInt(expo);
		if(var.equals("")) var = "";
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
	public Polynomial add(Polynomial other)
	{
		preparePolys(other);
		Polynomial result = calcSum();
		termPairs.clear();
		return result;
	}

	@Override
	public Polynomial subtract(Polynomial other)
	{
		preparePolys(other);
		Polynomial difference = calcDiff();
		termPairs.clear();
		return difference;
	}


	private void preparePolys(Polynomial other)
	{
		boolean first = this.terms <= other.terms;
		if(first) populateHashMap(this,other);
		else populateHashMap(other,this);
	}

	private Polynomial calcSum()
	{
		Polynomial sum;
		Node addend, auguend;
		StringBuilder result = new StringBuilder();

		for(Map.Entry<Pair,Integer> pairs : termPairs.entrySet()){
			addend = pairs.getKey().a;
			auguend = pairs.getKey().b;
			if(addend == null){
				appendSign(auguend,result);
				result.append(termToString(auguend));
			}else if(auguend == null){
				appendSign(addend,result);
				result.append(termToString(addend));
			}else{
				int coefSum = addend.coeff + auguend.coeff;
				if(result.length() != 0 && coefSum >= 0) result.append("+");
				result.append(coefSum).append(addend.var)
						.append("^").append(addend.expo);
			}
		}
		sum = new Polynomial(result.toString());
		return sum;
	}

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
			if(firstCurr.expo < secCurr.expo){
				termPairs.put(new Pair(secCurr,null),secCurr.expo);
				secCurr = secCurr.next;
			}else if(firstCurr.expo > secCurr.expo) {
				termPairs.put(new Pair(firstCurr,null),firstCurr.expo);
				firstCurr = firstCurr.next;
			}else{
				termPairs.put(new Pair(firstCurr,secCurr),firstCurr.expo);
				firstCurr = firstCurr.next;
				secCurr = secCurr.next;
			}
		}
		while(secCurr != null){
			termPairs.put(new Pair(secCurr,null),secCurr.expo);
			secCurr = secCurr.next;
		}
	}

	private Polynomial calcDiff()
	{
		Polynomial sum;
		Node subFirst, subSecond;
		StringBuilder result = new StringBuilder();

		for (Map.Entry<Pair, Integer> pairs : termPairs.entrySet()) {
			subFirst = pairs.getKey().a;
			subSecond = pairs.getKey().b;
			if (subFirst == null) {
				appendSign(subSecond, result);
				result.append(termToString(subSecond));
			} else if (subSecond == null) {
				appendSign(subFirst, result);
				result.append(termToString(subFirst));
			} else {
				int coefSum = subFirst.coeff - subSecond.coeff;
				if (result.length() != 0 && coefSum >= 0) result.append("+");
				result.append(coefSum).append(subFirst.var).append("^")
						.append(subFirst.expo);
			}
		}
		sum = new Polynomial(result.toString());
		return sum;
	}

	public void deletePolynomial()
	{
		this.head = null;
		System.out.println("List deleted");
	}


	private String termToString(Node term)
	{
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
			ans+= "^"; ans+=term.expo;
		return ans;
	}


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

	private static class Pair
	{
		Node a;
		Node b;
		private Pair(Node first, Node second){
			a = first;
			b = second;
		}
	}
}

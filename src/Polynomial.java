
/**
 * PolynomialEvaluation
 *
 * @author Mani Shah
 * Date:  12/25/2018
 * Time:  19:00
 * @since 1.0
 */

public class Polynomial implements PolynomialInterface
{
	private Node head;
	private Node tail;
	private String poly;

	public Polynomial(String poly)
	{
		this.poly = poly;
		storePoly();
		printList();
		deleteList();
	}

	@SuppressWarnings("Duplicates")
	private void storePoly()
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
				if(i > 0 && (Character.isLetter(poly.charAt(i-1))
						|| expo || Character.isDigit(poly.charAt(i-1)))){
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
				expo = false;
				sign = false;
			}
		}
		createTerm(coeff,var,expon,sign);
	}

	private void createTerm(String coeff, String var, String expo, boolean sign)
	{
		int ex;
		int co = Integer.parseInt(coeff);
		if(sign) co *= -1;
		if(expo.equals("")) ex = 0;
		else ex = Integer.parseInt(expo);
		if(var.equals("")) var = null;
		insertNode(new Node(co,ex,var));

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

	private void printList()
	{
		Node curr = head;
		while(curr != null){
			System.out.print(curr.coeff);
			if(curr.var != null)
				System.out.print(curr.var);
				if(curr.expo != 0)
					System.out.print("^"+curr.expo);
			curr = curr.next;
			if(curr != null && curr.coeff > 0) System.out.print("+");
		}
		System.out.println();
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

	private void deleteList()
	{
		head = tail = null;
		System.out.println("List deleted");
	}


}

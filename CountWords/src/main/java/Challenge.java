import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.*;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;
import java.util.stream.Stream;
import static java.lang.System.*;


 enum MembershipLevel {
	PREMIUM, ENTERPRISE, COMMUNITY;
}
public class Challenge {	
	public static void main(String[] args) {
		String st="" + (char)1;
	    List<String> ls=Arrays.asList("{","a","0","A",st);
	    Collections.sort(ls);
	     ;
	    ls.stream().flatMap(s-> s.chars().mapToObj(i->""+(char)i)).forEach(System.out::println);
		System.out.println( Character.MIN_VALUE);
	    System.out.println( (char)('z'+1));		
	    System.out.println("" + Character.MIN_VALUE);
	    System.out.println("char max. value: " + (int)Character.MAX_VALUE);
		Comparator <Customer> compByFirst=comparing(c-> c.firstName,String.CASE_INSENSITIVE_ORDER);
	    Comparator <Customer> compByLast=comparing(c->c.lastName,String.CASE_INSENSITIVE_ORDER);		    
 	    Comparator <Customer> compByLevel=comparingInt((Customer c)-> c.level).reversed();	    
	    Comparator <Customer> compByName=compByFirst.thenComparing(compByLast);
	    Comparator <Customer> compBy=compByLevel.thenComparing(compByName);
	    Stream<Customer> customers ,customers2;
	    Customer cu2=new Customer("Q","e",1);
	    Customer cu0=new Customer("A","z",2,cu2);
	    Customer cu1=new Customer("B","b",3,cu2);	
	    Customer cu3=new Customer("R","e",2);
	    Customer cu4=new Customer("C","z",1,cu3);
	    Customer cu5=new Customer("D","b",2,cu3);	    
	    Customer cu6=new Customer("S","g",3);
	    customers=Stream.of(cu0,cu1,cu2,cu3,cu4,cu5,cu6);
	   customers2= customers.map(c->{if(c.optParent.isPresent()&&c.optParent.get().level<c.level)
		                                c.optParent.get().level=c.level;
	                                     return c;})			
			   .collect(toList()).stream();			   
	   customers2
	    .parallel().unordered()
	    .map(c->{if(c.optParent.isPresent())c.level=c.optParent.get().level;return c;})	           
	        .collect(groupingBy( (Customer c)->{Customer nc = c.optParent.orElse(c);return nc;},
	        		()->new TreeMap<Customer,TreeSet<Customer>>(compBy),
	        		toCollection(()->new TreeSet<Customer>(compByName))
	        		 ))      
	  
	        .entrySet().stream().map(m->{m.getValue().remove(m.getKey());return m;}).forEach(m->{System.out.println(m.getKey());
	        m.getValue().forEach(e->{ System.out.println(e);});});  	        	  
			}
}

class Customer
{
  String firstName;
  String lastName;
  int level;
  Optional<Customer> optParent=Optional.empty();
public Customer(String firstName, String lastName, int level) {
	super();
	this.firstName = firstName;
	this.lastName = lastName;
	this.level = level;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public int getLevel() {
	return level;
}
public void setLevel(int level) {
	this.level = level;
}
public Optional<Customer> getOptParent() {
	return optParent;
}
public void setOptParent(Optional<Customer> optParent) {
	this.optParent = optParent;
}
@Override
public String toString() {
	return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", level=" + level + "]";
}
public Customer(String firstName, String lastName, int level,  Customer parent) {
	super();
	this.firstName = firstName;
	this.lastName = lastName;
	this.level = level;
	this.optParent = Optional.of(parent);
}
  
  
}